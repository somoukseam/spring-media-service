package com.example.mediaservice.feature.media;

import com.example.mediaservice.domain.Media;
import com.example.mediaservice.feature.media.dto.MediaResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Value;

@Mapper(componentModel = "spring")
public abstract class MediaMapper {
    // change from interface class to abstract in case we want to custom mapper

    @Value("${storage.base-uri}")
    private String baseUri;

    @Mapping(source = "name", target = "uri", qualifiedByName = "mapUri")
    abstract MediaResponse toMediaResponse(Media media);

    @Named("mapUri")
    String mapUri (String name){
        return baseUri + name;
    }
}
