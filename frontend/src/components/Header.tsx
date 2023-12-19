import { Button } from "./ui/button"
import { Link } from "react-router-dom"

function Header() {
  return (
    <header className="h-20 container flex justify-between items-center">
      <h1>
        <Link to="/">Restaurant Management</Link>
      </h1>
      <nav className="flex gap-x-4">
        <ul className="gap-x-4 items-center hidden md:flex">
          <li>Cennik</li>
          <li>Opinie</li>
          <li>O nas</li>
          <li>Kontakt</li>
        </ul>
        <Link to="/login">
          <Button className="text-sm font-bold uppercase bg-slate-800 text-slate-100">
            Zaloguj
          </Button>
        </Link>
      </nav>
    </header>
  )
}

export default Header
