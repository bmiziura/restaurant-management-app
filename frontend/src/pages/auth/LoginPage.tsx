import useAuth from "@/context/AuthContext.tsx"
import { Link, Navigate } from "react-router-dom"
import { Input } from "@/components/ui/input.tsx"
import { Button } from "@/components/ui/button.tsx"

function LoginPage() {
  const { user, loginUser } = useAuth()

  if (user) {
    return <Navigate to="/dashboard" />
  }

  const handleLogin = async (event: any) => {
    event.preventDefault()
    loginUser(event.target[0].value, event.target[1].value)
  }

  return (
    <>
      <section className="section-container flex flex-col items-center gap-6 py-16">
        <form onSubmit={handleLogin} className="space-y-6 text-center">
          <h2 className="text-2xl">Zaloguj się</h2>
          <div className="space-y-7">
            <Input id="email" type="email" placeholder="Email" required />
            <Input id="password" type="password" placeholder="Hasło" required />
          </div>
          <p className="text-sm">
            Nie pamiętasz hasła?{" "}
            <Link to="/password-recovery" className="text-sky-700">
              Kliknij tutaj
            </Link>
          </p>
          <Button type="submit" className="w-full">
            Zaloguj się
          </Button>
        </form>
        <p>
          Nie masz konta?{" "}
          <Link to="/register" className="text-sky-700">
            Zarejestruj się
          </Link>
        </p>
      </section>
    </>
  )
}

export default LoginPage
