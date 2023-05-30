package io.endeavour.stocks.dao;

import io.endeavour.stocks.vo.StockPriceHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class PriceHistoryDao {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public PriceHistoryDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public List<StockPriceHistory> getPriceHistory(String tickerSymbol, LocalDate fromDate, LocalDate toDate) {
        String query = """
                SELECT
                	TICKER_SYMBOL,
                	TRADING_DATE,
                	OPEN_PRICE,
                	CLOSE_PRICE,
                	VOLUME
                FROM
                	ENDEAVOUR.STOCKS_PRICE_HISTORY sph
                WHERE
                	TICKER_SYMBOL = :tickerSymbol
                	AND TRADING_DATE BETWEEN :fromDate AND :toDate
                """;

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("tickerSymbol", tickerSymbol);
        mapSqlParameterSource.addValue("fromDate", fromDate);
        mapSqlParameterSource.addValue("toDate", toDate);

        List<StockPriceHistory> priceHistoryList = namedParameterJdbcTemplate.query(query, mapSqlParameterSource, (resultSet, rowNumber) -> {
            StockPriceHistory stockPriceHistory = new StockPriceHistory();
            stockPriceHistory.setTickerSymbol(resultSet.getString("TICKER_SYMBOL"));
            stockPriceHistory.setTradingDate(resultSet.getDate("TRADING_DATE").toLocalDate());
            stockPriceHistory.setOpenPrice(resultSet.getBigDecimal("OPEN_PRICE"));
            stockPriceHistory.setClosePrice(resultSet.getBigDecimal("CLOSE_PRICE"));
            stockPriceHistory.setVolume(resultSet.getLong("VOLUME"));
            return stockPriceHistory;
        });
        return priceHistoryList;
    }
}
