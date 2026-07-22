package com.example.portfolio.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.portfolio.entity.Holding;
import com.example.portfolio.entity.Portfolio;
import com.example.portfolio.entity.Transaction;
import com.example.portfolio.entity.TransactionType;
import com.example.portfolio.repository.HoldingRepository;
import com.example.portfolio.repository.PortfolioRepository;
import com.example.portfolio.repository.TransactionRepository;

@Service
public class PortfolioService {

    private final PortfolioRepository portfolioRepository;
    private final HoldingRepository holdingRepository;
    private final TransactionRepository transactionRepository;

    public PortfolioService(
            PortfolioRepository portfolioRepository,
            HoldingRepository holdingRepository,
            TransactionRepository transactionRepository) {
        this.portfolioRepository = portfolioRepository;
        this.holdingRepository = holdingRepository;
        this.transactionRepository = transactionRepository;
    }

    @Transactional
    public void buyStock(Long portfolioId, String symbol, BigDecimal quantity, BigDecimal price) {
        // 1. Find portfolio
        Portfolio portfolio = portfolioRepository.findById(portfolioId)
                .orElseThrow(() -> new IllegalArgumentException("Portfolio not found: " + portfolioId));

        // 2. Check if holding exists
        Holding holding = holdingRepository
                .findByPortfolioIdAndSymbol(portfolioId, symbol)
                .orElse(null);

        if (holding != null) {
            // 3. Update quantity and average price
            BigDecimal oldQuantity = holding.getQuantity();
            BigDecimal oldAverage = holding.getAveragePrice();
            BigDecimal newQuantity = oldQuantity.add(quantity);
            BigDecimal newAverage = oldQuantity.multiply(oldAverage)
                    .add(quantity.multiply(price))
                    .divide(newQuantity, 4, RoundingMode.HALF_UP);

            holding.setQuantity(newQuantity);
            holding.setAveragePrice(newAverage);
            holding.setCurrentPrice(price);
        } else {
            // 4. Create a new holding
            holding = new Holding();
            holding.setPortfolio(portfolio);
            holding.setSymbol(symbol);
            holding.setQuantity(quantity);
            holding.setAveragePrice(price);
            holding.setCurrentPrice(price);
        }

        // 5. Save the holding
        holdingRepository.save(holding);

        // 6. Create a transaction
        Transaction transaction = new Transaction();
        transaction.setPortfolio(portfolio);
        transaction.setType(TransactionType.BUY);
        transaction.setSymbol(symbol);
        transaction.setQuantity(quantity);
        transaction.setPrice(price);
        transaction.setTimestamp(LocalDateTime.now());

        // 7. Save the transaction
        transactionRepository.save(transaction);
    }
}
