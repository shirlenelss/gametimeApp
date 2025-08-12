import React from 'react';
import './RequestHistory.css';

const statusColor = (status) => {
    if (status === 'APPROVED') return { color: '#43a047', fontWeight: 'bold' };
    if (status === 'REJECTED') return { color: '#e53935', fontWeight: 'bold' };
    return { color: '#757575' };
};

const RequestHistory = ({ history, reloadHistory }) => (
    <div className="request-history-container">
        <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'space-between' }}>
            <h2 style={{ color: '#1976d2' }}>Approved recently</h2>
            <button className="history-reload-btn" onClick={reloadHistory}>Reload</button>
        </div>
        {!history || history.length === 0 ? (
            <p className="history-empty">No request history available.</p>
        ) : (
            <ul className="request-history-list">
                {history.map((req) => (
                    <li key={req.id}>
                        <span>
                            <b>{req.childName}</b>:
                            <span style={statusColor(req.status)}> {req.status}</span>
                            <span> ({req.requestedTime} mins)</span>
                            {req.note && <span style={{ color: '#757575' }}> - Note: {req.note}</span>}
                        </span>
                    </li>
                ))}
            </ul>
        )}
    </div>
);

export default RequestHistory;
