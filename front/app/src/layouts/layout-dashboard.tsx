import { PropsWithChildren } from "react";
import NavBar from "../components/dashboard-components/navbar";
import AppFooter from "../components/footer/appfooter";
import SideBar from "../components/dashboard-components/sidebar";

export default function LayoutDashboard(props: PropsWithChildren) {
  return (
    <>
      <div className="leading-normal tracking-normal" id="dashboard-layout">
        <div className="flex flex-wrap">
          <SideBar />
          {/* todo: overlay reactive to sidebar  */}
          <div className="w-full bg-gray-100 pl-0 lg:pl-64 min-h-screen overlay">
            <NavBar />
            <div className="p-6 bg-gray-100 mb-20">{props.children}</div>

            <AppFooter />
          </div>
        </div>
      </div>
    </>
  );
}
