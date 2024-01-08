import { Button, Flex } from "antd";

import "./hero_page.css";
import { useNavigate } from "react-router-dom";

export default function HeroPage() {
  const navigate = useNavigate();
  const register = () => {
    navigate("/sign-up");
  };
  const signIn = () => {
    navigate("/log-in");
  };
  return (
    <div className="centered-container">
      <Flex justify="center" align="center" vertical>
        <h1>Make quizzes everyone can enjoy.</h1>
        <div>
          <Button
            type="default"
            htmlType="button"
            size="large"
            className="button"
            onClick={signIn}
          >
            Log In
          </Button>
          <Button
            type="primary"
            htmlType="button"
            size="large"
            className="button"
            onClick={register}
          >
            Register
          </Button>
        </div>
      </Flex>
    </div>
  );
}
