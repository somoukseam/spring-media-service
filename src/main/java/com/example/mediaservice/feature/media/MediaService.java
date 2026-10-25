package com.example.mediaservice.feature.media;

import com.example.mediaservice.feature.media.dto.MediaResponse;
import org.springframework.web.multipart.MultipartFile;

public interface MediaService {
    /**
     * Upload single file
     * @param file submit from client
     * @return MediaResponse
     */

    MediaResponse uploadSingle(MultipartFile file);
}
