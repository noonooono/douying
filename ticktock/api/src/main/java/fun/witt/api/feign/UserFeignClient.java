package fun.witt.api.feign;

import fun.witt.api.vo.UserExt;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "ticktock-user", path = "/douyin/feign/user")
public interface UserFeignClient {

    /**
     * 根据用户ID获取用户信息
     * @param userID
     * @param loginUserID
     * @return
     */
    @GetMapping("/info")
    UserExt getUserInfo(@RequestParam("userID") long userID,
                        @RequestParam("loginUserID") long loginUserID);

    @GetMapping("/batch/info")
    List<UserExt> batchUserInfo(@RequestParam("userIDList") List<Long> userIDList,
                                @RequestParam("loginUserID") long loginUserID);
}
