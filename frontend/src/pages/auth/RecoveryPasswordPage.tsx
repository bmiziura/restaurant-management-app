import { Button } from "@/components/ui/button.tsx"
import useAuth from "@/context/AuthContext.tsx"
import { Link, Navigate } from "react-router-dom"
import { Input } from "@/components/ui/input.tsx"
import { ArrowRightIcon } from "@radix-ui/react-icons"

function RecoveryPasswordPage() {
  const { user } = useAuth()

  if (user) {
    return <Navigate to="/login" />
  }

  return (
    <>
      <section className="section-container flex flex-col items-center gap-6 py-16">
        <form onSubmit={() => {}} className="space-y-6 text-center">
          <h2 className="text-2xl">Odzyskiwanie Konta</h2>
          <p className="max-w-sm text-sm">
            Wpisz poniżej swój e-mail, aby odzyskać konto. Będziesz musiał
            potwierdzić zmianę hasła za pomocą linku we wiadomości wysłanej na
            podany e-mail.
          </p>
          <div className="space-y-7">
            <Input
              id="email"
              type="email"
              placeholder="Email do odzyskania"
              required
            />
          </div>

          <Button type="submit" className="w-full">
            Odzyskaj Konto
          </Button>

          <div>
            <Link to="/login">
              <Button
                variant="outline"
                className="w-full flex items-center gap-1"
              >
                lub przejdź do logowania
                <ArrowRightIcon />
              </Button>
            </Link>
          </div>
        </form>
      </section>
    </>
  )
}

export default RecoveryPasswordPage
