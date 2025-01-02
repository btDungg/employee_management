import React from "react";

const BankAccount = (props) => {
    const employee = props.employee;

    return (
        <div className="content-under-navbar bg-white rounded-1">
            <div className="p-5">
                <h1 className="text-xl font-bold mb-2">Bank Account</h1>

            </div>
        </div>
    );
};

export default BankAccount;