import LayoutMain from "../layouts/layout-main";
import Container from "../components/containter";

export default function SignUp() {
  return (
    <LayoutMain>
      <div
        aria-hidden="true"
        className="absolute inset-0 grid grid-cols-2 -space-x-52 opacity-40 dark:opacity-20"
      >
        <div className="blur-[106px] h-56 bg-gradient-to-br from-primary to-purple-400 dark:from-blue-700"></div>
        <div className="blur-[106px] h-32 bg-gradient-to-r from-cyan-400 to-sky-300 dark:to-indigo-600"></div>
      </div>
      <Container>
        <div className="relative pt-36 ml-auto flex justify-center py-12">
          <div className="px-8 py-8 rounded bg-white w-2/5 shadow">
            <h1 className="mb-4 text-3xl font-bold text-gray-900 text-center">
              Start making quizzes today!
            </h1>
            <form action="/sign-up/submit" method="post">
              <div className="py-2">
                <label
                  htmlFor="Email"
                  className="text-sm font-semibold text-gray-800"
                >
                  Email
                </label>
                <input
                  id="Email"
                  type="email"
                  name="email"
                  placeholder="johndoe@example.com"
                  required
                  autoComplete="email"
                  className="w-full px-3 py-2 border border-gray-300 placeholder-gray-500 text-gray-800 rounded"
                />
              </div>
              <div className="py-2">
                <label
                  htmlFor="Password"
                  className="text-sm font-semibold text-gray-800"
                >
                  Password
                </label>
                <input
                  id="Password"
                  type="password"
                  name="password"
                  placeholder="password"
                  required
                  className="w-full px-3 py-2 border border-gray-300 placeholder-gray-500 text-gray-800 rounded"
                />
              </div>
              <div className="py-2">
                <label
                  htmlFor="Confirm-Password"
                  className="text-sm font-semibold text-gray-800"
                >
                  Confirm Password
                </label>
                <input
                  id="Confirm-Password"
                  type="password"
                  placeholder="confirm password"
                  name="confirmed-password"
                  required
                  className="w-full px-3 py-2 border border-gray-300 placeholder-gray-500 text-gray-800 rounded"
                />
              </div>
              <div className="py-2 flex justify-center">
                <button
                  className=" w-full py-4 px-2 rounded bg-indigo-600 text-white hover:bg-indigo-700 font-bold text-lg"
                  type="submit"
                >
                  Sign up
                </button>
              </div>
              <div className="py-2 flex justify-between w-full text-xs text-gray-500">
                <p>
                  Already have an account?{" "}
                  <a href="/sign-in" className="underline">
                    Sign in
                  </a>
                </p>
              </div>
            </form>
          </div>
        </div>
      </Container>
    </LayoutMain>
  );
}
