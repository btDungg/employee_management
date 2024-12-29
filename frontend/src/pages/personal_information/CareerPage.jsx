import React from 'react';
import PersonalInfoNavbar from "../../components/personal_information/Bar/PersonalInfoNavbar.jsx";
import {Navigate, Route, Routes} from "react-router-dom";
import Skills from "../../components/personal_information/Career/Skills.jsx";
import JobHistory from "../../components/personal_information/Career/JobHistory.jsx";
import InternalProjects from "../../components/personal_information/Career/InternalProjects.jsx";
import Languages from "../../components/personal_information/Career/Languages.jsx";
import Achievements from "../../components/personal_information/Career/Achievements.jsx";

const CareerPage = (props) => {
    return (
        <div className="content-personal bg-body-secondary">
            <PersonalInfoNavbar showNavBar={true} showLinks={{ skills: true, jobHistory: true, internalProjects: true, languages: true, achievements: true }}/>
            <Routes>
                <Route path="/" element={<Navigate to="skills"/>}/>
                <Route path="skills" element={<Skills employee={props.employee}/>}/>
                <Route path="job-history" element={<JobHistory employee={props.employee}/>}/>
                <Route path="internal-projects" element={<InternalProjects employee={props.employee}/>}/>
                <Route path="languages" element={<Languages employee={props.employee}/>}/>
                <Route path="achievements" element={<Achievements employee={props.employee}/>}/>
            </Routes>
        </div>
    );
};

export default CareerPage;