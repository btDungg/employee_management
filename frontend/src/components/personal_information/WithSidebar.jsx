import React from 'react';
import PersonalInfoSidebar from './PersonalInfoSidebar.jsx';

const WithSidebar = ({ WrappedComponent, employee }) => {
    return (
        <div className="container-fluid">
            <div className="row">
                {/* Truyền employee vào PersonalInfoSidebar */}
                <PersonalInfoSidebar employee={employee} />
                <div className="col-8">
                    {/* Truyền employee vào component con */}
                    <WrappedComponent employee={employee} />
                </div>
            </div>
        </div>
    );
};

export default WithSidebar;
