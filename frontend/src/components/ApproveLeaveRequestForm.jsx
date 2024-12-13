import React, { useState, useEffect } from 'react';
import ApproveLeaveRequestForm from '../pages/ViewMyApproveLRPage';
import NotificationPopup from './NotificationPopup';
import { getMyDayOff, sendLeaveRequest } from '../apis/api';
import '../../public/css/Popup.css';
import '../../public/css/ApproveLeaveRequestPage.css'

const AcceptLeaveRequestForm = ({onCommit}) =>
{
    const [formData, setFormData] = useState({});
    const [showApprove, setShowApprove] = useState(false);
    const [reasonApprove, setReasonApprove] = useState('');

    const handleCommit = (data) => {
        setFormData(data);
        setShowApprove(true);
    };

    const handleSubmit = (e) => {
        e.preventDefault(); 
        onCommit({
            ...formData,
            reasonApprove,
        });
    };

    const handleReturn = () => {
        setShowApprove(false);
    }

    return (
        <div>
            {!showApprove && (
                <ApproveLeaveRequestForm onCommit={handleCommit} />
            )}
            {showApprove &&
                <form className="approve-lr-info" onSubmit={handleSubmit}>
                <h2>Phê duyệt yêu cầu nghỉ phép</h2>
                    <p><strong>Tên nhân viên: </strong>{formData.employeeName}</p>
                    <p><strong>Ngày bắt đầu: </strong> {formData.startDate}</p>
                    <p><strong>Ngày kết thúc: </strong> {formData.endDate}</p>
                    <p><strong>Số ngày yêu cầu: </strong> {formData.requestDays}</p>
                    <p><strong>Lý do xin nghỉ: </strong> {formData.reason}</p>
                    <p><strong>Loại ngày nghỉ: </strong> {formData.dayOffType}</p>
                    <div className="form-group">
                        {formData.status == "Đã duyệt" ? 
                        <p><strong><label htmlFor="reason">Lý do chấp thuận</label></strong></p> :
                        <p><strong><label htmlFor="reason">Lý do từ chối </label></strong></p>}
                        <textarea
                            id="reason"
                            value={reasonApprove}
                            onChange={(e) => setReasonApprove(e.target.value)}
    
                    />
                    </div>
    
                <button className="btn" type="submit">Xác nhận</button>
                <button className="btn" type="button" onClick={() => handleReturn()} >Trở về</button>
            </form>
            }
        </div>
        
    )
    
}

export default AcceptLeaveRequestForm;