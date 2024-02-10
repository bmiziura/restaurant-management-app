import { Navigate, Outlet } from "react-router-dom"
import useAuth from "@/context/AuthContext.tsx"

function DashboardPage() {
  return <div>Dashboard Page</div>
}

export const DashboardPageLayout = () => {
  const { user } = useAuth()

  if (!user) {
    return <Navigate to="/login" />
  }

  if (!user.activated) {
    return <Navigate to="/confirm-account" />
  }

  if (user)
    return (
      <>
        <Outlet />
      </>
    )
}

export default DashboardPage
