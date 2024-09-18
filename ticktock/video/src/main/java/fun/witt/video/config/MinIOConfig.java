package fun.witt.video.config;

import fun.witt.common.template.MinioTemplate;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Setter
@Getter
@RefreshScope
@Configuration
public class MinIOConfig {

    @Value("${minio.endpoint}")
    private String endpoint;
    @Value("${minio.bucket}")
    private String bucket;
    @Value("${minio.access-key-id}")
    private String accessKeyId;
    @Value("${minio.access-key-secret}")
    private String accessKeySecret;
    @Value("${minio.type}")
    private Integer ossType;
    @Value("${minio.maxLength:128}")
    private Integer maxLength;

    @Bean
    public MinioTemplate afterPropertiesSet() {
        return new MinioTemplate(bucket, endpoint, accessKeyId, accessKeySecret);
    }
}
