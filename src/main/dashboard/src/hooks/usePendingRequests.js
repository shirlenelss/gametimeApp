import { useState, useEffect, useCallback } from "react";
import { fetchPendingRequests } from "../api/timeRequests";

export function usePendingRequests() {
    const [pendingRequests, setPendingRequests] = useState([]);

    const loadPending = useCallback(async () => {
        const data = await fetchPendingRequests();
        setPendingRequests(data);
    }, []);

    useEffect(() => {
        loadPending();
    }, [loadPending]);

    return { pendingRequests, reload: loadPending };
}
