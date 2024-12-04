import React from 'react';
import { useNavigate } from 'react-router-dom';
import "../../../public/css/personal_information/MembersList.css";

const MembersList = ({ teamMates }) => {
    const navigate = useNavigate();

    const handleRowClick = (id) => {
        navigate(`/profile/${id}`); // Điều hướng đến trang chi tiết của thành viên
    };

    return (
        <div className="content bg-white">
            <h4>Team Mates:</h4>
            {teamMates && teamMates.length > 0 ? (
                <table className="table table-bordered table-hover">
                    <thead className="table-light">
                        <tr>
                            <th>ID</th>
                            <th>Name</th>
                            <th>Type</th>
                            <th>Email</th>
                            <th>Organization</th>
                            <th>Manager</th>
                        </tr>
                    </thead>
                    <tbody>
                        {teamMates.map((mate, index) => (
                            <tr key={index} onClick={() => handleRowClick(mate.id)}>
                                <td>{mate.id}</td>
                                <td>{mate.name}</td>
                                <td>{mate.type}</td>
                                <td>{mate.email_company}</td>
                                <td>{mate.organization}</td>
                                <td>{mate.manager_name}</td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            ) : (
                <p>No team mates available</p>
            )}
        </div>
    );
};

export default MembersList;
