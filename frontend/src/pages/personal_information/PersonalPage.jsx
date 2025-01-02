import React from 'react';
import Contact from "../../components/personal_information/Personal/Contact.jsx";
import PersonalInfoNavbar from "../../components/personal_information/Bar/PersonalInfoNavbar.jsx";
import {Route, Routes, Navigate} from "react-router-dom";
import Emergency from "../../components/personal_information/Personal/Emergency.jsx";
import Information from "../../components/personal_information/Personal/Information.jsx";

const PersonalPage = (props) => {
    return (
        <div className="content-personal bg-body-secondary">
            <PersonalInfoNavbar showNavBar={true} showLinks={{ contact: true, personalInfomation: true, emergency: true }} />
            <Routes>
                <Route path="/" element={<Navigate to="contact"/>} />
                <Route path="contact" element={<Contact employee={props.employee}/>} />
                <Route path="personal-infomation" element={<Information employee={props.employee}/>} />
                <Route path="emergency" element={<Emergency employee={props.employee}/>} />
            </Routes>
        </div>
    );
};

export default PersonalPage;