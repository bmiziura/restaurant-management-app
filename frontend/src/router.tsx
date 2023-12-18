import { RouterProvider, createBrowserRouter } from "react-router-dom"
import HomePage, { HomePageLayout } from "./pages/HomePage"
import AuthPage from "./pages/AuthPage"

const router = createBrowserRouter([
  {
    path: "/",
    element: <HomePageLayout />,
    children: [
      { path: "/", element: <HomePage /> },
      { path: "/login", element: <AuthPage /> },
    ],
  },
])

export default function BrowserRouterProvider() {
  return <RouterProvider router={router} />
}
