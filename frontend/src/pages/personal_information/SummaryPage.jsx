import React, { useState, useEffect } from 'react';
import Summary from "../../components/personal_information/Summary.jsx";
import { getEmployeeDetailsByEmail } from "../../apis/api.js";
import WithSidebar from "../../components/personal_information/WithSidebar.jsx";

const SummaryPage = () => {
    const [employee, setEmployee] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchEmployee = async () => {
            try {
                const data = await getEmployeeDetailsByEmail();
                setEmployee(data);
            } catch (err) {
                setError("Không thể lấy thông tin nhân viên.");
            } finally {
                setLoading(false);
            }
        };

        fetchEmployee();
    }, []);

    if (loading) return <p>Đang tải...</p>;
    if (error) return <p>{error}</p>;

    return <WithSidebar WrappedComponent={Summary} employee={employee} />;
};

export default SummaryPage;
