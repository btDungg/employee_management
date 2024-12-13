import React from "react";
import "../../../../public/css/personal_information/Navbar.css"

const Contact = (props) => {
    const employee = props.employee;
    const homeAddresses = employee?.addresses?.filter(addr => addr.usage_type === "yes") || [];
    const phones = employee?.phones || [];
    const emails = employee?.emails || [];

    return (
        <div className="content-under-navbar bg-white rounded-1">
            <div className="px-5 pt-5 pb-3">
                <h1 className="text-xl font-bold mb-2">Contact</h1>
                <button className="mb-4 px-3 py-2 rounded">Edit</button>
                <h2 className="text-xl font-bold">Home contact information</h2>
                {/* Home Addresses */}
                <div className="mb-6 ">
                    <p className="text-lg font-semibold fs-4">Home Addresses</p>
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
                    <p className="text-lg font-semibold fs-4">Home Phones</p>
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
                    <p className="text-lg font-semibold fs-4">Home Emails</p>
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

                <h2 className="text-xl font-bold">Home contact information</h2>
                {/* Work Addresses */}
                <div className="mb-6">
                    <p className="text-lg font-semibold fs-4">Work Addresses</p>
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
                    <p className="text-lg font-semibold fs-4">Work Emails</p>
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