package fun.witt.relation.service.impl;

import fun.witt.api.feign.UserFeignClient;
import fun.witt.api.vo.ResultVO;
import fun.witt.api.vo.UserExt;
import fun.witt.api.vo.UserListVO;
import fun.witt.utils.constant.Constant;
import fun.witt.mapper.RelationMapper;
import fun.witt.model.Relation;
import fun.witt.relation.service.RelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RelationServiceImpl implements RelationService {

    @Autowired
    private RelationMapper relationMapper;

    @Autowired
    private UserFeignClient userFeignClient;

    /**
     * 关注操作
     * @param actionType
     * @param userID
     * @param loginUserID
     * @return
     */
    @Override
    public ResultVO followAction(String actionType, long userID, long loginUserID) {
        // todo 同步粉丝数量
        //判断关注类型
        switch (actionType) {
            //关注操作
            case Constant.RELATION_FOLLOW -> {
                Relation relation = new Relation();
                relation.setFollowId(userID);
                relation.setFollowerId(loginUserID);
                if (relationMapper.insert(relation) > 0) {
                    return ResultVO.ok();
                }
            }
            //取消关注操作
            case Constant.RELATION_UNFOLLOW -> {
                Example example = new Example(Relation.class);
                Example.Criteria criteria = example.createCriteria();
                criteria.andEqualTo("followId", userID);
                criteria.andEqualTo("followerId", loginUserID);
                if (relationMapper.deleteByExample(example) > 0) {
                    return ResultVO.ok();
                }
            }
        }
        return ResultVO.fail("");
    }

    /**
     * 获取关注状态
     * @param userID
     * @param loginUserID
     * @return
     */
    @Override
    public boolean followState(long userID, long loginUserID) {
        Example example = new Example(Relation.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("followId", userID);
        criteria.andEqualTo("followerId", loginUserID);
        Relation relation = relationMapper.selectOneByExample(example);
        return relation != null;
    }

    /**
     * 获取关注列表
     * @param userID
     * @param loginUserID
     * @return
     */
    @Override
    public ResultVO followList(long userID, long loginUserID) {
        Example example = new Example(Relation.class);
        Example.Criteria criteria = example.createCriteria();
        //将用户id关注的关注列表查询出来 ‘粉丝’是访问用户id本身
        criteria.andEqualTo("followerId", userID);
        //获取用户id关注的用户列表（即用户列表A）
        List<Relation> relationList = relationMapper.selectByExample(example);

        UserListVO vo = new UserListVO();
        //获取用户列表A的id列表
        List<Long> followUserIDList = relationList.stream().map(Relation::getFollowId).toList();
        if (followUserIDList.isEmpty()) {
            return vo;
        }
        //将每个对象映射为键值对 键为用户列表A的id，值则是该id是否被登陆者关注
        Map<Long, Boolean> followStateDict = relationList.stream()
                .collect(Collectors.toMap(Relation::getFollowId,
                        relation -> userID == loginUserID || relation.getFollowId() == loginUserID));
        //根据用户列表A的id获取对应的用户信息
        List<UserExt> userExtList = userFeignClient.batchUserInfo(followUserIDList, 0);
        //设置关注状态
        userExtList = userExtList.stream()
                .peek(userExt -> userExt.setFollow(followStateDict.getOrDefault(userExt.getId(), false)))
                .toList();
        vo.setUserList(userExtList);
        return vo;
    }

    /**
     * 获取粉丝列表
     * @param userID
     * @param loginUserID
     * @return
     */
    @Override
    public ResultVO followerList(long userID, long loginUserID) {
        Example example = new Example(Relation.class);
        Example.Criteria criteria = example.createCriteria();
        //将粉丝列表查询出来 ‘被关注的’是访问用户id本身
        criteria.andEqualTo("followId", userID);
        List<Relation> relationList = relationMapper.selectByExample(example);
        //获取关注了用户id的用户列表（即用户列表B）
        UserListVO vo = new UserListVO();
        List<Long> followerUserIDList = relationList.stream().map(Relation::getFollowerId).toList();
        if (followerUserIDList.isEmpty()) {
            return vo;
        }
        //将每个对象映射为键值对 键为用户列表B的id，值则是该id是否被登陆者关注
        Map<Long, Boolean> followerStateDict = relationList.stream()
                .collect(Collectors.toMap(Relation::getFollowerId
                        , relation -> relation.getFollowerId() == loginUserID));
        //根据用户列表B的id获取对应的用户信息
        List<UserExt> userExtList = userFeignClient.batchUserInfo(followerUserIDList, 0);
        //设置关注状态
        userExtList = userExtList.stream()
                .peek(userExt -> userExt.setFollow(followerStateDict.getOrDefault(userExt.getId(), false)))
                .toList();
        vo.setUserList(userExtList);
        return null;
    }

}