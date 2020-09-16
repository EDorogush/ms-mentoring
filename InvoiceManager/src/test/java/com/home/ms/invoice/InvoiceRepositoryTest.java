package com.home.ms.invoice;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Import(TestApplication.class)
@Sql("/data-h2.sql")
class InvoiceRepositoryTest {
  @Autowired JdbcTemplate jdbcTemplate;

  @Autowired InvoiceRepository invoiceRepository;

  @Test
  public void updateStatusWorks() {
    // given
    final String id = "id-1";
    final InvoiceEntity entityBeforeUpdate = getInvoiceById(id);
    assertThat(entityBeforeUpdate.getStatus(), is(InvoiceEntityStatus.WAIT));
    // when
    invoiceRepository.updateStatus(
        id, InvoiceEntityStatus.WAIT.name(), InvoiceEntityStatus.PROCESS.name());
    final InvoiceEntity entityAfterUpdate = getInvoiceById(id);
    assertThat(entityAfterUpdate.getStatus(), is(InvoiceEntityStatus.PROCESS));
  }

  @Test
  public void updateStatusThrowsExceptionWhenNotFound() {
    // given
    final String id = "id-2";
    final InvoiceEntity entityBeforeUpdate = getInvoiceById(id);
    assertThat(entityBeforeUpdate.getStatus(), not(InvoiceEntityStatus.WAIT));
    // when
    final int rowsUpdated =
        invoiceRepository.updateStatus(
            id, InvoiceEntityStatus.WAIT.name(), InvoiceEntityStatus.PROCESS.name());
    assertThat(rowsUpdated, is(0));
  }

  private InvoiceEntity getInvoiceById(String id) {
    final String sql = "SELECT * FROM invoices WHERE ID = ?";
    InvoiceEntity invoiceEntity =
        jdbcTemplate.queryForObject(
            sql,
            new Object[] {id},
            (rs, rowNum) -> {
              InvoiceEntity entity = new InvoiceEntity();
              entity.setId(rs.getString("id"));
              entity.setUserId(rs.getString("user_id"));
              entity.setStatus(InvoiceEntityStatus.valueOf(rs.getString("status")));
              return entity;
            });
    return invoiceEntity;
  }
}
