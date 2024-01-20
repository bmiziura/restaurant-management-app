import { useEffect, useState } from "react"

export default function useScrollVisibility(initialState: boolean = true) {
  const [isVisible, setVisible] = useState<boolean>(initialState)

  useEffect(() => {
    let lastScrollTop = 0
    const handler = (event: any) => {
      let st = window.scrollY
      if (st > lastScrollTop) {
        setVisible(false)
      } else if (st < lastScrollTop) {
        setVisible(true)
      }
      lastScrollTop = st <= 0 ? 0 : st
    }

    window.addEventListener("scrollend", handler)

    return () => {
      window.removeEventListener("scrollend", handler)
    }
  }, [])

  return { isVisible, setVisible }
}
