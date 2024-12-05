import React from 'react';
import WithSidebar from "../../components/personal_information/WithSidebar.jsx";
import Summary from "../../components/personal_information/Summary.jsx";
import Contact from "../../components/personal_information/Personal/Contact.jsx";
import PersonalInfoNavbar from "../../components/personal_information/PersonalInfoNavbar.jsx";
import {Route, Routes, Navigate} from "react-router-dom";

const PersonalPage = () => {
    return (
        <div className="content-personal bg-white">
            <PersonalInfoNavbar showNavBar={true} showLinks={{ contact: true, personalInfomation: true, emergency: true }} />
            <Routes>
                <Route path="/" element={<Navigate to="contact" />} />
                <Route path="contact" element={<Contact />} />
                <Route path="personal-infomation" element={<div>Personal Information Content</div>} />
                <Route path="emergency" element={<div>Emergency Contact Content</div>} />
            </Routes>
        </div>
    );
};

export default WithSidebar(PersonalPage);