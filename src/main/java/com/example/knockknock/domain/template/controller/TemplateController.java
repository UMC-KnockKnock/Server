package com.example.knockknock.domain.template.controller;

import com.example.knockknock.domain.template.repository.TemplateRepository;
import com.example.knockknock.domain.template.service.TemplateService;
import com.example.knockknock.global.message.ResponseMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Tag(name = "Template", description = "Template API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/template")
public class TemplateController {
    // 멘트 템플릿으로 이동 /template
    private final TemplateService templateService;
    @Operation(summary = "템플릿 보기", description = "템플릿 보기")
    @GetMapping()
    public ResponseEntity getTemplate(){
        return ResponseMessage.SuccessResponse("템플릿 조회 성공", templateService.getTemplate());
    }
}
