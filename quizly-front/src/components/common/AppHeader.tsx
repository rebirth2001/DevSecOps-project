import { Dropdown, Menu, MenuProps } from "antd";
import { Header } from "antd/es/layout/layout";
import { Link, useLocation } from "react-router-dom";
import "./AppHeader.css";
import {
  CaretDownOutlined,
  HomeOutlined,
  PlusSquareOutlined,
  UserOutlined,
} from "@ant-design/icons";
import { UserProfile } from "../../lib/user";

export interface AppHeaderProps {
  isAuthenticated: boolean;
  currentUser: UserProfile | null;
  onLogout: () => void;
}

export default function AppHeader(props: AppHeaderProps) {
  const handleMenuClick = (key: string) => {
    if (key === "logout") {
      props.onLogout();
    }
  };
  let headerItems: MenuProps["items"];
  if (props.isAuthenticated) {
    headerItems = [
      {
        label: (
          <Link to={"/"}>
            <HomeOutlined />
          </Link>
        ),
        key: "/",
        className: "nav-icon",
      },
      {
        label: (
          <Link to={"/quiz/new"}>
            <PlusSquareOutlined />
          </Link>
        ),
        key: "/quiz/new",
      },
      {
        label: (
          <ProfileDropdownMenu
            currentUser={props.currentUser!}
            handleMenuClick={handleMenuClick}
          />
        ),
        key: "/profile",
        className: "profile-menu",
      },
    ];
  } else {
    headerItems = [
      {
        label: <Link to={"/log-in"}>Login</Link>,
        key: "/log-in",
      },
      {
        label: <Link to={"/sign-up"}>Signup</Link>,
        key: "/sign-up",
      },
    ];
  }

  const location = useLocation();
  return (
    <Header className="app-header">
      <div className="container">
        <div className="app-title">
          <Link to={"/"}>Quizly</Link>
        </div>
        <Menu
          className="app-menu"
          mode="horizontal"
          selectedKeys={[location.pathname]}
          style={{ lineHeight: "64px" }}
          items={headerItems}
        ></Menu>
      </div>
    </Header>
  );
}

interface ProfileDropdownMenuProps {
  currentUser: UserProfile;
  handleMenuClick: (key: string) => void;
}
function ProfileDropdownMenu(props: ProfileDropdownMenuProps) {
  const dropdownMenu = (
    <Menu
      onClick={(info: any) => {
        props.handleMenuClick(info.key);
      }}
      className="profile-dropdown-menu"
    >
      <Menu.Item key="user-info" className="dropdown-item" disabled>
        <div className="user-full-name-info">{props.currentUser.username}</div>
        <div className="username-info">@{props.currentUser.username}</div>
      </Menu.Item>
      <Menu.Divider />
      <Menu.Item key="profile" className="dropdown-item">
        <Link to={`/users/${props.currentUser.username}`}>Profile</Link>
      </Menu.Item>
      <Menu.Item key="logout" className="dropdown-item">
        Logout
      </Menu.Item>
    </Menu>
  );
  // const dropdownMenu: MenuProps["items"] = [
  //   {
  //     label: <Link to={`/users/${props.currentUser.username}`}>Profile</Link>,
  //     key: "profile",
  //     className: "dropdown-item",
  //   },
  //   {
  //     label: <span>Logout</span>,
  //     key: "logout",
  //     className: "dropdown-item",
  //   },
  // ];

  return (
    <Dropdown
      overlay={dropdownMenu}
      trigger={["click"]}
      getPopupContainer={(_: HTMLElement): HTMLElement => {
        return document.querySelector(".profile-menu")!;
      }}
    >
      <a className="ant-dropdown-link">
        <UserOutlined className="nav-icon" style={{ marginRight: 0 }} />{" "}
        <CaretDownOutlined />
      </a>
    </Dropdown>
  );
}
