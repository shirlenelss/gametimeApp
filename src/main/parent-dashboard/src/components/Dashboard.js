import React, { useState} from "react";

import { handleRequestAction } from "../api/timeRequests";
import { usePendingRequests } from "../hooks/usePendingRequests";
import { useHistoryRequests } from "../hooks/useHistoryRequests";

const Dashboard = () => {
  const [note, setNote] = useState("");
  const [customTime, setCustomTime] = useState("");
  const [selectedRequest, setSelectedRequest] = useState(null);
  const [range] = useState("DAY");

  const { pendingRequests, reload: reloadPending } = usePendingRequests();
  const { history, reload: reloadHistory } = useHistoryRequests(range);

  return (
    <div>
      <h2>Pending Time Requests</h2> <button onClick={reloadPending}>Reload</button>
      <ul>
        {pendingRequests.map((req) => (
          <li key={req.id}>
            {req.childName} requested {req.requestedTime} mins
            <button onClick={() => handleRequestAction(req.id, "approve")}>
              Approve
            </button>
            <button onClick={() => handleRequestAction(req.id, "deny")}>Deny</button>
            <button onClick={() => setSelectedRequest(req)}>Custom</button>
          </li>
        ))}
      </ul>
      {selectedRequest && (
        <div>
          <h3>Custom Response for {selectedRequest.childName}</h3>
          <input
            type="number"
            placeholder="Custom time (mins)"
            value={customTime}
            onChange={(e) => setCustomTime(e.target.value)}
          />
          <input
            type="text"
            placeholder="Optional note"
            value={note}
            onChange={(e) => setNote(e.target.value)}
          />
          <button onClick={() => handleRequestAction(selectedRequest.id, "custom")}>
            Send
          </button>
          <button onClick={() => setSelectedRequest(null)}>Cancel</button>
        </div>
      )}
      <h2>Request History</h2><button onClick={reloadHistory}>Reload</button>
      <ul>
        {history.map((req) => (
          <li key={req.id}>
            {req.childName}: {req.status} ({req.requestedTime} mins)
            {req.note && <> - Note: {req.note}</>}
          </li>
        ))}
      </ul>
    </div>
  );
};

export default Dashboard;
