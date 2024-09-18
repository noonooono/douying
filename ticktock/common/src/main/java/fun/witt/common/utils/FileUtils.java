package fun.witt.common.utils;

import com.google.common.io.Files;
import org.apache.tika.Tika;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public enum FileUtils {
    INSTANCE;

    public String fileMediaType(MultipartFile file) throws IOException {
        Tika tika = new Tika();
        return tika.detect(file.getBytes());
    }

    public String fileExt(String filename) {
        return Files.getFileExtension(filename);
    }
}
