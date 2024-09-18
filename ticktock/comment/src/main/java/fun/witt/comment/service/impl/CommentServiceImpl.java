package fun.witt.comment.service.impl;

import fun.witt.api.feign.UserFeignClient;
import fun.witt.api.utils.ConvertUtil;
import fun.witt.api.vo.*;
import fun.witt.comment.service.CommentService;
import fun.witt.mapper.CommentMapper;
import fun.witt.model.Comment;
import fun.witt.model.Video;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private UserFeignClient userFeignClient;

    /**
     * 发表评论
     * @param videoID
     * @param loginUserID
     * @param text
     * @return
     */
    @Override
    public CommentVO publish(long videoID, long loginUserID, String text) {
        // todo 同步视频的评论数量 发布+1
        //1. 创建评论对象
        //2. 保存评论到数据库
        Comment comment = new Comment();
        comment.setUserId(loginUserID);
        comment.setVideoId(videoID);
        comment.setCommentText(text);
        comment.setCreateTime(new Date());
        commentMapper.insert(comment);
        //3.进行封装返回
        //3.1对评论对象转为扩展对象（这里插入库后就不需要视频id了）
        CommentExt commentExt = ConvertUtil.convertComment(comment);
        //查询发布评论者的信息
        UserExt userExt = userFeignClient.getUserInfo(loginUserID, 0L);
        commentExt.setUser(userExt);
        CommentVO vo = new CommentVO();
        vo.setComment(commentExt);
        return vo;
    }

    /**
     * 删除评论
     * @param commentID
     * @param loginUserID
     * @return
     */
    @Override
    public ResultVO delete(long commentID, long loginUserID) {
        // todo 同步视频的评论数量 删除-1
        //1.创建查询条件
        Example example = new Example(Comment.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("id", commentID);
        criteria.andEqualTo("userId", loginUserID);
        if (commentMapper.deleteByExample(example) > 0) {
            return ResultVO.ok();
        }
        return ResultVO.fail("fail");
    }

    /**
     * 获取评论列表
     * @param videoID
     * @param loginUserID
     * @return
     */
    @Override
    public CommentListVO list(long videoID, long loginUserID) {
        //1.根据视频id查询评论列表
        Example example = new Example(Comment.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("videoId", videoID);
        List<Comment> commentList = commentMapper.selectByExample(example);

        CommentListVO vo = new CommentListVO();
        if (commentList.isEmpty()) {
            return vo;
        }
        //2.将评论列表转换成VO列表
        List<CommentExt> commentExtList = commentList.stream().map(comment -> {
            CommentExt commentExt = ConvertUtil.convertComment(comment);
            commentExt.setUser(userFeignClient.getUserInfo(comment.getUserId(), loginUserID));
            return commentExt;
        }).toList();
        vo.setCommentList(commentExtList);
        return vo;
    }
}
