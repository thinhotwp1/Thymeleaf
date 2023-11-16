package com.tpb.vms.modules.report.service;

import com.tpb.vms.modules.report.dto.ReportDTO;
import org.springframework.http.ResponseEntity;

public interface ReportService {
	byte[] getReportByte(ReportDTO reportDto);
	String getReportString(ReportDTO reportDto);
}
