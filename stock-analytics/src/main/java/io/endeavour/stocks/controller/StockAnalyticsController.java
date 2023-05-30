package io.endeavour.stocks.controller;

import io.endeavour.stocks.service.StockAnalyticsService;
import io.endeavour.stocks.vo.StockPriceHistory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/analytics")
public class StockAnalyticsController {
    private final StockAnalyticsService stockAnalyticsService;
    private static final Logger LOGGER = LoggerFactory.getLogger(StockAnalyticsController.class);

    public StockAnalyticsController(StockAnalyticsService stockAnalyticsService) {
        this.stockAnalyticsService = stockAnalyticsService;
    }

    @GetMapping("/price-history/{tickerSymbol}/{fromDate}/{toDate}")
    public List<StockPriceHistory> getPriceHistory(@PathVariable("tickerSymbol")
                                                   String tickerSymbol,
                                                   @PathVariable("fromDate")
                                                   @DateTimeFormat(pattern = "yyyy-MM-dd")
                                                   LocalDate fromDate,
                                                   @PathVariable("toDate")
                                                   @DateTimeFormat(pattern = "yyyy-MM-dd")
                                                   LocalDate toDate,
                                                   @RequestParam(value = "sortField", required = false)
                                                   Optional<String> sortFieldOptional,
                                                   @RequestParam(value = "sortDirection", required = false)
                                                   Optional<String> sortDirectionOptional) {
        LOGGER.info("Path params are {} {} {}. Query params are {}", tickerSymbol, fromDate, toDate,
                sortFieldOptional, sortDirectionOptional);
        return stockAnalyticsService.getPriceHistory(tickerSymbol, fromDate, toDate, sortFieldOptional, sortDirectionOptional);
    }

    @GetMapping("/ticker-lookup/{tickerSymbol}")
    public String getTickerName(@PathVariable("tickerSymbol") String tickerSymbol) {
        return stockAnalyticsService.getTickerName(tickerSymbol);
    }
}
