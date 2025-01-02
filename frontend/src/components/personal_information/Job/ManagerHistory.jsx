import React from "react";

const ManagerHistory = (props) => {

    return (
        <div className="content-under-navbar bg-white rounded-1">
            <div className="px-5 pt-5">
                <h1 className="text-xl font-bold mb-2">Manager History</h1>
            </div>
            <div className="px-5 pb-5">
                <table className="table table-bordered">
                    <thead>
                    <tr>
                        <th>Job</th>
                        <th>Start Date</th>
                        <th>End date</th>
                        <th>Manager</th>
                        <th>Manager From</th>
                        <th>Manager To</th>
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

export default ManagerHistory;