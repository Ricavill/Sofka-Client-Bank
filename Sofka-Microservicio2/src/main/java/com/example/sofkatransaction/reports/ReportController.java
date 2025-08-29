package com.example.sofkatransaction.reports;

import com.example.sofkatransaction.account.Account;
import com.example.sofkatransaction.shared.commons.DateRange;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("reportes")
public class ReportController {
    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("")
    public List<Account> getAccountReport(@RequestParam(name = "fecha") DateRange dateRange) {
        return reportService.getAccountsReport(dateRange);
    }
}
