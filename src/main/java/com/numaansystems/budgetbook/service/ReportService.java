package com.numaansystems.budgetbook.service;

import com.numaansystems.budgetbook.model.Biennium;
import com.numaansystems.budgetbook.repository.BienniumRepository;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.util.JRLoader;
import org.springframework.stereotype.Service;
import org.springframework.core.io.ClassPathResource;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final BienniumRepository bienniumRepository;

    public byte[] generatePdfReport(String reportName, Map<String, Object> parameters, Connection connection) {
        try {
            // Get active biennium to determine which template folder to use
            Biennium biennium = bienniumRepository.findByActiveTrue()
                    .orElseThrow(() -> new RuntimeException("No active biennium found"));

            // Determine template path based on current fiscal year
            String templateFolder = biennium.isFirstYear() ? "year1" : "year2";
            String templatePath = "templates/" + templateFolder + "/" + reportName + ".jasper";

            // Load the compiled report template
            InputStream reportStream = new ClassPathResource(templatePath).getInputStream();
            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(reportStream);

            // Fill the report
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, connection);

            // Export to PDF
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);

            return outputStream.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Error generating PDF report: " + e.getMessage(), e);
        }
    }
}
