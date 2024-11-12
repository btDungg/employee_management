import { useState, useEffect } from 'react';
import { getListEmployeesPoints,getEmployeePoints } from "../apis/api";
import '../../public/css/StylePointInfo.css';
import { useParams } from 'react-router-dom';




const PointInfo = () => {
  const [employeesPoints, setEmployeesPoints] = useState([]);
  const [selectedEmployeeDetail, setSelectedEmployeeDetail] = useState(null);

  useEffect(() => {
    const fetchEmployeesPoints = async () => {
      try {
        const data = await getListEmployeesPoints(1);
        setEmployeesPoints(data);
      } catch (error) {
        console.error("Lỗi khi lấy danh sách điểm:", error);
      }
    };
    fetchEmployeesPoints();
  }, []);

  const handleRowClick = async (id) => {
    
    try {
        const employeeDetail = await getEmployeePoints(id);
        setSelectedEmployeeDetail(employeeDetail);
    } catch (error) {
        console.error("Lỗi khi lấy chi tiết điểm:", error);
    }
};

  return (
    <div className="point-info">
      <h2>Danh sách điểm của nhân viên</h2>
      {employeesPoints.length > 0 ? (
        <table className="points-table">
          <thead>
            <tr>
              <th>ID</th>
              <th>Tên</th>
              <th>Điểm hiện có</th>
              <th>Loại nhân viên</th>
              <th>Người quản lý trực tiếp</th>
            </tr>
          </thead>
          <tbody>
            {employeesPoints.map((employee) => (
              <tr key={employee.id} onClick={() => handleRowClick(employee.id)} style={{ cursor: "pointer" }}>
                <td>{employee.id}</td>
                <td>{employee.name}</td>
                <td>{employee.point}</td>
                <td>{employee.type}</td>
                <td>{employee.managerId}</td>
              </tr>
            ))}
          </tbody>
        </table>
      ) : (
        <p>Đang tải...</p>
      )}

            {selectedEmployeeDetail && (
            <>
                <div className="modal-overlay" onClick={() => setSelectedEmployeeDetail(null)}></div>
                <div className="employee-detail-modal">
                <h3>Chi tiết điểm của nhân viên</h3>
                <p><strong>Tên:</strong> {selectedEmployeeDetail.name}</p>
                <p><strong>Điểm:</strong> {selectedEmployeeDetail.point}</p>
                <p><strong>Loại nhân viên:</strong> {selectedEmployeeDetail.type}</p>
                <p><strong>Người quản lý trực tiếp:</strong> {selectedEmployeeDetail.managerId}</p>
                <button onClick={() => setSelectedEmployeeDetail(null)}>Đóng</button>
                </div>
            </>
            )}
    </div>
  );
};

export default PointInfo;
