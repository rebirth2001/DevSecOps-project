import { useState } from "react";

type NavProps = {
  setSideBarState: React.Dispatch<React.SetStateAction<boolean>>;
  isOpen: boolean;
};

export default function NavBar(sideBarCntrl: NavProps) {
  let [dropDownOpen, setDropDown] = useState(false);
  return (
    <>
      <div className="sticky top-0 z-40">
        <div className="w-full h-20 px-6 bg-gray-100 border-b flex items-center justify-between">
          <div className="flex">
            {/* mobile ham menu */}
            <div className="inline-block lg:hidden flex items-center mr-4">
              <button
                className="hover:text-blue-500 hover:border-white focus:outline-none navbar-burger"
                onClick={() => {
                  sideBarCntrl.setSideBarState(!sideBarCntrl.isOpen);
                }}
              >
                <svg
                  className="h-5 w-5"
                  v-bind:style="{ fill: 'black' }"
                  viewBox="0 0 20 20"
                  xmlns="http://www.w3.org/2000/svg"
                >
                  <title>Menu</title>
                  <path d="M0 3h20v2H0V3zm0 6h20v2H0V9zm0 6h20v2H0v-2z" />
                </svg>
              </button>
            </div>
          </div>

          {/* <!-- navbar right side --> */}
          <div className="flex items-center relative">
            <svg
              xmlns="http://www.w3.org/2000/svg"
              height="24"
              viewBox="0 0 24 24"
              width="24"
              className="fill-current mr-3 hover:text-blue-500"
            >
              <path d="M0 0h24v24H0z" fill="none" />
              <path d="M12 22c1.1 0 2-.9 2-2h-4c0 1.1.9 2 2 2zm6-6v-5c0-3.07-1.63-5.64-4.5-6.32V4c0-.83-.67-1.5-1.5-1.5s-1.5.67-1.5 1.5v.68C7.64 5.36 6 7.92 6 11v5l-2 2v1h16v-1l-2-2zm-2 1H8v-6c0-2.48 1.51-4.5 4-4.5s4 2.02 4 4.5v6z" />
            </svg>
            <img
              src="https://a7sas.net/wp-content/uploads/2019/07/4060.jpeg"
              className="w-12 h-12 rounded-full shadow-lg"
              onClick={() => {
                setDropDown(!dropDownOpen);
              }}
            />
          </div>
        </div>

        <div
          className={
            dropDownOpen
              ? "absolute bg-gray-100 border border-t-0 shadow-xl text-gray-700 rounded-b-lg w-48 bottom-10 right-0 mr-6"
              : "absolute bg-gray-100 border border-t-0 shadow-xl text-gray-700 rounded-b-lg w-48 bottom-10 right-0 mr-6 hidden"
          }
        >
          <a href="#" className="block px-4 py-2 hover:bg-gray-200">
            Logout
          </a>
        </div>
      </div>
    </>
  );
}
