package fun.witt.api.req;

import lombok.Data;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 用户注册请求参数
 */
@Data
public class AccountReq {
    private String username;
    private String password;
}
