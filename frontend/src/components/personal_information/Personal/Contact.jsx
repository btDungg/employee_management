import React, { useContext } from "react";
import { EmployeeContext } from "../EmployeeProvider.jsx";

const Contact = () => {
    const { employee } = useContext(EmployeeContext);
    console.log(employee)
    const homeAddresses = employee?.addresses?.filter(addr => addr.usage_type === "yes") || [];
    const phones = employee?.phones || [];
    const emails = employee?.emails || [];

    return (
        <div className="p-6">
            <h2 className="text-xl font-bold mb-4">Contact</h2>
            <button className="mb-4 px-4 py-2 rounded">Edit</button>
            {/* Home Addresses */}
            <div className="border p-4 rounded">
                <div className="mb-6 ">
                    <h3 className="text-lg font-semibold">Home Addresses</h3>
                    <table className="table table-bordered">
                        <thead>
                        <tr>
                            <th>Address</th>
                            <th>Effective Date</th>
                            <th>Usage</th>
                        </tr>
                        </thead>
                        <tbody>
                        {homeAddresses.map((address) => (
                            <tr key={address.id}>
                                <td>{address.address}</td>
                                <td>{new Date(address.effectiveDate).toLocaleDateString()}</td>
                                <td>{address.usage_type}</td>
                            </tr>
                        ))}
                        </tbody>
                    </table>
                </div>

                {/* Home Phones */}
                <div className="mb-6">
                    <h3 className="text-lg font-semibold">Home Phones</h3>
                    <table className="table table-bordered">
                        <thead>
                        <tr>
                            <th>Phone</th>
                            <th>Usage</th>
                        </tr>
                        </thead>
                        <tbody>
                        {phones.map((phone) => (
                            <tr key={phone.id}>
                                <td>{phone.phone}</td>
                                <td>{phone.usage_type}</td>
                            </tr>
                        ))}
                        </tbody>
                    </table>
                </div>

                {/* Home Emails */}
                <div className="mb-6">
                    <h3 className="text-lg font-semibold">Home Emails</h3>
                    <table className="table table-bordered">
                        <thead>
                        <tr>
                            <th>Email</th>
                            <th>Usage</th>
                        </tr>
                        </thead>
                        <tbody>
                        {emails.map((email) => (
                            <tr key={email.id}>
                                <td>{email.email}</td>
                                <td>{email.usage_type}</td>
                            </tr>
                        ))}
                        </tbody>
                    </table>
                </div>

                {/* Work Addresses */}
                <div className="mb-6">
                    <h3 className="text-lg font-semibold">Work Addresses</h3>
                    <table className="table table-bordered">
                        <thead>
                        <tr>
                            <th>Address</th>
                            <th>Effective Date</th>
                            <th>Usage</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr key={1}>
                            <td>{employee?.location}</td>
                            <td>{new Date(employee.hireDate).toLocaleDateString()}</td>
                            <td>Business(Primary)</td>
                        </tr>
                        </tbody>
                    </table>
                </div>

                {/* Work Emails */}
                <div>
                    <h3 className="text-lg font-semibold">Work Emails</h3>
                    <table className="table table-bordered">
                        <thead>
                        <tr>
                            <th>Email</th>
                            <th>Usage</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td>{employee?.emailCompany}</td>
                            <td>Business(Primary)</td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    );
};

export default Contact;