package com.example.demo.service;

import com.example.demo.model.Tag;
import com.example.demo.repository.TagRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@AllArgsConstructor
@Service
public class TagService {

    private final TagRepository tagRepository;

    public void updateTagByBulk(Tag tag){

        tagRepository.updateByQuery(tag.getIdx(), "update by bulk");

    }
}
