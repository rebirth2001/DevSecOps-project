import Container from "../containter";

export default function AppFooter() {
  return (
    <footer className="py-10 md:py-20">
      <Container>
        <div className="m-auto md:w-10/12 lg:w-8/12 xl:w-6/12">
          <div className="flex flex-wrap items-center justify-between md:flex-nowrap">
            <div className="flex w-full justify-center space-x-12 text-gray-600 dark:text-gray-300 sm:w-7/12 md:justify-start">
              <ul className="list-inside list-disc space-y-4">
                <li>
                  <a href="#" className="transition hover:text-primary">
                    Home
                  </a>
                </li>

                <li>
                  <a href="#" className="transition hover:text-primary">
                    About
                  </a>
                </li>
                <li>
                  <a href="#" className="transition hover:text-primary">
                    Guide
                  </a>
                </li>
                <li>
                  <a href="#" className="transition hover:text-primary">
                    Terms of Use
                  </a>
                </li>
              </ul>
            </div>

            <div className="m-auto mt-16 w-10/12 space-y-4 text-center sm:mt-auto sm:w-5/12 sm:text-left">
              <span className="block text-gray-500 dark:text-gray-400">
                We make quizzes fun for everyone
              </span>
              <span className="block text-gray-500 dark:text-gray-400">
                Equipe Quizzly &copy; <span id="year">2023</span>
              </span>

              <span className="flex justify-between text-gray-600 dark:text-white">
                <a href="#" className="font-medium">
                  {" "}
                  Privacy Policy
                </a>
              </span>
              <span className="block text-gray-500 dark:text-gray-400">
                Need help?{" "}
                <a
                  href="#"
                  className="font-semibold text-gray-600 dark:text-white"
                >
                  {" "}
                  Contact Us
                </a>
              </span>
            </div>
          </div>
        </div>
      </Container>
    </footer>
  );
}
