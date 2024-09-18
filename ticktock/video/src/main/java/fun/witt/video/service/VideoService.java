package fun.witt.video.service;

import fun.witt.api.vo.ResultVO;
import fun.witt.api.vo.VideoListVO;
import org.springframework.web.multipart.MultipartFile;

public interface VideoService {

    /**
     * 发布视频
     * @param userID
     * @param title
     * @param file
     * @return
     */
    ResultVO publish(long userID, String title, MultipartFile file);

    /**
     * 查询用户发布的视频
     * @param userID
     * @param loginUserID
     * @return
     */
    VideoListVO listVideo(long userID, long loginUserID);

    /**
     * 获取视频流
     * @param loginUserID
     * @param lastTime
     * @param count
     * @return
     */
    VideoListVO feedVideo(long loginUserID, long lastTime, int count);

    /**
     * 获取访问用户点赞视频列表
     * @param userID
     * @param loginUserID
     * @return
     */
    VideoListVO listFavoriteVideo(long userID, long loginUserID);

}
