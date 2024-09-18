package fun.witt.common.template;

import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import io.minio.http.Method;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

@Slf4j
public class MinioTemplate {
    private final String bucket;
    private final MinioClient minioClient;

    public MinioTemplate(String bucket, String endpoint, String accessKeyID, String accessKeySecret) {
        this.bucket = bucket;
        this.minioClient = MinioClient.builder()
                .endpoint(endpoint)
                .credentials(accessKeyID, accessKeySecret)
                .build();
    }

    /**
     * 删除文件
     *
     * @param objectName 文件名称
     * @throws Exception <a href="https://docs.minio.io/cn/java-client-api-reference.html#removeObject"/a>
     */
    public void removeObject(String objectName) throws Exception {
        minioClient.removeObject(RemoveObjectArgs.builder()
                .object(objectName)
                .bucket(bucket)
                .build());
    }

    /**
     * 获得上传的URL
     *
     * @param objectName x
     */
    public String getObjectUrl(String objectName) {
        try {
            return minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                    .bucket(bucket)
                    .object(objectName).expiry(12, TimeUnit.HOURS)
                    .method(Method.GET)
                    .build());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * 上传文件
     * @param bytes
     * @param filePath
     * @param contentType
     * @throws IOException
     */
    public void uploadFile(byte[] bytes, String filePath, String contentType) throws IOException {
        try (InputStream input = new ByteArrayInputStream(bytes)) {
            //输入流上传到minio存储
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucket)
                            .contentType(contentType)
                            .stream(input, input.available(), -1)
                            .object(filePath)
                            .build()
            );
        } catch (Exception e) {
            log.error("minio上传文件错误：", e);
        }
    }

    public void uploadFile(MultipartFile file) throws IOException {
        String path = file.getName();
        uploadFile(file.getBytes(), path, file.getContentType());
    }
}
