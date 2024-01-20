import Header from "@/components/Header"
import Footer from "@/components/Footer"
import { Outlet } from "react-router-dom"
import { Button } from "@/components/ui/button.tsx"

import pricingData, { PricingPlans } from "@/data/pricing.ts"
import { ArrowRightIcon } from "@radix-ui/react-icons"
import { clsx } from "clsx"

function HomePage() {
  return (
    <main>
      <HeroSection />
      <TrustedSection />
      <FeaturesSection />
      <PricingSection />
      <TryNowSection />
    </main>
  )
}

const HeroSection = () => {
  return (
    <section className="section-container my-32">
      <div className="grid grid-cols-1 lg:grid-cols-2 gap-8">
        <div className="flex flex-col justify-around lg:w-10/12 gap-8 lg:gap-0">
          <div className="space-y-8 text-center lg:text-start">
            <h2 className="font-bold text-3xl tracking-wide leading-10">
              Usprawnij pracę własnej restauracji
            </h2>
            <p className="tracking-wide leading-6 text-slate-600">
              Wykorzystaj nasze oprogramowanie w celu optymalizacji zamówień
              online. Stwórz personalną stronę dla swojej restauracji i przyjmuj
              zamówienia na terenie całej polski on-line!
            </p>
          </div>
          <div className="flex items-center justify-center lg:justify-start">
            <Button>Wypróbuj już teraz!</Button>
          </div>
        </div>
        <div>
          <img src="../../public/assets/hero_image.svg" alt="hero-image" />
        </div>
      </div>
      <div></div>
    </section>
  )
}

const TrustedSection = () => {
  return (
    <section className="my-16 py-16 bg-slate-50">
      <div className="section-container space-y-12">
        <h2 className="text-center tracking-tight">Zaufali nam</h2>
        <div className="grid grid-cols-2 md:grid-cols-3 lg:grid-cols-6 gap-16 place-items-center">
          <img
            src="/assets/company/google.png"
            alt="google"
            height={32}
            width={100}
          />
          <img
            src="/assets/company/google.png"
            alt="google"
            height={32}
            width={100}
          />
          <img
            src="/assets/company/google.png"
            alt="google"
            height={32}
            width={100}
          />
          <img
            src="/assets/company/google.png"
            alt="google"
            height={32}
            width={100}
          />
          <img
            src="/assets/company/google.png"
            alt="google"
            height={32}
            width={100}
          />
          <img
            src="/assets/company/google.png"
            alt="google"
            height={32}
            width={100}
          />
        </div>
      </div>
    </section>
  )
}

const FeaturesSection = () => {
  return (
    <section className="bg-slate-800 text-slate-50 py-8 my-24">
      <div className="section-container">Features</div>
    </section>
  )
}

const PricingSection = () => {
  const pricing = pricingData

  return (
    <section className="section-container my-32 space-y-8" id="pricing">
      <h2 className="text-center tracking-tight text-2xl">Cennik</h2>

      <div className="flex gap-4 flex-col lg:flex-row">
        {pricing.map((data) => (
          <PricingCard key={data.title} data={data} />
        ))}
      </div>
    </section>
  )
}

const PricingCard = ({ data }: { data: PricingPlans }) => {
  return (
    <div
      className={clsx(
        "border-[1px] shadow-md rounded-2xl p-4 flex flex-col justify-between gap-5",
        !data.isFeatured && "lg:mt-8",
      )}
    >
      <div className="space-y-5">
        <div className={clsx("flex justify-between items-center")}>
          <h3>{data.title}</h3>
          <span className="text-xl tracking-tight font-thin">
            {data.price}zł<span className="text-sm">/msc</span>
          </span>
        </div>
        <p className="text-xs text-slate-600">{data.description}</p>
        <ul className="flex flex-col justify-center">
          {data.features.map((feature) => (
            <li key={feature} className="text-sm flex items-center gap-2">
              <ArrowRightIcon />
              <span>{feature}</span>
            </li>
          ))}
        </ul>
      </div>
      <Button
        variant={data.isFeatured ? "default" : "outline"}
        className="w-full"
      >
        Rozpocznij
      </Button>
    </div>
  )
}

const TryNowSection = () => {
  return <section>Try now for free!</section>
}

export const HomePageLayout = () => {
  return (
    <>
      <Header />
      <Outlet />
      <Footer />
    </>
  )
}

export default HomePage //bruh
