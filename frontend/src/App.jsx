import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import { useState } from 'react';
import HomePage from './pages/HomePage.jsx';
import EmployeePage from './pages/EmployeePage.jsx';
import HRPage from './pages/HRPage.jsx';
import ManagerPage from './pages/ManagerPage.jsx';
import Sidebar from './components/Sidebar.jsx';
import './app.css';

const App = () => {
    const [isSidebarOpen, setIsSidebarOpen] = useState(false);

    const toggleSidebar = () => {
        setIsSidebarOpen(!isSidebarOpen);
    };

    return (
        <Router>
            <div className="app">
                <Sidebar isOpen={isSidebarOpen} toggleSidebar={toggleSidebar} />
                <div className={`content ${isSidebarOpen ? "" : "expanded"}`}>
                    <Routes>
                        <Route path="/" element={<HomePage />} />
                        <Route path="/employee/:employeeId" element={<EmployeePage />} />
                        <Route path="/hr/:employeeId" element={<HRPage />} />
                        <Route path="/manager/:employeeId" element={<ManagerPage />} />
                    </Routes>
                </div>
            </div>
        </Router>
    );
};

export default App;