import React from "react";
import { NavLink, useLocation } from "react-router-dom";
import "../../../../public/css/personal_information/Navbar.css";

const NavBar = ({ showNavBar, showLinks = { contact: false, personalInfomation: false,
    emergency: false, job: false , skills: false, jobHistory: false, internalProjects: false,
    languages: false, achievements: false, feedbackReceived: false, feedbackGiven: false} }) => {
    const location = useLocation();
    const basePath = (() => {
        const regex = /^(.+?)\/(job|career|personal|feedback)/;
        const match = location.pathname.match(regex);
        return match ? match[1] : '/personal-info';
    })();



    if (!showNavBar) {
        return null;
    }

    return (
        <div className="personal-navbar p-2 mb-3 mt-3 text-muted bg-white rounded-1">
            <div className="personal-navbar-flex text-sm">
                {/* Personal */}
                {showLinks.contact && (
                    <NavLink
                        to={`${basePath}/personal/contact`}
                        className={({ isActive }) => isActive ? 'personal-nav-link active' : 'personal-nav-link'}
                    >
                        Contact
                    </NavLink>
                )}
                {showLinks.personalInfomation && (
                    <NavLink
                        to={`${basePath}/personal/personal-infomation`}
                        className={({ isActive }) => isActive ? 'personal-nav-link active' : 'personal-nav-link'}
                    >
                        Personal Information
                    </NavLink>
                )}
                {showLinks.emergency && (
                    <NavLink
                        to={`${basePath}/personal/emergency`}
                        className={({ isActive }) => isActive ? 'personal-nav-link active' : 'personal-nav-link'}
                    >
                        Emergency Contact
                    </NavLink>
                )}
                {/* Job */}
                {showLinks.job && (
                    <NavLink
                        to={`${basePath}/job/job-detail`}
                        className={({ isActive }) => isActive ? 'personal-nav-link active' : 'personal-nav-link'}
                    >
                        Job Detail
                    </NavLink>
                )}
                {/* Career */}
                {showLinks.skills && (
                    <NavLink
                        to={`${basePath}/career/skills`}
                        className={({ isActive }) => isActive ? 'personal-nav-link active' : 'personal-nav-link'}
                    >
                        Skills
                    </NavLink>
                )}
                {showLinks.jobHistory && (
                    <NavLink
                        to={`${basePath}/career/job-history`}
                        className={({ isActive }) => isActive ? 'personal-nav-link active' : 'personal-nav-link'}
                    >
                        Job History
                    </NavLink>
                )}
                {showLinks.internalProjects && (
                    <NavLink
                        to={`${basePath}/career/internal-projects`}
                        className={({ isActive }) => isActive ? 'personal-nav-link active' : 'personal-nav-link'}
                    >
                        Internal Projects
                    </NavLink>
                )}
                {showLinks.languages && (
                    <NavLink
                        to={`${basePath}/career/languages`}
                        className={({ isActive }) => isActive ? 'personal-nav-link active' : 'personal-nav-link'}
                    >
                        Languages
                    </NavLink>
                )}
                {showLinks.achievements && (
                    <NavLink
                        to={`${basePath}/career/achievements`}
                        className={({ isActive }) => isActive ? 'personal-nav-link active' : 'personal-nav-link'}
                    >
                        Achievements
                    </NavLink>
                )}
                {/* Feedback */}
                {showLinks.feedbackReceived && (
                    <NavLink
                        to={`${basePath}/feedback/received`}
                        className={({ isActive }) => isActive ? 'personal-nav-link active' : 'personal-nav-link'}
                    >
                        Feedback Received
                    </NavLink>
                )}
                {showLinks.feedbackGiven && (
                    <NavLink
                        to={`${basePath}/feedback/given`}
                        className={({ isActive }) => isActive ? 'personal-nav-link active' : 'personal-nav-link'}
                    >
                        Feedback Given
                    </NavLink>
                )}
                {/* Compensation */}
            </div>
        </div>
    );
};

export default NavBar;