package com.home.ms.invoice;

import com.home.ms.invoice.model.InvoiceResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Import(TestApplication.class)
@Sql("/data-h2.sql")
class InvoiceProcessorTest {
  private InvoiceProcessor invoiceProcessor;

  @Autowired JdbcTemplate jdbcTemplate;

  @Autowired InvoiceRepository invoiceRepository;

  @Mock MessageBrokerMessageProducer<InvoiceResult> invoiceResultProducer;

  // @SpyBean InvoiceProcessor invoiceProcessor;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);
    invoiceProcessor = new InvoiceProcessor(invoiceResultProducer, invoiceRepository);
  }

  @Test
  public void testSearchAndProcess() {}
}
