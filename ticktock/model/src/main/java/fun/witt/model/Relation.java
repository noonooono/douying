package fun.witt.model;

import javax.persistence.*;

@Table(name = "t_relation")
public class Relation {
    /**
     * 自增主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 用户id
     */
    @Column(name = "follow_id")
    private Long followId;

    /**
     * 关注的用户（关注者）
     */
    @Column(name = "follower_id")
    private Long followerId;

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
     * 获取用户id
     *
     * @return follow_id - 用户id
     */
    public Long getFollowId() {
        return followId;
    }

    /**
     * 设置用户id
     *
     * @param followId 用户id
     */
    public void setFollowId(Long followId) {
        this.followId = followId;
    }

    /**
     * 获取关注的用户
     *
     * @return follower_id - 关注的用户
     */
    public Long getFollowerId() {
        return followerId;
    }

    /**
     * 设置关注的用户
     *
     * @param followerId 关注的用户
     */
    public void setFollowerId(Long followerId) {
        this.followerId = followerId;
    }
}