import React from 'react';
import { Link } from 'react-router-dom';

const Sidebar = () => {
    return (
        <div className="col-3 sidebar bg-light">
            <div className="profile-section text-center py-4">
                <h5>Bui Minh Duy</h5>
                <p>Staff</p>
                <div className="profile-icons">
                    <button className="btn btn-outline-secondary me-2">📧 Email</button>
                    <button className="btn btn-outline-secondary">👥 Team</button>
                </div>
            </div>
            <ul className="nav flex-column mt-4">
                <li className="nav-item">
                    <Link className="nav-link active" to="#">Summary</Link>
                </li>
                <li className="nav-item">
                    <Link className="nav-link" to="#">Overview</Link>
                </li>
                <li className="nav-item">
                    <Link className="nav-link" to="#">Job</Link>
                </li>
                <li className="nav-item">
                    <Link className="nav-link" to="#">Compensation</Link>
                </li>
                <li className="nav-item">
                    <Link className="nav-link" to="#">Personal</Link>
                </li>
                <li className="nav-item">
                    <Link className="nav-link" to="#">Performance</Link>
                </li>
                <li className="nav-item">
                    <Link className="nav-link" to="#">Career</Link>
                </li>
                <li className="nav-item">
                    <Link className="nav-link" to="#">Feedback</Link>
                </li>
            </ul>
        </div>
    );
};

export default Sidebar;