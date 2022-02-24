import React from "react";
import { styled } from "baseui";
import Sidebar from "./Sidebar";
import DashboardHeader from "./DashboardHeader";
import Content from "./Content";

const Dashboard = () => {
  const [open, setOpen] = React.useState(false);
  const [page, setPage] = React.useState();

  return (
    <DashboardWrapper>
      <Sidebar open={open} setOpen={setOpen} setPage={setPage} />
      <DashboardHeader open={open} setOpen={setOpen} />
      <Content pageID={page} />
    </DashboardWrapper>
  );
};
export default Dashboard;
const DashboardWrapper = styled("section", {
  display: "flex",
  flexDirection: "column",
  alignItems: "flex-start",
  background: "#F7F8FC",
  position: "relative",
  paddingLeft: "285px",
  paddingRight: "2rem",
  width: "100%",
  minHeight: "100vh",
  maxWidth: "100vw",
  boxSizing: "border-box",
  "@media (max-width: 768px)": {
    paddingLeft: "0",
  },
});
