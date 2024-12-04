import React, { useState, useEffect } from 'react';
import PersonalInfoSidebar from './PersonalInfoSidebar.jsx';

const WithSidebar = (WrappedComponent) => {
    return (props) => {
        const [employee, setEmployee] = useState(null);

        useEffect(() => {
            console.log('Employee prop received in WithSidebar:', props.employee);

            if (props.employee) {
                setEmployee(props.employee);
            } else {
                console.warn('No employee prop received in WithSidebar.');
            }
        }, [props.employee]);

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
};

export default WithSidebar;