import React, { useState, useEffect } from 'react';
import { getTeamMates,getDetailTeamMates } from "../../apis/api.js";


const EmployeeContext = React.createContext();

const TeamMateProvider = ({ children }) => {
    const [employee, setEmployee] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchEmployee = async () => {
            try {
                const data = await getEmployeeDetailsByEmail();
                setEmployee(data);
            } catch (err) {
                setError("Failed to fetch employee details");
            } finally {
                setLoading(false);
            }
        };

        fetchEmployee();
    }, []);

    return (
        <EmployeeContext.Provider value={{ employee, loading, error }}>
            {children}
        </EmployeeContext.Provider>
    );
};

export { TeamMateProvider, EmployeeContext };