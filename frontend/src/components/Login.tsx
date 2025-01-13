import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom'; // React Router의 useNavigate
import api from '../api'; // Axios 설정 파일

const Login: React.FC = () => {
    const [username, setUsername] = useState<string>('');
    const [password, setPassword] = useState<string>('');
    const [message, setMessage] = useState<string>('');
    const navigate = useNavigate(); // 페이지 이동을 위한 useNavigate

    const handleLogin = async (): Promise<void> => {
        try {
            console.log('로그인 시도:', { username, password }); // 디버깅용 로그

            // API 요청
            const response = await api.post(`/auth/login`, null, {
                params: { username, password },
            });

            console.log('응답 데이터:', response.data); // 응답 데이터 확인

            // 토큰 저장
            localStorage.setItem('accessToken', response.data.accessToken);

            setMessage('로그인 성공!');

            // PostList 화면으로 이동
            navigate('/posts');
        } catch (error: any) {
            console.error('로그인 에러:', error); // 에러 디버깅용 로그
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
