import React, { useState, useEffect, useContext } from 'react';
import { useParams, useLocation } from "react-router-dom";
import Summary from "../../components/personal_information/Summary.jsx";
import { getDetailTeamMates } from "../../apis/api"; // Import API nếu chưa có
import { EmployeeContext } from "../../components/personal_information/EmployeeProvider.jsx"; // Import context
import WithSidebar from "../../components/personal_information/WithSidebar.jsx";
import PersonalInfoSidebar from '../../components/personal_information/PersonalInfoSidebar.jsx';

const SummaryPage = () => {
    const { id } = useParams(); // Lấy ID từ URL
    const location = useLocation(); // Lấy dữ liệu từ state
    const { employee } = useContext(EmployeeContext); // Lấy employee từ context
    const [viewedEmployee, setViewedEmployee] = useState(employee); // state cho người xem
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    // Lấy teammateDetails từ state (nếu có)
    const teammateDetails = location.state?.teammateDetails;

    useEffect(() => {
        if (teammateDetails) {
            setViewedEmployee(teammateDetails);  // Cập nhật dữ liệu khi teammateDetails có giá trị
            setLoading(false);
        } else if (id && id !== employee.id) {
            setLoading(true);
            const fetchEmployeeDetails = async () => {
                try {
                    const data = await getDetailTeamMates(id);  // Gọi API để lấy chi tiết người khác
                    setViewedEmployee(data);  // Cập nhật dữ liệu người xem vào state
                } catch (err) {
                    setError("Failed to fetch employee details.");
                } finally {
                    setLoading(false);
                }
            };
            fetchEmployeeDetails();
        } else {
            setLoading(false);
        }
    }, [id, teammateDetails, employee.id]);
    if (loading) {
        return <div className="content bg-white">Loading...</div>;
    }

    if (error) {
        return <div className="content bg-white text-danger">{error}</div>;
    }

    
    return (
        <div className="content bg-white">
            <PersonalInfoSidebar employee={viewedEmployee} />
            <h2>{id && id !== employee.id ? `Teammate: ${viewedEmployee.name}` : "Your Summary"}</h2>
            <Summary employee={viewedEmployee} /> {/* Truyền dữ liệu người xem vào Summary */}
        </div>
    );
};

export default WithSidebar(SummaryPage);
