import React from "react";
import WithSidebar from './WithSidebar';

const Personal = ({ employee }) => {
    if (!employee) {
        return <div>No employee data available.</div>;
    }

    const homeAddresses = employee.addresses?.filter(addr => addr.usage_type === "yes") || [];
    const workAddresses = employee.addresses?.filter(addr => addr.usage_type === "no") || [];
    const primaryPhones = employee.phones?.filter(phone => phone.usage_type === "yes") || [];
    const otherPhones = employee.phones?.filter(phone => phone.usage_type === "no") || [];
    const primaryEmails = employee.emails?.filter(email => email.usage_type === "yes") || [];
    const otherEmails = employee.emails?.filter(email => email.usage_type === "no") || [];

    return (
        <div className="content p-6">
            <h2 className="text-xl font-bold mb-4">Contact</h2>

            {/* Home Contact Information */}
            <div className="mb-6">
                <h3 className="text-lg font-semibold">Home Contact Information</h3>
                <p>Addresses:</p>
                <ul>
                    {homeAddresses.map((address) => (
                        <li key={address.id}>
                            {address.address} (Effective Date: {new Date(address.effectiveDate).toLocaleDateString()})
                        </li>
                    ))}
                </ul>

                <p>Phones:</p>
                <ul>
                    {primaryPhones.map((phone) => (
                        <li key={phone.id}>
                            {phone.phone} - Mobile (Home Primary)
                        </li>
                    ))}
                    {otherPhones.map((phone) => (
                        <li key={phone.id}>
                            {phone.phone} - Mobile (Home)
                        </li>
                    ))}
                </ul>

                <p>Email Addresses:</p>
                <ul>
                    {primaryEmails.map((email) => (
                        <li key={email.id}>{email.email} (Primary)</li>
                    ))}
                    {otherEmails.map((email) => (
                        <li key={email.id}>{email.email}</li>
                    ))}
                </ul>
            </div>

            {/* Work Contact Information */}
            <div>
                <h3 className="text-lg font-semibold">Work Contact Information</h3>
                <p>Addresses:</p>
                <ul>
                    {workAddresses.map((address) => (
                        <li key={address.id}>
                            {address.address} (Effective Date: {new Date(address.effectiveDate).toLocaleDateString()})
                        </li>
                    ))}
                </ul>

                <p>Email Addresses:</p>
                <ul>
                    <li>{employee.businessTitle}@company.com</li>
                </ul>
            </div>
        </div>
    );
};

export default WithSidebar(Personal);
