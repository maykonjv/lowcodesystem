import React from "react";
import { styled, useStyletron } from "baseui";
import logo from "../assets/images/logo.svg";
import SideNavListItem from "./SideNavListItem";
import { menuData } from "../assets/constants";

const Sidebar = ({ open, setOpen, setPage }) => {
  const [css] = useStyletron();
  const [active, setActive] = React.useState(null);
  return (
    <SidebarWrapper
      className={css({
        "@media (max-width: 768px)": {
          display: open ? "block" : "none",
        },
      })}
    >
      <div
        className={css({
          position: "fixed",
          top: "0",
          left: "0",
          width: "100vw",
          background: "rgba(0, 0, 0, 0.5)",
          height: "100vh",
          zIndex: "-1",
          display: "none",
          "@media (max-width: 768px)": {
            display: open ? "block" : "none",
          },
        })}
        onClick={() => setOpen(false)}
      />
      <Logo>
        <img
          className={css({
            width: "2rem",
            height: "2rem",
            marginRight: "0.5rem",
          })}
          src={logo}
          alt="logo"
        />
        Dashboard Kit
      </Logo>
      {menuData.map(({ icon, title, page }, index) => {
        if (active === index) setPage(page);
        return (
          <SideNavListItem
            key={index}
            active={active === index}
            title={title}
            id={index}
            setActive={setActive}
          >
            {icon}
          </SideNavListItem>
        );
      })}
    </SidebarWrapper>
  );
};
export default Sidebar;
const SidebarWrapper = styled("section", {
  position: "fixed",
  top: "0",
  left: "0",
  width: "100%",
  maxWidth: "255px",
  height: "100vh",
  background: "#363740",
  zIndex: "1",
  overflowX: "hidden",
});
const Logo = styled("div", {
  padding: "2.5rem 2rem",
  display: "flex",
  alignItems: "center",
  justifyContent: "center",
  fontSize: "1.25rem",
  color: "#f2f2f2",
  fontWeight: "bold",
  boxSizing: "border-box",
  background: "none",
});
