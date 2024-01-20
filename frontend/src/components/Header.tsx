import { Link, useLocation } from "react-router-dom"
import { Button } from "@/components/ui/button.tsx"
import {
  ArrowRightIcon,
  Cross1Icon,
  HamburgerMenuIcon,
} from "@radix-ui/react-icons"
import React, { useEffect, useState } from "react"
import { clsx } from "clsx"
import { Sheet, SheetContent, SheetTrigger } from "@/components/ui/sheet.tsx"
import {
  NavigationMenu,
  NavigationMenuContent,
  NavigationMenuItem,
  NavigationMenuLink,
  NavigationMenuList,
  NavigationMenuTrigger,
  navigationMenuTriggerStyle,
} from "@/components/ui/navigation-menu.tsx"
import { cn } from "@/lib/utils.ts"
import useScrollVisibility from "@/hooks/useScrollVisibility.ts"

type NavigationLinkChildren = {
  title: string
  description?: string
  href?: string
}

type NavigationLink = {
  title: string
  href: string
  isHashLink?: boolean
  children?: NavigationLinkChildren[]
}

const links: NavigationLink[] = [
  {
    title: "Oferta",
    href: "/#offer",
    isHashLink: true,
    children: [
      {
        title: "Some Title",
        description: "Some description",
        href: "/offer/test-url",
      },
      {
        title: "Some Title2",
        description: "Some description2",
        href: "/offer/test-url/3",
      },
    ],
  },
  {
    title: "Cennik",
    href: "/#pricing",
    isHashLink: true,
  },
  {
    title: "Opinie",
    href: "/#opinie",
    isHashLink: true,
  },
  {
    title: "O nas",
    href: "/#o-nas",
    isHashLink: true,
  },
  {
    title: "Kontakt",
    href: "/contact",
  },
]

const Header = () => {
  const [isSheetVisible, setSheetVisible] = useState<boolean>(false)
  const { isVisible, setVisible } = useScrollVisibility()

  const location = useLocation()

  useEffect(() => {
    if (isSheetVisible) setSheetVisible(false)
    if (!isVisible) setVisible(true)
  }, [location])

  return (
    <header
      className={clsx(
        "sticky top-0 h-20 bg-white transition-transform",
        !isVisible && "-translate-y-[1000px]",
      )}
    >
      <div className="container flex justify-between items-center h-full">
        <div className="flex items-center gap-8">
          <Link to="/">Restaurant Management</Link>
          <NavigationMenu className="hidden lg:block">
            <NavigationMenuList>
              {links.map((link) => (
                <NavLink link={link} key={link.title} />
              ))}
            </NavigationMenuList>
          </NavigationMenu>
        </div>
        <div className="flex items-center gap-4">
          <Link to="/login" className="hidden sm:block">
            <Button variant="outline">Zaloguj</Button>
          </Link>
          <Link to="/register" className="hidden sm:block">
            <Button className="flex items-center gap-2">
              <span>Wypróbuj</span>
              <ArrowRightIcon />
            </Button>
          </Link>
          <Sheet open={isSheetVisible} onOpenChange={setSheetVisible}>
            <SheetTrigger asChild>
              <div className="cursor-pointer sm:hidden">
                {isSheetVisible ? (
                  <Cross1Icon width={24} height={24} />
                ) : (
                  <HamburgerMenuIcon width={24} height={24} />
                )}
              </div>
            </SheetTrigger>
            <SheetContent side="left" className="py-8">
              <div className="flex flex-col items-center gap-8">
                <Link to="/" className="text-center">
                  Restaurant Management
                </Link>
                <NavigationMenu
                  className="w-full max-w-full"
                  orientation="vertical"
                >
                  <NavigationMenuList className="flex-col w-full">
                    {links.map((link) => (
                      <NavLink link={link} isSheet={true} key={link.title} />
                    ))}
                  </NavigationMenuList>
                </NavigationMenu>
                <div className="flex flex-col gap-2 w-full">
                  <Link to="/login">
                    <Button variant="outline" className="w-full">
                      Zaloguj
                    </Button>
                  </Link>
                  <Link to="/register">
                    <Button className="flex items-center gap-2 w-full">
                      <span>Wypróbuj</span>
                      <ArrowRightIcon />
                    </Button>
                  </Link>
                </div>
              </div>
            </SheetContent>
          </Sheet>
        </div>
      </div>
    </header>
  )
}

const NavLink = ({
  link,
  isSheet = false,
}: {
  link: NavigationLink
  isSheet?: boolean
}) => {
  return (
    <NavigationMenuItem>
      {link.children ? (
        <>
          {isSheet ? (
            <></>
          ) : (
            // <Accordion
            //   type="single"
            //   collapsible
            //   className="w-full"
            //   orientation="vertical"
            // >
            //   <AccordionItem
            //     value={link.title}
            //     className={clsx("border-b-0 w-full")}
            //   >
            //     <AccordionTrigger
            //       className={clsx(
            //         navigationMenuTriggerStyle(),
            //         "w-full data-[active]:bg-accent/50 data-[state=open]:bg-accent/50",
            //       )}
            //       data-active={false}
            //     >
            //       Test
            //     </AccordionTrigger>
            //     <AccordionContent>Content</AccordionContent>
            //   </AccordionItem>
            // </Accordion>
            <>
              <NavigationMenuTrigger>{link.title}</NavigationMenuTrigger>
              <NavigationMenuContent>
                <ul className="grid w-[400px] gap-3 p-4 md:w-[500px] md:grid-cols-2 lg:w-[600px]">
                  {link.children.map((childLink) => (
                    <ListItem
                      key={childLink.title}
                      title={childLink.title}
                      href={childLink.href}
                    >
                      {childLink.description}
                    </ListItem>
                  ))}
                </ul>
              </NavigationMenuContent>
            </>
          )}
        </>
      ) : (
        <Link to={link.href}>
          <NavigationMenuLink className={navigationMenuTriggerStyle()}>
            {link.title}
          </NavigationMenuLink>
        </Link>
      )}
    </NavigationMenuItem>
    // <li key={link.href} className="text-sm">
    //   {link.isHashLink ? (
    //     <HashLink smooth to={link.href}>
    //       {link.title}
    //     </HashLink>
    //   ) : (
    //     <Link to={link.href}>{link.title}</Link>
    //   )}
    // </li>
  )
}

const ListItem = React.forwardRef<
  React.ElementRef<"a">,
  React.ComponentPropsWithoutRef<"a">
>(({ className, title, children, ...props }, ref) => {
  return (
    <li>
      <NavigationMenuLink asChild>
        <a
          ref={ref}
          className={cn(
            "block select-none space-y-1 rounded-md p-3 leading-none no-underline outline-none transition-colors hover:bg-accent hover:text-accent-foreground focus:bg-accent focus:text-accent-foreground",
            className,
          )}
          {...props}
        >
          <div className="text-sm font-medium leading-none">{title}</div>
          <p className="line-clamp-2 text-sm leading-snug text-muted-foreground">
            {children}
          </p>
        </a>
      </NavigationMenuLink>
    </li>
  )
})

export default Header
