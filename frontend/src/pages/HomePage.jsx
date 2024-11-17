import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { getEmployeeRoleById } from '../apis/api';
import 'bootstrap/dist/css/bootstrap.min.css';

const HomePage = () => {
    const [employeeId, setEmployeeId] = useState('');
    const navigate = useNavigate();

    const handleSubmit = async () => {
        try {
            const role = await getEmployeeRoleById(employeeId);
            if (role === 'HR') {
                navigate(`/hr/${employeeId}`);
            } else if (role === 'Manager') {
                navigate(`/manager/${employeeId}`);
            } else {
                navigate(`/employee/${employeeId}`);
            }
        } catch (error) {
            console.error('Error fetching employee role:', error);
        }
    };

    return (
        <div className="container mt-5">
            
        </div>
    );
};

export default HomePage;