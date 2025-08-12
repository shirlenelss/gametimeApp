import React, { useState} from "react";

import { handleRequestAction } from "../api/timeRequests";
import { usePendingRequests } from "../hooks/usePendingRequests";
import { useHistoryRequests } from "../hooks/useHistoryRequests";
import PendingRequests from "./PendingRequests";
import RequestHistory from "./RequestHistory";

const Dashboard = () => {
  const [note, setNote] = useState("");
  const [customTime, setCustomTime] = useState("");
  const [selectedRequest, setSelectedRequest] = useState(null);
  const [range] = useState("DAY");

  const { pendingRequests, reload: reloadPending } = usePendingRequests();
  const { history, reload: reloadHistory } = useHistoryRequests(range);

  return (
    <div>
        <div>
            <PendingRequests
                reloadPending={reloadPending}
                pendingRequests={pendingRequests}
                onApprove={handleRequestAction}
                onReject={handleRequestAction}
            />
        </div>
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
        <div>
            <RequestHistory
                reloadHistory={reloadHistory}
                history={history}
            />
        </div>
    </div>
  );
};

export default Dashboard;
