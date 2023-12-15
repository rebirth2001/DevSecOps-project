import { Link } from 'react-router-dom';
import LayoutDashboard from '../layouts/layout-dashboard';
import { useEffect, useState } from 'react';
import axios from 'axios';

interface Quiz {
  id: number;
  codeOfInvitation: string;
  description: string;
  title: string;
  questionNumber: number;
  questions: any[];
  participant: any[];
}

export default function QuizList(): JSX.Element {
  const [quizzes, setQuizzes] = useState<Quiz[]>([]);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await axios.get<Quiz[]>('http://localhost:8080/api/v1/quizzes');
        setQuizzes(response.data);
      } catch (error) {
        console.error('Error getting data:', error);
      }
    };

    fetchData();
  }, []);

  return (
    <LayoutDashboard>
      <div id="home">
        <div className="lg:flex justify-between items-center mb-6">
          <p className="text-2xl font-semibold mb-2 lg:mb-0">Quiz List</p>
          <Link to={'/create-quiz'}>
            <button className="bg-blue-500 hover:bg-blue-600 focus:outline-none rounded-lg px-6 py-2 text-white font-semibold shadow">
              Create a new quiz
            </button>
          </Link>
        </div>
      </div>
      <div className="overflow-x-auto">
        <table className="table">
          <thead>
            <tr>
              <th>ID</th>
              <th>Quiz Name</th>
              <th>Question Number</th>
              <th>Quiz Code</th>
              <th>Action</th>
            </tr>
          </thead>
          <tbody>
            {quizzes.map((quiz) => (
              <tr key={quiz.id}>
                <th>{quiz.id}</th>
                <td>{quiz.title}</td>
                <td>{quiz.questionNumber}</td>
                <td>{quiz.codeOfInvitation}</td>
                <td>
                  <button className="btn btn-circle btn-outline mr-2" title="Edit">
                    <svg
                      className="w-6 h-6"
                      aria-hidden="true"
                      xmlns="http://www.w3.org/2000/svg"
                      fill="none"
                      viewBox="0 0 18 18"
                    >
                      <path
                        stroke="currentColor"
                        strokeLinecap="round"
                        strokeLinejoin="round"
                        strokeWidth="2"
                        d="M9 1v16M1 9h16"
                      />
                    </svg>
                  </button>
                  <button className="btn btn-circle btn-outline mr-2" title="Delete">
                    <svg
                      xmlns="http://www.w3.org/2000/svg"
                      className="h-6 w-6"
                      fill="none"
                      viewBox="0 0 24 24"
                      stroke="currentColor"
                    >
                      <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M6 18L18 6M6 6l12 12" />
                    </svg>
                  </button>
                  <Link to={`/quizResults/${quiz.id}`}>
                    <button className="btn btn-circle btn-outline" title="Results">
                      <svg
                        className="w-6 h-6 text-gray-800 stroke-blue-300 fill-none"
                        xmlns="http://www.w3.org/2000/svg"
                        width="50px"
                        height="50px"
                        viewBox="0 -0.5 25 25"
                      >
                        <path
                          stroke-linejoin="round"
                          stroke-linecap="round"
                          strokeWidth="1.5"
                          d="M15.17 11.053L11.18 15.315C10.8416 15.6932 10.3599 15.9119 9.85236 15.9178C9.34487 15.9237 8.85821 15.7162 8.51104 15.346C7.74412 14.5454 7.757 13.2788 8.54004 12.494L13.899 6.763C14.4902 6.10491 15.3315 5.72677 16.2161 5.72163C17.1006 5.71649 17.9463 6.08482 18.545 6.736C19.8222 8.14736 19.8131 10.2995 18.524 11.7L12.842 17.771C12.0334 18.5827 10.9265 19.0261 9.78113 18.9971C8.63575 18.9682 7.55268 18.4695 6.78604 17.618C5.0337 15.6414 5.07705 12.6549 6.88604 10.73L12.253 5"></path>
                    </svg>
                </button>
                </Link>
              </td>
            </tr>
            ))}
          </tbody>
        </table>
      </div>
    </LayoutDashboard>
  );
}