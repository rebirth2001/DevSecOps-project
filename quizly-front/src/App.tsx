import { Layout, notification } from "antd";
import "./App.css";
import AppHeader from "./components/common/AppHeader";
import { Content } from "antd/es/layout/layout";
import { Navigate, Route, Routes, useNavigate } from "react-router-dom";
import NewQuiz from "./components/quiz/NewQuiz";
import SignUp from "./components/signup/signup";
import Login from "./components/login/login";
import HeroPage from "./components/hero_page/hero_page";
import { useEffect, useState } from "react";
import { UserProfile } from "./lib/user";
import { getCurrentUser } from "./utils/api";
import { ACCESS_TOKEN } from "./constants";
import LoadingIndicator from "./components/common/LoadingIndicator";
import NotFound from "./components/common/NotFound";
import Profile from "./components/profile/profile";
import QuizList from "./components/quiz/QuizList";

function App() {
  const navigate = useNavigate();
  const [isAuthenticated, setIsAuthenticated] = useState(false);
  const [isLoading, setIsLoading] = useState(true);
  const [currentUser, setCurrentUser] = useState<UserProfile | null>(null);

  notification.config({
    placement: "topRight",
    top: 70,
    duration: 3,
  });

  const loadCurrentUser = () => {
    getCurrentUser()
      .then((response) => {
        setCurrentUser(response);
        setIsAuthenticated(true);
        setIsLoading(false);
      })
      .catch((_) => {
        // setCurrentUser(testUser);
        // setIsAuthenticated(true);
        setIsLoading(false);
      });
  };

  const handleLogout = () => {
    localStorage.removeItem(ACCESS_TOKEN);
    setCurrentUser(null);
    setIsAuthenticated(false);
    navigate("/");
    notification.success({
      message: "Success",
      description: "You're successfully logged out.",
    });
  };

  const handleLogin = () => {
    notification.success({
      message: "Success",
      description: "You're successfully logged in.",
    });
    loadCurrentUser();
    navigate("/");
  };

  useEffect(() => {
    loadCurrentUser();
  }, []);

  if (isLoading) {
    return <LoadingIndicator />;
  }

  return (
    <Layout className="app-container">
      <AppHeader
        isAuthenticated={isAuthenticated}
        currentUser={currentUser}
        onLogout={handleLogout}
      />
      <Content className="app-content">
        <div className="container">
          <Routes>
            <Route
              path="/"
              element={
                isAuthenticated ? (
                  <QuizList username={undefined} listType={undefined} />
                ) : (
                  <HeroPage />
                )
              }
            />
            <Route path="/sign-up" element={<SignUp />} />
            <Route
              path="/log-in"
              element={<Login handleLogin={handleLogin} />}
            />
            <Route
              path="/users/:username"
              element={
                isAuthenticated ? (
                  <Profile
                    currentUser={currentUser!}
                    isAuthenticated={isAuthenticated}
                  />
                ) : (
                  <Navigate replace={true} to="/log-in" />
                )
              }
            />
            <Route
              path="/quiz/new"
              element={
                isAuthenticated ? (
                  <NewQuiz />
                ) : (
                  <Navigate replace={true} to="/log-in" />
                )
              }
            />
            {/* Catch all routes */}
            <Route path="/*" element={<NotFound />} />
          </Routes>
        </div>
      </Content>
    </Layout>
  );
}

export default App;
