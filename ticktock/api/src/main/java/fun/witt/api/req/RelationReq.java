package fun.witt.api.req;

import lombok.Data;

@Data
public class RelationReq {
    /**
     * 1-关注，2-取消关注
     */
    private String action_type;
    /**
     * 对方用户id
     */
    private String to_user_id;
    /**
     * 用户鉴权token
     */
    private String token;
}
