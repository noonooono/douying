package fun.witt.api.vo;

import lombok.Data;

@Data
public class CommentExt {
    private long id;
    private String content;
    private String createDate;
    private UserExt user;
}
