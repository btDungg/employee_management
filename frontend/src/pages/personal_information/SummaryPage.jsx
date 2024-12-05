import React, { useState, useEffect } from 'react';

import PersonalInfoLayout from '../../components/personal_information/PersonalInfoLayout.jsx';
import Summary from "../../components/personal_information/Summary.jsx";

const SummaryPage = () => {

    return (
        <div className="content bg-white">
             <PersonalInfoLayout>
                <Summary />
            </PersonalInfoLayout>
        </div>
    );
};


export default SummaryPage;