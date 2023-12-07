import { PropsWithChildren, useState } from "react";
import NavBar from "../components/dashboard-components/navbar";
import SideBar from "../components/dashboard-components/sidebar";

export default function LayoutDashboard(props: PropsWithChildren) {
  const [sideBarOpen, setSideBarOpen] = useState(false);

  return (
    <>
      <div className="leading-normal tracking-normal" id="dashboard-layout">
        <div className="flex flex-wrap">
          <SideBar isOpen={sideBarOpen} />
          {/* todo: overlay reactive to sidebar  */}
          <div
            className={
              sideBarOpen
                ? "w-full bg-gray-100 pl-0 lg:pl-64 min-h-screen overlay"
                : "w-full bg-gray-100 pl-0 lg:pl-64 min-h-screen"
            }
          >
            <NavBar setSideBarState={setSideBarOpen} isOpen={sideBarOpen} />
            <div className="p-6 bg-gray-100 mb-20">{props.children}</div>
          </div>
        </div>
      </div>
    </>
  );
}
