import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import HomePage from './pages/HomePage.jsx';
import EmployeePage from './pages/EmployeePage.jsx';
import HRPage from './pages/HRPage.jsx';
import ManagerPage from './pages/ManagerPage.jsx';

const App = () => {
    return (
        <Router>
            <Routes>
                <Route path="/" element={<HomePage />} />
                <Route path="/employee/:employeeId" element={<EmployeePage />} />
                <Route path="/hr/:employeeId" element={<HRPage />} />
                <Route path="/manager/:employeeId" element={<ManagerPage />} />
            </Routes>
        </Router>
    );
};

export default App;