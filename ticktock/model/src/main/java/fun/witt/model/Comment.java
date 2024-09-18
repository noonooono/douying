package fun.witt.model;

import javax.persistence.*;
import java.util.Date;

@Table(name = "t_comment")
public class Comment {
    /**
     * 评论id，自增主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 评论发布用户id
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 评论视频id
     */
    @Column(name = "video_id")
    private Long videoId;

    /**
     * 评论内容
     */
    @Column(name = "comment_text")
    private String commentText;

    /**
     * 评论发布时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 获取评论id，自增主键
     *
     * @return id - 评论id，自增主键
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置评论id，自增主键
     *
     * @param id 评论id，自增主键
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取评论发布用户id
     *
     * @return user_id - 评论发布用户id
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * 设置评论发布用户id
     *
     * @param userId 评论发布用户id
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 获取评论视频id
     *
     * @return video_id - 评论视频id
     */
    public Long getVideoId() {
        return videoId;
    }

    /**
     * 设置评论视频id
     *
     * @param videoId 评论视频id
     */
    public void setVideoId(Long videoId) {
        this.videoId = videoId;
    }

    /**
     * 获取评论内容
     *
     * @return comment_text - 评论内容
     */
    public String getCommentText() {
        return commentText;
    }

    /**
     * 设置评论内容
     *
     * @param commentText 评论内容
     */
    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    /**
     * 获取评论发布时间
     *
     * @return create_time - 评论发布时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置评论发布时间
     *
     * @param createTime 评论发布时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}