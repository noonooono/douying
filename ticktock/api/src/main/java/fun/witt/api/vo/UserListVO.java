package fun.witt.api.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserListVO extends ResultVO {
    private List<UserExt> userList;
}
