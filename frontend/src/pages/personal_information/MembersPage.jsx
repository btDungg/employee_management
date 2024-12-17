import React, { useState, useEffect } from 'react';
import MembersList from "../../components/personal_information/MembersList.jsx";
import WithSidebar from "../../components/personal_information/WithSidebar.jsx";

const MembersPage = (props) => {

    return (
        <div className="content-personal bg-body-secondary">
            <div className="row">
                <MembersList employee={props.employee}/>
            </div>
        </div>
    );
};

export default MembersPage;
