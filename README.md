
# Project introduction
current project contains a number of separate applications which serve next endpoints:
```

/games
/games/{gameId}
/games/{gameId}/achievements
/games/{gameId}/achievements/{itemId}
/games/{gameId}/playHistory
//possibly /games/{gameId}/playHistory/{itemId} will be created
/purchase-history
/purchase-history/games  
/purchase-history/games/{gameId} 
/invoices 
/invoices/{id} 
/shoppingCartItems
/shoppingCartItems/{itemId}
/aggregator/games?sortBy=
/aggregator/users?sortBy=

```

RabbitMQ is used to manage interaction between two services: ShoppingCartManager (com.home.ms.shoppingcart.service.invoice) and InvoiceManager

Both Consumers and Producers are implemented in services. Producers are used from service methods, consumers are work independently as messagebroker's queue listeners.
 
ShoppingCartManager messageProducer usage: see com.home.ms.shoppingcart.service.ShoppingCartService#updateStateWithStatusInvoice

ShoppingCartManager messageConsumer usage: see com.home.ms.shoppingcart.service.invoice.InvoiceConsumer

InvoiceManager messageConsumer usage: see com.home.ms.invoice.messagebroker.RabbitMqInvoiceConsumer

InvoiceManager messageProducer usage: see com.home.ms.invoice.InvoiceProcessor#processInvoice

Async requests with retry pattern are used in next services: ShoppingCartManager, ProductManager/PurchaseHistory (both implementations are the same)

ShoppingCartManager request usage: see com.home.ms.shoppingcart.service.ShoppingCartService#updateStateWithStatusApproved

PurchaseHistory request usage: see com.home.ms.product.purchasehistory.PurchaseHistoryService#addItem


All workflow "buy a game"

user fills his shopping cart by items. Then he push "pay" button (HTTP patch to /shoppingCart).
after that next steps occur (optimistic scenario):

1, user's shopping cart is "locked" by status INVOICE and data is sent to Invoice manager (by messageQueue broker). user recieves no content at response ans his next HTTP GET /shoppingCart requests will return list of items and shopping cart status "invoice sent"

2. InvoiceManager receives the message from MBQ and write invoice item to database with status "WAIT"

3. separate InvoiceManager's service InvoiceProcessor periodically search database for invoices with "WAIT" status. If any founded invoiceProcessor tries to update item's status from "WAIT" to "PROCESS" and start processing. When processing is finished invoice's status is updated from "PROCESS" to "OK" or "REJECTED" and result message is sent to MBQ

4. ShoppingCartManager receives the message from MBQ with invoice result. If invoice's status is "OK", shoppingCart's
 status is updated to "APPROVED" and data about purchased games is sent to purchase history (async HTTP post /purchase-history). 

5. When purchaseHistory process post request (HTTP post /purchase-history) it also sends async HTTP post request to /games to duplicate user/game data to game.

6. if all within steps 4 and 5 is OK, shopping cart is cleaned from elements.

Orchestration pattern: ShoppingCartManager could be Orchestrator

Choreography patterns: ShoppingCartManager-PurchaseHistory-GamesUserHistory could be Choreography
    


   