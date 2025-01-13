import React, { useState, useEffect } from 'react';
import api from '../api'; // Axios 설정 파일을 import

interface Post {
    id: number;
    title: string;
    content: string;
    author: string;
}

const PostList: React.FC = () => {
    const [posts, setPosts] = useState<Post[]>([]);

    useEffect(() => {
        const fetchPosts = async (): Promise<void> => {
            try {
                const response = await api.get('/post', {
                    headers: {
                        Authorization: `Bearer ${localStorage.getItem('accessToken')}`,
                    },
                });
                setPosts(response.data.content);
            } catch (error: any) {
                console.error('게시물 가져오기 실패:', error);
            }
        };

        fetchPosts();
    }, []);

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
