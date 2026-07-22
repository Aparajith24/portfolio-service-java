package com.example.portfolio.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.portfolio.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    // SELECT * FROM transactions WHERE portfolio_id = ? ORDER BY timestamp DESC
    List<Transaction> findByPortfolioIdOrderByTimestampDesc(Long portfolioId);
}
