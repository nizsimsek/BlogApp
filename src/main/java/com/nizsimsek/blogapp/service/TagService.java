package com.nizsimsek.blogapp.service;

import com.nizsimsek.blogapp.dto.TagDto;
import com.nizsimsek.blogapp.dto.converter.TagDtoConverter;
import com.nizsimsek.blogapp.dto.request.CreateTagRequest;
import com.nizsimsek.blogapp.dto.request.UpdateTagRequest;
import com.nizsimsek.blogapp.exception.GeneralNotFoundException;
import com.nizsimsek.blogapp.model.Tag;
import com.nizsimsek.blogapp.repository.TagRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TagService {

    private final TagRepository tagRepository;
    private final TagDtoConverter tagDtoConverter;

    public TagService(TagRepository tagRepository,
                      TagDtoConverter tagDtoConverter) {
        this.tagRepository = tagRepository;
        this.tagDtoConverter = tagDtoConverter;
    }

    public TagDto createTag(CreateTagRequest request) {

        Tag tag = new Tag(
                request.getName()
        );

        return tagDtoConverter.convert(tagRepository.save(tag));
    }

    public List<TagDto> getTagDtoList() {

        return tagDtoConverter.convertToTagDtoList(findTags());
    }

    public TagDto getTagById(String id) {

        return tagDtoConverter.convert(findTagById(id));
    }

    public TagDto updateTag(String id, UpdateTagRequest request) {

        Tag tag = findTagById(id);

        Tag updatedTag = new Tag(
                tag.getId(),
                request.getName(),
                tag.getPostList()
        );

        return tagDtoConverter.convert(tagRepository.save(updatedTag));
    }

    public String deleteTagById(String id) {

        Tag tag = findTagById(id);

        tagRepository.delete(tag);

        return "Tag has been deleted with this id : " + id;
    }

    protected Tag findTagById(String id) {

        return tagRepository.findById(id)
                .orElseThrow(() -> new GeneralNotFoundException("Tag could not found this id : " + id));
    }

    protected List<Tag> findTags() {

        return tagRepository.findAll();
    }

    protected List<Tag> findTagListByIdList(List<String> idList) {

        return Optional.of(tagRepository.findAllByIdIn(idList))
                .filter(tag -> !tag.isEmpty())
                .orElseThrow(() -> new GeneralNotFoundException("Tags could not find by ids : " + idList));
    }
}
