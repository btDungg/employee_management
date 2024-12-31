import React from "react";

const FeedbackGiven = (props) => {

    return (
        <div className="content-under-navbar bg-white rounded-1">
            <div className="px-5 pt-5">
                <h1 className="text-xl font-bold mb-2">Feedback Given</h1>
            </div>
            <div className="px-5 pb-5">
                <table className="table table-bordered">
                    <thead>
                        <tr>
                            <th>Date</th>
                            <th>Photo</th>
                            <th>From</th>
                            <th>Feedback</th>
                            <th>Type</th>
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

export default FeedbackGiven;