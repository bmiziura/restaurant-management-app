import { RouterProvider, createBrowserRouter } from "react-router-dom"
import HomePage from "./pages/HomePage"
import AuthPage from "./pages/AuthPage"

const router = createBrowserRouter([
  { path: "/", element: <HomePage /> },
  { path: "/login", element: <AuthPage /> },
])

export default function BrowserRouterProvider() {
  return <RouterProvider router={router} />
}
