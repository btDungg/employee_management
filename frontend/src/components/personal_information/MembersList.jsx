import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import "../../../public/css/personal_information/MembersList.css";
import WithSidebar from "./WithSidebar.jsx";
import { getTeamMates } from "../../apis/api.js";

const MembersList = () => {
    const navigate = useNavigate();
    const [teamMates, setTeamMates] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    // Fetch team mates on component mount
    useEffect(() => {
        const fetchTeamMates = async () => {
            try {
                const data = await getTeamMates();
                setTeamMates(data);
            } catch (err) {
                console.error("Failed to fetch team mates:", err);
                setError("Failed to load team mates. Please try again.");
            } finally {
                setLoading(false);
            }
        };

        fetchTeamMates();
    }, []);

    const handleRowClick = (id) => {
        navigate(`/profile/${id}`); // Điều hướng đến trang chi tiết của thành viên
    };

    if (loading) {
        return <div className="content bg-white">Loading...</div>;
    }

    if (error) {
        return <div className="content bg-white text-danger">{error}</div>;
    }

    return (
        <div >
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
                        {teamMates.map((mate) => (
                            <tr key={mate.id} onClick={() => handleRowClick(mate.id)}>
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
                <p>No team mates available.</p>
            )}
        </div>
    );
};

export default WithSidebar(MembersList);
