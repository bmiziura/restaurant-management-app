import { createBrowserRouter, RouterProvider } from "react-router-dom"
import useAuth from "./context/AuthContext"
import HomePage, { HomePageLayout } from "@/pages/HomePage.tsx"
import LoginPage from "@/pages/LoginPage.tsx"
import RegisterPage from "@/pages/RegisterPage.tsx"

const router = createBrowserRouter([
  {
    path: "/",
    element: <HomePageLayout />,
    children: [
      { path: "/", element: <HomePage /> },
      { path: "/login", element: <LoginPage /> },
      { path: "/register", element: <RegisterPage /> },
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
