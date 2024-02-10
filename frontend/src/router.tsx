import { createBrowserRouter, RouterProvider } from "react-router-dom"
import useAuth from "./context/AuthContext"
import HomePage, { HomePageLayout } from "@/pages/HomePage.tsx"
import LoginPage from "@/pages/auth/LoginPage.tsx"
import RegisterPage from "@/pages/auth/RegisterPage.tsx"
import DashboardPage, {
  DashboardPageLayout,
} from "@/pages/dashboard/DashboardPage.tsx"
import ConfirmAccountPage from "@/pages/auth/ConfirmAccountPage.tsx"

const router = createBrowserRouter([
  {
    path: "/",
    element: <HomePageLayout />,
    children: [
      { path: "/", element: <HomePage /> },
      { path: "/login", element: <LoginPage /> },
      { path: "/register", element: <RegisterPage /> },
      { path: "/confirm-account", element: <ConfirmAccountPage /> },
    ],
  },
  {
    path: "/dashboard",
    element: <DashboardPageLayout />,
    children: [
      {
        path: "/dashboard",
        element: <DashboardPage />,
      },
    ],
  },
])

export default function BrowserRouterProvider() {
  const { isLoading } = useAuth()

  if (isLoading) {
    return <></>
  }

  return <RouterProvider router={router} />
}
