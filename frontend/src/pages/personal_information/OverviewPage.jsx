import React from 'react';
import PersonalInfoNavbar from "../../components/personal_information/Bar/PersonalInfoNavbar.jsx";
import {Navigate, Route, Routes} from "react-router-dom";
import Contact from "../../components/personal_information/Personal/Contact.jsx";
import Information from "../../components/personal_information/Personal/Information.jsx";
import Emergency from "../../components/personal_information/Personal/Emergency.jsx";
import JobDetail from "../../components/personal_information/Job/JobDetail.jsx";
import Overview from "../../components/personal_information/Overview/Overview.jsx";

const OverviewPage = (props) => {
    return (
        <div className="content-personal bg-body-secondary">
            <PersonalInfoNavbar showNavBar={true} showLinks={{  }}/>
            <Routes>
                <Route path="/" element={<Navigate to="overview-page"/>} />
                <Route path="overview-page" element={<Overview employee={props.employee}/>} />
            </Routes>
        </div>
    );
};

export default OverviewPage;