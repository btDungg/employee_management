import React from 'react';
import { Link, useLocation } from 'react-router-dom';
import '../../public/css/StyleSidebar.css';

const Sidebar = ({ isOpen, toggleSidebar }) => {
  const location = useLocation();
  
  const role = "Nhân viên"; // Replace with actual data
  const TenNV = "Nguyễn Văn A"; // Replace with actual data

  return (
    <>
      {/* Button to toggle sidebar on smaller screens */}
      <button className="menu-button" onClick={toggleSidebar}>
        ☰
      </button>

      <div className={`sidebar ${isOpen ? "show" : "hidden"}`}>
        <div className="sidebar-header">
          <Link to="/" className="sidebar-title">Quản lý công ty</Link>
          <button className="close-button" onClick={toggleSidebar}>×</button>
        </div>

        {/* Sidebar Links */}
        <Link to="/" className={location.pathname === "/" ? "active" : ""}>Trang chủ</Link>
        <Link to="/personal-info" className={location.pathname === "/personal-info" ? "active" : ""}>Thông tin cá nhân</Link>
        <Link to="/leave-request" className={location.pathname === "/leave-request" ? "active" : ""}>Yêu cầu nghỉ phép</Link>
        <Link to="/timesheet-update" className={location.pathname === "/timesheet-update" ? "active" : ""}>Cập nhật timesheet</Link>
        <Link to="/personal-activity" className={location.pathname === "/personal-activity" ? "active" : ""}>Hoạt động cá nhân</Link>
        <Link to="/wfh" className={location.pathname === "/wfh" ? "active" : ""}>WFH</Link>
        <Link to="/point-info" className={location.pathname === "/point-info" ? "active" : ""}>Thông tin điểm</Link>
        <Link to="/gift-points" className={location.pathname === "/gift-points" ? "active" : ""}>Tặng điểm</Link>
        <Link to="/convert-points" className={location.pathname === "/convert-points" ? "active" : ""}>Quy đổi điểm</Link>

        {/* Employee Info and Logout */}
        <div className="employee-info">
          <div>{role}</div>
          <div>{TenNV}</div>
        </div>
        <button className="logout-button">Đăng xuất</button>
      </div>
    </>
  );
};

export default Sidebar;