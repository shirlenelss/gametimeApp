import axios from "axios";

export async function fetchPendingRequests() {
  const res = await axios.get("/api/requests/pending");
  return res.data;
}

export async function fetchHistoryRequests(range) {
  const res = await axios.get(`/api/history?range=${range}`);
  return res.data;
}

export async function handleRequestAction(id, action, note, customTime) {
  await axios.post(`/api/requests/${id}/${action}`, { note, customTime });
}
