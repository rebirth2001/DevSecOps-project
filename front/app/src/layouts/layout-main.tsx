import { PropsWithChildren } from "react";
import NavBar from "../components/navbar/navbar";
import AppFooter from "../components/footer/appfooter";

export default function LayoutMain(props: PropsWithChildren) {
  return (
    <>
      <NavBar />
      {props.children}
      <AppFooter />
    </>
  );
}
