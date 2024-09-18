package fun.witt.api.utils;

import cn.hutool.core.date.DateUtil;
import fun.witt.api.vo.CommentExt;
import fun.witt.api.vo.CommentVO;
import fun.witt.api.vo.UserExt;
import fun.witt.api.vo.VideoExt;
import fun.witt.model.Comment;
import fun.witt.model.User;
import fun.witt.model.Video;

public class ConvertUtil {

    public static UserExt convertUser(User user) {
        UserExt userExt = new UserExt();
        userExt.setId(user.getId());
        userExt.setName(user.getUserName());
        userExt.setFollowCount(user.getFollowCount());
        userExt.setFollowerCount(user.getFollowerCount());
        return userExt;
    }

    public static VideoExt convertVideo(Video video) {
        VideoExt videoExt = new VideoExt();
        videoExt.setId(video.getId());
        videoExt.setTitle(video.getTitle());
        videoExt.setCoverUrl(video.getCoverUrl());
        videoExt.setPlayUrl(video.getPlayUrl());
        videoExt.setFavoriteCount(video.getFavoriteCount());
        videoExt.setCommentCount(video.getCommentCount());
        return videoExt;
    }

    public static CommentExt convertComment(Comment comment) {
        CommentExt commentExt = new CommentExt();
        commentExt.setId(comment.getId());
        commentExt.setContent(comment.getCommentText());
        commentExt.setCreateDate(DateUtil.formatDateTime(comment.getCreateTime()));
        return commentExt;
    }
}
