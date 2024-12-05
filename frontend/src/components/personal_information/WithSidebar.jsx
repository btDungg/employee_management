import React, { useContext } from 'react';
import PersonalInfoSidebar from './PersonalInfoSidebar.jsx';
import { EmployeeContext } from "../../components/personal_information/EmployeeProvider.jsx";
import PersonalInfoNavbar from './PersonalInfoNavbar.jsx';
import "../../../public/css/personal_information/content.css";
const WithSidebar = (WrappedComponent) => {
    const HOC = (props) => {
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
                    <div className="coll-8 d-flex flex-column align-items-center">
                        <div className="" >
                            <PersonalInfoNavbar />
                            <WrappedComponent {...props} employee={employee} />
                        </div>
                    </div>
                </div>
            </div>
        );
    };

    return HOC;
};

export default WithSidebar;