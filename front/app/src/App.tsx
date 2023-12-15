import "./App.css";
import Index from "./pages";
import { RouterProvider, createBrowserRouter } from "react-router-dom";
import SignIn from "./pages/sign-in";
import SignUp from "./pages/sign-up";
import Dashboard from "./pages/dashboard";
import QuizList from "./pages/quizList";
import Quiz from "./pages/quiz";
import QuizResults from "./pages/quizResults";
import CreateQuiz from "./pages/createQuiz";

function App() {
  const router = createBrowserRouter([
    {
      path: "/",
      element: <Index />,
    },
    {
      path: "/sign-in",
      element: <SignIn />,
    },
    {
      path: "/create-quiz",
      element: <CreateQuiz/>
    },
    {
      path: "/sign-up",
      element: <SignUp />,
    },
    {
      path: "/dashboard",
      element: <Dashboard />,
    },
    {
      path: "/quizList",
      element: <QuizList />,
    },
    {
      path: "/quiz",
      element: <Quiz />,
    },
    {
      path: "/quizResults",
      element: <QuizResults />,
    },
  ]);
  return (
    <>
      <RouterProvider router={router} />
    </>
  );
}

export default App;
