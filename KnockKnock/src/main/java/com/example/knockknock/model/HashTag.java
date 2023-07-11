package com.example.knockknock.model;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
public class HashTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "HASHTAG_ID")
    private Long id;

    @ManyToMany(mappedBy = "hashTags")
    private List<Post> posts = new ArrayList<>();

    @Column(name = "TAG")
    private String tagName;


    public void setTagName(String tagName) {
        this.tagName = tagName;
    }
}
