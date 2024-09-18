package fun.witt.api.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserVO extends ResultVO {
    private UserExt user;
}
