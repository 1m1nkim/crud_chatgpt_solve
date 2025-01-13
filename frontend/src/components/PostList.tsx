import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import api from "../api";

interface Post {
    id: number;
    title: string;
    author: string;
    content: string;
}

const PostList: React.FC = () => {
    const [posts, setPosts] = useState<Post[]>([]);
    const [page, setPage] = useState<number>(0);
    const [totalPages, setTotalPages] = useState<number>(1);
    const navigate = useNavigate();

    const fetchPosts = async () => {
        try {
            const response = await api.get("/post", {
                params: { page },
                headers: {
                    Authorization: `Bearer ${localStorage.getItem("accessToken")}`,
                },
            });
            setPosts(response.data.content);
            setTotalPages(response.data.totalPages);
        } catch (error) {
            console.error("게시물 가져오기 실패:", error);
        }
    };

    const handleLogout = () => {
        localStorage.removeItem("accessToken");
        navigate("/");
    };

    const goToCreatePost = () => {
        navigate("/create");
    };

    useEffect(() => {
        fetchPosts();
    }, [page]);

    return (
        <div className="container">
            <h2>게시물 목록</h2>
            <div style={{ display: "flex", justifyContent: "space-between" }}>
                <button onClick={goToCreatePost}>글 작성하기</button>
                <button onClick={handleLogout}>로그아웃</button>
            </div>
            <ul>
                {posts.map((post) => (
                    <li
                        key={post.id}
                        onClick={() => navigate(`/posts/${post.id}`)} // PostDetail로 이동
                        style={{
                            cursor: "pointer",
                            margin: "10px 0",
                            padding: "10px",
                            border: "1px solid #ccc",
                            borderRadius: "4px",
                        }}
                    >
                        <h3>{post.title}</h3>
                        <p>작성자: {post.author}</p>
                    </li>

                ))}
            </ul>
            <div style={{display: "flex", justifyContent: "center", alignItems: "center", gap: "10px"}}>
                <button onClick={() => setPage((prev) => Math.max(prev - 1, 0))} disabled={page === 0}>
                    이전
                </button>
                <span>
          {page + 1} / {totalPages}
        </span>
                <button onClick={() => setPage((prev) => Math.min(prev + 1, totalPages - 1))} disabled={page + 1 === totalPages}>
                    다음
                </button>
            </div>
        </div>
    );
};

export default PostList;
