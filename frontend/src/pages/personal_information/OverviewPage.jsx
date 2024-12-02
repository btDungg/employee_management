import React from 'react';
import WithSidebar from "../../components/personal_information/WithSidebar.jsx";

const OverviewPage = () => {
    return (
        <div className="content bg-white">
            <p>This is the Overview Page</p>
        </div>
    );
};

export default WithSidebar(OverviewPage);