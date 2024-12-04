import React, { useState, useEffect } from 'react';
import PersonalInfoSidebar from './PersonalInfoSidebar.jsx';
import { getEmployeeDetailsByEmail } from "../../apis/api.js";


const WithSidebar = (WrappedComponent) => {
    const HOC = (props) => {
        const [employee, setEmployee] = useState(null);

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

        
        return (
            <div className="container-fluid">
                <div className="row">
                    <PersonalInfoSidebar employee={employee} />
                    <div className="col-8">
                        <WrappedComponent {...props} employee={employee} />
                    </div>
                </div>
            </div>

        );
    };

    return HOC;
};

export default WithSidebar;
