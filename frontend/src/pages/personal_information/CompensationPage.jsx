import React, {useContext} from 'react';
import WithSidebar from "../../components/personal_information/WithSidebar.jsx";
import PersonalInfoNavbar from "../../components/personal_information/Bar/PersonalInfoNavbar.jsx";
import {Navigate, Route, Routes, useLocation} from "react-router-dom";
import FeedbackReceived from "../../components/personal_information/Feedback/FeedbackReceived.jsx";
import FeedbackGiven from "../../components/personal_information/Feedback/FeedbackGiven.jsx";
import JobDetail from "../../components/personal_information/Job/JobDetail.jsx";
import ManagementChain from "../../components/personal_information/Job/ManagementChain.jsx";
import ManagerHistory from "../../components/personal_information/Job/ManagerHistory.jsx";
import RetirementDate from "../../components/personal_information/Job/RetirementDate.jsx";
import {UserContext} from "../../components/personal_information/UserProvider.jsx";
import Compensation from "../../components/personal_information/Compensation/Compensation.jsx";
import PayHistory from "../../components/personal_information/Compensation/PayHistory.jsx";
import BankAccount from "../../components/personal_information/Compensation/BankAccount.jsx";

const CompensationPage = (props) => {
    const { user } = useContext(UserContext);
    const location = useLocation();
    const personalInfo = location.pathname.includes("personal-info");

    return (
        <div className="content-personal bg-body-secondary">
            { !personalInfo && user && user.type === "HR" && <PersonalInfoNavbar showNavBar={true} showLinks={{ compensation: true, bankAccount: true, payHistory: true, }} />}
            { personalInfo && <PersonalInfoNavbar showNavBar={true} showLinks={{ compensation: true, bankAccount: true, payHistory: true, }} />}
            { !personalInfo && user && user.type === "Manager" && <PersonalInfoNavbar showNavBar={true} showLinks={{ compensation: true }} />}
            <Routes>
                <Route path="/" element={<Navigate to="compensation-detail"/>}/>
                <Route path="/compensation-detail" element={<Compensation employee={props.employee}/>} />
                <Route path="bank-account" element={<BankAccount employee={props.employee}/>} />
                <Route path="pay-history" element={<PayHistory employee={props.employee}/>} />
            </Routes>
        </div>
    );
};

export default CompensationPage;