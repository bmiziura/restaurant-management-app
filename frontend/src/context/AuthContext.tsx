import {
  ReactNode,
  createContext,
  useContext,
  useEffect,
  useState,
} from "react"

export type Role = "ADMIN" | "RESTAURANT_OWNER" | "USER"

export type User = {
  id: number
  email: string
  firstName?: string
  lastName?: string
  roles: Role[]
  activated: boolean
}

type AuthContextProps = {
  user?: User
  loginUser: (email: string, password: string) => void
  registerUser: (email: string, password: string) => void
  confirmAccount: (email: string, token: string) => void
  retryConfirmationEmail: () => void
  logoutUser: () => void
  isLoading: boolean
}

const AuthContext = createContext<AuthContextProps | null>(null)

export default function useAuth() {
  const authContext = useContext(AuthContext)
  if (authContext == null) {
    throw new Error("You cannot use this inside this component!")
  }

  return authContext
}

export function AuthContextProvider({ children }: { children: ReactNode }) {
  const [user, setUser] = useState<User | undefined>()
  const [isLoading, setLoading] = useState<boolean>(true)
  const [refetch, setRefetch] = useState<boolean>(true)

  useEffect(() => {
    if (!refetch) return

    setRefetch(false)

    async function fetchUser() {
      const res = await fetch("/api/auth/me", {
        credentials: "include",
      })

      if (res.ok) {
        const user = await res.json()

        setUser(user)
      } else {
        setUser(undefined)
      }

      setLoading(false)
    }

    fetchUser()
  }, [refetch])

  async function loginUser(email: string, password: string) {
    const res = await fetch("/api/auth/login", {
      method: "POST",
      body: JSON.stringify({
        email,
        password,
      }),
      credentials: "include",
      headers: {
        "Content-Type": "application/json",
      },
    })

    if (res.ok) {
      setRefetch(true)
    }
  }

  async function registerUser(email: string, password: string) {
    const res = await fetch("/api/auth/register", {
      method: "POST",
      body: JSON.stringify({
        email,
        password,
      }),
      credentials: "include",
      headers: {
        "Content-Type": "application/json",
      },
    })

    if (res.ok) {
      setRefetch(true)
    }
  }

  async function confirmAccount(email: string, token: string) {
    const res = await fetch(
      `/api/auth/token/activate?email=${email}&token=${token}`,
      {
        method: "POST",
        credentials: "include",
      },
    )

    if (res.ok) {
      setRefetch(true)
    } else {
      throw new Error("Token jest nieprawidłowy lub konto jest już aktywne!")
    }
  }

  async function retryConfirmationEmail() {
    const res = await fetch(`/api/auth/token/retry`, {
      method: "POST",
      credentials: "include",
    })

    if (!res.ok) {
      switch (res.status) {
        case 400: {
          setRefetch(true)
          break
        }
        case 429:
          throw new Error(
            "Osiągnięto limit wysłanych wiadomości! Spróbuj ponownie za kilka minut!",
          )
      }
    }
  }

  async function logoutUser() {
    const res = await fetch("/api/auth/logout", {
      method: "POST",
      credentials: "include",
    })

    if (res.ok) {
      setRefetch(true)
    }
  }

  const value: AuthContextProps = {
    user,
    loginUser,
    registerUser,
    confirmAccount,
    retryConfirmationEmail,
    logoutUser,
    isLoading,
  }

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>
}
