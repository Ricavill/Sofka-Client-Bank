package com.example.sofkatransaction.reports;

import com.example.sofkatransaction.account.Account;
import com.example.sofkatransaction.shared.commons.DateRange;
import com.example.sofkatransaction.transaction.TransactionDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
    public Page<TransactionDTO> getTransactionReport(@RequestParam(name = "fecha") DateRange dateRange,
                                                     @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return reportService.getAccountsReport(dateRange,pageable);
    }
}
