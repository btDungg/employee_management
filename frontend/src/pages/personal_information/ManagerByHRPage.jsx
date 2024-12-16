import React, { useState, useEffect } from 'react';
import ManagerByHR from "../../components/personal_information/ManagerByHR.jsx";

const ManagerByHRPage = ({employee}) => {

    return (
        <div className="content-personal bg-body-secondary">
            <ManagerByHR employee={employee}/>
        </div>
    );
};


export default ManagerByHRPage;