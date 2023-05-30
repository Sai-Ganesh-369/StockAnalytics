package io.endeavour.stocks.dao;

import io.endeavour.stocks.vo.StockLookup;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LookupsDao {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public LookupsDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public String getTickerName(String tickerSymbol) {
        String query = """
                SELECT
                    TICKER_NAME
                  FROM
                    ENDEAVOUR.STOCKS_LOOKUP sl
                  WHERE
                    TICKER_SYMBOL = :tickerSymbol
                """;
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("tickerSymbol", tickerSymbol);
        List<StockLookup> tickerNames = namedParameterJdbcTemplate.query(query, mapSqlParameterSource, (resultSet, rowNumber) -> {
            StockLookup stockLookup = new StockLookup();
            stockLookup.setTickerSymbol(tickerSymbol);
            stockLookup.setTickerName(resultSet.getString("TICKER_NAME"));
            return stockLookup;
        });

        if (tickerNames.isEmpty()) {
            return "";
        }
        return tickerNames.get(0).getTickerName();
    }
}
