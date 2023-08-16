package com.example.knockknock.domain.report.entity;

public enum ReportType {
    // 신고 사유:
    // 게시판 성격에 부적절함, 음란물/불건전한 대화, 낚시/놀람/도배,
    // 욕설/혐오, 정치적 발언, 상업적 광고, 유출/사칭/사기
    INAPPROPRIATE_FOR_BOARD,
    OBSCENE_CONTENT,
    CLICKBAIT_SPAM,
    PROFANITY_AND_HATE,
    POLITICAL_REMARKS,
    COMMERCIAL_ADVERTISING,
    LEAK_IMPERSONATION_FRAUD
}
