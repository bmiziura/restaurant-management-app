export type PricingPlans = {
  title: string
  description: string
  price: number
  features: string[]
  isFeatured?: boolean
}

const pricingData: PricingPlans[] = [
  {
    title: "Starter",
    description:
      "Plan oferujący podstawowe funkcjonalności naszego oprogramowania.",
    price: 39.99,
    features: ["Test", "Test2", "Test3"],
  },
  {
    title: "Small Business",
    description:
      "Plan oferujący podstawowe funkcjonalności naszego oprogramowania.",
    price: 59.99,
    features: ["Test", "Test2", "Test3", "Test", "Test2", "Test3"],
    isFeatured: true,
  },
  {
    title: "Enterprise",
    description:
      "Plan oferujący podstawowe funkcjonalności naszego oprogramowania.",
    price: 159.99,
    features: ["Test", "Test2", "Test3"],
  },
]

export default pricingData
