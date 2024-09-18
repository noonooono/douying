package fun.witt.favorite.feign;

import com.google.common.collect.Maps;
import fun.witt.api.feign.FavoriteFeignClient;
import fun.witt.mapper.FavoriteMapper;
import fun.witt.model.Favorite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/feign/favorite")
public class FavoriteFeignClientImpl implements FavoriteFeignClient {

    @Autowired
    private FavoriteMapper favoriteMapper;

    /**
     * 批量获取视频点赞状态
     * @param videoIDList
     * @param userID
     * @return
     */
    public Map<Long, Boolean> batchFavoriteState(@RequestParam("videoIDList") List<Long> videoIDList,
                                                 @RequestParam("userID") long userID) {
        //创建查询条件（当然登录用户对视频的点赞）
        Example example = new Example(Favorite.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId", userID);
        Example.Criteria criteria1 = example.createCriteria();
        criteria1.andIn("videoId", videoIDList);
        //1.获取该登录者id下的点赞视频列表
        List<Favorite> favoriteList = favoriteMapper.selectByExample(example);
        //2.若为空，返回空map
        Map<Long, Boolean> result = Maps.newHashMap();
        if (favoriteList.isEmpty()) {
            return result;
        }
        //2.1不为空则将视频id和点赞状态存入map，
        //3.返回数据字典，key为视频id，value为点赞状态(设置为ture)
        return favoriteList.stream()
                .collect(Collectors.toMap(
                        Favorite::getVideoId,
                        favorite -> true));
    }

    /**
     * 获取访问用户点赞视频列表
     * @param userID
     * @return
     */
    @Override
    public List<Long> listUserFavoriteVideo(long userID) {
        //1.创建查询条件
        Example example = new Example(Favorite.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId", userID);
        List<Favorite> favoriteList = favoriteMapper.selectByExample(example);
        if (favoriteList.isEmpty()) {
            return new ArrayList<>();
        }
        //2.取出点赞视频列表的id并返回
        return favoriteList.stream()
                .map(Favorite::getVideoId)
                .toList();
    }
}
