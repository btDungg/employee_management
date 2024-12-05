import React, { useState, useEffect, useContext } from 'react';
import { useParams, useLocation, Routes, Route, Navigate } from "react-router-dom";
import WithSidebar from "../../components/personal_information/WithSidebar.jsx";
import Summary from "../../components/personal_information/Summary.jsx";
import Contact from "../../components/personal_information/Personal/Contact.jsx";
import Emergency from "../../components/personal_information/Personal/Emergency.jsx";
import Information from "../../components/personal_information/Personal/Information.jsx";
import PersonalInfoSidebar from '../../components/personal_information/PersonalInfoSidebar.jsx';
import { EmployeeContext } from "../../components/personal_information/EmployeeProvider.jsx";  // Import context

const PersonalPage = () => {
    const { id } = useParams();  // Get ID from URL
    const location = useLocation();  // Get state data from location
    const { employee, loading, error } = useContext(EmployeeContext);  // Get employee data from context

    const [viewedEmployee, setViewedEmployee] = useState(employee);  // State for the viewed employee
    const [isLoading, setIsLoading] = useState(true);  // Loading state for fetching teammate details
    const teammateDetails = location.state?.teammateDetails;  // Get teammateDetails from state

    // Fetch employee details if not provided by context or from teammateDetails in state
    useEffect(() => {
        if (teammateDetails) {
            setViewedEmployee(teammateDetails);  // If teammateDetails exist in state, use them
            setIsLoading(false);
        } else if (id && id !== employee?.id) {
            setIsLoading(true);
            const fetchEmployeeDetails = async () => {
                try {
                    const data = await getDetailTeamMates(id);  // Fetch data using API
                    setViewedEmployee(data);  // Update viewed employee data
                } catch (err) {
                    setError("Failed to fetch employee details.");
                } finally {
                    setIsLoading(false);
                }
            };
            fetchEmployeeDetails();
        } else {
            setIsLoading(false);  // If it's the current employee, no need to fetch
        }
    }, [id, teammateDetails, employee?.id]);  // Dependencies

    if (isLoading || loading) {
        return <div className="content bg-white">Loading...</div>;  // Show loading while fetching data
    }

    if (error) {
        return <div className="content bg-white text-danger">{error}</div>;  // Show error if fetching fails
    }

    return (
        <div className="content-personal bg-white">
            <PersonalInfoSidebar employee={viewedEmployee} />
            <Routes>
                <Route path="/" element={<Navigate to="personal-infomation" />} />
                <Route path="contact" element={<Contact employee={viewedEmployee} />} />
                <Route path="personal-infomation" element={<Information emp={viewedEmployee} />} /> {/* Pass viewed employee to Information */}
                <Route path="emergency" element={<Emergency />} />
            </Routes>
        </div>
    );
};

export default WithSidebar(PersonalPage);  // Wrap with sidebar HOC
