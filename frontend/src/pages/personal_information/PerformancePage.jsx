import React from 'react';
import WithSidebar from "../../components/personal_information/WithSidebar.jsx";
import PersonalInfoNavbar from "../../components/personal_information/Bar/PersonalInfoNavbar.jsx";
import {Navigate, Route, Routes} from "react-router-dom";
import Contact from "../../components/personal_information/Personal/Contact.jsx";
import Information from "../../components/personal_information/Personal/Information.jsx";
import Emergency from "../../components/personal_information/Personal/Emergency.jsx";
import PerformanceReviews from "../../components/personal_information/Performance/PerformanceReviews.jsx";
import IndividualGoals from "../../components/personal_information/Performance/IndividualGoals.jsx";
import ArchivedGoals from "../../components/personal_information/Performance/ArchivedGoals.jsx";

const PerformancePage = (props) => {
    return (
        <div className="content-personal bg-body-secondary">
            <PersonalInfoNavbar showNavBar={true} showLinks={{ performanceReviews: true, individualGoals: true, archivedGoals: true }} />
            <Routes>
                <Route path="/" element={<Navigate to="performance-reviews"/>} />
                <Route path="performance-reviews" element={<PerformanceReviews employee={props.employee}/>} />
                <Route path="individual-goals" element={<IndividualGoals employee={props.employee}/>} />
                <Route path="archived-goals" element={<ArchivedGoals employee={props.employee}/>} />
            </Routes>
        </div>
    );
};

export default PerformancePage;