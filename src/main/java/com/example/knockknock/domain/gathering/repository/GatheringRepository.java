package com.example.knockknock.domain.gathering.repository;

import com.example.knockknock.domain.gathering.entity.Gathering;
import com.example.knockknock.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GatheringRepository extends JpaRepository<Gathering, Long> {
    List<Gathering> findAllByMember(Member member);
}
