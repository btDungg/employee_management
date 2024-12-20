import React, {useContext} from 'react';
import {NavLink} from "react-router-dom";
import {UserContext} from "./UserProvider.jsx";

const Summary = ({ employee }) => {
    const { user } = useContext(UserContext);
    if (!employee) {
        return <div className="text-center mt-5">No employee data available.</div>;
    }
    const managerUrl = employee.managerID === user?.id
        ? '/personal-info/summary'
        : `/profile/${employee.managerID}/summary`;
    return (
        <div className="p-4">
            <div className="">
                {/* Header Section */}
                <div className="row">
                    {/* Mỗi phần là 1 card */}
                    <div className="col-md-6 mb-3">
                        <div className="card p-3 shadow-sm">
                            <div className="row">
                                <div className="col-md-6">
                                    <h6 className="mb-2 text-muted">
                                        <i className="fas fa-briefcase"></i> Position Title
                                    </h6>
                                    <p>{employee.businessTitle || "N/A"}</p>
                                </div>
                                <div className="col-md-6">
                                    <h6 className="mb-2 text-muted">
                                        <i className="fas fa-map-marker-alt"></i> Location
                                    </h6>
                                    <p>{employee.location || "N/A"}</p>
                                </div>
                                <div className="col-md-6">
                                    <h6 className="mb-2 text-muted">
                                        <i className="fas fa-user-tie"></i> Manager
                                    </h6>
                                    <NavLink to={managerUrl}>
                                        <p>{employee.managerName || "N/A"}</p>
                                    </NavLink>
                                </div>
                                <div className="col-md-6">
                                    <h6 className="mb-2 text-muted">
                                        <i className="fas fa-building"></i> Organization
                                    </h6>
                                    <p>{employee.organizationName || "N/A"}</p>
                                </div>
                            </div>
                        </div>
                    </div>
                    {/* Skills */}
                    <div className="col-md-6 mb-4">
                        <div className="card p-3 shadow-sm">
                            <h6 className="text-muted mb-3">Skills</h6>
                            <hr />
                            <a href="#">Edit</a>
                        </div>
                    </div>
                    {/* Internal Project */}
                    <div className="col-md-6 mb-4">
                        <div className="card p-3 shadow-sm">
                            <h6 className="text-muted mb-3">Internal Project</h6>
                            <hr />
                            <a href="#">Edit</a>
                        </div>
                    </div>
                    {/* JobDetail History */}
                    <div className="col-md-6 mb-4">
                        <div className="card p-3 shadow-sm">
                            <h6 className="text-muted mb-3">Job History</h6>
                            <hr />
                            <a href="#">Edit</a>
                        </div>
                    </div>
                    {/* Education */}
                    <div className="col-md-6 mb-4">
                        <div className="card p-3 shadow-sm">
                            <h6 className="text-muted">Education</h6>
                            <hr />
                            <a href="#">Edit</a>
                        </div>
                    </div>
                    {/* Feedback */}
                    <div className="col-md-6 mb-4">
                        <div className="card p-3 shadow-sm">
                            <h6 className="text-muted mb-3">Feedback</h6>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default Summary;