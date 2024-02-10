import { Button } from "@/components/ui/button.tsx"
import useAuth from "@/context/AuthContext.tsx"
import { useState } from "react"
import { Link, Navigate } from "react-router-dom"
import { cn } from "@/lib/utils.ts"
import { Input } from "@/components/ui/input.tsx"

export type Error = {
  type: "NO_MATCH" | "MIN_LENGTH"
  message: string
}

function RegisterPage() {
  const { user, registerUser } = useAuth()
  const [error, setError] = useState<Error | null>(null)

  if (user) {
    return <Navigate to="/dashboard" />
  }

  const handleRegister = async (event: any) => {
    event.preventDefault()
    const email = event.target[0].value
    const password = event.target[1].value
    const confirmPassword = event.target[2].value

    if (password.length < 8) {
      setError({
        type: "MIN_LENGTH",
        message: "Hasło musi mieć 8 znaków",
      })
      return
    }

    if (password !== confirmPassword) {
      setError({
        type: "NO_MATCH",
        message: "Hasła nie są takie same",
      })
      return
    }

    setError(null)
    registerUser(email, password)
  }

  return (
    <>
      <section className="section-container flex flex-col items-center gap-6 py-16">
        <form onSubmit={handleRegister} className="space-y-6 text-center">
          <h2 className="text-2xl">Zarejestruj się</h2>
          <div className="space-y-7">
            <Input id="email" type="email" placeholder="Email" required />
            <div className="relative">
              <Input
                id="password"
                type="password"
                placeholder="Hasło"
                required
                className={cn(error && "border-red-300")}
              />
              {error && (
                <p className="absolute -top-5 right-0 w-max z-10 text-sm text-red-500">
                  {error.message}
                </p>
              )}
            </div>
            <div className="relative">
              <Input
                id="repeat-password"
                type="password"
                placeholder="Potwórz Hasło"
                required
                className={cn(error?.type === "NO_MATCH" && "border-red-300")}
              />
              {error?.type === "NO_MATCH" && (
                <p className="absolute -top-5 right-0 w-max z-10 text-sm text-red-500">
                  {error.message}
                </p>
              )}
            </div>
          </div>
          <Button type="submit">Zarejestruj się</Button>
        </form>
        <p>
          Masz już konto?{" "}
          <Link to="/login" className="text-sky-700">
            Zaloguj się
          </Link>
        </p>
      </section>
    </>
  )
}

export default RegisterPage
