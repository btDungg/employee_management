import React, { useState, useEffect } from 'react';
import { getDetailTeamMates } from "../../apis/api.js";

export const EmployeeContext = React.createContext();

const EmployeeProvider = ({ children, id }) => {
    const [employee, setEmployee] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    const fetchEmployee = async (employeeId) => {
        setLoading(true);
        try {
            const data = await getDetailTeamMates(employeeId);
            setEmployee(data);
        } catch (err) {
            setError("Failed to fetch employee details");
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        if (id) {
            fetchEmployee(id);
        }
    }, [id]);

    return (
        <EmployeeContext.Provider value={{ employee, loading, error, fetchEmployee }}>
            {children}
        </EmployeeContext.Provider>
    );
};

export default EmployeeProvider;