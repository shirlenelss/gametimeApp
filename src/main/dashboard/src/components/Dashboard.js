import React, { useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import "../styles/Dashboard.css";

import { handleRequestAction } from "../api/timeRequests";
import { usePendingRequests } from "../hooks/usePendingRequests";
import { useHistoryRequests } from "../hooks/useHistoryRequests";
import PendingRequests from "./PendingRequests";
import RequestHistory from "./RequestHistory";
import CreateRequest from "./CreateRequest";

const Dashboard = () => {
  const location = useLocation();
  const navigate = useNavigate();
  const params = new URLSearchParams(location.search);
  // Read role and userId from params if present, otherwise fallback to sessionStorage
  const role = params.get("role") || sessionStorage.getItem("userRole");
  const loginUserId = params.get("userId") || sessionStorage.getItem("userId");

  const [isParent] = useState(role === "PARENT");
  const [note, setNote] = useState("not used (note)");
  const [customTime, setCustomTime] = useState("");
  const [selectedRequest, setSelectedRequest] = useState(null);
  const [range] = useState("DAY");

  const { pendingRequests, reload: reloadPending } = usePendingRequests();
  const { history, reload: reloadHistory } = useHistoryRequests(range);
  const [isCreateExpanded, setIsCreateExpanded] = useState(true);

  // Wrap handleRequestAction to reload pending after action
  const handleAndReloadRequestAction = async (id, action) => {
    await handleRequestAction(id, action)
    await reloadPending();
  };

  // Logout handler
  const handleLogout = () => {
    sessionStorage.clear();
    navigate("/login");
  };

  return (
    <div>
      {/* Top panel with logout button */}
      <div className="dashboard-top-panel">
        <span className="dashboard-title">Dashboard</span>
        <button className="dashboard-logout-btn" onClick={handleLogout}>
          Logout
        </button>
      </div>

      {!isParent && (
        <div>
            <button className={"dashboard-create-btn"}   onClick={() => setIsCreateExpanded(prev => !prev)}>
                Create
            </button>
        </div>)}

        {isCreateExpanded && !isParent && (
            <div className="content">
                <CreateRequest
                    userId={loginUserId}
                    afterSave={() => {
                        reloadPending().then(r => {
                            setSelectedRequest(null);
                            setIsCreateExpanded(false);
                        });
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