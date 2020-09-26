package com.home.ms.shoppingcart;

import com.home.ms.shoppingcart.repository.ShoppingCartRepository;
import com.home.ms.shoppingcart.repository.ShoppingCartStatus;
import com.home.ms.shoppingcart.service.*;
import com.home.ms.shoppingcart.service.invoice.RabbitMqInvoiceProducer;
import com.home.ms.shoppingcart.service.purchasehistory.PurchaseHistoryRequestProducer;
import com.home.ms.shoppingcart.web.ShoppingCart;
import com.home.ms.shoppingcart.web.ShoppingCartElement;
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
import org.springframework.test.jdbc.JdbcTestUtils;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;


@ExtendWith(SpringExtension.class)
@DataJpaTest
@Import(TestApplication.class)
@Sql("/data-h2.sql")
class ShoppingCartServiceTest {
  private static final String SHOPPING_CART_TABLE_NAME = "shoppingcarts";
  private static final String SHOPPING_CART_ELEMENT_TABLE_NAME = "shopping_cart_elements";
  private ShoppingCartService shoppingCartService;

  @Autowired JdbcTemplate jdbcTemplate;

  @Autowired ShoppingCartRepository repository;

  @Autowired IdGenerator idGenerator;

  @Mock
  RabbitMqInvoiceProducer mockRabbitMqInvoiceProducer;
  @Mock
  PurchaseHistoryRequestProducer mockPurchaseHistoryProducer;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);
    shoppingCartService =
        new ShoppingCartService(
            repository,
            new ShoppingCartMapper(),
            idGenerator,
                mockRabbitMqInvoiceProducer,
            mockPurchaseHistoryProducer);
  }

  @Test
  public void checkInitialDataInTables() {

    final int shoppingCartCount =
        JdbcTestUtils.countRowsInTable(jdbcTemplate, SHOPPING_CART_TABLE_NAME);
    final int shoppingCartElementCount =
        JdbcTestUtils.countRowsInTable(jdbcTemplate, SHOPPING_CART_ELEMENT_TABLE_NAME);
    assertThat(shoppingCartCount, is(5));
    assertThat(shoppingCartElementCount, is(8));
  }

  @Test
  public void checkFindShoppingCartReturnsRightResult() {
    final String userId = "user_id-2";
    ShoppingCart shoppingCart = shoppingCartService.findShoppingCart(userId);
    assertThat(shoppingCart.getGameIds().size(), is(2));
  }

  @Test
  public void checkFindShoppingCartReturnsEmptyOpenShoppingCartIfDoesntExist() {
    final String userId = "user99";
    ShoppingCart shoppingCart = shoppingCartService.findShoppingCart(userId);
    assertThat(shoppingCart.getGameIds().size(), is(0));
    assertThat(shoppingCart.getStatus(), is(ShoppingCartStatus.OPEN.getValue()));
  }

  @Test
  public void checkFindShoppingCartReturnsNotNullList() {
    final String userIdWithEmptyShoppingCart = "user_id-1";
    ShoppingCart shoppingCart = shoppingCartService.findShoppingCart(userIdWithEmptyShoppingCart);
    assertThat(shoppingCart.getGameIds().size(), is(0));
  }

  @Test
  public void checkAddItem() {
    final int sbefore =
        JdbcTestUtils.countRowsInTable(jdbcTemplate, SHOPPING_CART_ELEMENT_TABLE_NAME);
    final String userId = "user_id-1";
    final String gameId = "game2";
    ShoppingCartElement element = new ShoppingCartElement();
    element.setGameId(gameId);
    shoppingCartService.addItem(userId, element);
    final int shoppingCartElementCount =
        JdbcTestUtils.countRowsInTable(jdbcTemplate, SHOPPING_CART_ELEMENT_TABLE_NAME);
    assertThat(shoppingCartElementCount, is(9));
  }

  @Test
  public void checkAddItemWhenUserCartNotFount() {
    final String userId = "user0";
    final String gameId = "game2";
    ShoppingCartElement element = new ShoppingCartElement();
    element.setGameId(gameId);
    shoppingCartService.addItem(userId, element);
    final int shoppingCartCount =
        JdbcTestUtils.countRowsInTable(jdbcTemplate, SHOPPING_CART_TABLE_NAME);
    final int shoppingCartElementCount =
        JdbcTestUtils.countRowsInTable(jdbcTemplate, SHOPPING_CART_ELEMENT_TABLE_NAME);
    assertThat(shoppingCartCount, is(6));
    assertThat(shoppingCartElementCount, is(9));
  }

  @Test
  public void checkRemoveItem() {
    final String userId = "user_id-2";
    final String gameId = "game_id-1";
    shoppingCartService.removeItemFromList(userId, gameId);
    final int shoppingCartElementCount =
        JdbcTestUtils.countRowsInTable(jdbcTemplate, SHOPPING_CART_ELEMENT_TABLE_NAME);
    assertThat(shoppingCartElementCount, is(7));
  }

  @Test
  public void chekUpdateStateWithInvoiceStatus() {
    final String userId = "user_id-2";
    shoppingCartService.updateStateWithStatusInvoice(userId);
  }
}
