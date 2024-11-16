import React, { useState, useEffect } from 'react';
import { getMyPoint } from '../apis/api';

const PointInfoPage = () => {
    //return <div>Thông tin điểm</div>;
	const [employee, setEmployee] = useState({});
	    
	    useEffect(() => {
	        const getPoints = async () => {
			try	{
	            const data = await getMyPoint();
	            setEmployee(data);
	        } catch (error) {
			        console.error("Lỗi khi lấy điểm:", error);
			 }
			};
	        getPoints();
	    },[]);

	    return (
	        <div className="point-info">
	            <h2 className="mb-3">Thông tin điểm</h2>
	            <p><strong>Tên:</strong> {employee.name}</p>
	            <p><strong>Điểm:</strong> {employee.point}</p>
				{("bonusEmployeePoint" in employee) && (
				        <p><strong>Điểm thưởng riêng:</strong> {employee.bonusEmployeePoint}</p>
				      )}
	        </div>
	    );
	};


export default PointInfoPage;