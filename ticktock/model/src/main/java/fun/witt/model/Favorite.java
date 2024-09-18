package fun.witt.model;

import javax.persistence.*;

@Table(name = "t_favorite")
public class Favorite {
    /**
     * 自增主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 点赞用户id
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 被点赞的视频id
     */
    @Column(name = "video_id")
    private Long videoId;

    /**
     * 获取自增主键
     *
     * @return id - 自增主键
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置自增主键
     *
     * @param id 自增主键
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取点赞用户id
     *
     * @return user_id - 点赞用户id
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * 设置点赞用户id
     *
     * @param userId 点赞用户id
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 获取被点赞的视频id
     *
     * @return video_id - 被点赞的视频id
     */
    public Long getVideoId() {
        return videoId;
    }

    /**
     * 设置被点赞的视频id
     *
     * @param videoId 被点赞的视频id
     */
    public void setVideoId(Long videoId) {
        this.videoId = videoId;
    }
}