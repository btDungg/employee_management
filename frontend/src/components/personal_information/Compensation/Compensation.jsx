import React from "react";

const Compensation = (props) => {
    const employee = props.employee;

    return (
        <div className="content-under-navbar bg-white rounded-1">
            <div className="p-5">
                <h1 className="text-xl font-bold mb-2">Compensation</h1>
                <div className="mb-6 ">
                    <p className="text-lg font-semibold fs-4">Totals</p>
                    <table className="table table-bordered">
                        <thead>
                        <tr>
                            <th>Total Salary & Allowances</th>
                            <th>Total Base Pay</th>
                            <th>Currency</th>
                            <th>Frequecy</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td>{employee.salary ? employee.salary.toLocaleString('vi-VN') : 'N/A'}</td>
                            <td>{employee.salary ? employee.salary.toLocaleString('vi-VN') : 'N/A'}</td>
                            <td>VND</td>
                            <td>Annual</td>
                        </tr>
                        </tbody>
                    </table>
                </div>
                <div className="mb-6 ">
                    <p className="text-lg font-semibold fs-4">Plan Assugnments</p>
                    <table className="table table-bordered">
                        <thead>
                        <tr>
                            <th>Effective Date</th>
                            <th>Plan Type</th>
                            <th>Compensation Plan</th>
                            <th>Assignment</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td>{employee?.hireDate ? new Date(employee.hireDate).toLocaleDateString() : "N/A"}</td>
                            <td>Hourly</td>
                            <td>Hourly Plan</td>
                            <td>{employee.salary ? (employee.salary / (12 * 20 * 8)).toLocaleString('vi-VN') : 'N/A'} VND Hourly</td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    );
};

export default Compensation;