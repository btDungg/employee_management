import React from 'react';

const Summary = ({ employee }) => {
    return (
        <div className="content bg-white">
            <div className="row pt-4">
                {/* Header Section */}
                <div className="col-md-6 mb-4">
                    <h6>Position Title</h6>
                    <p>{employee?.businessTitle || "N/A"}</p>
                </div>
                <div className="col-md-6 mb-4">
                    <h6>Location</h6>
                    <p>{employee?.location || "N/A"}</p>
                </div>
                <div className="col-md-6 mb-4">
                    <h6>Manager</h6>
                    <p>{employee?.managerName}</p>
                </div>
                <div className="col-md-6 mb-4">
                    <h6>Organization</h6>
                    <p>{employee?.organizationName}</p>
                </div>
            </div>

            <div className="row">
                {/* Skills */}
                <div className="col-md-6 mb-4">
                    <div className="card p-3">
                        <h6>Skills</h6>
                        <button className="btn-form mt-2">Edit</button>
                    </div>
                </div>
                {/* Internal Project */}
                <div className="col-md-6 mb-4">
                    <div className="card p-3">
                        <h6>Internal Project</h6>
                        <button className="btn-form mt-2">Edit</button>
                    </div>
                </div>
                {/* Job History */}
                <div className="col-md-6 mb-4">
                    <div className="card p-3">
                        <h6>Job History</h6>
                        <button className="btn-form mt-2">Edit</button>
                    </div>
                </div>
                {/* Education */}
                <div className="col-md-6 mb-4">
                    <div className="card p-3">
                        <h6>Education</h6>
                        <button className="btn-form mt-2">Edit</button>
                    </div>
                </div>
                {/* Feedback */}
                <div className="col-md-6 mb-4">
                    <div className="card p-3">
                        <h6>Feedback</h6>
                        <button className="btn-form mt-2">Edit</button>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default Summary;
