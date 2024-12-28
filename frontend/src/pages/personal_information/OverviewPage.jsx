import React from 'react';
import PersonalInfoNavbar from "../../components/personal_information/Bar/PersonalInfoNavbar.jsx";
import {Navigate, Route, Routes} from "react-router-dom";
import Contact from "../../components/personal_information/Personal/Contact.jsx";
import Information from "../../components/personal_information/Personal/Information.jsx";
import Emergency from "../../components/personal_information/Personal/Emergency.jsx";
import JobDetail from "../../components/personal_information/Job/JobDetail.jsx";

const OverviewPage = (props) => {
    return (
        <div className="content-personal bg-body-secondary">
            <PersonalInfoNavbar showNavBar={true} showLinks={{ job: true }}/>
            <Routes>
                <Route path="/" element={<Navigate to="job-detail"/>} />
                <Route path="job-detail" element={<JobDetail employee={props.employee}/>} />
            </Routes>
        </div>
    );
};

export default OverviewPage;