package Model.Reports.Factories;

import Interfaces.Report;

public abstract class AbstractReportFactory {

    abstract Report getReport(String reportType);

}
