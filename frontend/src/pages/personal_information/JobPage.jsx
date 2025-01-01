import React, {useContext} from 'react';
import PersonalInfoNavbar from "../../components/personal_information/Bar/PersonalInfoNavbar.jsx";
import {Route, Routes, Navigate, useLocation} from "react-router-dom";
import JobDetail from "../../components/personal_information/Job/JobDetail.jsx";
import ManagementChain from "../../components/personal_information/Job/ManagementChain.jsx";
import ManagerHistory from "../../components/personal_information/Job/ManagerHistory.jsx";
import RetirementDate from "../../components/personal_information/Job/RetirementDate.jsx";
import {UserContext} from "../../components/personal_information/UserProvider.jsx";

const JobPage = (props) => {
    const { user } = useContext(UserContext);
    const location = useLocation();
    const personalInfo = location.pathname.includes("personal-info");

    return (
        <div className="content-personal bg-body-secondary">
            { !personalInfo && user && user.type === "HR" && <PersonalInfoNavbar showNavBar={true} showLinks={{ job: true, managementChain: true, managerHistory: true, retirementDate: true }} />}
            { personalInfo && <PersonalInfoNavbar showNavBar={true} showLinks={{ job: true, managementChain: true, managerHistory: true, retirementDate: true }} />}
            { !personalInfo && user && user.type === "Manager" && <PersonalInfoNavbar showNavBar={true} showLinks={{ job: true, managementChain: true}} />}
            <Routes>
                <Route path="/" element={<Navigate to="job-detail"/>} />
                <Route path="job-detail" element={<JobDetail employee={props.employee}/>} />
                <Route path="management-chain" element={<ManagementChain employee={props.employee}/>} />
                <Route path="manager-history" element={<ManagerHistory employee={props.employee}/>} />
                <Route path="retirement-date" element={<RetirementDate employee={props.employee}/>} />
            </Routes>
        </div>
    );
};

export default JobPage;