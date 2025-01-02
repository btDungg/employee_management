import React from "react";

const Emergency = (props) => {
    const employee = props.employee;
    const emergencyContacts = employee?.emergencyContacts || [];

    return (
        <div className="content-under-navbar bg-white rounded-1">
            <div className="px-5 pt-5 pb-3">
                <h1 className="text-xl font-bold mb-2">Emergency</h1>
                <button className="mb-4 px-3 py-2 rounded">Edit</button>
                <table className="table table-bordered">
                    <thead>
                    <tr>
                        <th>Priority</th>
                        <th>Emergency Contact</th>
                        <th>Relationship</th>
                        <th>Preferred Language</th>
                        <th>Primary Contact Information</th>
                        <th>Alternative Contact Information</th>
                    </tr>
                    </thead>
                    <tbody>
                    {emergencyContacts.map((contact, index) => (
                        <tr key={index}>
                            <td>{contact.priority}</td>
                            <td>{contact.name}</td>
                            <td>{contact.relationship}</td>
                            <td>{contact.preferredLanguage}</td>
                            <td>{contact.primaryContactInformation}</td>
                            <td>{contact.alternativeContactInformation}</td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            </div>
        </div>
    );
};

export default Emergency;