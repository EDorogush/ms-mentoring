package com.home.ms.shoppingcart.service.invoice;

import java.math.BigDecimal;
import java.util.Objects;

public class InvoiceToSend {
  private final String userId;
  private final BigDecimal invoice;

  public InvoiceToSend(String userId, BigDecimal invoice) {
    this.userId = userId;
    this.invoice = invoice;
  }

  public String getUserId() {
    return userId;
  }

  public BigDecimal getInvoice() {
    return invoice;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    InvoiceToSend that = (InvoiceToSend) o;
    return Objects.equals(userId, that.userId) && Objects.equals(invoice, that.invoice);
  }

  @Override
  public int hashCode() {
    return Objects.hash(userId, invoice);
  }

  @Override
  public String toString() {
    return "InvoiceToProduce{" + "userId='" + userId + '\'' + ", invoice=" + invoice + '}';
  }
}
