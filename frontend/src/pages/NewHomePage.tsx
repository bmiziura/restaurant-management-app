import { Outlet } from "react-router-dom"
import Header from "@/components/Header.tsx"

function NewHomePage() {
  return <div className="h-[300vh]">asdasd</div>
}

export const NewHomePageLayout = () => {
  return (
    <>
      <Header />
      <Outlet />
    </>
  )
}

export default NewHomePage
