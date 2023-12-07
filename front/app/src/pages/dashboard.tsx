import { Link } from "react-router-dom";
import LayoutDashboard from "../layouts/layout-dashboard";

export default function Dashboard() {
  return (
    <LayoutDashboard>
      <div id="home">
        <div className="lg:flex justify-between items-center mb-6">
          <p className="text-2xl font-semibold mb-2 lg:mb-0">
            Good afternoon, Joe!
          </p>
          <Link to={'/quizStart'}>
            <button className="bg-blue-500 hover:bg-blue-600 focus:outline-none rounded-lg px-6 py-2 text-white font-semibold shadow">
              Create a quizz
            </button>
          </Link>
        </div>
      </div>
    </LayoutDashboard>
  );
}
