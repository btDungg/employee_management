import React from "react";
import { NavLink } from "react-router-dom";
import "../../../../public/css/personal_information/Navbar.css";

const NavBar = ({ showNavBar, showLinks }) => {
    if (!showNavBar) {
        return null;
    }

    return (
        <div className="personal-navbar p-2 mb-3">
            <div className="personal-navbar-flex text-sm">
                {showLinks.contact && (
                    <NavLink
                        to="/personal-info/personal/contact"
                        className={({ isActive }) => isActive ? 'personal-nav-link active' : 'personal-nav-link'}
                    >
                        Thông tin liên lạc
                    </NavLink>
                )}
                {showLinks.personalInfomation && (
                    <NavLink
                        to="/personal-info/personal/personal-infomation"
                        className={({ isActive }) => isActive ? 'personal-nav-link active' : 'personal-nav-link'}
                    >
                        thông tin cá nhân
                    </NavLink>
                )}
                {showLinks.emergency && (
                    <NavLink
                        to="/personal-info/personal/emergency"
                        className={({ isActive }) => isActive ? 'personal-nav-link active' : 'personal-nav-link'}
                    >
                        liên lạc khẩn cấp
                    </NavLink>
                )}
            </div>
        </div>
    );
};

export default NavBar;