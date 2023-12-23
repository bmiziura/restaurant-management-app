import { Button } from "@/components/ui/button"
import useAuth from "@/context/AuthContext"
import { Link } from "react-router-dom"

function LoginPage() {
  const { isLoading, user, loginUser, logoutUser, registerUser } = useAuth()
  const handleLogin = async (event: any) => {
    event.preventDefault()
    await loginUser(event.target[0].value, event.target[1].value)
  }
  const handleRegister = async (event: any) => {
    event.preventDefault()
    await registerUser(event.target[0].value, event.target[1].value)
  }
  if (isLoading) return <></>
  return (
    <>
      <div className="container text-center max-w-xs py-16 md:py-64">
        <form onSubmit={handleLogin}>
          <h2 className="text-2xl mb-4">Zaloguj się</h2>
          <div className="relative mt-6">
            <input
              type="email"
              id="email"
              name="email"
              placeholder=""
              className="peer absolute top-0 left-0 w-full border-2 rounded-xl p-2 border-slate-100 focus:outline-none focus:border-2 focus:border-sky-500"
            />
            <label
              htmlFor="email"
              className="transition-all absolute p-2 top-0 left-0 z-10 select-none peer-focus:text-sky-500 peer-focus:-top-8 peer-focus:text-sm peer-[:not(:placeholder-shown)]:-top-8 peer-[:not(:placeholder-shown)]:text-sm"
            >
              Email
            </label>
          </div>
          <div className="relative mt-24">
            <input
              type="password"
              id="password"
              name="password"
              placeholder=""
              className="peer absolute top-0 left-0 w-full border-2 rounded-xl p-2 border-slate-100 focus:outline-none focus:border-2 focus:border-sky-500"
            />
            <label
              htmlFor="password"
              className="transition-all absolute p-2 top-0 left-0 z-10 select-none peer-focus:text-sky-500 peer-focus:-top-8 peer-focus:text-sm peer-[:not(:placeholder-shown)]:-top-8 peer-[:not(:placeholder-shown)]:text-sm"
            >
              Hasło
            </label>
          </div>
          <Button type="submit" className="mt-[4.5rem]">
            Zaloguj się
          </Button>
        </form>
        <p className="mt-2">
          Nie masz konta?{" "}
          <Link to="/register" className="text-sky-700">
            Zarejestruj się
          </Link>
        </p>
      </div>
    </>
  )
}

export default LoginPage
