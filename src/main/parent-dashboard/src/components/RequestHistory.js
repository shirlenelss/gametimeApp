import React from 'react';

const RequestHistory = ({ history, reloadHistory }) => (
    <div>
        <h2>Request History</h2>
        <button onClick={reloadHistory}>Reload</button>
        {!history || history.length === 0 ? (
            <p>No request history available.</p>
        ) : (
            <ul>
                {history.map((req) => (
                    <li key={req.id}>
                        {req.childName}: {req.status} ({req.requestedTime} mins)
                        {req.note && <> - Note: {req.note}</>}
                    </li>
                ))}
            </ul>
        )}
    </div>
);

export default RequestHistory;
