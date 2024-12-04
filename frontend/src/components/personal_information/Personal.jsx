import React from "react";

const Personal = () => {
    return (
        <div className="content p-6">
            <h2 className="text-xl font-bold mb-4">Contact</h2>
            <div className="mb-6">
                <h3 className="text-lg font-semibold">Home Contact Information</h3>
                <p>Addresses: Địa chỉ của mình</p>
                <p>Phones:</p>
                <ul>
                    <li>123456789 - Mobile (Home Primary)</li>
                    <li>321434345 - Mobile (Home)</li>
                </ul>
                <p>Email Addresses:</p>
                <ul>
                    <li>diyubd719@gmail.com</li>
                    <li>211204913@student.hcmus.edu.vn</li>
                </ul>
            </div>

            <div>
                <h3 className="text-lg font-semibold">Work Contact Information</h3>
                <p>Addresses: Địa chỉ công ty</p>
                <p>Email Addresses:</p>
                <ul>
                    <li>duyminhbui@cyabc.com</li>
                </ul>
            </div>
        </div>
    );
};

export default Personal;
