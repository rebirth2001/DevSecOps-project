import HeroSection from "../components/hero-section/hero-section";
//import RegisterForm from "../components/register-form/register";
import SignInForm from "../components/sing-in-form/sign-in";
import LayoutMain from "../layouts/layout-main";
function Index() {
  return (
    <LayoutMain>
        {/* <HeroSection/> */}
        <SignInForm/>
    </LayoutMain>
  );
}

export default Index;