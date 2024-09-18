package fun.witt.model;

import javax.persistence.*;

@Table(name = "t_user")
public class User {
    /**
     * 用户id，自增主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 用户名
     */
    @Column(name = "user_name")
    private String userName;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 该用户关注其他用户个数
     */
    @Column(name = "follow_count")
    private Long followCount;

    /**
     * 该用户粉丝个数
     */
    @Column(name = "follower_count")
    private Long followerCount;

    /**
     * 该用户被喜欢的视频数量
     */
    @Column(name = "total_favorited")
    private Long totalFavorited;

    /**
     * 该用户喜欢的视频数量
     */
    @Column(name = "favorite_count")
    private Long favoriteCount;

    /**
     * 签名
     */
    private String signature;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 主页背景
     */
    @Column(name = "background_image")
    private String backgroundImage;

    /**
     * 获取用户id，自增主键
     *
     * @return id - 用户id，自增主键
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置用户id，自增主键
     *
     * @param id 用户id，自增主键
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取用户名
     *
     * @return user_name - 用户名
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 设置用户名
     *
     * @param userName 用户名
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * 获取用户密码
     *
     * @return password - 用户密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置用户密码
     *
     * @param password 用户密码
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取该用户关注其他用户个数
     *
     * @return follow_count - 该用户关注其他用户个数
     */
    public Long getFollowCount() {
        return followCount;
    }

    /**
     * 设置该用户关注其他用户个数
     *
     * @param followCount 该用户关注其他用户个数
     */
    public void setFollowCount(Long followCount) {
        this.followCount = followCount;
    }

    /**
     * 获取该用户粉丝个数
     *
     * @return follower_count - 该用户粉丝个数
     */
    public Long getFollowerCount() {
        return followerCount;
    }

    /**
     * 设置该用户粉丝个数
     *
     * @param followerCount 该用户粉丝个数
     */
    public void setFollowerCount(Long followerCount) {
        this.followerCount = followerCount;
    }

    /**
     * 获取该用户被喜欢的视频数量
     *
     * @return total_favorited - 该用户被喜欢的视频数量
     */
    public Long getTotalFavorited() {
        return totalFavorited;
    }

    /**
     * 设置该用户被喜欢的视频数量
     *
     * @param totalFavorited 该用户被喜欢的视频数量
     */
    public void setTotalFavorited(Long totalFavorited) {
        this.totalFavorited = totalFavorited;
    }

    /**
     * 获取该用户喜欢的视频数量
     *
     * @return favorite_count - 该用户喜欢的视频数量
     */
    public Long getFavoriteCount() {
        return favoriteCount;
    }

    /**
     * 设置该用户喜欢的视频数量
     *
     * @param favoriteCount 该用户喜欢的视频数量
     */
    public void setFavoriteCount(Long favoriteCount) {
        this.favoriteCount = favoriteCount;
    }

    /**
     * 获取签名
     *
     * @return signature - 签名
     */
    public String getSignature() {
        return signature;
    }

    /**
     * 设置签名
     *
     * @param signature 签名
     */
    public void setSignature(String signature) {
        this.signature = signature;
    }

    /**
     * 获取用户头像
     *
     * @return avatar - 用户头像
     */
    public String getAvatar() {
        return avatar;
    }

    /**
     * 设置用户头像
     *
     * @param avatar 用户头像
     */
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    /**
     * 获取主页背景
     *
     * @return background_image - 主页背景
     */
    public String getBackgroundImage() {
        return backgroundImage;
    }

    /**
     * 设置主页背景
     *
     * @param backgroundImage 主页背景
     */
    public void setBackgroundImage(String backgroundImage) {
        this.backgroundImage = backgroundImage;
    }
}