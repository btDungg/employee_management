import React from 'react';
import WithSidebar from "../../components/personal_information/WithSidebar.jsx";
import PersonalInfoNavbar from "../../components/personal_information/Bar/PersonalInfoNavbar.jsx";
import {Navigate, Route, Routes} from "react-router-dom";
import JobDetail from "../../components/personal_information/Job/JobDetail.jsx";

const FeedbackPage = (props) => {
    return (
        <div className="content-personal bg-body-secondary">
            <PersonalInfoNavbar showNavBar={true} showLinks={{job: true}}/>
            <Routes>
                <Route path="/" element={<Navigate to="job-detail"/>}/>
                <Route path="job-detail" element={<JobDetail employee={props.employee}/>}/>
            </Routes>
        </div>
    );
};

export default FeedbackPage;