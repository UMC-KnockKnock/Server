package com.example.knockknock.domain.template.service;

import com.example.knockknock.domain.template.dto.responseDto.TemplateResponseDto;
import com.example.knockknock.domain.template.entity.Template;
import com.example.knockknock.domain.template.repository.TemplateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TemplateService {
    private final TemplateRepository templateRepository;

    public List<TemplateResponseDto> getTemplate() {
        List<Template> templates = templateRepository.findAll();
        List<TemplateResponseDto> templateResponseDtos = new ArrayList<>();
        for (Template template : templates) {
            TemplateResponseDto templateResponseDto = new TemplateResponseDto(template);
            templateResponseDtos.add(templateResponseDto);
        }
        return templateResponseDtos;
    }
}
