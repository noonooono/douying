package fun.witt.user.controller;

import cn.hutool.crypto.digest.BCrypt;
import cn.hutool.crypto.digest.DigestUtil;
import fun.witt.api.req.AccountReq;
import fun.witt.api.req.UserReq;
import fun.witt.api.vo.ResultVO;
import fun.witt.api.vo.UserExt;
import fun.witt.api.vo.UserTokenVO;
import fun.witt.api.vo.UserVO;
import fun.witt.common.template.JWTTemplate;
import fun.witt.model.User;
import fun.witt.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JWTTemplate jwtTemplate;


    /**
     * 注册
     * @param req
     * @return
     */
    @PostMapping("/register")
    public ResultVO register(AccountReq req) {
        //1. 验证账号是否存在
        User user = userService.queryNameIsExist(req.getUsername());
        //2. 账号不存在，创建账号
        if (user == null) {
            user = userService.createUser(req.getUsername(), req.getPassword());
            //3.生成token
            String token = jwtTemplate.generateToken(user);
            UserTokenVO tokenVO = new UserTokenVO();
            tokenVO.setUserID(user.getId());
            tokenVO.setToken(token);
            //成功默认调用无参构造，返回成功的状态码和信息
            return tokenVO;
        }
        return ResultVO.fail("注册失败");
    }

    /**
     * 登录
     * @param req
     * @return
     */
    @PostMapping("/login")
    public ResultVO login(AccountReq req) {
        //1. 验证账号是否存在
        User user = userService.queryNameIsExist(req.getUsername());
        if (user == null) {
            return ResultVO.fail("请检查用户名");
        }
        //2.验证密码
        String hashedPassword=DigestUtil.sha1Hex(req.getPassword());
        if (user.getPassword().equals(hashedPassword)) {
            //3.生成token
            String token = jwtTemplate.generateToken(user);
            UserTokenVO tokenVO = new UserTokenVO();
            tokenVO.setUserID(user.getId());
            tokenVO.setToken(token);
            return tokenVO;
        }
        return ResultVO.fail("登录失败");
    }

    /**
     * 获取用户信息
     * @param req
     * @return
     */
    @GetMapping
    public ResultVO info(UserReq req) {
        //从token中取出用户id
        Number loginUserID = jwtTemplate.getUserIDFromToken(req.getToken());
        UserExt userExt = userService.queryUserByID(Long.parseLong(req.getUser_id()), loginUserID.longValue());
        if (userExt != null) {
            UserVO userVO = new UserVO();
            userVO.setUser(userExt);
            return userVO;
        }
        return ResultVO.fail("not found");
    }
}
