import { useState, useEffect } from 'react';
import { getListEmployeesPoints, getManagerBonusPointsById } from '../apis/api';
import PropTypes from "prop-types";
import 'bootstrap/dist/css/bootstrap.min.css';

const ManagerPoints = ({ employeeId }) => {
    const [employeesPoints, setEmployeesPoints] = useState([]);
    const [bonusPoints, setBonusPoints] = useState(0);
    const [showPopup, setShowPopup] = useState(false);
    const [selectedEmployee, setSelectedEmployee] = useState(null);

    useEffect(() => {
        const fetchEmployeesPoints = async () => {
            const data = await getListEmployeesPoints(employeeId);
            setEmployeesPoints(data);
        };

        const fetchBonusPoints = async () => {
            const bonus = await getManagerBonusPointsById(employeeId);
            setBonusPoints(bonus);
        };

        fetchEmployeesPoints();
        fetchBonusPoints();
    }, [employeeId]);

    const handleAddClick = (employee) => {
        setSelectedEmployee(employee);
        setShowPopup(true);
    };

    const handleClosePopup = async () => {
        setShowPopup(false);
        setSelectedEmployee(null);
        const bonus = await getManagerBonusPointsById(employeeId);
        setBonusPoints(bonus);
    };

    const handlePointsIncreased = () => {
        // Refresh the points data after increasing points
        const fetchEmployeesPoints = async () => {
            const data = await getListEmployeesPoints(employeeId);
            setEmployeesPoints(data);
        };
        fetchEmployeesPoints();
    };

    return (
        <div className="container mt-5">
            <h2 className="text-center mb-4">Points of Employees You Manage</h2>
            <p className="text-center">Your bonus Points: {bonusPoints}</p>
            <table className="table table-striped">
                <thead>
                <tr>
                    <th>Id</th>
                    <th>Name</th>
                    <th>Points</th>
                    <th>Action</th>
                </tr>
                </thead>
                <tbody>
                {employeesPoints.map((employee, index) => (
                    <tr key={index}>
                        <td>{employee.id}</td>
                        <td>{employee.name}</td>
                        <td>{employee.point}</td>
                        <td>
                            <button
                                className="btn-form btn-primary"
                                onClick={() => handleAddClick(employee)}
                            >
                                Add
                            </button>
                        </td>
                    </tr>
                ))}
                </tbody>
            </table>
            {selectedEmployee && (
                <IncreasePointsPopupManager
                    show={showPopup}
                    handleClose={handleClosePopup}
                    employee={selectedEmployee}
                    managerId={employeeId}
                    onPointsIncreased={handlePointsIncreased}
                />
            )}
        </div>
    );
};

ManagerPoints.propTypes = {
    employeeId: PropTypes.string.isRequired,
};

export default ManagerPoints;
