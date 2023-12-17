import { Button } from "./ui/button"

function Header() {
  return (
    <header className="h-20 container">
      <div className="flex h-full justify-between items-center">
        <h1>Restaurant Management</h1>
        <nav>
          <ul className="flex text-lg gap-x-4 items-center">
            <li>Cennik</li>
            <li>Opinie</li>
            <li>O nas</li>
            <li>Kontakt</li>
            <li>
              <Button className="text-sm font-bold uppercase bg-slate-800 text-slate-100">
                Zaloguj
              </Button>
            </li>
          </ul>
        </nav>
      </div>
    </header>
  )
}

export default Header
