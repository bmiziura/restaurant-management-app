import { User } from "@/api/types.ts"

export async function getCurrentUser(): Promise<User> {
  const res = await fetch("/api/auth/me", {
    credentials: "include",
  })

  return res.json()
}

export async function loginUser(
  email: string,
  password: string,
): Promise<Response> {
  return await fetch("/api/auth/login", {
    method: "POST",
    body: JSON.stringify({
      email,
      password,
    }),
    credentials: "include",
    headers: {
      "Content-Type": "application/json",
    },
  })
}

export async function registerUser(
  email: string,
  password: string,
): Promise<Response> {
  return await fetch("/api/auth/register", {
    method: "POST",
    body: JSON.stringify({
      email,
      password,
    }),
    credentials: "include",
    headers: {
      "Content-Type": "application/json",
    },
  })
}

export async function activateUser(
  email: string,
  token: string,
): Promise<Response> {
  return await fetch(`/api/auth/token/activate?email=${email}&token=${token}`, {
    method: "POST",
    credentials: "include",
  })
}

export async function sendActivationEmail(): Promise<Response> {
  return await fetch(`/api/auth/token/retry`, {
    method: "POST",
    credentials: "include",
  })
}

export async function logoutUser(): Promise<Response> {
  return await fetch(`/api/auth/logout`, {
    method: "POST",
    credentials: "include",
  })
}
