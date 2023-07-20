package com.example.knockknock.domain.report.dto;

import com.example.knockknock.domain.report.entity.Report;
import com.example.knockknock.domain.report.entity.ReportType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class GetReportResponseDto {
    private Long reportId;
    private Long reporterId;
    private ReportType reportType;
    private String reportContent;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    public static GetReportResponseDto from(Report report) {
        return GetReportResponseDto.builder()
                .reportId(report.getReportId())
                .reporterId(report.getReporter().getMemberId())
                .reportType(report.getReportType())
                .reportContent(report.getReportContent())
                .createdAt(report.getCreatedAt())
                .build();
    }

}
