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
import '../public/css/app.css';

const App = () => {
    const [isSidebarOpen, setIsSidebarOpen] = useState(false);
    const location = useLocation();
    const showTopNavbar = ['/point-info', '/view-other-points', '/point-history', '/change-points'].includes(location.pathname);

    const toggleSidebar = () => {
        setIsSidebarOpen(!isSidebarOpen);
    };

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
            <Sidebar isOpen={isSidebarOpen} toggleSidebar={toggleSidebar} />
            {showTopNavbar && <TopNavbarPoint />}
            <div className={`content ${isSidebarOpen ? "" : "expanded"}`} style={{ marginTop: showTopNavbar ? '60px' : '0' }}>
                <Routes>
                    <Route path="/" element={<HomePage />} />
                    <Route path="/employee/:employeeId" element={<EmployeePage />} />
                    <Route path="/hr/:employeeId" element={<HRPage />} />
                    <Route path="/manager/:employeeId" element={<ManagerPage />} />
                    <Route path="/point-info" element={<PointInfoPage />} />
                    <Route path="/view-other-points" element={<ViewOtherPointsPage />} />
                    <Route path="/point-history" element={<PointHistoryPage />} />
                    <Route path="/change-points" element={<ChangePointsPage />} />
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