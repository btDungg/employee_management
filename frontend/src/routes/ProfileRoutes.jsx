import React from 'react';
import {Route, Routes, useParams} from 'react-router-dom';
import EmployeeProvider from '../components/personal_information/EmployeeProvider.jsx';
import PrivateRoute from '../components/PrivateRoute.jsx';
import WithSidebar from '../components/personal_information/WithSidebar.jsx';
import SummaryPage from '../pages/personal_information/SummaryPage.jsx';
import OverviewPage from '../pages/personal_information/OverviewPage.jsx';
import JobPage from '../pages/personal_information/JobPage.jsx';
import CompensationPage from '../pages/personal_information/CompensationPage.jsx';
import PersonalPage from '../pages/personal_information/PersonalPage.jsx';
import PerformancePage from '../pages/personal_information/PerformancePage.jsx';
import CareerPage from '../pages/personal_information/CareerPage.jsx';
import FeedbackPage from '../pages/personal_information/FeedbackPage.jsx';
import MembersPage from '../pages/personal_information/MembersPage.jsx';

const ProfileRoutes = () => {
    const { id } = useParams();
    return (
        <EmployeeProvider id={id}>
            <Routes>
                <Route path="summary" element={<PrivateRoute element={WithSidebar(SummaryPage, true)} />} />
                <Route path="overview" element={<PrivateRoute element={WithSidebar(OverviewPage, true)} />} />
                <Route path="job/*" element={<PrivateRoute element={WithSidebar(JobPage, true)} />} />
                <Route path="compensation" element={<PrivateRoute element={WithSidebar(CompensationPage, true)} />} />
                <Route path="personal/*" element={<PrivateRoute element={WithSidebar(PersonalPage, true)} />} />
                <Route path="performance" element={<PrivateRoute element={WithSidebar(PerformancePage, true)} />} />
                <Route path="career" element={<PrivateRoute element={WithSidebar(CareerPage, true)} />} />
                <Route path="feedback" element={<PrivateRoute element={WithSidebar(FeedbackPage, true)} />} />
                <Route path="members" element={<PrivateRoute element={WithSidebar(MembersPage, true)} />} />
            </Routes>
        </EmployeeProvider>
    );
};

export default ProfileRoutes;