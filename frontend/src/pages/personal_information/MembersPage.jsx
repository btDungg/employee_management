import React, { useState, useEffect } from 'react';
import MembersList from "../../components/personal_information/MembersList.jsx";
import WithSidebar from "../../components/personal_information/WithSidebar.jsx";
import { getEmployeeDetailsByEmail, getTeamMates } from "../../apis/api.js";

const MembersPage = () => {
    const [employee, setEmployee] = useState(null); // Trạng thái lưu thông tin employee
    const [teamMates, setTeamMates] = useState([]); // Trạng thái lưu thông tin team mates
    const [loading, setLoading] = useState(true);   // Trạng thái loading
    const [error, setError] = useState(null);       // Trạng thái lỗi

    useEffect(() => {
        const fetchData = async () => {
            try {
                const [employeeData, teamData] = await Promise.all([
                    getEmployeeDetailsByEmail(),
                    getTeamMates(),
                ]);

                setEmployee(employeeData);
                setTeamMates(teamData);
            } catch (err) {
                setError("Failed to fetch data. Please try again later.");
            } finally {
                setLoading(false);
            }
        };

        fetchData();
    }, []);

    if (loading) return <p>Loading...</p>;
    if (error) return <p className="text-danger">{error}</p>;

    return (
        <div className="container-fluid">
            <div className="row">
                {/* Truyền thông tin team mates cho MembersList */}
                <MembersList teamMates={teamMates} />
            </div>
        </div>
    );
};

export default WithSidebar(MembersPage);
