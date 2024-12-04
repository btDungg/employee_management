import React from 'react';
import { NavLink, useNavigate } from 'react-router-dom';
import '../../../public/css/personal_information/Sidebar.css';
import { logout } from "../../apis/api.js";

const PersonalInfoSidebar = ({ employee }) => {
    const navigate = useNavigate();

    const handleLogout = () => {
        try {
            logout();
            navigate('/login', { replace: true });
        } catch (error) {
            console.error('Đăng xuất thất bại:', error);
        }
    };

    const handleHomeClick = () => {
        navigate('/');
    };

    return (
        <div className="col-4 sidebar d-flex flex-column justify-content-between">
            <div>
                <div className="profile-section text-center py-4">
                    <h5>{employee?.name || "Guest"}</h5>
                    <p>{employee?.type || "N/A"}</p>
                    <button>Action</button>
                    <div className="profile-icons">
                        <button className="icon-button me-2"><i className="fas fa-envelope"></i></button>
                        <button className="icon-button" onClick={() => navigate('/personal-info/members')}><i className="fas fa-users"></i></button>
                    </div>
                </div>
                <ul className="nav flex-column mt-4">
                    <li className="nav-item">
                        <NavLink to="/personal-info/summary" className={({ isActive }) => isActive ? 'nav-link active' : 'nav-link'}>Tóm tắt</NavLink>
                    </li>
                    <li className="nav-item">
                        <NavLink to="/personal-info/overview" className={({ isActive }) => isActive ? 'nav-link active' : 'nav-link'}>Tổng quan</NavLink>
                    </li>
                    <li className="nav-item">
                        <NavLink to="/personal-info/job" className={({ isActive }) => isActive ? 'nav-link active' : 'nav-link'}>Công việc</NavLink>
                    </li>
                    <li className="nav-item">
                        <NavLink to="/personal-info/compensation" className={({ isActive }) => isActive ? 'nav-link active' : 'nav-link'}>Lương thưởng</NavLink>
                    </li>
                    <li className="nav-item">
                        <NavLink to="/personal-info/personal" className={({ isActive }) => isActive ? 'nav-link active' : 'nav-link'}>Cá nhân</NavLink>
                    </li>
                    <li className="nav-item">
                        <NavLink to="/personal-info/performance" className={({ isActive }) => isActive ? 'nav-link active' : 'nav-link'}>Hiệu suất</NavLink>
                    </li>
                    <li className="nav-item">
                        <NavLink to="/personal-info/career" className={({ isActive }) => isActive ? 'nav-link active' : 'nav-link'}>Sự nghiệp</NavLink>
                    </li>
                    <li className="nav-item">
                        <NavLink to="/personal-info/feedback" className={({ isActive }) => isActive ? 'nav-link active' : 'nav-link'}>Phản hồi</NavLink>
                    </li>
                </ul>
            </div>
            <div className="mt-4">
                <button className="btn btn-primary w-100 mb-2" onClick={handleHomeClick}>Trang chủ</button>
                <button className="btn btn-danger w-100" onClick={handleLogout}>Đăng xuất</button>
            </div>
        </div>
    );
};

export default PersonalInfoSidebar;