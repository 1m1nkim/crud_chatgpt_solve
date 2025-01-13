import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import api from '../api';

const PostList: React.FC = () => {
    const [posts, setPosts] = useState<any[]>([]);
    const navigate = useNavigate();

    useEffect(() => {
        const fetchPosts = async () => {
            const token = localStorage.getItem('accessToken');
            if (!token) {
                alert('로그인이 필요합니다!');
                navigate('/'); // 로그인 페이지로 이동
                return;
            }

            try {
                const response = await api.get('/post', {
                    headers: {
                        Authorization: `Bearer ${token}`,
                    },
                });
                setPosts(response.data.content);
            } catch (error) {
                console.error('게시물 가져오기 실패:', error);
                alert('인증에 실패했습니다. 다시 로그인하세요.');
                navigate('/');
            }
        };

        fetchPosts();
    }, [navigate]);

    return (
        <div>
            <h2>게시물 목록</h2>
            <ul>
                {posts.map((post) => (
                    <li key={post.id}>
                        <h3>{post.title}</h3>
                        <p>{post.content}</p>
                        <p>작성자: {post.author}</p>
                    </li>
                ))}
            </ul>
        </div>
    );
};

export default PostList;
