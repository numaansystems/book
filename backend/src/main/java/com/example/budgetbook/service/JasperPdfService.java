package com.example.budgetbook.service;

import net.sf.jasperreports.engine.*;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.Map;

@Service
public class JasperPdfService {
    private final ResourceLoader resourceLoader;

    public JasperPdfService(ResourceLoader resourceLoader) { this.resourceLoader = resourceLoader; }

    // Load jrxml from classpath jrxml/year1 or year2 and compile and fill
    public byte[] generatePdf(String yearFolder, String templateName, Map<String, Object> params, JRDataSource dataSource) throws JRException {
        String path = "classpath:jrxml/" + yearFolder + "/" + templateName;
        Resource res = resourceLoader.getResource(path);
        try (InputStream is = res.getInputStream()) {
            JasperReport report = JasperCompileManager.compileReport(is);
            JasperPrint print = JasperFillManager.fillReport(report, params, dataSource == null ? new JREmptyDataSource() : dataSource);
            return JasperExportManager.exportReportToPdf(print);
        } catch (Exception e) {
            throw new JRException(e);
        }
    }
}
