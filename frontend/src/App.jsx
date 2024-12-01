import { BrowserRouter as Router, Routes, Route, useLocation } from 'react-router-dom';
import { useState, useEffect } from 'react';
import HomePage from './pages/HomePage.jsx';
import EmployeePage from './pages/EmployeePage.jsx';
import HRPage from './pages/HRPage.jsx';
import ManagerPage from './pages/ManagerPage.jsx';
import Sidebar from './components/Sidebar.jsx';
import TopNavbarPoint from './components/TopNavbarPoint.jsx';
import PointInfoPage from './pages/PointInfoPage.jsx';
import ViewOtherPointsPage from './pages/ViewOtherPointsPage.jsx';
import PointHistoryPage from './pages/PointHistoryPage.jsx';
import ChangePointsPage from './pages/ChangePointsPage.jsx';
import LoginPage from './pages/LoginPage.jsx';
import PrivateRoute from './components/PrivateRoute.jsx';
import '../public/css/App.css';
import InfoNavigationPage from "./pages/InfoNavigationPage.jsx";
import SummaryPage from "./pages/personal_information/SummaryPage.jsx";
import OverviewPage from "./pages/personal_information/OverviewPage.jsx";
import JobPage from "./pages/personal_information/JobPage.jsx";
import CompensationPage from "./pages/personal_information/CompensationPage.jsx";
import PersonalPage from "./pages/personal_information/PersonalPage.jsx";
import PerformancePage from "./pages/personal_information/PerformancePage.jsx";
import CareerPage from "./pages/personal_information/CareerPage.jsx";
import FeedbackPage from "./pages/personal_information/FeedbackPage.jsx";
import '@fortawesome/fontawesome-free/css/all.min.css';

const App = () => {
    const [isSidebarOpen, setIsSidebarOpen] = useState(false);
    const location = useLocation();
    const showTopNavbar = ['/point-info', '/view-other-points', '/point-history', '/change-points'].includes(location.pathname);
    const hideSidebar = location.pathname.includes('/personal-info/');
    const isLoggedIn = !!localStorage.getItem('token');

    const toggleSidebar = () => {
        setIsSidebarOpen(!isSidebarOpen);
    };

    useEffect(() => {
        if (location.pathname.includes('/personal-info/')) {
            setIsSidebarOpen("");
        }
    }, [location]);

    useEffect(() => {
        const topNavbar = document.querySelector('.topnavbarpoint');
        if (topNavbar) {
            if (isSidebarOpen) {
                topNavbar.classList.add('sidebar-open');
            } else {
                topNavbar.classList.remove('sidebar-open');
            }
        }
    }, [isSidebarOpen, location]);

    return (
        <div className="app">
            {!hideSidebar && isLoggedIn && <Sidebar isOpen={isSidebarOpen} toggleSidebar={toggleSidebar} />}
            {showTopNavbar && <TopNavbarPoint />}
            <div className={`content ${isSidebarOpen ? "" : "expanded"}`} style={{ marginTop: showTopNavbar ? '60px' : '0' }}>
                <Routes>
                    <Route path="/login" element={<LoginPage />} />
                    <Route path="/" element={<PrivateRoute element={HomePage} />} />
                    <Route path="/employee/:employeeId" element={<PrivateRoute element={EmployeePage} />} />
                    <Route path="/hr/:employeeId" element={<PrivateRoute element={HRPage} />} />
                    <Route path="/manager/:employeeId" element={<PrivateRoute element={ManagerPage} />} />
                    <Route path="/point-info" element={<PrivateRoute element={PointInfoPage} />} />
                    <Route path="/view-other-points" element={<PrivateRoute element={ViewOtherPointsPage} />} />
                    <Route path="/point-history" element={<PrivateRoute element={PointHistoryPage} />} />
                    <Route path="/change-points" element={<PrivateRoute element={ChangePointsPage} />} />
                    <Route path="/personal-info-navigation" element={<PrivateRoute element={InfoNavigationPage} />} />
                    <Route path="/personal-info/summary" element={<PrivateRoute element={SummaryPage} />} />
                    <Route path="/personal-info/overview" element={<PrivateRoute element={OverviewPage} />} />
                    <Route path="/personal-info/job" element={<PrivateRoute element={JobPage} />} />
                    <Route path="/personal-info/compensation" element={<PrivateRoute element={CompensationPage} />} />
                    <Route path="/personal-info/personal" element={<PrivateRoute element={PersonalPage} />} />
                    <Route path="/personal-info/performance" element={<PrivateRoute element={PerformancePage} />} />
                    <Route path="/personal-info/career" element={<PrivateRoute element={CareerPage} />} />
                    <Route path="/personal-info/feedback" element={<PrivateRoute element={FeedbackPage} />} />
                </Routes>
            </div>
        </div>
    );
};

const AppWrapper = () => (
    <Router>
        <App />
    </Router>
);

export default AppWrapper;