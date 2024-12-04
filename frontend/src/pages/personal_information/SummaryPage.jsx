import React, { useState, useEffect } from 'react';
import Summary from "../../components/personal_information/Summary.jsx";
import { getEmployeeDetailsByEmail } from "../../apis/api.js";
import WithSidebar from "../../components/personal_information/WithSidebar.jsx";

const SummaryPage = () => {
    const [employee, setEmployee] = useState(null); // State to store employee data
    const [loading, setLoading] = useState(true);  // State to handle loading
    const [error, setError] = useState(null);      // State to handle errors

    useEffect(() => {
        const fetchEmployee = async () => {
            try {
                const data = await getEmployeeDetailsByEmail();
                console.log('Fetched employee data:', data); // Check the fetched data
                setEmployee(data);
            } catch (err) {
                setError("Failed to fetch employee details");
            } finally {
                setLoading(false);
            }
        };

        fetchEmployee();
    }, []);

    if (loading) return <p>Loading...</p>;
    if (error) return <p>{error}</p>;

    return (
        <div>
            <Summary employee={employee} />
        </div>
    );
};

// Pass the employee prop to WithSidebar HOC
export default WithSidebar(SummaryPage);