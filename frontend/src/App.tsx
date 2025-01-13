// src/App.tsx
import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Login from './components/Login';
import PostList from './components/PostList';

const App: React.FC = () => {
    return (
        <Router>
            <div>
                <h1>API 테스트</h1>
                <Routes>
                    <Route path="/" element={<Login />} />
                    <Route path="/posts" element={<PostList />} />
                </Routes>
            </div>
        </Router>
    );
};

export default App;
