import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import HomePage from './pages/HomePage.jsx';
import EmployeePage from './pages/EmployeePage.jsx';
import HRPage from './pages/HRPage.jsx';
import ManagerPage from './pages/ManagerPage.jsx';
import Sidebar from './components/Sidebar.jsx';
import './app.css'
const App = () => {
    return (
        <Router>
            <div className="app">
                <Sidebar />
                <div className="content">
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

