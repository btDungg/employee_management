import React from 'react';
import WithSidebar from "../../components/personal_information/WithSidebar.jsx";
import Summary from "../../components/personal_information/Summary.jsx";
import Personal from "../../components/personal_information/Personal.jsx";

const PersonalPage = () => {
    return (
        <div className="content bg-white">
            <p>This is the Personal Page</p>
            <Personal />
        </div>
    );
};

export default WithSidebar(PersonalPage);