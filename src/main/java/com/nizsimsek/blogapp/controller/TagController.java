package com.nizsimsek.blogapp.controller;

import com.nizsimsek.blogapp.dto.TagDto;
import com.nizsimsek.blogapp.dto.request.CreateTagRequest;
import com.nizsimsek.blogapp.dto.request.UpdateTagRequest;
import com.nizsimsek.blogapp.service.TagService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/tags")
public class TagController {

    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @PostMapping
    public ResponseEntity<TagDto> createTag(@Valid @RequestBody CreateTagRequest request) {
        return ResponseEntity.ok(tagService.createTag(request));
    }

    @GetMapping
    public ResponseEntity<List<TagDto>> getTagList() {
        return ResponseEntity.ok(tagService.getTagDtoList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TagDto> getTagById(@PathVariable String id) {
        return ResponseEntity.ok(tagService.getTagById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TagDto> updateTag(@PathVariable String id, @Valid @RequestBody UpdateTagRequest request) {
        return ResponseEntity.ok(tagService.updateTag(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTagById(@PathVariable String id) {
        return ResponseEntity.ok(tagService.deleteTagById(id));
    }
}
