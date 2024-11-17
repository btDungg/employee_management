import React, { useState } from 'react';
import { getEmployeeById } from '../apis/api';
import '../../public/css/ChangePointsForm.css';

const HRChangePointsForm = ({ onCommit }) => {
    const [employeeId, setEmployeeId] = useState('');
    const [employeeName, setEmployeeName] = useState('');
    const [currentPoints, setCurrentPoints] = useState(0);
    const [changePoints, setChangePoints] = useState(0);
    const [changeType, setChangeType] = useState('');
    const [reason, setReason] = useState('');
    const [searchWarning, setSearchWarning] = useState('');

    const handleSearch = async () => {
        try {
            // Reset employee-related state variables
            setEmployeeName('');
            setCurrentPoints(0);
            setChangePoints(0);
            setChangeType('');
            setReason('');
            setSearchWarning('');

            const employeeData = await getEmployeeById(employeeId);
            setEmployeeName(employeeData.name);
            setCurrentPoints(employeeData.point);
        } catch (error) {
            console.error('Error fetching employee data:', error);
            setSearchWarning('Nhân viên này không tồn tại.');
        }
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        onCommit({
            employeeId,
            employeeName,
            currentPoints,
            changePoints,
            changeType,
            reason
        });
    };

    return (
        <div className="change-points-form">
            <h2 >Thay đổi điểm</h2>
            <form onSubmit={handleSubmit}>
                <div className="form-group search-group">
                    <label htmlFor="employeeId">ID nhân viên</label>
                    <input
                        type="text"
                        id="employeeId"
                        value={employeeId}
                        onChange={(e) => setEmployeeId(e.target.value)}
                        required
                    />
                    <button className="btn" type="button" onClick={handleSearch}>Tìm kiếm</button>
                </div>
                {searchWarning && <div className="search-warning">{searchWarning}</div>}
                <div className="form-group">
                    <label>Tên nhân viên</label>
                    <div>{employeeName}</div>
                </div>
                <div className="form-group">
                    <label>Điểm hiện tại của nhân viên</label>
                    <div>{currentPoints}</div>
                </div>
                <div className="form-group">
                    <label htmlFor="changePoints">Số điểm muốn đổi</label>
                    <input
                        type="number"
                        id="changePoints"
                        value={changePoints}
                        onChange={(e) => setChangePoints(e.target.value)}
                        required
                    />
                </div>
                <div className="form-group">
                    <label htmlFor="changeType">Loại thay đổi</label>
                    <select
                        id="changeType"
                        value={changeType}
                        onChange={(e) => setChangeType(e.target.value)}
                        required
                    >
                        <option value="">Chọn loại</option>
                        <option value="increase">Tăng</option>
                        <option value="decrease">Giảm</option>
                    </select>
                </div>
                <div className="form-group">
                    <label htmlFor="reason">Lý do</label>
                    <textarea
                        id="reason"
                        value={reason}
                        onChange={(e) => setReason(e.target.value)}
                        required
                    />
                </div>
                <button className="btn" type="submit">Xác nhận</button>
            </form>
        </div>
    );
};

export default HRChangePointsForm;