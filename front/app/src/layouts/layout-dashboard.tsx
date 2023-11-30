import { PropsWithChildren } from "react";
import NavBar from "../components/dashboard-components/navbar";
import AppFooter from "../components/footer/appfooter";

export default function LayoutDashboard(props: PropsWithChildren) {
  return (
    <>
      <NavBar />
      {props.children}
      <AppFooter />
    </>
  );
}
