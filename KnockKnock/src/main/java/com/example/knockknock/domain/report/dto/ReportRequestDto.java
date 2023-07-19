package com.example.knockknock.domain.report.dto;

import com.example.knockknock.domain.report.entity.ReportType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ReportRequestDto {
    @NotNull
    private Long reporterId;
    @NotBlank
    private ReportType reportType;

    private String reportContent;

}
