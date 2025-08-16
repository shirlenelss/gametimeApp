import React, { useState} from "react";
import { useLocation } from "react-router-dom";

import { handleRequestAction } from "../api/timeRequests";
import { usePendingRequests } from "../hooks/usePendingRequests";
import { useHistoryRequests } from "../hooks/useHistoryRequests";
import PendingRequests from "./PendingRequests";
import RequestHistory from "./RequestHistory";
import CreateRequest from "./CreateRequest";

const Dashboard = () => {
  const location = useLocation();
  const params = new URLSearchParams(location.search);
  const role = params.get("role");

  const [isParent, setIsParent] = useState(role === "parent");
  const [note, setNote] = useState("not used (note)");
  const [customTime, setCustomTime] = useState("");
  const [selectedRequest, setSelectedRequest] = useState(null);
  const [range] = useState("DAY");

  const { pendingRequests, reload: reloadPending } = usePendingRequests();
  const { history, reload: reloadHistory } = useHistoryRequests(range);
  const [isCreateExpanded, setIsCreateExpanded] = useState(true);

  const parent = { userId: 2, name: "mama" };
  const child = { userId: 3, name: "boy" };

  // Wrap handleRequestAction to reload pending after action
  const handleAndReloadRequestAction = async (id, action) => {
    await handleRequestAction(id, action)
    await reloadPending();
  };

  const getCurrentUserID = () => {
      return isParent ? parent.userId : child.userId
  }

  return (
    <div>
        <label>isParent: <input type="checkbox" name="myCheckbox" onClick={()=>setIsParent(prevState => !prevState)} /></label>
        <div>
            <button onClick={() => setIsCreateExpanded(prev => !prev)}>
                Create
            </button>
        </div>

        {isCreateExpanded && !isParent && (
            <div className="content">
                <CreateRequest
                    userId={getCurrentUserID()}
                    afterSave={() => {
                        reloadPending();
                        setSelectedRequest(null);
                        setIsCreateExpanded(false);
                    }}
                    onCancel={() => {
                        setSelectedRequest(null);
                        setIsCreateExpanded(false);
                    }
                }
                />
            </div>
        )}

        <div>
            <PendingRequests
                reloadPending={reloadPending}
                pendingRequests={pendingRequests}
                onApprove={handleAndReloadRequestAction}
                onReject={handleAndReloadRequestAction}
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
          <button onClick={async () => {
            await handleAndReloadRequestAction(selectedRequest.id, "custom");
            setSelectedRequest(null);
          }}>
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