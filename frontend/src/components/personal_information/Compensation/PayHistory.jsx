import React from "react";

const PayHistory = (props) => {
    const employee = props.employee;

    return (
        <div className="content-under-navbar bg-white rounded-1">
            <div className="p-5">
                <h1 className="text-xl font-bold mb-2">Pay History</h1>
                <div className="mb-6 ">
                    <p className="text-lg font-semibold fs-4">Totals</p>
                    <table className="table table-bordered">
                        <thead>
                            <tr>
                                <th>Effective Date</th>
                                <th>Compensation Action</th>
                                <th>Reason</th>
                                <th>Total Salary & Allowances</th>
                                <th>Total Base Pay</th>
                                <th>Currency</th>
                                <th>Frequecy</th>
                            </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td>{employee?.hireDate ? new Date(employee.hireDate).toLocaleDateString() : "N/A"}</td>
                            <td>Hire Compensation</td>
                            <td>Hire Employee &gt; New Employee</td>
                            <td>{employee.salary ? employee.salary.toLocaleString('vi-VN') : 'N/A'}</td>
                            <td>{employee.salary ? employee.salary.toLocaleString('vi-VN') : 'N/A'}</td>
                            <td>VND</td>
                            <td>Annual</td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    );
};

export default PayHistory;