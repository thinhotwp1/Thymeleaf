package com.tpb.vms.modules.report.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.tpb.vms.modules.report.config.ReportProperties;
import com.tpb.vms.modules.report.dto.ReportDTO;

import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;


@Slf4j
@Service
public class ReportServiceImpl implements ReportService {
    @Autowired
    ReportProperties reportProperties;

    @Value("${statement.path}")
    private String pathStatement;

    public byte[] getReport(ReportDTO reportDto) {
        try {
            String src = getPath(reportDto.getReportId());
            File initialFile = new File(src);
            InputStream inputStream = new FileInputStream(initialFile);
            JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);
            //JRSaver.saveObject(jasperReport, "employeeReport.jasper");

            Map<String, Object> parameters = new HashMap<>();

            Map<String, String> inputParameter = reportDto.getParameters();
            ;
            if (inputParameter != null) {
                for (Entry<String, String> entry : inputParameter.entrySet()) {
                    parameters.put(entry.getKey(), entry.getValue());
                }
            }

            Map<String, List<Map<String, String>>> subData = reportDto.getSubData();
            if (subData != null) {
                for (Entry<String, List<Map<String, String>>> entry : subData.entrySet()) {
                    List<Map<String, String>> sourceSubData = entry.getValue();
                    JRDataSource subDataSource = new JRBeanCollectionDataSource(sourceSubData);
                    parameters.put(entry.getKey(), subDataSource);
                }
            }

            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(reportDto.getData());

            JasperPrint jasperPrint = JasperFillManager.fillReport(
                    jasperReport, parameters, dataSource);
            return JasperExportManager.exportReportToPdf(jasperPrint);

        } catch (Exception e) {
            log.error(e.toString(), e);
            return null;
        }
    }

    private String getPath(String report) {
        String baseUrl = reportProperties.getBaseUrl();
        if (!baseUrl.endsWith("/")) {
            baseUrl = baseUrl + "/";
        }
        String src = baseUrl + report + ".jrxml";
        return src;
    }

    @Override
    public byte[] getReportByte(ReportDTO reportDto) {
        return getReport(reportDto);
    }

    @Override
    public String getReportString(ReportDTO reportDto) {
        byte[] reportByte = getReport(reportDto);
        String reportString = Base64.getEncoder().encodeToString(reportByte);
        return reportString;
    }

    /**
     * api download template
     * input: name file, hard fix
     * output: file pdf in [pathStatement] in server
     */
    public ResponseEntity<?> downloadTemplate(String typeTemplate) {
        String pdfTemplate = pathStatement + typeTemplate + ".pdf";
        Resource resource = new FileSystemResource(pdfTemplate);
        try {
            byte[] pdfBytes = Files.readAllBytes(Paths.get(resource.getURI()));

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", typeTemplate + ".pdf");

            return ResponseEntity.ok().headers(headers).body(pdfBytes);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(500).body(null);
        }
    }
}
