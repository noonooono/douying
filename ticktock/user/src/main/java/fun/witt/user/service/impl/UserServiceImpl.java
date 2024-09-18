package fun.witt.user.service.impl;

import cn.hutool.crypto.digest.BCrypt;
import cn.hutool.crypto.digest.DigestUtil;
import fun.witt.api.feign.RelationFeignClient;
import fun.witt.api.utils.ConvertUtil;
import fun.witt.api.vo.UserExt;
import fun.witt.mapper.UserMapper;
import fun.witt.model.User;
import fun.witt.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RelationFeignClient feignClient;

    /**
     *  查询用户名是否存在
     * @param name
     * @return
     */
    @Override
    public User queryNameIsExist(String name) {
        //这里使用mybatis的查询条件构造器，避免编写SQL语句
        Example userExample = new Example(User.class);
        Example.Criteria criteria = userExample.createCriteria();
        criteria.andEqualTo("userName", name);
        return userMapper.selectOneByExample(userExample);
    }
     /**
     *  创建用户
     * @param name
     * @param password
     * @return
     */
    @Override
    public User createUser(String name, String password) {
        String hashedPassword = DigestUtil.sha1Hex(password);
        User user = new User();
        user.setUserName(name);
        user.setPassword(hashedPassword);
        user.setFollowCount(0L);
        user.setFollowerCount(0L);
        user.setTotalFavorited(0L);
        user.setFavoriteCount(0L);
        user.setSignature("default sign");
        user.setAvatar("https://wallpapercave.com/wp/wp7250032.png");
        user.setBackgroundImage("https://tse1-mm.cn.bing.net/th/id/OIP-C.oHcirgij3f3dLXoHtbJBsgHaHI?rs=1&pid=ImgDetMain");
        try {
            userMapper.insert(user);
        }catch (Exception e){
            System.out.println(e.getMessage());
            System.out.println("创建用户失败");
        }
        return user;
    }

    /**
     * 根据访问用户id查询信息
     * @param userID
     * @param loginUserID
     * @return
     */
    @Override
    public UserExt queryUserByID(long userID, long loginUserID) {
        User user = userMapper.selectByPrimaryKey(userID);
        UserExt userExt = ConvertUtil.convertUser(user);
        //查询用户信息的同时，查询对应的关注状态 为0时不用查
        if (loginUserID > 0) {
            userExt.setFollow(feignClient.followState(user.getId(), loginUserID));
        }
        return userExt;
    }

    @Override
    public List<UserExt> listUserByIDList(List<Long> userIDList, long loginUserID) {
        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn("id", userIDList);
        List<User> userList = userMapper.selectByExample(example);
        if (userList.isEmpty()) {
            return new ArrayList<>();
        }

        return userList.stream().map(user -> {
            UserExt userExt = ConvertUtil.convertUser(user);
            if (loginUserID > 0) {
                userExt.setFollow(feignClient.followState(user.getId(), loginUserID));
            }
            return userExt;
        }).toList();
    }
}
