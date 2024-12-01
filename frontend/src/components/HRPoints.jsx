import React, { useState, useEffect } from 'react';
import { getListEmployeesPoints } from '../apis/api';
import PropTypes from "prop-types";
import 'bootstrap/dist/css/bootstrap.min.css';

const HRPoints = ({ employeeId }) => {
    const [employeesPoints, setEmployeesPoints] = useState([]);
    const [showPopup, setShowPopup] = useState(false);
    const [selectedEmployee, setSelectedEmployee] = useState(null);

    useEffect(() => {
        const fetchEmployeesPoints = async () => {
            const data = await getListEmployeesPoints(employeeId);
            setEmployeesPoints(data);
        };
        fetchEmployeesPoints();
    }, [employeeId]);

    const handleAddClick = (employee) => {
        setSelectedEmployee(employee);
        setShowPopup(true);
    };

    const handleClosePopup = () => {
        setShowPopup(false);
        setSelectedEmployee(null);
    };

    const handlePointsIncreased = async () => {
        const data = await getListEmployeesPoints(employeeId);
        setEmployeesPoints(data);
    };

    return (
        <div className="container mt-5">
            <h2 className="text-center mb-4">All Employee Points</h2>
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
                <IncreasePointsPopupHR
                    show={showPopup}
                    handleClose={handleClosePopup}
                    employee={selectedEmployee}
                    onPointsIncreased={handlePointsIncreased}
                />
            )}
        </div>
    );
};

HRPoints.propTypes = {
    employeeId: PropTypes.string.isRequired,
};

export default HRPoints;
