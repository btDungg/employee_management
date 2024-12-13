import React from "react";
import { Routes, Route } from "react-router-dom";
import TopNavBarLeaveRequest from "../components/TopNavBarLeaveRequest";
import LeaveRequestPage from "./LeaveRequestPage";
import ApproveLeaveRequestPage from "./ApproveLeaveRequestPage"
import DeleteLeaveRequestPage from "./DeleteLeaveRequestPage"; // Uncomment nếu cần

const LeaveRequestManagementPage = () => {
  return (
    <div style={{ marginTop: '100px'}}>
      {/* Top Navbar Leave Request */}
      <TopNavBarLeaveRequest />
      {/* Routes bên trong /leave-request */}
      <Routes>
        <Route path="new" element={<LeaveRequestPage />} />
        <Route path="" element={<LeaveRequestPage />} />
        <Route path="approve" element={<ApproveLeaveRequestPage />} />
        <Route path="history" element={<DeleteLeaveRequestPage />} />
      </Routes>
    </div>
  );
};

export default LeaveRequestManagementPage;
