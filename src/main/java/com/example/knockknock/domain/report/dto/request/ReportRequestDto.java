package com.example.knockknock.domain.report.dto.request;

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
    @NotBlank
    private ReportType reportType;

}
