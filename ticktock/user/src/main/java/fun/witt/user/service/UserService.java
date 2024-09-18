package fun.witt.user.service;

import fun.witt.api.vo.UserExt;
import fun.witt.model.User;

import java.util.List;

public interface UserService {
    /**
     *  查询用户名是否存在
     * @param name
     * @return
     */
    User queryNameIsExist(String name);

    /**
     *  创建用户
     * @param name
     * @param password
     * @return
     */
    User createUser(String name, String password);

    /**
     *  通过id查询用户信息
     * @param userID
     * @param loginUserID
     * @return
     */
    UserExt queryUserByID(long userID, long loginUserID);

    List<UserExt> listUserByIDList(List<Long> userIDList, long loginUserID);

}
