import React from 'react';
import '../styles/PendingRequests.css';

// Helper to format date to short calendar date time
function formatDateTime(dateString) {
  if (!dateString) return '';
  const date = new Date(dateString);
  return date.toLocaleString(undefined, {
    year: '2-digit',
    month: '2-digit',
    day: '2-digit',
    hour: '2-digit',
    minute: '2-digit',
  });
}

const PendingRequests = ({ reloadPending, pendingRequests, onApprove, onReject }) => (
  <div className="pending-requests-container">
    <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'space-between' }}>
      <h2 style={{ color: '#1976d2' }}>Pending Requests</h2>
      <button className="pending-reload-btn" onClick={reloadPending}>Reload</button>
    </div>
    {pendingRequests.length === 0 ? (
      <p className="pending-empty">No pending requests.</p>
    ) : (
      <ul className="pending-requests-list">
        {pendingRequests.map((request) => (
          <li key={request.id}>
            <span>
              <span className="pending-date">{formatDateTime(request.createdAt)}</span> for <b>{request.requestedMinutes} minutes</b> from <b>{request.childName}</b>
            </span>
            <span>
              <button className="pending-approve-btn" onClick={() => onApprove(request.id, "approve")}>Approve</button>
              <button className="pending-reject-btn" onClick={() => onReject(request.id, "reject")}>Reject</button>
            </span>
          </li>
        ))}
      </ul>
    )}
  </div>
);

export default PendingRequests;