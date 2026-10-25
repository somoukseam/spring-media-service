package com.example.mediaservice.feature.media;

import com.example.mediaservice.domain.Media;
import com.example.mediaservice.feature.media.dto.MediaResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MediaServiceImpl implements MediaService{

    private final MediaRepository repository;
    private final MediaMapper mapper;

    @Value("${storage.server-path}")
    private String serverPath;

    @Override
    public MediaResponse uploadSingle(MultipartFile file) {
        // TO DO
        // extract extension
        // cloud.png
        // png
        Integer lastIndexOfDot = file.getOriginalFilename().lastIndexOf(".");
        String extension = file.getOriginalFilename().substring(lastIndexOfDot + 1);

        // create media name
        String mediaName = UUID.randomUUID() + "." + extension;

        // folder name
        String contentType = file.getContentType().split("/")[0];
        String folderName = switch (contentType){
            case "image" -> "image";
            case "video" -> "video";
            case "application" -> "report";
            default -> throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid file type");
        };

        // create path object
        Path path = Path.of(serverPath + folderName + "/" + mediaName);

        try {
            Files.copy(file.getInputStream(),path);
        }
        catch (IOException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "File copy failed");
        }

        Media media = new Media();
        media.setName(mediaName);
        media.setContentType(file.getContentType());
        media.setSize(file.getSize());
        media.setExtension(extension);
        media.setUploadedAt(LocalDateTime.now());
        media.setUploadedBy("admin");
        media.setIsDeleted(false);
        media = repository.save(media);

        return mapper.toMediaResponse(media);
    }
}
