package fun.witt.api.acl;

import lombok.Data;

@Data
public class CacheChangeUserCountReq {
    private long user_id;
    private long op;
    private String count_type;
}
