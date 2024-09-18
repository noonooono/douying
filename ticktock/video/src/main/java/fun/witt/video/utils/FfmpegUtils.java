package fun.witt.video.utils;

import lombok.extern.slf4j.Slf4j;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Slf4j
public class FfmpegUtils {
    public static final String format = "jpeg";
    //从输入流读取视频文件，抽出第一帧
    public static byte[] videoFrame(MultipartFile file) {
        try (FFmpegFrameGrabber ff = new FFmpegFrameGrabber(file.getInputStream())) {
            ff.start();
            int ftp = ff.getLengthInFrames();
            int flag = 0;
            Frame frame = null;
            while (flag <= ftp) {
                frame = ff.grabImage();
                if ((flag > 12) && (frame != null)) {
                    break;
                }
                flag++;
            }
            ff.stop();
            ByteArrayOutputStream imageOut = new ByteArrayOutputStream();
            try {RenderedImage renderedImage = frameToBufferedImage(frame);
                ImageIO.write(renderedImage, format, imageOut);}
            catch (Exception e){
                System.out.println("帧转为流失败");
            }
            return imageOut.toByteArray();
        } catch (IOException e) {
            log.error("video cut frame fail", new RuntimeException(e));
        }
        return null;
    }

    /**
     * 帧转为流
     */
    private static RenderedImage frameToBufferedImage(Frame frame) {
        Java2DFrameConverter converter = new Java2DFrameConverter();
        BufferedImage bufferedImage = null;
        try {
             bufferedImage = converter.getBufferedImage(frame);
            // 使用 bufferedImage...
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("帧转为流失败");
        }
        return bufferedImage;
    }
//        BufferedImage bufferedImage;
//        try (Java2DFrameConverter converter = new Java2DFrameConverter()) {
//            bufferedImage = converter.getBufferedImage(frame);
//        }
//        try {
//            bufferedImage = ImageIO.read(new ByteArrayInputStream(frame.getData()));
//        }
//        return bufferedImage;

}

