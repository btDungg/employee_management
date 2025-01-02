import React, {useContext} from 'react';
import { NavLink, useNavigate } from 'react-router-dom';
import '../../../../public/css/personal_information/Sidebar.css';
import TooltipWithClick from "./TooltipWithClick.jsx";
import {UserContext} from "../UserProvider.jsx";

const ProfileSidebar = ({ employee }) => {
    const navigate = useNavigate();
    const employeeId = employee?.id;
    const context = useContext(UserContext);
    if (!context) {
        return <div>Error: Context not found</div>;
    }
    const { user, loading, error } = context;
    if (loading) {
        return <div>Loading...</div>;
    }

    if (error) {
        return <div>{error}</div>;
    }

    return (
        <div className="sidebar sidebar-personal d-flex flex-column justify-content-between">
            <div className="flex-grow-1">
                <div className="profile-section text-center py-2">
                    <div className="avatar mb-1">
                        <img src={employee?.avatarUrl || "/default-avatar.png"} alt="Avatar"
                             className="rounded-circle avatar"/>
                    </div>
                    <h5>{employee?.name || "Guest"}</h5>
                    <p>{employee?.type || "Unknown"}</p>
                    <div className="profile-icons">
                        <TooltipWithClick emailCompany={employee?.emailCompany}/>
                        <button className="icon-button" onClick={() => navigate(`/profile/${employeeId}/members`)}><i
                            className="fas fa-users"></i></button>
                    </div>
                </div>
                <ul className="nav flex-column">
                    <li className="nav-item">
                        <NavLink to={`/profile/${employeeId}/summary`}
                                 className={({isActive}) => isActive ? 'nav-link active' : 'nav-link'}>
                            <i className="fas fa-chart-pie me-2"></i>Summary
                        </NavLink>
                    </li>
                    <li className="nav-item">
                        <NavLink to={`/profile/${employeeId}/overview`}
                                 className={({isActive}) => isActive ? 'nav-link active' : 'nav-link'}>
                            <i className="fas fa-info-circle me-2"></i>Overview
                        </NavLink>
                    </li>
                    {(user.type === "HR" || user.type === "Manager") && (
                        <li className="nav-item">
                            <NavLink to={`/profile/${employeeId}/job`}
                                     className={({isActive}) => isActive ? 'nav-link active' : 'nav-link'}>
                                <i className="fas fa-briefcase me-2"></i>Job
                            </NavLink>
                        </li>
                    )}
                    {(user.type === "HR" || user.type === "Manager") && (
                        <li className="nav-item">
                            <NavLink to={`/profile/${employeeId}/compensation`}
                                     className={({isActive}) => isActive ? 'nav-link active' : 'nav-link'}>
                                <i className="fas fa-dollar-sign me-2"></i>Compensation
                            </NavLink>
                        </li>
                    )}
                    {user.type === "HR" && (
                        <li className="nav-item">
                            <NavLink to={`/profile/${employeeId}/personal`}
                                     className={({isActive}) => isActive ? 'nav-link active' : 'nav-link'}>
                                <i className="fas fa-user me-2"></i>Personal
                            </NavLink>
                        </li>
                    )}
                    {(user.type === "HR" || user.type === "Manager") && (
                        <li className="nav-item">
                            <NavLink to={`/profile/${employeeId}/performance`}
                                     className={({isActive}) => isActive ? 'nav-link active' : 'nav-link'}>
                                <i className="fas fa-chart-line me-2"></i>Performance
                            </NavLink>
                        </li>
                    )}
                    <li className="nav-item">
                        <NavLink to={`/profile/${employeeId}/career`}
                                 className={({isActive}) => isActive ? 'nav-link active' : 'nav-link'}>
                            <i className="fas fa-graduation-cap me-2"></i>Career
                        </NavLink>
                    </li>
                    <li className="nav-item">
                        <NavLink to={`/profile/${employeeId}/feedback`}
                                 className={({isActive}) => isActive ? 'nav-link active' : 'nav-link'}>
                            <i className="fas fa-comments me-2"></i>Feedback
                        </NavLink>
                    </li>
                </ul>
            </div>
            <div className="mt-auto">
                <NavLink to="/"
                         className={({isActive}) => isActive ? 'nav-link active' : 'nav-link'}>
                    <i className="fas fa-home me-2"></i>Homepage
                </NavLink>
            </div>
        </div>
    );
};

export default ProfileSidebar;