import axios from 'axios';

// 환경변수에서 API URL 가져오기
const API_URL = import.meta.env.VITE_API_URL || 'http://localhost:8080/api';

const api = axios.create({
    baseURL: API_URL,
    withCredentials: true, // 쿠키 기반 인증 활성화
});

export default api;
