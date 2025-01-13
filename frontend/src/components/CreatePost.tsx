import React, { useState } from 'react';
import api from '../api'; // Axios 설정 파일을 import

const CreatePost: React.FC = () => {
    const [title, setTitle] = useState<string>('');
    const [content, setContent] = useState<string>('');
    const [message, setMessage] = useState<string>('');

    const handleCreatePost = async (): Promise<void> => {
        try {
            // response 변수 제거, 필요 없다면 생략 가능
            await api.post(
                '/post',
                { title, content },
                {
                    headers: {
                        Authorization: `Bearer ${localStorage.getItem('accessToken')}`,
                    },
                }
            );
            setMessage('게시물 생성 성공!');
        } catch (error: any) {
            setMessage('게시물 생성 실패: ' + error.response?.data || '알 수 없는 오류');
        }
    };

    return (
        <div>
            <h2>게시물 생성</h2>
            <input
                type="text"
                placeholder="제목"
                value={title}
                onChange={(e) => setTitle(e.target.value)}
            />
            <textarea
                placeholder="내용"
                value={content}
                onChange={(e) => setContent(e.target.value)}
            ></textarea>
            <button onClick={handleCreatePost}>게시물 생성</button>
            <p>{message}</p>
        </div>
    );
};

export default CreatePost;
