package spring.hi_hello_spring.common.s3;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class FileUploadDTO {

    private MultipartFile file;
    
}
