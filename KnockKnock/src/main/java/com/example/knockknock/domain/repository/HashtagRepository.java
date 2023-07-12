package com.example.knockknock.domain.repository;

import com.example.knockknock.domain.model.Hashtag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HashtagRepository extends JpaRepository<Hashtag, Long> {
}
