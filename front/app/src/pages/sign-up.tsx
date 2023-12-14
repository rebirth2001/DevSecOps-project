import LayoutMain from "../layouts/layout-main";
import Container from "../components/containter";
import FormInput from "../components/form/input";
import {
  RegisterRequest,
  registerUser,
  validateRegisterForm,
} from "../libs/user";
import React, { useState } from "react";
import { useNavigate } from "react-router";
import { Link } from "react-router-dom";

export default function SignUp() {
  const [username, setUsername] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");
  const [errorMsg, setErrorMsg] = useState("");
  const navigate = useNavigate();
  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    const request: RegisterRequest = {
      username: username.toLowerCase(),
      email: email.toLowerCase(),
      password,
      confirmPassword,
    };
    /*
     * Form values validation
     */
    if (!validateRegisterForm(request, setErrorMsg)) {
      return;
    }
    /*
     * End of sanitization.
     */

    const sucess = await registerUser(request, setErrorMsg);
    if (sucess) {
      navigate("/sign-in");
    }
  };
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
            <div
              role="alert"
              className={`alert alert-error ${errorMsg ? "" : "hidden"}`}
            >
              <svg
                xmlns="http://www.w3.org/2000/svg"
                className="stroke-current shrink-0 h-6 w-6"
                fill="none"
                viewBox="0 0 24 24"
              >
                <path
                  strokeLinecap="round"
                  strokeLinejoin="round"
                  strokeWidth="2"
                  d="M10 14l2-2m0 0l2-2m-2 2l-2-2m2 2l2 2m7-2a9 9 0 11-18 0 9 9 0 0118 0z"
                />
              </svg>
              <span>{errorMsg}</span>
            </div>
            <form onSubmit={handleSubmit}>
              <FormInput
                label="Username"
                type="text"
                name="username"
                placeholder="QUIZLY_USER"
                changeEventHandler={(event) => {
                  setUsername(event.currentTarget.value);
                }}
                required
              />
              <FormInput
                label="Email"
                type="email"
                name="email"
                placeholder="johndoe@example.com"
                changeEventHandler={(event) => {
                  setEmail(event.currentTarget.value);
                }}
                required
              />
              <FormInput
                label="Password"
                type="password"
                name="password"
                placeholder="password"
                changeEventHandler={(event) => {
                  setPassword(event.currentTarget.value);
                }}
                required
              />
              <FormInput
                label="Confirm Password"
                type="password"
                placeholder="confirm password"
                name="confirmPassword"
                changeEventHandler={(event) => {
                  setConfirmPassword(event.currentTarget.value);
                }}
                required
              />
              <div className="py-2 flex justify-center">
                <input
                  className=" w-full py-4 px-2 rounded bg-indigo-600 text-white hover:bg-indigo-700 font-bold text-lg"
                  type="submit"
                  value={"Sign up"}
                ></input>
              </div>
              <div className="py-2 flex justify-between w-full text-xs text-gray-500">
                <p>
                  Already have an account?{" "}
                  <Link to="/sign-in" className="underline">
                    Sign in
                  </Link>
                </p>
              </div>
            </form>
          </div>
        </div>
      </Container>
    </LayoutMain>
  );
}
