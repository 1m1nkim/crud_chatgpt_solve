import React, { useState } from 'react';
import api from '../api'; // Axios 설정 파일

const Login: React.FC = () => {
    const [username, setUsername] = useState<string>('');
    const [password, setPassword] = useState<string>('');
    const [message, setMessage] = useState<string>('');

    const handleLogin = async (): Promise<void> => {
        try {
            console.log('로그인 시도:', { username, password }); // 디버깅용

            const response = await api.post(`/auth/login`, null, {
                params: { username, password }, // URL 파라미터 전달
            });

            console.log('응답 데이터:', response.data); // 디버깅용
            localStorage.setItem('accessToken', response.data.accessToken);
            setMessage('로그인 성공!');
        } catch (error: any) {
            console.error('로그인 에러:', error); // 에러 디버깅용
            setMessage('로그인 실패: ' + (error.response?.data || '알 수 없는 오류'));
        }
    };


    return (
        <div>
            <h2>로그인</h2>
            <input
                type="text"
                placeholder="Username"
                value={username}
                onChange={(e) => setUsername(e.target.value)}
            />
            <input
                type="password"
                placeholder="Password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
            />
            <button onClick={handleLogin}>로그인</button>
            <p>{message}</p>
        </div>
    );
};

export default Login;
