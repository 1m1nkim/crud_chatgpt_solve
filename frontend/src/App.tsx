import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Login from "./components/Login";
import PostList from "./components/PostList";
import CreatePost from "./components/CreatePost";
import PostDetail from "./components/PostDetail.tsx";
import "./App.css";

const App: React.FC = () => {
    return (
        <Router>
            <div>
                <Routes>
                    <Route path="/" element={<Login />} />
                    <Route path="/posts" element={<PostList />} />
                    <Route path="/create" element={<CreatePost />} />
                    <Route path="/posts/:id" element={<PostDetail />} />
                </Routes>
            </div>
        </Router>
    );
};

export default App;
