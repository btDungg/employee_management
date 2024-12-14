import React from 'react';
import PersonalInfoNavbar from "../../components/personal_information/Bar/PersonalInfoNavbar.jsx";
import {Route, Routes, Navigate} from "react-router-dom";
import JobDetail from "../../components/personal_information/JobDetail.jsx";

const JobPage = (props) => {
    return (
        <div className="content-personal bg-body-secondary">
            <PersonalInfoNavbar showNavBar={true} showLinks={{ job: true }} />
            <Routes>
                <Route path="/" element={<Navigate to="job-detail"/>} />
                <Route path="job-detail" element={<JobDetail employee={props.employee}/>} />
            </Routes>
        </div>
    );
};

export default JobPage;