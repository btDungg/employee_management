import React from 'react';
import WithSidebar from "../../components/personal_information/WithSidebar.jsx";

const PersonalPage = () => {
    return (
        <div className="content bg-white">
            <p>This is the Personal Page</p>
        </div>
    );
};

export default WithSidebar(PersonalPage);