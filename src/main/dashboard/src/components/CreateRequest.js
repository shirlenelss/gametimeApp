import React, { useState } from 'react';
import { createRequest as apiCreateRequest } from '../api/timeRequests';
import './CreateRequest.css';

const MIN_INTERVAL = 15;

const CreateRequest = ({ userId, afterSave, onDelete }) => {
    const [requestedTime, setRequestedTime] = useState(MIN_INTERVAL);
    const [note, setNote] = useState('');

    const handleIncrease = () => setRequestedTime(requestedTime + MIN_INTERVAL);
    const handleDecrease = () => setRequestedTime(Math.max(MIN_INTERVAL, requestedTime - MIN_INTERVAL));

    const handleSave = async () => {
        var newRequest = await apiCreateRequest(userId, requestedTime);
        setRequestedTime(MIN_INTERVAL);
        setNote('');
        if (afterSave) afterSave(newRequest);
    };

    const handleDelete = () => {
        setRequestedTime(MIN_INTERVAL);
        setNote('');
        if (onDelete) onDelete();
    };

    return (
        <div className="create-request-container">
            <h2 style={{ textAlign: 'center', color: '#1976d2', marginBottom: 20 }}>Create Time Request</h2>
            <div className="create-request-controls" style={{ justifyContent: 'center', marginBottom: 20 }}>
                <button
                    className="circle-btn"
                    onClick={handleDecrease}
                    disabled={requestedTime === MIN_INTERVAL}
                    aria-label="Decrease time"
                >-</button>
                <span style={{ margin: '0 20px', fontWeight: 'bold', fontSize: 18 }}>{requestedTime} min</span>
                <button
                    className="circle-btn"
                    onClick={handleIncrease}
                    aria-label="Increase time"
                >+</button>
            </div>
            <div>
                <input
                    type="text"
                    placeholder="Optional note"
                    value={note}
                    onChange={e => setNote(e.target.value)}
                    className="create-request-note"
                    style={{ marginBottom: 20 }}
                />
            </div>
            <div className="create-request-actions" style={{ display: 'flex', justifyContent: 'center' }}>
                <button className="primary-btn" onClick={handleSave}>Save</button>
                <button className="secondary-btn" onClick={handleDelete} style={{ marginLeft: '10px' }}>Delete</button>
            </div>
        </div>
    );
};

export default CreateRequest;
