package fun.witt.video.controller;

import fun.witt.api.req.PublishReq;
import fun.witt.api.req.UserReq;
import fun.witt.api.vo.ResultVO;
import fun.witt.common.template.JWTTemplate;
import fun.witt.video.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/publish")
public class PublishController {

    @Autowired
    private VideoService videoService;

    @Autowired
    private JWTTemplate jwtTemplate;

    /**
     * 用户上传视频
     * @param req
     * @return
     */
    //@ModelAttribute注解用于将请求参数与控制器方法的参数进行映射
    @PostMapping("/action")
    public ResultVO action(@ModelAttribute PublishReq req) {
        if (req.getData().isEmpty()) {
            return ResultVO.fail("视频数据不能为空");
        }
        if(req.getTitle().isEmpty()){
            return ResultVO.fail("标题不能为空");
        }
        if (req.getToken().isEmpty()){
            return ResultVO.fail("token is empty");
        }
        Number loginUserID = jwtTemplate.getUserIDFromToken(req.getToken());
        return videoService.publish(loginUserID.longValue(), req.getTitle(), req.getData());
    }

    /**
     * 查看对应用户的发布列表
     * @param req
     * @return
     */
    @GetMapping("/list")
    public ResultVO list(UserReq req) {
        // todo 分页查看（提高性能）
        Number loginUserID = jwtTemplate.getUserIDFromToken(req.getToken());
        return videoService.listVideo(Long.parseLong(req.getUser_id()), loginUserID.longValue());
    }
}
