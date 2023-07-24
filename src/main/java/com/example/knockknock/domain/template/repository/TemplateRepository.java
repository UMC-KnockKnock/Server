package com.example.knockknock.domain.template.repository;

import com.example.knockknock.domain.template.entity.Template;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TemplateRepository extends JpaRepository<Template, Long> {
}
