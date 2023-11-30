import Container from "../containter";
import { useState } from "react";

import "./navbar.css";

export default function NavBar() {
  const links = [];

  const [isToggled, toggle] = useState(false);

  function toggleHamMenu() {
    toggle(!isToggled);
  }

  return (
    <nav className="absolute z-10 w-full boredr-b lg:border-transparent">
      <Container>
        <div className="relative flex flex-wrap items-center justify-between gap-6 py-3 md:gap-0 md:py-4">
          <div className="relative z-20 flex w-full justify-between md:px-0 lg:w-max">
            <a
              href="/"
              aria-label="logo"
              className="flex items-center space-x-2"
            >
              <span className="text-2xl font-bold text-gray-900 dark:text-white">
                Quizzly
              </span>
            </a>
            <div className="relative flex max-h-10 items-center lg:hidden">
              <button
                aria-label="humburger"
                id="hamburger"
                className={
                  isToggled
                    ? "toggled relative -mr-6 p-6 "
                    : "relative -mr-6 p-6 "
                }
                onClick={toggleHamMenu}
              >
                <div
                  aria-hidden="true"
                  id="line"
                  className="m-auto h-0.5 w-5 rounded bg-sky-900 transition duration-300 dark:bg-gray-300"
                ></div>
                <div
                  aria-hidden="true"
                  id="line2"
                  className="m-auto mt-2 h-0.5 w-5 rounded bg-sky-900 transition duration-300 dark:bg-gray-300"
                ></div>
                <div
                  aria-hidden="true"
                  id="line3"
                  className="m-auto mt-2 h-0.5 w-5 rounded bg-sky-900 transition duration-300 dark:bg-gray-300"
                ></div>
              </button>
            </div>
          </div>
          <div
            id="navLayer"
            aria-hidden="true"
            className={
              isToggled
                ? ` origin-top scale-y-100
                fixed inset-0 z-10 h-screen w-screen origin-bottom scale-y-0 bg-white/70 backdrop-blur-2xl transition duration-500 dark:bg-gray-900/70 lg:hidden`
                : "fixed inset-0 z-10 h-screen w-screen origin-bottom scale-y-0 bg-white/70 backdrop-blur-2xl transition duration-500 dark:bg-gray-900/70 lg:hidden"
            }
          ></div>
          <div
            id="navlinks"
            className={
              isToggled
                ? `!visible !scale-100 !opacity-100 !lg:translate-y-0
                invisible absolute top-full left-0 z-20 w-full origin-top-right translate-y-1 scale-90 flex-col flex-wrap justify-end gap-6 rounded-3xl border border-gray-100 bg-white p-8 opacity-0 shadow-2xl shadow-gray-600/10 transition-all duration-300 dark:border-gray-700 dark:bg-gray-800 dark:shadow-none lg:visible lg:relative lg:flex lg:w-7/12 lg:translate-y-0 lg:scale-100 lg:flex-row lg:items-center lg:gap-0 lg:border-none lg:bg-transparent lg:p-0 lg:opacity-100 lg:shadow-none`
                : "invisible absolute top-full left-0 z-20 w-full origin-top-right translate-y-1 scale-90 flex-col flex-wrap justify-end gap-6 rounded-3xl border border-gray-100 bg-white p-8 opacity-0 shadow-2xl shadow-gray-600/10 transition-all duration-300 dark:border-gray-700 dark:bg-gray-800 dark:shadow-none lg:visible lg:relative lg:flex lg:w-7/12 lg:translate-y-0 lg:scale-100 lg:flex-row lg:items-center lg:gap-0 lg:border-none lg:bg-transparent lg:p-0 lg:opacity-100 lg:shadow-none"
            }
          >
            <div className="mt-2 mr-2 lg:mt-0">
              <a href="/sign-in" className="btn btn-ghost">
                <span className="relative flex h-11 w-full items-center justify-center px-6  sm:w-max">
                  {" "}
                  Sign In
                </span>
              </a>
            </div>
            <div className="mt-2 lg:mt-0">
              <a href="/sign-up" className="btn btn-primary">
                <span className="relative text-sm font-semibold text-white">
                  {" "}
                  Get Started
                </span>
              </a>
            </div>
          </div>
        </div>
      </Container>
    </nav>
  );
}
