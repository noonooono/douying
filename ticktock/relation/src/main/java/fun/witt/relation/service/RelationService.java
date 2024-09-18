package fun.witt.relation.service;

import fun.witt.api.vo.ResultVO;

public interface RelationService {
    /**
     * 关注操作
     * @param actionType
     * @param userID
     * @param loginUserID
     * @return
     */
    ResultVO followAction(String actionType, long userID, long loginUserID);

    /**
     * 获取关注状态
     * @param userID
     * @param loginUserID
     * @return
     */
    boolean followState(long userID, long loginUserID);

    /**
     * 获取关注列表
     * @param userID
     * @param loginUserID
     * @return
     */
    ResultVO followList(long userID, long loginUserID);

    /**
     * 获取粉丝列表
     * @param userID
     * @param loginUserID
     * @return
     */
    ResultVO followerList(long userID, long loginUserID);
}
