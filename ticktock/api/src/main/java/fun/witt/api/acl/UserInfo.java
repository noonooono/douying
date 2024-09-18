package fun.witt.api.acl;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {
    private long id; // 用户id
    private String name; // 用户名称
    private long followCount; // 关注总数
    private long followerCount; // 粉丝总数
    private boolean isFollow; // true-已关注，false-未关注
    private String avatar; //头像地址
    private String backgroundImage;//背景图片地址
    private String signature;//个性签名
    private long totalFavorite;
    private long favoriteCount;
}
