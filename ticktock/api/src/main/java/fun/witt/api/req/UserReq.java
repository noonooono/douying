package fun.witt.api.req;

import lombok.Data;

@Data
public class UserReq {
    /**
     * 用户id
     */
    private String user_id;
    /**
     * 用户鉴权token
     */
    private String token;
}
