// src/App.js
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import LoginForm from "./components/LoginForm";
import Dashboard from "./components/Dashboard";

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<LoginForm />} />
        <Route path="/login" element={<LoginForm />} />
        <Route path="/dashboard" element={<Dashboard />} />
        {/* Add more routes here */}
      </Routes>
    </Router>
  );
}

export default App;