package fun.witt.favorite.service.impl;

import fun.witt.utils.constant.Constant;
import fun.witt.favorite.service.FavoriteService;
import fun.witt.mapper.FavoriteMapper;
import fun.witt.model.Favorite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

@Service
public class FavoriteServiceImpl implements FavoriteService {

    @Autowired
    private FavoriteMapper favoriteMapper;
    /**
     * 点赞操作
     * @param actionType
     * @param videoID
     * @param userID
     * @return
     */
    @Override
    public boolean likeAction(String actionType, long videoID, long userID) {
        // todo 点赞类型为1时，点赞数量+1，点赞类型为2时，点赞数量-1
        //  （点赞数量有两个：登陆者的视频点赞数量以及视频的总点赞数量）
        //1.根据点赞类型进行对应操作
        switch (actionType) {
            case Constant.FAVORITE_LIKE -> {
                //2.点赞操作，数据库中插入数据
                Favorite favorite = new Favorite();
                favorite.setUserId(userID);
                favorite.setVideoId(videoID);
                return favoriteMapper.insert(favorite) > 0;
            }
            case Constant.FAVORITE_UNLIKE -> {
                //3.取消点赞操作，数据库中删除对应数据
                Example example = new Example(Favorite.class);
                Example.Criteria criteria = example.createCriteria();
                criteria.andEqualTo("videoId", videoID);
                criteria.andEqualTo("userId", userID);
                return favoriteMapper.deleteByExample(example) > 0;
            }
            default -> {
                return false;
            }
        }
    }

}
