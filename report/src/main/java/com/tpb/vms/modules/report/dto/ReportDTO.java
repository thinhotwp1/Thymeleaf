package com.tpb.vms.modules.report.dto;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportDTO {
	private String reportId;
	private List<Map<String,String>> data;
	private Map<String,List<Map<String,String>>> subData;
	private Map<String,String> parameters;
}
