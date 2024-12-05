import React from 'react';
import Personal from "../../components/personal_information/Personal.jsx";
import PersonalInfoLayout from '../../components/personal_information/PersonalInfoLayout.jsx';


const PersonalPage = () => {
    return (
        <div className="content bg-white">
            <PersonalInfoLayout>
            <p>This is the Personal Page</p>
            <Personal />
            </PersonalInfoLayout>
        </div>
    );
};

export default PersonalPage;