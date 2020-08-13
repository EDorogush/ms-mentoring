package com.home.ms.invoice.repository;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "invoices")
public class InvoiceEntity {
    @Id
    @Column(name = "id", length = 36, unique = true)
    private String id;

    @Column(name = "user_id", length = 36)
    private String userId;

    @Column(name = "bill")
    private BigDecimal bill;

    @Column(name = "status")
    private int status;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public BigDecimal getBill() {
        return bill;
    }

    public void setBill(BigDecimal bill) {
        this.bill = bill;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
