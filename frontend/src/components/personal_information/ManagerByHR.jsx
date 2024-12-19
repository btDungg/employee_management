import React, { useState, useEffect } from 'react';
import WithSidebar from "./WithSidebar.jsx";
import { searchEmployee } from "../../apis/api.js";
import { useNavigate } from 'react-router-dom';

const ManagerByHR = ({ employee }) => {
    const navigate = useNavigate();
    const [listEmployee, setlistEmployee] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [searchType, setSearchType] = useState("name"); // Loại tìm kiếm (id, name, email, organization, organization_id)
    const [searchValue, setSearchValue] = useState(""); // Giá trị tìm kiếm

    useEffect(() => {
        fetchListEmployee(); // Lấy danh sách nhân viên ban đầu
    }, []);

    const fetchListEmployee = async (params = {}) => {
        setLoading(true);
        setError(null);
        try {
            const data = await searchEmployee(params);
            setlistEmployee(data);
        } catch (err) {
            console.error("Failed to fetch employee:", err);
            setError("Failed to load team employee. Please try again.");
        } finally {
            setLoading(false);
        }
    };

    const handleSearch = () => {
        const params = { [searchType]: searchValue };
        console.log("Search params:", params);
        fetchListEmployee(params);
    };

    const handleRowClick = (id) => {
        console.log("Clicked row with ID:", id);
        if (id === employee?.id) {
            navigate(`/personal-info/summary`);
        } else {
            navigate(`/profile/${id}/summary`);
        }
    };

    if (loading) {
        return <div className="content bg-white">Loading...</div>;
    }

    if (error) {
        return <div className="content bg-white text-danger">{error}</div>;
    }

    return (
        <div className="bg-white">
            <h4>List employee:</h4>
            {/* Phần tìm kiếm */}
            <div className="mb-3">
                <div className="row g-2">
                    <div className="col-md-7">
                       
                        <input
                            type="text"
                            className="form-control"
                            placeholder="Nhập giá trị tìm kiếm..."
                            value={searchValue}
                            onChange={(e) => setSearchValue(e.target.value)}
                        />
                    </div>
                    <div className="col-md-3">
                    <select
                            className="form-select"
                            value={searchType}
                            onChange={(e) => setSearchType(e.target.value)}
                        >
                            <option value="id">ID</option>
                            <option value="name">Tên</option>
                            <option value="email">Email</option>
                            <option value="nameOrganization">Phòng ban</option>
                            
                        </select>
                    </div>
                    <div className="col-md-2">
                        <button className="btn btn-primary w-100" onClick={handleSearch}>
                            Search
                        </button>
                    </div>
                </div>
            </div>

            {/* Danh sách nhân viên */}
            {listEmployee && listEmployee.length > 0 ? (
                <table className="table table-bordered table-hover" >
                    <thead className="table-light">
                        <tr>
                            <th>ID</th>
                            <th>Name</th>
                            <th>Gender</th>
                            <th>Company Email</th>
                            <th>Personal Email</th>
                            <th>Team</th>
                            <th>Hire Date</th>
                            <th>Type</th>
                            <th>Manager Name</th>
                        </tr>
                    </thead>
                    <tbody>
                        {listEmployee.map((emp) => {
                            const primaryEmails = emp.emails?.filter(email => email.usage_type?.includes("Primary")) || [];
                            const personalEmail = primaryEmails[0] || emp.emails?.[0];

                            return (
                                <tr key={emp.id} onClick={() => handleRowClick(emp.id)}>
                                    <td>{emp.id}</td>
                                    <td>{emp.name}</td>
                                    <td>{emp.gender}</td>
                                    <td>{emp.email_company}</td>
                                    <td>{personalEmail?.email || "No Email"}</td>
                                    <td>{emp.organization_name}</td>
                                    <td>{emp.hire_date}</td>
                                    <td>{emp.type}</td>
                                    <td>{emp.manager_name}</td>
                                </tr>
                            );
                        })}
                    </tbody>
                </table>
            ) : (
                <p>Không tìm thấy nhân viên.</p>
            )}
        </div>
    );
};

export default ManagerByHR;
