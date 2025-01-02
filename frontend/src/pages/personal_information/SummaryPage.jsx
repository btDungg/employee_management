import React, { useState, useEffect } from 'react';
import Summary from "../../components/personal_information/Summary.jsx";

const SummaryPage = ({employee}) => {

    return (
        <div className="content-personal bg-body-secondary">
            <Summary employee={employee}/>
        </div>
    );
};


export default SummaryPage;