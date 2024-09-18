package fun.witt.favorite.controller;

import fun.witt.api.req.FavoriteReq;
import fun.witt.api.vo.ResultVO;
import fun.witt.common.template.JWTTemplate;
import fun.witt.favorite.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/favorite")
public class FavoriteController {

    @Autowired
    private FavoriteService favoriteService;

    @Autowired
    private JWTTemplate jwtTemplate;

    /**
     * 视频点赞
     * @param req
     * @return
     */
    @PostMapping("/action")
    public ResultVO action(FavoriteReq req) {
        Number loginUserID = jwtTemplate.getUserIDFromToken(req.getToken());
        if (favoriteService.likeAction(req.getAction_type(),
                Long.parseLong(req.getVideo_id()),
                loginUserID.longValue())) {
            return ResultVO.ok();
        }
        return ResultVO.fail("fail");
    }

}
