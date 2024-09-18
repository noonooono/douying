package fun.witt.api.req;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class PublishReq {
    /**
     * 用户鉴权token
     */
    private String token;
    /**
     * 视频标题
     */
    private String title;
    /**
     * 视频数据
     */
    private MultipartFile data;
}
