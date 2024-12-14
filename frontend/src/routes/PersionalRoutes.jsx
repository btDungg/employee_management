import React from 'react';
import { Route, Routes } from 'react-router-dom';
import SummaryPage from '../pages/personal_information/SummaryPage.jsx';
import OverviewPage from '../pages/personal_information/OverviewPage.jsx';
import JobPage from '../pages/personal_information/JobPage.jsx';
import CompensationPage from '../pages/personal_information/CompensationPage.jsx';
import PersonalPage from '../pages/personal_information/PersonalPage.jsx';
import PerformancePage from '../pages/personal_information/PerformancePage.jsx';
import CareerPage from '../pages/personal_information/CareerPage.jsx';
import FeedbackPage from '../pages/personal_information/FeedbackPage.jsx';
import MembersPage from '../pages/personal_information/MembersPage.jsx';
import PrivateRoute from '../components/PrivateRoute.jsx';
import WithSidebar from '../components/personal_information/WithSidebar.jsx';
import { UserProvider } from '../components/personal_information/UserProvider.jsx';

const PersonalRoutes = () => (
    <UserProvider>
        <Routes>
            <Route path="summary" element={<PrivateRoute element={WithSidebar(SummaryPage, false)} />} />
            <Route path="overview" element={<PrivateRoute element={WithSidebar(OverviewPage, false)} />} />
            <Route path="job/*" element={<PrivateRoute element={WithSidebar(JobPage, false)} />} />
            <Route path="compensation" element={<PrivateRoute element={WithSidebar(CompensationPage, false)} />} />
            <Route path="personal/*" element={<PrivateRoute element={WithSidebar(PersonalPage, false)} />} />
            <Route path="performance" element={<PrivateRoute element={WithSidebar(PerformancePage, false)} />} />
            <Route path="career" element={<PrivateRoute element={WithSidebar(CareerPage, false)} />} />
            <Route path="feedback" element={<PrivateRoute element={WithSidebar(FeedbackPage, false)} />} />
            <Route path="members" element={<PrivateRoute element={WithSidebar(MembersPage, false)} />} />
        </Routes>
    </UserProvider>
);

export default PersonalRoutes;