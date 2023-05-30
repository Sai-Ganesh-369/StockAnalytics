package io.endeavour.stocks.service;

import io.endeavour.stocks.dao.LookupsDao;
import io.endeavour.stocks.dao.PriceHistoryDao;
import io.endeavour.stocks.vo.StockPriceHistory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class StockAnalyticsService {
    private final PriceHistoryDao priceHistoryDao;
    private final LookupsDao lookupsDao;

    public StockAnalyticsService(PriceHistoryDao priceHistoryDao, LookupsDao lookupsDao) {
        this.priceHistoryDao = priceHistoryDao;
        this.lookupsDao = lookupsDao;
    }

    public List<StockPriceHistory> getPriceHistory(String tickerSymbol, LocalDate fromDate, LocalDate toDate,
                                                   Optional<String> sortFieldOptional,
                                                   Optional<String> sortDirectionOptional) {
        String tickerName = lookupsDao.getTickerName(tickerSymbol);
        List<StockPriceHistory> priceHistoryList = priceHistoryDao.getPriceHistory(tickerSymbol, fromDate, toDate);
        priceHistoryList.forEach(priceHistory -> priceHistory.setTickerName(tickerName));

        String sortField = sortFieldOptional.orElse("tradingDate");
        Comparator<StockPriceHistory> comparator = switch (sortField) {
            case "tradingDate" -> Comparator.comparing(StockPriceHistory::getTradingDate);
            case "openPrice" -> Comparator.comparing(StockPriceHistory::getOpenPrice);
            case "closePrice" -> Comparator.comparing(StockPriceHistory::getClosePrice);
            case "volume" -> Comparator.comparing(StockPriceHistory::getVolume);
            default -> Comparator.comparing(StockPriceHistory::getTradingDate);
        };
        String sortDirection = sortDirectionOptional.orElse("asc");
        if("desc".equals(sortDirection)) {
            comparator = comparator.reversed();
        }
        priceHistoryList.sort(comparator);
        return priceHistoryList;
    }

    public String getTickerName(String tickerSymbol) {
        return lookupsDao.getTickerName(tickerSymbol);
    }
}
