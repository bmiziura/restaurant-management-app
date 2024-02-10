import useAuth from "@/context/AuthContext.tsx"
import { Link, Navigate, useSearchParams } from "react-router-dom"
import { useEffect, useState } from "react"
import { Button } from "@/components/ui/button.tsx"
import { ArrowRightIcon, SymbolIcon } from "@radix-ui/react-icons"

function ConfirmAccountPage() {
  const { user, confirmAccount, retryConfirmationEmail } = useAuth()

  const [isLoaded, setLoaded] = useState(false)
  const [error, setError] = useState<string>("")

  const [isRetrying, setRetry] = useState(false)
  const [retryError, setRetryError] = useState("")

  const [searchParams] = useSearchParams()

  const email = searchParams.get("email") as string
  const token = searchParams.get("token") as string

  useEffect(() => {
    const confirmAccountAsync = async () => {
      try {
        await confirmAccount(email, token)
      } catch (err: any) {
        setError(err.message)
      }
      setLoaded(true)
    }

    if (email && token) {
      confirmAccountAsync()
    } else {
      setLoaded(true)
    }
  }, [])

  const handleRetry = async () => {
    setRetry(true)

    try {
      await retryConfirmationEmail()
    } catch (err: any) {
      setRetryError(err.message)
    }

    setRetry(false)
  }

  if (!user && !email && !token) {
    return <Navigate to="/login" />
  }

  if (user?.activated) {
    return <Navigate to="/login" />
  }

  if (!isLoaded) {
    return <></>
  }

  if (error) {
    return (
      <section className="section-container my-64 flex items-center justify-center gap-5 flex-col text-center">
        <div>{error}</div>
        <p>
          Uważasz to za błąd? Kliknij przycisk, aby ponownie wysłać wiadomość!
        </p>
        <div className="flex items-center flex-col text-center gap-2">
          {retryError && <p className="text-sm text-red-700">{retryError}</p>}

          <div className="flex gap-4 flex-col md:flex-row">
            <Button
              onClick={handleRetry}
              disabled={isRetrying}
              className="flex items-center gap-1"
            >
              {isRetrying && <SymbolIcon className="animate-spin" />}
              Wyślij ponownie
            </Button>
            <Link to="/login">
              <Button className="flex items-center gap-1" variant="outline">
                lub{" "}
                {user
                  ? "przejdź do panelu użytkownika"
                  : "przejdź do logowania"}
                <ArrowRightIcon />
              </Button>
            </Link>
          </div>
        </div>
      </section>
    )
  }

  if (!user) {
    return <></>
  }

  return (
    <section className="section-container my-64 flex items-center justify-center gap-5 flex-col text-center">
      <p>
        Dziękujemy za założenie konta! Teraz musisz potwierdzić swoje konto.
        Wysłaliśmy na twoją skrzynkę pocztową wiadomość z linkiem aktywacyjnym.
      </p>
      <p>
        Nie otrzymałeś wiadomości? Kliknij przycisk, aby ponownie wysłać
        wiadomość!
      </p>
      <div className="flex items-center flex-col text-center gap-2">
        {retryError && <p className="text-sm text-red-700">{retryError}</p>}
        <Button
          onClick={handleRetry}
          disabled={isRetrying}
          className="flex items-center gap-1"
        >
          {isRetrying && <SymbolIcon className="animate-spin" />}
          Wyślij ponownie
        </Button>
      </div>
    </section>
  )
}

export default ConfirmAccountPage
