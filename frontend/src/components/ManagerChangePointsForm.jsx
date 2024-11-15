import React, { useState } from 'react';
import '../../public/css/ChangePointsForm.css';

const ManagerChangePointsForm = ({ onCommit }) => {
    const [employeeId, setEmployeeId] = useState('');
    const [employeeName, setEmployeeName] = useState('');
    const [currentPoints, setCurrentPoints] = useState(0);
    const [bonusPoints, setBonusPoints] = useState(0);
    const [changePoints, setChangePoints] = useState(0);
    const [changeType, setChangeType] = useState('');
    const [reason, setReason] = useState('');

    const handleSearch = () => {
        // Implement search logic here
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        onCommit({
            employeeId,
            employeeName,
            currentPoints,
            bonusPoints,
            changePoints,
            changeType,
            reason
        });
    };

    return (
        <div className="change-points-form">
            <form onSubmit={handleSubmit}>
                <div className="form-group search-group">
                    <label htmlFor="employeeId">Employee ID</label>
                    <input
                        type="text"
                        id="employeeId"
                        value={employeeId}
                        onChange={(e) => setEmployeeId(e.target.value)}
                        required
                    />
                    <button type="button" onClick={handleSearch}>Search</button>
                </div>
                <div className="form-group">
                    <label>Employee Name</label>
                    <div>{employeeName}</div>
                </div>
                <div className="form-group">
                    <label>Current Points</label>
                    <div>{currentPoints}</div>
                </div>
                <div className="form-group">
                    <label>Bonus Points</label>
                    <div>{bonusPoints}</div>
                </div>
                <div className="form-group">
                    <label htmlFor="changePoints">Change Points</label>
                    <input
                        type="number"
                        id="changePoints"
                        value={changePoints}
                        onChange={(e) => setChangePoints(e.target.value)}
                        required
                    />
                </div>
                <div className="form-group">
                    <label htmlFor="changeType">Change Type</label>
                    <select
                        id="changeType"
                        value={changeType}
                        onChange={(e) => setChangeType(e.target.value)}
                        required
                    >
                        <option value="">Select Type</option>
                        <option value="increase">Increase</option>
                        <option value="decrease">Decrease</option>
                    </select>
                </div>
                <div className="form-group">
                    <label htmlFor="reason">Reason</label>
                    <textarea
                        id="reason"
                        value={reason}
                        onChange={(e) => setReason(e.target.value)}
                        required
                    />
                </div>
                <button type="submit">Submit</button>
            </form>
        </div>
    );
};

export default ManagerChangePointsForm;