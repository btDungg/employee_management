import { useState, useEffect } from 'react';
import PropTypes from 'prop-types';
import { getEmployeePoints, getPointChanges } from '../apis/api';
import 'bootstrap/dist/css/bootstrap.min.css';

const EmployeePoints = ({ employeeId }) => {
    const [employee, setEmployee] = useState({});
    const [history, setHistory] = useState([]);

    useEffect(() => {
        const getPoints = async () => {
            const data = await getEmployeePoints(employeeId);
            setEmployee(data);
        };

        const getHistory = async () => {
            const historyData = await getPointChanges(employeeId);
            setHistory(historyData);
        };

        getPoints();
        getHistory();
    }, [employeeId]);

    return (
        <div className="container mt-4">
            <h2 className="mb-3">Your Points</h2>
            <p><strong>Name:</strong> {employee.name}</p>
            <p><strong>Points:</strong> {employee.point}</p>
            <h3 className="mt-4">History</h3>
            <table className="table table-striped">
                <thead>
                <tr>
                    <th>Amount</th>
                    <th>Change Date</th>
                    <th>Reason</th>
                    <th>Reserved Money</th>
                    <th>Received people</th>
                </tr>
                </thead>
                <tbody>
                {history.map((entry, index) => (
                    <tr key={index}>
                        <td>{entry.amount}</td>
                        <td>{new Date(entry.changeDate).toLocaleString()}</td>
                        <td>{entry.reason}</td>
                        <td>{entry.reservedMoney}</td>
                        <td>{entry.receivedId}</td>
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    );
};

EmployeePoints.propTypes = {
    employeeId: PropTypes.string.isRequired,
};

export default EmployeePoints;