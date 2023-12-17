import Header from "@/components/Header"
import Footer from "@/components/Footer"
import { Link, Outlet } from "react-router-dom"

function HomePage() {
  return (
    <div>
      <Link to="/login">Zaloguj się</Link>
    </div>
  )
}

export const HomePageLayout = () => {
  return (
    <>
      <Header />
      <Outlet />
      <Footer />
    </>
  )
}

export default HomePage //bruh
