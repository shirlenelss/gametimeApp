import React, { useState} from "react";

import { handleRequestAction } from "../api/timeRequests";
import { usePendingRequests } from "../hooks/usePendingRequests";
import { useHistoryRequests } from "../hooks/useHistoryRequests";
import PendingRequests from "./PendingRequests";
import RequestHistory from "./RequestHistory";
import CreateRequest from "./CreateRequest";

const Dashboard = () => {
  const [isParent, setIsParent] = useState(true);
  const [note, setNote] = useState("not used (note)");
  const [customTime, setCustomTime] = useState("");
  const [selectedRequest, setSelectedRequest] = useState(null);
  const [range] = useState("DAY");

  const { pendingRequests, reload: reloadPending } = usePendingRequests();
  const { history, reload: reloadHistory } = useHistoryRequests(range);
  const [isCreateExpanded, setIsCreateExpanded] = useState(false);

  const parent = { userId: 2, name: "mama" };
  const child = { userId: 3, name: "boy" };
  const currentUserID = isParent ? parent.userId : child.userId;

  return (
    <div>
        <label>isParent: <input type="checkbox" name="myCheckbox" onClick={()=>setIsParent(prevState => !prevState)} /></label>
        <div>
            <button onClick={() => setIsCreateExpanded(prev => !prev)}>
                Create
            </button>
        </div>

        {isCreateExpanded && (
            <div className="content">
                <CreateRequest
                    userId={currentUserID}
                    afterSave={() => {
                        setSelectedRequest(null);
                        setIsCreateExpanded(false);
                        reloadPending();
                    }}
                />
            </div>
        )}

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
        {isParent && (
        <div>
            <RequestHistory
                reloadHistory={reloadHistory}
                history={history}
            />
        </div>
        )}
    </div>
  );
};

export default Dashboard;