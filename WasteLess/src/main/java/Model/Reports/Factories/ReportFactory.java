package Model.Reports.Factories;

import Interfaces.Report;
import Model.Reports.ReportEntities.MonthlyReport;
import Model.Reports.ReportEntities.WeeklyReport;

public class ReportFactory extends AbstractReportFactory {

    @Override
    public Report getReport(String reportType) {
        if(reportType.equals("WEEKLY")){
            return new WeeklyReport();
        }
        if(reportType.equals("MONTHLY")){
            return new MonthlyReport();

        }
        return null;
    }
}
