import React, { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import api from "../api";

interface Post {
    id: number;
    title: string;
    content: string;
    author: string;
}

interface Comment {
    id: number;
    content: string;
    author: string;
}

const PostDetail: React.FC = () => {
    const { id } = useParams<{ id: string }>(); // URL 파라미터에서 게시글 ID 가져오기
    const [post, setPost] = useState<Post | null>(null);
    const [comments, setComments] = useState<Comment[]>([]);
    const [newComment, setNewComment] = useState<string>("");
    const navigate = useNavigate();

    const fetchPost = async () => {
        try {
            const response = await api.get(`/post/${id}`, {
                headers: {
                    Authorization: `Bearer ${localStorage.getItem("accessToken")}`,
                },
            });
            setPost(response.data);
        } catch (error) {
            console.error("게시글 가져오기 실패:", error);
        }
    };

    const fetchComments = async () => {
        try {
            const response = await api.get(`/comments/${id}`, {
                headers: {
                    Authorization: `Bearer ${localStorage.getItem("accessToken")}`,
                },
            });
            setComments(response.data.content); // 댓글 데이터 설정
        } catch (error) {
            console.error("댓글 가져오기 실패:", error);
        }
    };

    const handleAddComment = async () => {
        if (!newComment.trim()) return;

        try {
            await api.post(
                `/comments/${id}`,
                { content: newComment },
                {
                    headers: {
                        Authorization: `Bearer ${localStorage.getItem("accessToken")}`,
                    },
                }
            );
            setNewComment(""); // 댓글 입력란 초기화
            fetchComments(); // 새 댓글 가져오기
        } catch (error) {
            console.error("댓글 추가 실패:", error);
        }
    };

    useEffect(() => {
        fetchPost();
        fetchComments();
    }, [id]);

    return (
        <div className="container">
            {post ? (
                <>
                    <h2>{post.title}</h2>
                    <p>작성자: {post.author}</p>
                    <p>{post.content}</p>
                    <button onClick={() => navigate("/posts")} style={{ marginBottom: "20px" }}>
                        뒤로가기
                    </button>
                    <h3>댓글</h3>
                    <ul>
                        {comments.map((comment) => (
                            <li key={comment.id} style={{ margin: "10px 0", padding: "10px", border: "1px solid #ccc" }}>
                                <p>{comment.content}</p>
                                <p style={{ fontSize: "12px", color: "#666" }}>작성자: {comment.author}</p>
                            </li>
                        ))}
                    </ul>
                    <div>
                        <textarea
                            placeholder="댓글을 입력하세요..."
                            value={newComment}
                            onChange={(e) => setNewComment(e.target.value)}
                            style={{ width: "100%", marginTop: "10px", padding: "10px" }}
                        />
                        <button
                            onClick={handleAddComment}
                            style={{
                                marginTop: "10px",
                                padding: "10px 20px",
                                backgroundColor: "#4CAF50",
                                color: "white",
                                border: "none",
                                borderRadius: "4px",
                            }}
                        >
                            댓글 추가
                        </button>
                    </div>
                </>
            ) : (
                <p>게시글을 불러오는 중입니다...</p>
            )}
        </div>
    );
};

export default PostDetail;
