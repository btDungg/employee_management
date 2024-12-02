import React from 'react';
import Sidebar from "./Sidebar.jsx";

const WithSidebar = (WrappedComponent) => {
    return (props) => (
        <div className="container-fluid">
            <div className="row">
                <Sidebar />
                <div className="col-8">
                    <WrappedComponent {...props} />
                </div>
            </div>
        </div>
    );
};

export default WithSidebar;