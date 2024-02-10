export type Role = "ADMIN" | "RESTAURANT_OWNER" | "USER"

export type User = {
  id: number
  email: string
  firstName?: string
  lastName?: string
  roles: Role[]
  activated: boolean
}
