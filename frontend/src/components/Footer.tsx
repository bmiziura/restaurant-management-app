import { ArrowRightIcon } from "@radix-ui/react-icons"
import { Link } from "react-router-dom"
import { Button } from "@/components/ui/button.tsx"

function Footer() {
  return (
    <footer className="py-8 bg-slate-50">
      <div className="section-container flex flex-col divide-y">
        <div className="py-8 flex gap-16 flex-col lg:flex-row justify-between divide-y lg:divide-y-0">
          <div className="lg:w-2/4">
            <div className="space-y-2">
              <Link to="/">Restaurant Management</Link>
              <p className="text-xs text-slate-500 tracking-wider">
                Wykorzystaj nasze oprogramowanie w celu optymalizacji zamówień
                online. Stwórz personalną stronę dla swojej restauracji i
                przyjmuj zamówienia na terenie całej polski on-line!
              </p>
            </div>
          </div>
          <div className="flex gap-8 justify-between w-full py-16 lg:py-0">
            <div className="space-y-2">
              <h2 className="font-bold text-sm">Pages</h2>
              <ul className="text-slate-500 text-sm space-y-1">
                <li className="hover:text-slate-600">
                  <Link to="/">Home</Link>
                </li>
                <li className="hover:text-slate-600">
                  <Link to="/">About</Link>
                </li>
                <li className="hover:text-slate-600">
                  <Link to="/">Blog</Link>
                </li>
                <li className="hover:text-slate-600">
                  <Link to="/">Pricing</Link>
                </li>
                <li className="hover:text-slate-600">
                  <Link to="/">Careers</Link>
                </li>
                <li className="hover:text-slate-600">
                  <Link to="/">Contact</Link>
                </li>
              </ul>
            </div>
            <div className="space-y-2">
              <h2 className="font-bold text-sm">Pages</h2>
              <ul className="text-slate-500 text-sm space-y-1">
                <li className="hover:text-slate-600">
                  <Link to="/">Home</Link>
                </li>
                <li className="hover:text-slate-600">
                  <Link to="/">About</Link>
                </li>
                <li className="hover:text-slate-600">
                  <Link to="/">Blog</Link>
                </li>
                <li className="hover:text-slate-600">
                  <Link to="/">Pricing</Link>
                </li>
                <li className="hover:text-slate-600">
                  <Link to="/">Careers</Link>
                </li>
                <li className="hover:text-slate-600">
                  <Link to="/">Contact</Link>
                </li>
              </ul>
            </div>
            <div className="space-y-2">
              <h2 className="font-bold text-sm">Pages</h2>
              <ul className="text-slate-500 text-sm space-y-1">
                <li className="hover:text-slate-600">
                  <Link to="/">Home</Link>
                </li>
                <li className="hover:text-slate-600">
                  <Link to="/">About</Link>
                </li>
                <li className="hover:text-slate-600">
                  <Link to="/">Blog</Link>
                </li>
                <li className="hover:text-slate-600">
                  <Link to="/">Pricing</Link>
                </li>
                <li className="hover:text-slate-600">
                  <Link to="/">Careers</Link>
                </li>
                <li className="hover:text-slate-600">
                  <Link to="/">Contact</Link>
                </li>
              </ul>
            </div>
          </div>
        </div>
        <div className="py-8 flex justify-between items-center">
          <p className="text-xs">
            Copyright &copy; 2024r Restaurant Management App
          </p>
          <div className="flex gap-4">
            <Link to="/register">
              <Button className="flex items-center gap-2 w-full rounded-none text-xs">
                <span>Wypróbuj</span>
                <ArrowRightIcon />
              </Button>
            </Link>
            <Link to="/login">
              <Button variant="outline" className="w-full rounded-none text-xs">
                Zaloguj
              </Button>
            </Link>
          </div>
        </div>
      </div>
    </footer>
  )
}

export default Footer
