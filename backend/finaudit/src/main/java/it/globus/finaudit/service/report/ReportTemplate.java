package it.globus.finaudit.service.report;


import jakarta.annotation.PostConstruct;
import lombok.Getter;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.InputStream;

@Component
@Getter
public class ReportTemplate {
    private final String templatePath;
    private JasperReport generalReport;
    private JasperReport pieChartIncomeOrWithdraw;
    private JasperReport dynamicsOfOperationsReport;

    public ReportTemplate(@Value("${jasper.templates.path}") String templatePath) {
        this.templatePath = templatePath;
    }

    @PostConstruct
    public void init() throws JRException {
        generalReport = compileReport("general_report.jrxml");
        pieChartIncomeOrWithdraw = compileReport("pie_chart_income_or_withdraw.jrxml");
        dynamicsOfOperationsReport = compileReport("dynamics_of_operations.jrxml");
    }


    private JasperReport compileReport(String reportFileName) throws JRException {
        InputStream jrxmlStream = getClass().getClassLoader()
                .getResourceAsStream(templatePath + reportFileName);
        if (jrxmlStream == null) {
            throw new RuntimeException("JRXML template not found");
        }
        return JasperCompileManager.compileReport(jrxmlStream);
    }


}
