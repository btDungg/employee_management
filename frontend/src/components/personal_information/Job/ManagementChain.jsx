import React from "react";

const ManagementChain = (props) => {

    return (
        <div className="content-under-navbar bg-white rounded-1">
            <div className="px-5 pt-5">
                <h1 className="text-xl font-bold mb-2">Management Chain</h1>
            </div>
            <div className="px-5 pb-5">
                <table className="table table-bordered">
                    <thead>
                    <tr>
                        <th>Organization</th>
                        <th>Manager</th>
                        <th>Phone Number</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td colSpan="9" className="text-center">No items available.</td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
    );
};

export default ManagementChain;