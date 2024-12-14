import React from "react";
import { differenceInYears, differenceInMonths, differenceInDays, addYears, addMonths } from 'date-fns';

const JobDetail = (props) => {
    const employee = props.employee;

    // Explicitly map desired keys and values
    const employeeData = [
        { label: "Employee ID", value: employee?.id?.toString().padStart(8, '0') },
        { label: "Supervisory Organization", value: "N/A" },
        { label: "Business Title", value: employee?.businessTitle },
        { label: "Job Profile", value: employee?.jobProfile },
        { label: "Employee Type", value: employee?.marital },
        { label: "Time Type", value: employee?.timeType },
        { label: "FTE", value: (employee?.timeType === "full time" ? "100%" : "60%") },
        { label: "Location", value: employee?.location },
        { label: "Hire Day", value: employee?.hireDate ? new Date(employee.hireDate).toLocaleDateString() : "N/A" },
        {
            label: "Time In Job",
            value: (() => {
                if (!employee?.hireDate) return "N/A";

                const hireDate = new Date(employee.hireDate);
                const now = new Date();

                // Tính số năm làm việc
                const years = differenceInYears(now, hireDate);
                // Tính số tháng (sau khi trừ số năm đã tính)
                const months = differenceInMonths(now, addYears(hireDate, years));
                // Tính số ngày (sau khi trừ số năm và tháng đã tính)
                const days = differenceInDays(now, addMonths(addYears(hireDate, years), months));

                // Trả về chuỗi định dạng
                return `${years} year(s), ${months} month(s), ${days} day(s)`;
            })()
        }
    ];

    return (
        <div className="content-under-navbar bg-white rounded-1">
            <div className="px-5 pt-5 pb-3">
                <h1 className="text-xl font-bold mb-2">Job Detail</h1>
                <button className="mb-4 px-3 py-2 rounded">Edit</button>
                <ul className="list-unstyled">
                    {employeeData.map(
                        (item, index) =>
                            item.value && (
                                <li key={index} className="py-2 border-b border-gray-300">
                                    <div className="row">
                                        <span className="font-bold fw-bold col-3">{item.label} </span>
                                        <span className="col-3">{item.value}</span>
                                    </div>
                                </li>
                            )
                    )}
                </ul>
            </div>
        </div>
    );
};

export default JobDetail;