import { Button } from "./ui/button"

function Header() {
  return (
    <header className="h-20 container flex justify-between items-center">
      <h1>Restaurant Management</h1>
      <nav className="flex gap-x-4">
        <ul className="gap-x-4 items-center hidden md:flex">
          <li>Cennik</li>
          <li>Opinie</li>
          <li>O nas</li>
          <li>Kontakt</li>
        </ul>
        <Button className="text-sm font-bold uppercase bg-slate-800 text-slate-100">
          Zaloguj
        </Button>
      </nav>
    </header>
  )
}

export default Header
