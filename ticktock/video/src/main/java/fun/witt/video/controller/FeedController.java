package fun.witt.video.controller;

import cn.hutool.core.util.StrUtil;
import fun.witt.api.vo.ResultVO;
import fun.witt.common.template.JWTTemplate;
import fun.witt.video.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class FeedController {
    /**
     * 默认返回的视频数量
     */
    private static final int DEFAULT_LIMIT_COUNT = 30;
    @Autowired
    private VideoService videoService;

    @Autowired
    private JWTTemplate jwtTemplate;

    /**
     * 获取视频流
     * @param token
     * @param latestTime
     * @return
     */
    @GetMapping("/feed")
    public ResultVO feed(String token, String latestTime) {

        //1.token不为空就取其id，没有默认0
        Number loginUserID = 0;
        if (StrUtil.isNotBlank(token)) {
            loginUserID = jwtTemplate.getUserIDFromToken(token);
        }
        //2.判断有无返回视频的最新时间戳，没有默认当前时间
        long lastTime;
        if (StrUtil.isNotBlank(latestTime)) {
            lastTime = Long.parseLong(latestTime) * 1000;
        } else {
            lastTime = System.currentTimeMillis();
        }
        //3.动态传参
        return videoService.feedVideo(loginUserID.longValue(), lastTime, DEFAULT_LIMIT_COUNT);
    }
}
