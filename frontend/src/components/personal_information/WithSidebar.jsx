import React, { useContext } from 'react';
import PersonalInfoSidebar from './PersonalInfoSidebar.jsx';
import { EmployeeContext } from "../../components/personal_information/EmployeeProvider.jsx";
import PersonalInfoNavbar from './PersonalInfoNavbar.jsx';

const WithSidebar = (WrappedComponent) => {
    const HOC = (props) => {
        const { employee, loading, error } = useContext(EmployeeContext);

        if (loading) {
            return <div>Loading...</div>;
        }

        if (error) {
            return <div>{error}</div>;
        }

        const currentEmployee = props.viewedEmployee || employee;

        console.log("sidebar:",props.viewedEmployee);
        return (
            <div className="container-fluid">
                <div className="row">
                    <PersonalInfoSidebar employee={currentEmployee} />
                    <div className="d-flex flex-column align-items-center">
                        <WrappedComponent {...props} employee={employee} />
                    </div>
                </div>
            </div>
        );
    };

    return HOC;
};

export default WithSidebar;