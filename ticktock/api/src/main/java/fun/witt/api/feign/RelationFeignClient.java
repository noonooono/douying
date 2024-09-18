package fun.witt.api.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "ticktock-relation", path = "/douyin/feign/relation")
public interface RelationFeignClient {

    /**
     * 关注状态
     *
     * @param userID      用户 ID
     * @param loginUserID 登录用户
     */
    @GetMapping("/follow/state")
    boolean followState(@RequestParam("userID") long userID,
                        @RequestParam("loginUserID") long loginUserID);

}
