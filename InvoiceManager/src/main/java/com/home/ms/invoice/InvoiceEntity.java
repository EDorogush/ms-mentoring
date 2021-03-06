package com.home.ms.invoice;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

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
    @Enumerated(EnumType.STRING)
    private InvoiceEntityStatus status;

    @Column(name = "last_update")
    private Instant lastUpdate;

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

    public InvoiceEntityStatus getStatus() {
        return status;
    }

    public void setStatus(InvoiceEntityStatus status) {
        this.status = status;
    }

    public Instant getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Instant lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}
