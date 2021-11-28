package com.nizsimsek.blogapp.dto.converter;

import com.nizsimsek.blogapp.dto.TagDto;
import com.nizsimsek.blogapp.model.Tag;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TagDtoConverter {

    public TagDto convert(Tag tag) {

        return new TagDto(
                tag.getId(),
                tag.getName()
        );
    }

    public List<TagDto> convertToTagDtoList(List<Tag> tagList) {

        return tagList.stream().map(this::convert).collect(Collectors.toList());
    }
}
