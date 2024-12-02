import React from 'react';
import {NavLink, useNavigate} from 'react-router-dom';
import '../../../public/css/personal_information/Sidebar.css';
import {logout} from "../../apis/api.js";

const Sidebar = () => {
    const navigate = useNavigate();

    const handleLogout =  () => {
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
                    <h5>Bui Minh Duy</h5>
                    <p>Staff</p>
                    <button>Action</button>
                    <div className="profile-icons">
                        <button className="icon-button me-2"><i className="fas fa-envelope"></i></button>
                        <button className="icon-button"><i className="fas fa-users"></i></button>
                    </div>
                </div>
                <ul className="nav flex-column mt-4">
                    <li className="nav-item">
                        <NavLink className="nav-link" to="/personal-info/summary" activeClassName="active">Tóm tắt</NavLink>
                    </li>
                    <li className="nav-item">
                        <NavLink className="nav-link" to="/personal-info/overview" activeClassName="active">Tổng quan</NavLink>
                    </li>
                    <li className="nav-item">
                        <NavLink className="nav-link" to="/personal-info/job" activeClassName="active">Công việc</NavLink>
                    </li>
                    <li className="nav-item">
                        <NavLink className="nav-link" to="/personal-info/compensation" activeClassName="active">Lương thưởng</NavLink>
                    </li>
                    <li className="nav-item">
                        <NavLink className="nav-link" to="/personal-info/personal" activeClassName="active">Cá nhân</NavLink>
                    </li>
                    <li className="nav-item">
                        <NavLink className="nav-link" to="/personal-info/performance" activeClassName="active">Hiệu suất</NavLink>
                    </li>
                    <li className="nav-item">
                        <NavLink className="nav-link" to="/personal-info/career" activeClassName="active">Sự nghiệp</NavLink>
                    </li>
                    <li className="nav-item">
                        <NavLink className="nav-link" to="/personal-info/feedback" activeClassName="active">Phản hồi</NavLink>
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

export default Sidebar;