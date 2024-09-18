package fun.witt.api.req;

import lombok.Data;

@Data
public class VideoReq {
    /**
     * 用户id
     */
    private String video_id;
    /**
     * 用户鉴权token
     */
    private String token;
}
