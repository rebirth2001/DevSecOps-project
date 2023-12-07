import LayoutMain from "../layouts/layout-main";
import Container from "../components/containter";
import { useRef, useState, useEffect } from "react";
import axios from 'axios';
import { useNavigate } from "react-router-dom";

export default function SignIn() {  
  const navigate = useNavigate();
  const userRef = useRef<HTMLInputElement | null>(null);
  const errRef = useRef<HTMLDivElement | null>(null);

  const [email, setEmail] = useState<string>("");
  const [password, setPassword] = useState<string>("");
  const [errMsg, setErrMsg] = useState<string>("");
  const [success, setSuccess] = useState<boolean>(false);

  useEffect(() => {
    userRef.current?.focus();
  }, []);

  useEffect(() => {
    setErrMsg('');
  }, [email, password]);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      const response = await axios.post("http://localhost:8080/api/v1/auth/sign-in",
      JSON.stringify({ email, password }),
      {
        headers: { 'Content-Type': 'application/json' },
        withCredentials: false
      });
      console.log(JSON.stringify(response?.data));
      const accessToken = response?.data?.access_token;
      console.log(accessToken);
      if (accessToken !== null && accessToken !== "null") {
        setEmail('');
        setPassword('');
        setSuccess(true);
        navigate("/dashboard")
    } else {
        console.log("9awad");
        setSuccess(false);
    }
    }
    catch (err) {
      console.error(err);
    }
  }

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
                  Welcome back
                </h1>
                <form onSubmit={handleSubmit}>
                  <div className="py-2">
                    <label
                      htmlFor="Email"
                      className="text-sm font-semibold text-gray-800"
                    >
                      Email
                    </label>
                    <input
                      id="Email"
                      ref={userRef}
                      type="email"
                      name="email"
                      placeholder="quizly@example.com"
                      required
                      autoComplete="email"
                      className="w-full px-3 py-2 border border-gray-300 placeholder-gray-500 text-gray-800 rounded"
                      value={email}
                      onChange={(e) => setEmail(e.target.value)}
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
                      value={password}
                      onChange={(e) => setPassword(e.target.value)}
                    />
                  </div>
                  <div className="py-2 flex justify-center">
                    <button
                      className="w-full py-4 px-2 rounded bg-indigo-600 text-white hover:bg-indigo-700 font-bold text-lg"
                      type="submit"
                    >
                      Sign In
                    </button>
                  </div>
                  <div className="py-2 flex justify-between w-full text-xs text-gray-500">
                    <p>
                      Don't have an account?{" "}
                      <a href="/sign-up" className="underline">
                        Sign up
                      </a>
                    </p>
                    <p>
                      Forgot password?{" "}
                      <a href="/reset-pw" className="underline">
                        Reset password
                      </a>
                    </p>
                  </div>
                </form>
              </div>
            </div>
          </Container>
        </LayoutMain>
      )
}