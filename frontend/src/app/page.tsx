"use client";

import { useEffect, useState } from "react";

export default function Home() {
  const [user, setUser] = useState();

  useEffect(() => {
    async function loadUser() {
      try {
        setUser(
          await (
            await fetch("/api/auth/me", {
              headers: {
                Authorization:
                  "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0Iiwicm9sZXMiOlsiVVNFUiJdLCJpYXQiOjE3MDI2NjgwMjAsImV4cCI6MTcwMjY3MTYyMH0.8uGT9EBYG950FtsFzegHcFXhh-kx7Tz0G7btgCFd1HGv05LmeA4TkPSdVA7w29W4xmu68AuHE1eOz-YRkC2GEA",
              },
            })
          ).json(),
        );
      } catch (err) {
        console.log(err);
      }
    }

    loadUser();
  }, []);

  console.log(user);

  return (
    <main>
      <p className="text-4xl font-bold">Hello world</p>
    </main>
  );
}
