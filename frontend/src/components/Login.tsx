import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import api from "../api";

const Login: React.FC = () => {
    const [username, setUsername] = useState<string>("");
    const [password, setPassword] = useState<string>("");
    const [message, setMessage] = useState<string>("");
    const navigate = useNavigate();

    const handleLogin = async (): Promise<void> => {
        try {
            const response = await api.post(`/auth/login`, null, {
                params: { username, password },
            });
            localStorage.setItem("accessToken", response.data.accessToken);
            setMessage("로그인 성공!");
            navigate("/posts");
        } catch (error: any) {
            setMessage("로그인 실패: " + (error.response?.data || "알 수 없는 오류"));
        }
    };

    return (
        <div className="container">
            <h2>로그인</h2>
            <div>
                <input
                    type="text"
                    placeholder="Username"
                    value={username}
                    onChange={(e) => setUsername(e.target.value)}
                    style={{
                        margin: "5px",
                        padding: "10px",
                        width: "80%",
                    }}
                />
            </div>
            <div>
                <input
                    type="password"
                    placeholder="Password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    style={{
                        margin: "5px",
                        padding: "10px",
                        width: "80%",
                    }}
                />
            </div>
            <button
                onClick={handleLogin}
                style={{
                    margin: "10px",
                    padding: "10px 20px",
                    backgroundColor: "#4CAF50",
                    color: "white",
                    border: "none",
                    borderRadius: "4px",
                }}
            >
                로그인
            </button>
            <p>{message}</p>
        </div>
    );
};

export default Login;
