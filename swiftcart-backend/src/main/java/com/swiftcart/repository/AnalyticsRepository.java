package com.swiftcart.repository;

import com.swiftcart.entity.TransactionLedger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface AnalyticsRepository extends JpaRepository<TransactionLedger, Long> {

    @Query("SELECT COALESCE(SUM(t.amount), 0) FROM TransactionLedger t WHERE t.type = 'SALE'")
    BigDecimal getTotalSales();

    @Query("SELECT COALESCE(SUM(t.amount), 0) FROM TransactionLedger t WHERE t.type = 'REFUND'")
    BigDecimal getTotalRefunds();

    @Query("SELECT COALESCE(SUM(t.amount), 0) FROM TransactionLedger t WHERE t.type = 'LOSS'")
    BigDecimal getTotalLosses();
    
    @Query("SELECT COALESCE(SUM(t.amount), 0) FROM TransactionLedger t WHERE t.type = 'COGS'")
    BigDecimal getTotalCogs();
}
