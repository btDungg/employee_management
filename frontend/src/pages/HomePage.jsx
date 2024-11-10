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
            <div>123444</div>
            <h1 className="text-center mb-4">Home Page</h1>
            <div className="row justify-content-center">
                <div className="col-md-6">
                    <div className="input-group mb-3">
                        <input
                            type="text"
                            className="form-control"
                            value={employeeId}
                            onChange={(e) => setEmployeeId(e.target.value)}
                            placeholder="Enter Employee ID"
                        />
                        <button className="btn btn-primary" onClick={handleSubmit}>Submit</button>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default HomePage;