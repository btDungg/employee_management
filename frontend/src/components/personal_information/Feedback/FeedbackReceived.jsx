import React from "react";

const FeedbackReceived = (props) => {

    return (
        <div className="content-under-navbar bg-white rounded-1">
            <div className="px-5 pt-5">
                <h1 className="text-xl font-bold mb-2">Feedback Received</h1>
            </div>
            <div className="px-5 pb-5">
                <table className="table table-bordered">
                    <thead className="thead-light">
                        <tr>
                            <th>Date</th>
                            <th>About</th>
                            <th>Question</th>
                            <th>Comment</th>
                            <th>Relates To</th>
                            <th>Asked By</th>
                            <th>Type</th>
                            <th>Confidential: Not Shared with Feedback Recipient</th>
                            <th>Show Feedback Provider's Name?</th>
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

export default FeedbackReceived;