import React, { useEffect, useState } from 'react';
import Sidebar from "./Sidebar.jsx";

const WithSidebar = (WrappedComponent) => {
    return (props) => {
        const [employee, setEmployee] = useState(null); // State to store employee data

        useEffect(() => {
            console.log('Employee prop received in WithSidebar:', props.employee);
            if (props.employee) {
                setEmployee(props.employee);
            }
        }, [props.employee]); // Update when employee prop changes

        return (
            <div className="container-fluid">
                <div className="row">
                    {/* Pass employee prop to Sidebar */}
                    <Sidebar employee={employee} />
                    <div className="col-8">
                        {/* Pass all props to WrappedComponent */}
                        <WrappedComponent {...props} employee={employee} />
                    </div>
                </div>
            </div>
        );
    };
};

export default WithSidebar;