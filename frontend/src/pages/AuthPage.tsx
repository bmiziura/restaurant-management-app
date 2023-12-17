import useAuth from "@/context/AuthContext"
import { Link } from "react-router-dom"

function AuthPage() {
  const { isLoading, user, loginUser, logoutUser, registerUser } = useAuth()
  const handleLogin = async (event: any) => {
    event.preventDefault()
    await loginUser(event.target[0].value, event.target[1].value)
  }
  const handleRegister = async (event: any) => {
    event.preventDefault()
    await registerUser(event.target[0].value, event.target[1].value)
  }
  if (isLoading) return <></>
  return (
    <>
      <Link to="/">Strona główna</Link>
      {user ? (
        <div>
          <p>{user.id}</p>
          <button onClick={logoutUser}>Logout</button>
        </div>
      ) : (
        <div>
          <p>Zaloguj się</p>
          <form onSubmit={handleLogin}>
            <input type="text" id="email" placeholder="Email" />
            <input type="password" id="password" placeholder="Password" />
            <button type="submit">Zaloguj się</button>
          </form>
          <form onSubmit={handleRegister}>
            <input type="text" id="email" placeholder="Email" />
            <input type="password" id="password" placeholder="Password" />
            <button type="submit">Zarejestruj się</button>
          </form>
        </div>
      )}
    </>
  )
}

export default AuthPage
