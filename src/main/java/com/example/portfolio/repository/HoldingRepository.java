package com.example.portfolio.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.portfolio.entity.Holding;

public interface HoldingRepository extends JpaRepository<Holding, Long> {

    // SELECT * FROM holdings WHERE portfolio_id = ?
    List<Holding> findByPortfolioId(Long portfolioId);

    // SELECT * FROM holdings WHERE portfolio_id = ? AND symbol = ?
    Optional<Holding> findByPortfolioIdAndSymbol(Long portfolioId, String symbol);
}
