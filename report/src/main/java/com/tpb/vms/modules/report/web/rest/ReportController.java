package com.tpb.vms.modules.report.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.tpb.vms.modules.report.dto.ReportDTO;
import com.tpb.vms.modules.report.service.ReportServiceImpl;

@Controller
@RequestMapping("/api/report")
public class ReportController {

	@Autowired
    private ReportServiceImpl reportService;

	@PostMapping(produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> getReport(@RequestBody ReportDTO reportDto) {
		byte[] result = reportService.getReportByte(reportDto);
		return new ResponseEntity<byte[]>(result, HttpStatus.OK);
    }

	@PostMapping(value = "/string")
	public ResponseEntity<String> getReportString(@RequestBody ReportDTO reportDto) {
		String result = reportService.getReportString(reportDto);
		return new ResponseEntity<String>(result, HttpStatus.OK);
	}

	@GetMapping("/template")
	public ResponseEntity<?> downloadTemplate(@RequestParam String typeTemplate){
		return reportService.downloadTemplate(typeTemplate);
	}



}
