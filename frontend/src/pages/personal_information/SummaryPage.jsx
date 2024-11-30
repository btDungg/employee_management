import React from 'react';
import Sidebar from "../../components/personal_information/Sidebar.jsx";
import Summary from "../../components/personal_information/Summary.jsx";

const SummaryPage = () => {
    return (
        <div className="personal-summary container-fluid">
            <div className="row">
                <Sidebar/>
                <Summary/>
            </div>
        </div>
    );
};

export default SummaryPage;