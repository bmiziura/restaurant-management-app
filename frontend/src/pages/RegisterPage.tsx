import { Button } from "@/components/ui/button"
import useAuth from "@/context/AuthContext"
import { useState } from "react"
import { Link } from "react-router-dom"

export type Error = {
  id?: number
  message?: string
}

function RegisterPage() {
  const { isLoading, registerUser } = useAuth()
  const [error, setError] = useState<Error>({})

  const handleRegister = async (event: any) => {
    event.preventDefault()
    if (event.target[1].value.length >= 8) {
      if (event.target[1].value === event.target[2].value) {
        setError({})
        await registerUser(event.target[0].value, event.target[1].value)
      } else {
        setError({
          id: 1,
          message: "Hasła nie są takie same",
        })
      }
    } else {
      setError({
        id: 2,
        message: "Hasło musi mieć 8 znaków",
      })
    }
  }
  if (isLoading) return <></>
  return (
    <>
      <div className="container text-center max-w-xs py-16 md:py-64">
        <form onSubmit={handleRegister}>
          <h2 className="text-2xl mb-4">Zarejestruj się</h2>
          <div className="relative mt-6 py-6">
            <input
              type="email"
              id="email"
              name="email"
              placeholder=""
              className="peer absolute top-0 left-0 w-full border-2 rounded-xl p-2 border-slate-100 focus:outline-none focus:border-2 focus:border-sky-300"
              required
            />
            <label
              htmlFor="email"
              className="transition-all absolute p-2 top-0 left-0 z-10 select-none peer-focus:text-sky-500 peer-focus:-top-8 peer-focus:text-sm peer-[:not(:placeholder-shown)]:-top-8 peer-[:not(:placeholder-shown)]:text-sm"
            >
              Email
            </label>
          </div>
          <div className="relative mt-6 py-6">
            <input
              type="password"
              id="password"
              name="password"
              placeholder=""
              className={
                "peer absolute top-0 left-0 w-full border-2 rounded-xl p-2 border-slate-100 focus:outline-none focus:border-2 focus:border-sky-300 " +
                (error.id === 1 || error.id === 2 ? "border-red-300" : "")
              }
              required
            />
            <label
              htmlFor="password"
              className="transition-all absolute p-2 top-0 left-0 z-10 select-none peer-focus:text-sky-500 peer-focus:-top-8 peer-focus:text-sm peer-[:not(:placeholder-shown)]:-top-8 peer-[:not(:placeholder-shown)]:text-sm"
            >
              Hasło
            </label>
            <p
              id="password-error"
              className={
                "absolute p-2 -top-8 right-0 z-10 text-sm text-red-500 " +
                (error.id === 1 || error.id === 2 ? "" : "hidden")
              }
            >
              {error.message}
            </p>
          </div>
          <div className="relative mt-6 py-6">
            <input
              type="password"
              id="repeat-password"
              name="repeat-password"
              placeholder=""
              className={
                "peer absolute top-0 left-0 w-full border-2 rounded-xl p-2 border-slate-100 focus:outline-none focus:border-2 focus:border-sky-300 " +
                (error?.id === 1 ? "border-red-300" : "")
              }
              required
            />
            <label
              htmlFor="repeat-password"
              className="transition-all absolute p-2 top-0 left-0 z-10 select-none peer-focus:text-sky-500 peer-focus:-top-8 peer-focus:text-sm peer-[:not(:placeholder-shown)]:-top-8 peer-[:not(:placeholder-shown)]:text-sm"
            >
              Powtórz hasło
            </label>
            <p
              id="password-error"
              className={
                "absolute p-2 -top-8 right-0 z-10 text-sm text-red-500 " +
                (error.id === 1 ? "" : "hidden")
              }
            >
              {error.message}
            </p>
          </div>
          <Button type="submit" className="mt-6">
            Zarejestruj się
          </Button>
        </form>
        <p className="mt-6">
          Masz już konto?{" "}
          <Link to="/login" className="text-sky-700">
            Zaloguj się
          </Link>
        </p>
      </div>
    </>
  )
}

export default RegisterPage
