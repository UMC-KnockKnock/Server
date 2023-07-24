package com.example.knockknock.domain.template.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Tag(name = "Template", description = "Template API")
@RequiredArgsConstructor
@RestController
public class TemplateController {
    //- [ ]  멘트 템플릿으로 이동 /friends/{friendId}/template
}
