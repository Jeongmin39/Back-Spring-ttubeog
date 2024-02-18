package com.ttubeog.domain.image.dto.request;

import com.ttubeog.domain.image.domain.ImageType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CreateImageRequestDto {

    public String image;
    public ImageType imageType;
    public Long placeId;

}