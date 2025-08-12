import React from 'react';

const PendingRequests = ({ reloadPending, pendingRequests, onApprove, onReject }) => (
  <div>
    <h2>Pending Requests</h2><button onClick={reloadPending}>Reload</button>
      {pendingRequests.length === 0 ? (
          <p>No pending requests.</p>
      ) : (
    <ul>
      {pendingRequests.map((request) => (
        <li key={request.id}>
          {request.description}
          <button onClick={() => onApprove(request.id)}>Approve</button>
          <button onClick={() => onReject(request.id)}>Reject</button>
        </li>
      ))}
    </ul>
          )}
  </div>
);

export default PendingRequests;
