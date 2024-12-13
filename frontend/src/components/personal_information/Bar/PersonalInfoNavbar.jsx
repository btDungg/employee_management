import React from "react";
import { NavLink, useLocation } from "react-router-dom";
import "../../../../public/css/personal_information/Navbar.css";

const NavBar = ({ showNavBar, showLinks }) => {
    const location = useLocation();
    const basePath = location.pathname.includes('/profile') ? location.pathname.split('/personal')[0] : '/personal-info';

    if (!showNavBar) {
        return null;
    }

    return (
        <div className="personal-navbar p-2 mb-3 mt-3 text-muted bg-white rounded-1">
            <div className="personal-navbar-flex text-sm">
                {showLinks.contact && (
                    <NavLink
                        to={`${basePath}/personal/contact`}
                        className={({ isActive } ) => isActive ? 'personal-nav-link active' : 'personal-nav-link'}
                    >
                        Thông tin liên lạc
                    </NavLink>
                )}
                {showLinks.personalInfomation && (
                    <NavLink
                        to={`${basePath}/personal/personal-infomation`}
                        className={({ isActive }) => isActive ? 'personal-nav-link active' : 'personal-nav-link'}
                    >
                        Thông tin cá nhân
                    </NavLink>
                )}
                {showLinks.emergency && (
                    <NavLink
                        to={`${basePath}/personal/emergency`}
                        className={({ isActive }) => isActive ? 'personal-nav-link active' : 'personal-nav-link'}
                    >
                        Liên lạc khẩn cấp
                    </NavLink>
                )}
            </div>
        </div>
    );
};

export default NavBar;