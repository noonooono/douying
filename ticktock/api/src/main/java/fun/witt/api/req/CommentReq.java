package fun.witt.api.req;

import lombok.Data;

@Data
public class CommentReq {
    /**
     * 1-发布评论，2-删除评论
     */
    private String action_type;
    /**
     * 要删除的评论id，在action_type=2的时候使用
     */
    private String comment_id;
    /**
     * 用户填写的评论内容，在action_type=1的时候使用
     */
    private String comment_text;
    /**
     * 用户鉴权token
     */
    private String token;
    /**
     * 视频id
     */
    private String video_id;
}
