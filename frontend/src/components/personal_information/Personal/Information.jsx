import React, { useContext } from "react";
import { EmployeeContext } from "../EmployeeProvider.jsx";  // Import context

const Information = ({ emp }) => {
    const { employee: contextEmployee } = useContext(EmployeeContext);  // Get employee from context
    const employee = emp || contextEmployee;  // Use `emp` prop if available, otherwise fall back to context

    console.log("emp", emp);
   

    // Map employee data for display
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
        <div className="p-6">
            <h2 className="text-xl font-bold mb-4">Personal Information</h2>
            <button className="mb-4 px-4 py-2 rounded">Edit</button>

            <div className="border p-4 rounded">
                <ul className="list-none">
                    {employeeData.map(
                        (item, index) =>
                            item.value && (  // Only display non-null values
                                <li key={index} className="py-2 border-b border-gray-300">
                                    <span className="font-bold">{item.label}: </span>
                                    <span>{item.value}</span>
                                </li>
                            )
                    )}
                </ul>
            </div>
        </div>
    );
};

export default Information;
