import React from 'react';
import WithSidebar from "../../components/personal_information/WithSidebar.jsx";
import PersonalInfoNavbar from "../../components/personal_information/Bar/PersonalInfoNavbar.jsx";
import {Navigate, Route, Routes} from "react-router-dom";
import JobDetail from "../../components/personal_information/Job/JobDetail.jsx";
import FeedbackReceived from "../../components/personal_information/Feedback/FeedbackReceived.jsx";
import FeedbackGiven from "../../components/personal_information/Feedback/FeedbackGiven.jsx";

const FeedbackPage = (props) => {
    return (
        <div className="content-personal bg-body-secondary">
            <PersonalInfoNavbar showNavBar={true} showLinks={{ feedbackReceived: true, feedbackGiven: true }}/>
            <Routes>
                <Route path="/" element={<Navigate to="received"/>}/>
                <Route path="received" element={<FeedbackReceived employee={props.employee}/>}/>
                <Route path="given" element={<FeedbackGiven employee={props.employee}/>}/>
            </Routes>
        </div>
    );
};

export default FeedbackPage;