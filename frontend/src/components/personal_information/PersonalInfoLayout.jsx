import React, { useContext } from 'react';
import PersonalInfoSidebar from './PersonalInfoSidebar.jsx';
import { EmployeeContext } from './EmployeeProvider.jsx';

const PersonalInfoLayout = ({ children }) => {
    const { employee, loading, error } = useContext(EmployeeContext);

    if (loading) {
        return <div>Loading...</div>;
    }

    if (error) {
        return <div>{error}</div>;
    }

    return (
        <div className="container-fluid">
            <div className="row">
                <PersonalInfoSidebar employee={employee} />
                <div className="col-8">
                    {children}
                </div>
            </div>
        </div>
    );
};

export default PersonalInfoLayout;
