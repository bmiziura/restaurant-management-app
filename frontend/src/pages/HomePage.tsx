import Header from "@/components/Header"
import Footer from "@/components/Footer"
import { Link, Outlet } from "react-router-dom"

function HomePage() {
  return (
    <>
      <section>
        <div className="container flex gap-8 py-32">
          <div className="flex-1">
            <h1 className="font-bold text-4xl pb-4 tracking-wider">
              Usprawnij pracę restauracji
            </h1>
            <p className="text-lg text-slate-600 max-w-md">
              Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam
              eget purus leo. Nunc vel fermentum urna. Integer aliquet quam
              vitae sem maximus feugiat. Praesent id eros tempus, vestibulum
              nunc pellentesque, varius magna.
            </p>
          </div>
          <div className="flex-1">Image</div>
        </div>
      </section>
      <section className="bg-white">
        <div className="container py-32">
          <h1 className="font-bold text-4xl pb-4 tracking-wider text-center">
            Zaufali nam
          </h1>
          <div className="flex gap-4">
            <p className="text-lg text-slate-600 max-w-md">Image</p>
            <p className="text-lg text-slate-600 max-w-md">Image</p>
            <p className="text-lg text-slate-600 max-w-md">Image</p>
            <p className="text-lg text-slate-600 max-w-md">Image</p>
            <p className="text-lg text-slate-600 max-w-md">Image</p>
            <p className="text-lg text-slate-600 max-w-md">Image</p>
            <p className="text-lg text-slate-600 max-w-md">Image</p>
            <p className="text-lg text-slate-600 max-w-md">Image</p>
          </div>
        </div>
      </section>
    </>
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
