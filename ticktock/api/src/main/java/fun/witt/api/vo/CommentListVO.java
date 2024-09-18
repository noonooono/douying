package fun.witt.api.vo;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class CommentListVO extends ResultVO {
    private List<CommentExt> commentList;
}
