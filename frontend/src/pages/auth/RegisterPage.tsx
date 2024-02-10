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
      {/*<div className="container text-center max-w-xs py-16 md:py-64">*/}
      {/*  <form onSubmit={handleRegister}>*/}
      {/*    <h2 className="text-2xl mb-4">Zarejestruj się</h2>*/}
      {/*    <div className="relative mt-6 py-6">*/}
      {/*      <input*/}
      {/*        type="email"*/}
      {/*        id="email"*/}
      {/*        name="email"*/}
      {/*        placeholder=""*/}
      {/*        className="peer absolute top-0 left-0 w-full border-2 rounded-xl p-2 border-slate-100 focus:outline-none focus:border-2 focus:border-sky-300"*/}
      {/*        required*/}
      {/*      />*/}
      {/*      <label*/}
      {/*        htmlFor="email"*/}
      {/*        className="transition-all absolute p-2 top-0 left-0 z-10 select-none peer-focus:text-sky-500 peer-focus:-top-8 peer-focus:text-sm peer-[:not(:placeholder-shown)]:-top-8 peer-[:not(:placeholder-shown)]:text-sm"*/}
      {/*      >*/}
      {/*        Email*/}
      {/*      </label>*/}
      {/*    </div>*/}
      {/*    <div className="relative mt-6 py-6">*/}
      {/*      <input*/}
      {/*        type="password"*/}
      {/*        id="password"*/}
      {/*        name="password"*/}
      {/*        placeholder=""*/}
      {/*        className={cn(*/}
      {/*          "peer absolute top-0 left-0 w-full border-2 rounded-xl p-2 border-slate-100 focus:outline-none focus:border-2 focus:border-sky-300",*/}
      {/*          error && "border-red-300",*/}
      {/*        )}*/}
      {/*        required*/}
      {/*      />*/}
      {/*      <label*/}
      {/*        htmlFor="password"*/}
      {/*        className="transition-all absolute p-2 top-0 left-0 z-10 select-none peer-focus:text-sky-500 peer-focus:-top-8 peer-focus:text-sm peer-[:not(:placeholder-shown)]:-top-8 peer-[:not(:placeholder-shown)]:text-sm"*/}
      {/*      >*/}
      {/*        Hasło*/}
      {/*      </label>*/}
      {/*      {error && (*/}
      {/*        <p className="absolute p-2 -top-8 right-0 z-10 text-sm text-red-500">*/}
      {/*          {error.message}*/}
      {/*        </p>*/}
      {/*      )}*/}
      {/*    </div>*/}
      {/*    <div className="relative mt-6 py-6">*/}
      {/*      <input*/}
      {/*        type="password"*/}
      {/*        id="repeat-password"*/}
      {/*        name="repeat-password"*/}
      {/*        placeholder=""*/}
      {/*        className={cn(*/}
      {/*          "peer absolute top-0 left-0 w-full border-2 rounded-xl p-2 border-slate-100 focus:outline-none focus:border-2 focus:border-sky-300",*/}
      {/*          error?.type === "NO_MATCH" && "border-red-300",*/}
      {/*        )}*/}
      {/*        required*/}
      {/*      />*/}
      {/*      <label*/}
      {/*        htmlFor="repeat-password"*/}
      {/*        className="transition-all absolute p-2 top-0 left-0 z-10 select-none peer-focus:text-sky-500 peer-focus:-top-8 peer-focus:text-sm peer-[:not(:placeholder-shown)]:-top-8 peer-[:not(:placeholder-shown)]:text-sm"*/}
      {/*      >*/}
      {/*        Powtórz hasło*/}
      {/*      </label>*/}
      {/*      {error?.type === "NO_MATCH" && (*/}
      {/*        <p className="absolute p-2 -top-8 right-0 z-10 text-sm text-red-500">*/}
      {/*          {error.message}*/}
      {/*        </p>*/}
      {/*      )}*/}
      {/*    </div>*/}
      {/*    <Button type="submit" className="mt-6">*/}
      {/*      Zarejestruj się*/}
      {/*    </Button>*/}
      {/*  </form>*/}
      {/*  <p className="mt-6">*/}
      {/*    Masz już konto?{" "}*/}
      {/*    <Link to="/login" className="text-sky-700">*/}
      {/*      Zaloguj się*/}
      {/*    </Link>*/}
      {/*  </p>*/}
      {/*</div>*/}
    </>
  )
}

export default RegisterPage
