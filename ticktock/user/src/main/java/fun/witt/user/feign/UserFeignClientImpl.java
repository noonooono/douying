package fun.witt.user.feign;

import fun.witt.api.feign.UserFeignClient;
import fun.witt.api.vo.UserExt;
import fun.witt.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/feign/user") //Feign客户端请求路径
public class UserFeignClientImpl implements UserFeignClient {

    @Autowired
    private UserService userService;

    /**
     * 根据用户ID获取用户信息
     * @param userID
     * @param loginUserID
     * @return
     */
    @Override
    public UserExt getUserInfo(long userID, long loginUserID) {
        return userService.queryUserByID(userID, loginUserID);
    }

    @Override
    public List<UserExt> batchUserInfo(List<Long> userIDList, long loginUserID) {
        return userService.listUserByIDList(userIDList, loginUserID);
    }

    //
    //@Override
    //public Map<Long, UserInfo> getUserInfoDict(long[] userIDList) {
    //    return null;
    //}
    //
    //@Override
    //public List<UserInfo> getUserInfoList(long[] userIDList) {
    //    return null;
    //}
    //
    //@Override
    //public CommonResponse cacheChangeUserCount(CacheChangeUserCountReq req) {
    //    return null;
    //}
    //
    //@Override
    //public long cacheGetAuthor(long videoID) {
    //    return 0;
    //}
    //
    //@Override
    //public CommonResponse updateUserFavoritedCount(long userID, long actionType) {
    //    return null;
    //}
    //
    //@Override
    //public CommonResponse updateUserFavoriteCount(long userID, long actionType) {
    //    return null;
    //}
    //
    //@Override
    //public CommonResponse updateUserFollowCount(long userID, long actionType) {
    //    return null;
    //}
    //
    //@Override
    //public CommonResponse updateUserFollowerCount(long userID, long actionType) {
    //    return null;
    //}
}
