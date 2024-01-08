import { LockOutlined, UserOutlined } from "@ant-design/icons";
import { Button, Form, Input, notification } from "antd";
import FormItem from "antd/es/form/FormItem";
import { FormEvent } from "react";
import { Link } from "react-router-dom";
import { login } from "../../utils/api";
import { ACCESS_TOKEN } from "../../constants";

export interface LoginProps {
  handleLogin: () => void;
}
export default function Login(props: LoginProps) {
  const handleSubmit = (event: FormEvent) => {
    event.preventDefault();
    const loginRequest: LoginRequest = {
      email:
        event.currentTarget.querySelector<HTMLInputElement>("#email")?.value!,
      password:
        event.currentTarget.querySelector<HTMLInputElement>("#password")
          ?.value!,
    };
    login(loginRequest)
      .then((response) => {
        localStorage.setItem(ACCESS_TOKEN, response.accessToken);
        props.handleLogin();
      })
      .catch((error) => {
        if (error.status === 401) {
          notification.error({
            message: "Quizly Server",
            description:
              "Your Username or Password is incorrect. Please try again!",
          });
        } else {
          notification.error({
            message: "Quizly Server",
            description: "Sorry! Something went wrong. Please try again!",
          });
        }
      });
  };

  return (
    <div className="login-container">
      <h1 className="page-title">Login</h1>
      <div className="login-content">
        <Form onSubmitCapture={handleSubmit} className="login-form">
          <FormItem>
            <Input
              prefix={<UserOutlined />}
              size="large"
              id="email"
              name="email"
              placeholder="Email"
              required
            />
          </FormItem>
          <FormItem>
            <Input
              prefix={<LockOutlined />}
              size="large"
              name="password"
              id="password"
              type="password"
              placeholder="Password"
              required
            />
          </FormItem>
          <FormItem>
            <Button
              type="primary"
              htmlType="submit"
              size="large"
              className="login-form-button"
            >
              Login
            </Button>
            Or <Link to="/sign-up">register now!</Link>
          </FormItem>
        </Form>
      </div>
    </div>
  );
}
