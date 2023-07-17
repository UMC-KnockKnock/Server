package com.example.knockknock.domain.template.service;

import com.example.knockknock.domain.template.repository.TemplateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TemplateService {
    private final TemplateRepository templateRepository;
}
