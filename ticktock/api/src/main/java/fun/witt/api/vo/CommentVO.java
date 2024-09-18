package fun.witt.api.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class CommentVO extends ResultVO {
    private CommentExt comment;
}
