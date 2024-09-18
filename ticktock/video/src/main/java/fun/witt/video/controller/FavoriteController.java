package fun.witt.video.controller;

import fun.witt.api.req.UserReq;
import fun.witt.api.vo.ResultVO;
import fun.witt.common.template.JWTTemplate;
import fun.witt.video.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/favorite")
public class FavoriteController {

    @Autowired
    private VideoService videoService;

    @Autowired
    private JWTTemplate jwtTemplate;

    /**
     * 获取访问用户点赞的视频列表
     * @param req
     * @return
     */
    @GetMapping("/list")
    public ResultVO list(UserReq req) {
        // todo 分页获取视频列表
        Number loginUserID = jwtTemplate.getUserIDFromToken(req.getToken());
        return videoService.listFavoriteVideo(Long.parseLong(req.getUser_id()), loginUserID.longValue());
    }

}
