import React, { useContext } from 'react';
import PersonalInfoSidebar from './Bar/PersonalInfoSidebar.jsx';
import ProfileSidebar from './Bar/ProfileSidebar.jsx';
import { EmployeeContext } from "./EmployeeProvider.jsx";
import { UserContext } from "./UserProvider.jsx";

const WithSidebar = (WrappedComponent, isProfile) => {
    const HOC = (props) => {
        if (!isProfile) {
            const context = useContext(UserContext);
            if (!context) {
                return <div>Error: Context not found</div>;
            }
            const { user, loading, error } = context;
            if (loading) {
                return <div>Loading...</div>;
            }

            if (error) {
                return <div>{error}</div>;
            }

            return (
                <div className="container-fluid">
                    <div className="row">
                        <PersonalInfoSidebar employee={user} />
                        <div className="d-flex flex-column align-items-center">
                            <WrappedComponent {...props} employee={user} />
                        </div>
                    </div>
                </div>
            );
        } else {
            const context = useContext(EmployeeContext);
            if (!context) {
                return <div>Error: Context not found</div>;
            }
            const { employee, loading, error } = context;
            if (loading) {
                return <div>Loading...</div>;
            }

            if (error) {
                return <div>{error}</div>;
            }

            return (
                <div className="container-fluid">
                    <div className="row">
                        <ProfileSidebar employee={employee} />
                        <div className="d-flex flex-column align-items-center">
                            <WrappedComponent {...props} employee={employee} />
                        </div>
                    </div>
                </div>
            );
        }
    };

    return HOC;
};

export default WithSidebar;