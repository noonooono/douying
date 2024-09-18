package fun.witt.model;

import javax.persistence.*;
import java.util.Date;

@Table(name = "t_video")
public class Video {
    /**
     * 自增主键，视频唯一id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 视频作者id
     */
    @Column(name = "author_id")
    private Long authorId;

    /**
     * 播放url
     */
    @Column(name = "play_url")
    private String playUrl;

    /**
     * 封面url
     */
    @Column(name = "cover_url")
    private String coverUrl;

    /**
     * 视频的点赞数量
     */
    @Column(name = "favorite_count")
    private Long favoriteCount;

    /**
     * 视频的评论数量
     */
    @Column(name = "comment_count")
    private Long commentCount;

    /**
     * 发布时间戳
     */
    @Column(name = "publish_time")
    private Date publishTime;

    /**
     * 视频名称
     */
    private String title;

    /**
     * 获取自增主键，视频唯一id
     *
     * @return id - 自增主键，视频唯一id
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置自增主键，视频唯一id
     *
     * @param id 自增主键，视频唯一id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取视频作者id
     *
     * @return author_id - 视频作者id
     */
    public Long getAuthorId() {
        return authorId;
    }

    /**
     * 设置视频作者id
     *
     * @param authorId 视频作者id
     */
    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    /**
     * 获取播放url
     *
     * @return play_url - 播放url
     */
    public String getPlayUrl() {
        return playUrl;
    }

    /**
     * 设置播放url
     *
     * @param playUrl 播放url
     */
    public void setPlayUrl(String playUrl) {
        this.playUrl = playUrl;
    }

    /**
     * 获取封面url
     *
     * @return cover_url - 封面url
     */
    public String getCoverUrl() {
        return coverUrl;
    }

    /**
     * 设置封面url
     *
     * @param coverUrl 封面url
     */
    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    /**
     * 获取视频的点赞数量
     *
     * @return favorite_count - 视频的点赞数量
     */
    public Long getFavoriteCount() {
        return favoriteCount;
    }

    /**
     * 设置视频的点赞数量
     *
     * @param favoriteCount 视频的点赞数量
     */
    public void setFavoriteCount(Long favoriteCount) {
        this.favoriteCount = favoriteCount;
    }

    /**
     * 获取视频的评论数量
     *
     * @return comment_count - 视频的评论数量
     */
    public Long getCommentCount() {
        return commentCount;
    }

    /**
     * 设置视频的评论数量
     *
     * @param commentCount 视频的评论数量
     */
    public void setCommentCount(Long commentCount) {
        this.commentCount = commentCount;
    }

    /**
     * 获取发布时间戳
     *
     * @return publish_time - 发布时间戳
     */
    public Date getPublishTime() {
        return publishTime;
    }

    /**
     * 设置发布时间戳
     *
     * @param publishTime 发布时间戳
     */
    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    /**
     * 获取视频名称
     *
     * @return title - 视频名称
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置视频名称
     *
     * @param title 视频名称
     */
    public void setTitle(String title) {
        this.title = title;
    }
}