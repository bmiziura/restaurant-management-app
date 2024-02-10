import {
  ReactNode,
  createContext,
  useContext,
  useEffect,
  useState,
} from "react"

import { User } from "@/api/types.ts"
import {
  activateUser,
  getCurrentUser,
  loginUser as authLoginUser,
  registerUser as authRegisterUser,
  sendActivationEmail,
  logoutUser as authLogoutUser,
} from "@/api/auth.ts"

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
      try {
        const user = await getCurrentUser()
        setUser(user)
      } catch (err: any) {
        setUser(undefined)
      }

      setLoading(false)
    }

    fetchUser()
  }, [refetch])

  async function loginUser(email: string, password: string) {
    const res = await authLoginUser(email, password)

    if (res.ok) {
      setRefetch(true)
    }
  }

  async function registerUser(email: string, password: string) {
    const res = await authRegisterUser(email, password)

    if (res.ok) {
      setRefetch(true)
    }
  }

  async function confirmAccount(email: string, token: string) {
    const res = await activateUser(email, token)

    if (res.ok) {
      setRefetch(true)
    } else {
      throw new Error("Token jest nieprawidłowy lub konto jest już aktywne!")
    }
  }

  async function retryConfirmationEmail() {
    const res = await sendActivationEmail()

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
    const res = await authLogoutUser()

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
