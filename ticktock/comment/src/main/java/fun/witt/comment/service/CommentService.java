package fun.witt.comment.service;

import fun.witt.api.vo.CommentListVO;
import fun.witt.api.vo.CommentVO;
import fun.witt.api.vo.ResultVO;

public interface CommentService {

    /**
     * 发表评论
     * @param videoID
     * @param loginUserID
     * @param text
     * @return
     */
    CommentVO publish(long videoID, long loginUserID, String text);

    /**
     * 删除评论
     * @param commentID
     * @param loginUserID
     * @return
     */
    ResultVO delete(long commentID, long loginUserID);

    /**
     * 获取评论列表
     * @param videoID
     * @param loginUserID
     * @return
     */
    CommentListVO list(long videoID, long loginUserID);
}
