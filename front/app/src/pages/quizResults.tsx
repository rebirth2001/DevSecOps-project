import LayoutDashboard from "../layouts/layout-dashboard";

export default function QuizResults() {
  return (
    <LayoutDashboard>
      <div id="home">
        <div className="lg:flex justify-between items-center mb-6">
          <p className="text-2xl font-semibold mb-2 lg:mb-0">
            DevSecOps
          </p>
        </div>
      </div>
      <div className="overflow-x-auto">
        <table className="table">
          {/* head */}
          <thead>
            <tr>
              <th>ID</th>
              <th>Participant Name</th>
              <th>Result</th>
            </tr>
          </thead>
          <tbody>
            {/* row 1 */}
            <tr>
              <th>1</th>
              <td>ZamameAYoub</td>
              <td>15/20</td>
            </tr>
            <tr>
              <th>2</th>
              <td>mahrache</td>
              <td>0/20</td>
            </tr>
            <tr>
              <th>3</th>
              <td>joui</td>
              <td>2/20</td>
            </tr>
            <tr>
              <th>4</th>
              <td>imad</td>
              <td>3/20</td>
            </tr>
          </tbody>
        </table>
      </div>
    </LayoutDashboard>
  );
}