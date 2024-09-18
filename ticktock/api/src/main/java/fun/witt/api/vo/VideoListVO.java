package fun.witt.api.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class VideoListVO extends ResultVO {
    private long nextTime;
    private List<VideoExt> videoList;
}
