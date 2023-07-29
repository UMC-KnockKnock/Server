package com.example.knockknock.domain.template.dto.responseDto;

import com.example.knockknock.domain.template.entity.Template;
import com.example.knockknock.domain.template.entity.TemplateCategory;
import com.example.knockknock.domain.template.entity.TemplateTag;
import lombok.Getter;

@Getter
public class TemplateResponseDto {
    private TemplateCategory category;
    private String content;
    private TemplateTag tag;

    public TemplateResponseDto(Template template) {
        this.category = template.getCategory();
        this.content = template.getContent();
        this.tag = template.getTag();
    }
}
