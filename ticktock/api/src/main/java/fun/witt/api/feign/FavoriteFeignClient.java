package fun.witt.api.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@FeignClient(name = "ticktock-favorite", path = "/douyin/feign/favorite")
public interface FavoriteFeignClient {
    /**
     * 批量获取视频点赞状态
     * @param videoIDList
     * @param userID
     * @return
     */
    @GetMapping("/batch/state")
    Map<Long, Boolean> batchFavoriteState(@RequestParam("videoIDList") List<Long> videoIDList, @RequestParam("userID") long userID);

    /**
     * 获取访问用户点赞视频列表
     * @param userID
     * @return
     */
    @GetMapping("/user/list")
    List<Long> listUserFavoriteVideo(@RequestParam("userID") long userID);
}
