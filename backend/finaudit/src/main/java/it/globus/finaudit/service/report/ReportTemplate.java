package it.globus.finaudit.service.report;


import jakarta.annotation.PostConstruct;
import lombok.Getter;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Component
@Getter
public class ReportTemplate {
    private final String templatePath;
    private final ResourceLoader resourceLoader;
    private JasperReport generalReport;
    private JasperReport pieChartIncomeOrWithdraw;
    private JasperReport dynamicsOfOperationsReport;

    public ReportTemplate(@Value("${jasper.templates.path}") String templatePath, ResourceLoader resourceLoader) {
        this.templatePath = templatePath;
        this.resourceLoader = resourceLoader;
    }

    @PostConstruct
    public void init() throws JRException, IOException {
        generalReport = loadReport("general_report.jasper");
        pieChartIncomeOrWithdraw = loadReport("pie_chart_income_or_withdraw.jasper");
        dynamicsOfOperationsReport = loadReport("dynamics_of_operations.jasper");
    }


    private JasperReport loadReport(String reportFileName) throws JRException, IOException {
        Resource resource = resourceLoader.getResource("classpath:" + templatePath + reportFileName);
        try (InputStream inputStream = resource.getInputStream()) {
            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(inputStream);
            if (jasperReport == null) {
                throw new RuntimeException("JRXML template not found: " + reportFileName);
            }
            return jasperReport;
        }
    }
}
