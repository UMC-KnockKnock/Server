package com.example.knockknock.domain.template.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Template {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long templateId;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private TemplateCategory category;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private TemplateTag tag;
}
