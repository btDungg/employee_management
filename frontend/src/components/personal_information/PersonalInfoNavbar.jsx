import React from "react";
import "../../../public/css/personal_information/Navbar.css";

const NavBar = () => {
    return (
        <div className="navbar bg-gray-200 p-2 shadow-sm">
            <div className="flex space-x-2 text-sm">
                <a href="#contact" className="nav-link">Contact</a>
                <a href="#functional" className="nav-link">Functional Information</a>
                <a href="#emergency" className="nav-link">Emergency Contact</a>
            </div>
        </div>
    );
};

export default NavBar;