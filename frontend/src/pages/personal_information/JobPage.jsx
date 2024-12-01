import React from 'react';
import WithSidebar from "../../components/personal_information/WithSidebar.jsx";

const JobPage = () => {
    return (
        <div className="content bg-white">
            <p>This is the Job Page</p>
        </div>
    );
};

export default WithSidebar(JobPage);