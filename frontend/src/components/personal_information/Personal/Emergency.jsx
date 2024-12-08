import React from "react";

const Emergency = (props) => {
    const employee = props.employee;
    const emergencyContacts = employee?.emergencyContacts || [];
    console.log(emergencyContacts)
    return (
        <div className="p-6">
            <h2 className="text-xl font-bold mb-4">Emergency</h2>
            <button className="mb-4 px-4 py-2 rounded">Edit</button>

            <div className="border p-4 rounded">
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