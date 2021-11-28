package com.nizsimsek.blogapp.repository;

import com.nizsimsek.blogapp.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, String> {

    List<Tag> findAllByIdIn(List<String> idList);
}
