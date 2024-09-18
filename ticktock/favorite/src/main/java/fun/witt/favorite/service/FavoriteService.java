package fun.witt.favorite.service;

public interface FavoriteService {
    /**
     * 点赞操作
     * @param actionType
     * @param videoID
     * @param userID
     * @return
     */
    boolean likeAction(String actionType, long videoID, long userID);

}
