import { RouterProvider, createBrowserRouter } from "react-router-dom"
import HomePage, { HomePageLayout } from "./pages/HomePage"
import LoginPage from "./pages/LoginPage"
import RegisterPage from "./pages/RegisterPage"
import useAuth from "./context/AuthContext"

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
