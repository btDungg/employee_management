import React from 'react';
import { NavLink, useNavigate } from 'react-router-dom';
import '../../../../public/css/personal_information/Sidebar.css';
import { logout } from "../../../apis/api.js";
import TooltipWithClick from "./TooltipWithClick.jsx";


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
        <div className="sidebar sidebar-personal d-flex flex-column justify-content-between">
            <div>
                <div className="profile-section text-center py-2">
                    {/* Avatar */}
                    <div className="avatar mb-1">
                        <img src={employee?.avatarUrl || "/default-avatar.png"} alt="Avatar"
                             className="rounded-circle avatar"/>
                    </div>
                    {/* Hiển thị thông tin nhân viên */}
                    <h5>{employee?.name || "Khách"}</h5>
                    <p>{employee?.type || "Không xác định"}</p>
                    <div className="profile-icons">
                        <TooltipWithClick emailCompany={employee?.emailCompany}/>
                        <button className="icon-button" onClick={() => navigate('/personal-info/members')}><i
                            className="fas fa-users"></i></button>
                    </div>
                </div>
                <ul className="nav flex-column">
                    <li className="nav-item">
                        <NavLink to="/personal-info/summary"
                                 className={({isActive}) => isActive ? 'nav-link active' : 'nav-link'}>
                            <i className="fas fa-chart-pie me-2"></i>Tóm tắt
                        </NavLink>
                    </li>
                    <li className="nav-item">
                        <NavLink to="/personal-info/overview"
                                 className={({isActive}) => isActive ? 'nav-link active' : 'nav-link'}>
                            <i className="fas fa-info-circle me-2"></i>Tổng quan
                        </NavLink>
                    </li>
                    <li className="nav-item">
                        <NavLink to="/personal-info/job"
                                 className={({isActive}) => isActive ? 'nav-link active' : 'nav-link'}>
                            <i className="fas fa-briefcase me-2"></i>Công việc
                        </NavLink>
                    </li>
                    <li className="nav-item">
                        <NavLink to="/personal-info/compensation"
                                 className={({isActive}) => isActive ? 'nav-link active' : 'nav-link'}>
                            <i className="fas fa-dollar-sign me-2"></i>Lương thưởng
                        </NavLink>
                    </li>
                    <li className="nav-item">
                        <NavLink to="/personal-info/personal"
                                 className={({isActive}) => isActive ? 'nav-link active' : 'nav-link'}>
                            <i className="fas fa-user me-2"></i>Cá nhân
                        </NavLink>
                    </li>
                    <li className="nav-item">
                        <NavLink to="/personal-info/performance"
                                 className={({isActive}) => isActive ? 'nav-link active' : 'nav-link'}>
                            <i className="fas fa-chart-line me-2"></i>Hiệu suất
                        </NavLink>
                    </li>
                    <li className="nav-item">
                        <NavLink to="/personal-info/career"
                                 className={({isActive}) => isActive ? 'nav-link active' : 'nav-link'}>
                            <i className="fas fa-graduation-cap me-2"></i>Sự nghiệp
                        </NavLink>
                    </li>
                    <li className="nav-item">
                        <NavLink to="/personal-info/feedback"
                                 className={({isActive}) => isActive ? 'nav-link active' : 'nav-link'}>
                            <i className="fas fa-comments me-2"></i>Phản hồi
                        </NavLink>
                    </li>
                    {employee.type === "HR" && (
                        <li className="nav-item">
                            <NavLink to="/personal-info/manager" className={({ isActive }) => isActive ? 'nav-link active' : 'nav-link'}>
                                <i className="fas fa-user-tie me-2"></i>Quản lý
                            </NavLink>
                        </li>
                    )}
                </ul>
                <NavLink to="/"
                         className={({isActive}) => isActive ? 'nav-link active' : 'nav-link'}>
                    <i className="fas fa-home me-2"></i>Trang chủ
                </NavLink>
            </div>
        </div>
    );
};

export default PersonalInfoSidebar;
