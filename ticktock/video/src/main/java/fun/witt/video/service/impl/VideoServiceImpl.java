package fun.witt.video.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import fun.witt.api.feign.FavoriteFeignClient;
import fun.witt.api.feign.UserFeignClient;
import fun.witt.api.utils.ConvertUtil;
import fun.witt.api.vo.ResultVO;
import fun.witt.api.vo.VideoExt;
import fun.witt.api.vo.VideoListVO;
import fun.witt.common.template.MinioTemplate;
import fun.witt.mapper.VideoMapper;
import fun.witt.model.Video;
import fun.witt.video.service.VideoService;
import fun.witt.video.utils.FfmpegUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Example;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class VideoServiceImpl implements VideoService {
    //定义日期格式
    private static final String NORM_DAY_PATTERN = "yyyy-MM-dd";

    @Autowired
    private VideoMapper videoMapper;

    @Autowired
    private MinioTemplate minioTemplate;

    @Autowired
    private UserFeignClient userFeignClient;

    @Autowired
    private FavoriteFeignClient favoriteFeignClient;

    /**
     * 发布视频
     * @param userID
     * @param title
     * @param file
     * @return
     */
    @Override
    public ResultVO publish(long userID, String title, MultipartFile file) {
        // fixme Exception & filename & upload & frame content type
        //1.获取当前时间+用户id
        String filePath = DateUtil.format(new Date(), NORM_DAY_PATTERN) + "/" + IdUtil.simpleUUID();
        //2.先判断文件名是否为空，再取文件名后缀
        String fileSuffix = Objects.requireNonNull(file.getOriginalFilename())
                .substring(file.getOriginalFilename().lastIndexOf("."));
        String videoURL;
        try {
            //3.拼接并上传视频
            videoURL = filePath + fileSuffix;
            minioTemplate.uploadFile(file.getBytes(), videoURL, file.getContentType());
        } catch (IOException e) {
            e.printStackTrace();
            return ResultVO.fail("上传失败");
        }

        String coverURL;
        try {
            coverURL = filePath + ".jpeg";
            byte[] videoFrame = FfmpegUtils.videoFrame(file);
            minioTemplate.uploadFile(videoFrame, coverURL, "image/jpeg");
        } catch (IOException e) {
            e.printStackTrace();
            return ResultVO.fail("截取视频封面失败");
        }

        Video video = new Video();
        video.setFavoriteCount(0L);
        video.setCommentCount(0L);
        video.setAuthorId(userID);
        video.setTitle(title);
        video.setPlayUrl(videoURL);
        video.setCoverUrl(coverURL);
        video.setPublishTime(new Date());
        if (videoMapper.insert(video) <= 0) {
            return ResultVO.fail("上传失败");
        }
        return ResultVO.ok();
    }
    /**
     * 查询用户发布的视频
     * @param userID
     * @param loginUserID
     * @return
     */
    @Override
    public VideoListVO listVideo(long userID, long loginUserID) {
        Example example = new Example(Video.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("authorId", userID);
        //1.根据用户id获取对应作者对的视频列表
        List<Video> videoList = videoMapper.selectByExample(example);
        //2.将video对象转换为videoExt对象
        List<VideoExt> videoExtList = convertVideoExtList(videoList, loginUserID);

        //3.封装返回对象
        VideoListVO vo = new VideoListVO();
        vo.setVideoList(videoExtList);
        return vo;
    }

    /**
     * 获取视频流
     * @param loginUserID
     * @param latestTime
     * @param count
     * @return
     */
    @Override
    public VideoListVO feedVideo(long loginUserID, long latestTime, int count) {
        DateTime date = DateUtil.date(latestTime);
        //1.获取视频列表动态传入判断条件和返回条数
        List<Video> videoList = videoMapper.queryVideoOrderByLatestTime(date.toJdkDate(), count);
        //2.将video对象转换为videoExt对象
        List<VideoExt> videoExtList = convertVideoExtList(videoList, loginUserID);
        //3.封装到返回对象
        VideoListVO vo = new VideoListVO();
        vo.setVideoList(videoExtList);
        //4.设置下一次拉取的起始时间，确保每次刷新都是最新投稿视频

        //设置默认视频，他的时间撮最大
        Video defaultVideo = new Video();
        defaultVideo.setPublishTime(new Date());

        Date maxDate = videoList.stream()
                .max(Comparator.comparing(Video::getPublishTime))
                .orElse(defaultVideo)//找不到最大视频返回默认视频
                .getPublishTime();
        vo.setNextTime(maxDate.getTime() / 1000);
        return vo;
    }

    /**
     * 获取访问用户点赞视频列表
     * @param userID
     * @param loginUserID
     * @return
     */
    @Override
    public VideoListVO listFavoriteVideo(long userID, long loginUserID) {
        //1.获取访问用户点赞视频的videoID列表
        List<Long> videoIDList = favoriteFeignClient.listUserFavoriteVideo(userID);
        if (videoIDList.isEmpty()) {
            return new VideoListVO();
        }
        //2.根据videoID列表查询视频列表
        Example example = new Example(Video.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn("id", videoIDList);
        List<Video> videoList = videoMapper.selectByExample(example);
        //3.将video列表转换为VideoExt列表
        List<VideoExt> videoExtList = convertVideoExtList(videoList, loginUserID);
        VideoListVO vo = new VideoListVO();
        vo.setVideoList(videoExtList);
        return vo;
    }

    /**
     * 将video列表转换为VideoExt列表
     * 对video进行扩展处理（登录者是否对传来的视频点赞）
     * 设置当前用户对视频的点赞状态
     * @param videoList
     * @param loginUserID
     * @return
     */
    private List<VideoExt> convertVideoExtList(List<Video> videoList, long loginUserID) {
        //1.获取每个video对象id，转为长整型列表
        List<Long> videoIDList = videoList.parallelStream().map(Video::getId).toList();
        //2.批量获取当前登录用户对视频的点赞状态
        Map<Long, Boolean> favoriteStateDict = favoriteFeignClient.batchFavoriteState(videoIDList, loginUserID);
        //3.将每个video对象转换为VideoExt对象，并设置对应属性
        return videoList.parallelStream()
                .map(video -> {
                    // fixme batch request
                    VideoExt videoExt = ConvertUtil.convertVideo(video);
                    videoExt.setPlayUrl(minioTemplate.getObjectUrl(video.getPlayUrl()));
                    videoExt.setCoverUrl(minioTemplate.getObjectUrl(video.getCoverUrl()));
                    videoExt.setAuthor(userFeignClient.getUserInfo(video.getAuthorId(), loginUserID));
                    //判断id是否是当前登录用户点赞的视频，是则返回对应值，否则默认flase
                    if (favoriteStateDict != null && !favoriteStateDict.isEmpty()) {
                        //传值则是数据字典里设置的点赞状态为true，不传值说明不在喜欢列表里，不传值默认为false
                        videoExt.setFavorite(favoriteStateDict.getOrDefault(video.getId(), false));
                    } else {
                        videoExt.setFavorite(false);
                    }
                    return videoExt;
                })
                .collect(Collectors.toList());
    }
}
