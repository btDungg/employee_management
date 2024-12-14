import React from "react";

const Information = (props) => {
    const employee = props.employee;

    // Explicitly map desired keys and values
    const employeeData = [
        { label: "Name", value: employee?.name },
        { label: "Age", value: employee?.age },
        { label: "Gender", value: employee?.gender ? "Male" : "Female" },
        { label: "Date of Birth", value: employee?.dateOfBirth ? new Date(employee.dateOfBirth).toLocaleDateString() : null },
        { label: "Marital Status", value: employee?.marital },
        { label: "Citizenship Status", value: employee?.citizenshipStatus },
        { label: "Country of Birth", value: employee?.countryOfBirth },
        { label: "City of Birth", value: employee?.cityOfBirth },
        { label: "Region of Birth", value: employee?.regionOfBirth },
        { label: "Primary Nationality", value: employee?.primaryNationality },
        { label: "Religion", value: employee?.religion }
    ];

    return (
        <div className="content-under-navbar bg-white rounded-1">
            <div className="px-5 pt-5 pb-3">
                <h1 className="text-xl font-bold mb-2">Personal Information</h1>
                <button className="mb-4 px-3 py-2 rounded">Edit</button>
                <ul className="list-unstyled">
                    {employeeData.map(
                        (item, index) =>
                            item.value && (
                                <li key={index} className="py-2 border-b border-gray-300">
                                    <div className="row">
                                        <span className="font-bold fw-bold col-2">{item.label} </span>
                                        <span className="col-1">{item.value}</span>
                                    </div>
                                </li>
                            )
                        )
                    }
                </ul>
            </div>
        </div>
    );
};

export default Information;
