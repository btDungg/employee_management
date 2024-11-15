import React, { useState, useEffect } from 'react';
import { Link, useLocation } from 'react-router-dom';
import '../../public/css/StyleSidebar.css';
import { getEmployeeProfile,logout } from '../apis/api';

const Sidebar = ({ isOpen, toggleSidebar }) => {
  const location = useLocation();

  // State để lưu thông tin người dùng
  const [role, setRole] = useState('');
  const [TenNV, setTenNV] = useState('');

  // Gọi API để lấy thông tin người dùng
  useEffect(() => {
    const fetchProfile = async () => {
      try {
        const profileData = await getEmployeeProfile();  // Lấy thông tin người dùng từ API
        setRole(profileData.type);  // Giả sử trả về role là type
        setTenNV(profileData.name);  // Giả sử trả về tên là name
      } catch (error) {
        console.error('Không thể lấy thông tin người dùng', error);
      }
    };

    fetchProfile();
  }, []);  // Chỉ gọi 1 lần khi component được load


  // Hàm xử lý logout
  const handleLogout = async () => {
    try {
      // Gửi yêu cầu đến API logout
      logout();
    } catch (error) {
      console.error('Đăng xuất thất bại', error);
    }
  };

  // //du lieu mau
  // const role = "Nhân viên";
  // const TenNV = "Nguyễn Văn A";

  return (
      <>
        {/* Sidebar Button */}
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
          <button className="logout-button" onClick={handleLogout}>Đăng xuất</button>
        </div>
      </>
  );
};

export default Sidebar;
