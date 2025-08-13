import { useState, useEffect, useCallback } from "react";
import { fetchHistoryRequests } from "../api/timeRequests";

export function useHistoryRequests(range) {
    const [history, setHistory] = useState([]);

    const loadHistory = useCallback(async () => {
        const data = await fetchHistoryRequests(range);
        setHistory(data);
    }, [range]);

    useEffect(() => {
        loadHistory();
    }, [loadHistory]);

    return { history, reload: loadHistory };
}
