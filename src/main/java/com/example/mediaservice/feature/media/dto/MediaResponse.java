package com.example.mediaservice.feature.media.dto;

public record MediaResponse(
        String name,
        String contentType,
        Long size,
        String extension,
        String uri
) {
}
