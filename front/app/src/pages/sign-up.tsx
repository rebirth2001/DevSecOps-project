import LayoutMain from "../layouts/layout-main";
import Container from "../components/containter";
import FormInput from "../components/form/input";
import { RegisterRequest, RegisterUser } from "../libs/user";
import React, { useState } from "react";

export default function SignUp() {
  const [username, setUsername] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");
  const handleSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    /* sanitize data and show errors*/
    /*... */
    /* Send register request */
    const registerData: RegisterRequest = {
      username: username,
      email: email,
      password: password,
      confirmPassword: confirmPassword,
    };

    console.log("Request sent to the server", registerData);

    const success = await RegisterUser(registerData);
    if (success) {
      alert("Success");
    } else {
      alert("Failed");
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
            <form onSubmit={handleSubmit}>
              <FormInput
                label="Username"
                type="text"
                name="username"
                placeholder="quiz_dady2002"
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
