package com.home.ms.invoice.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.Objects;

/** Class keeps data to start invoice processing */
public class InvoiceToPay {
  private final String userId;
  private final BigDecimal bill;

  @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
  public InvoiceToPay(@JsonProperty("userId") String userId, @JsonProperty("invoice") BigDecimal invoice) {
    this.userId = userId;
    this.bill = invoice;
  }

  public String getUserId() {
    return userId;
  }

  public BigDecimal getBill() {
    return bill;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    InvoiceToPay that = (InvoiceToPay) o;
    return Objects.equals(userId, that.userId) && Objects.equals(bill, that.bill);
  }

  @Override
  public int hashCode() {
    return Objects.hash(userId, bill);
  }

  @Override
  public String toString() {
    return "InvoiceToPay{" + "userId='" + userId + '\'' + ", invoice=" + bill + '}';
  }
}
