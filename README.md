
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

need to update/fix code:
    1. playHistory per game,  gameid go through request params. Entity have id, userId, gameId, value
usecases (do I need userGameid column in table ? )
    2. there are should be mapping of gameid to unique list of achievemnt
    

SELECT:
SELECT games
    get games needs request to purchasehistory do we need to have duplicate data about user's purchase?
    anyway, we need sync request to purchase history to get list of user's games. 
    what if we'll need another filter?
SELECT  playHistory:
    do not need cross request here
Select purchase history
    do not need cross request here
selesct achievement
    do not need cross request here

get shopping cart: 
    get shoppingcart items, then get games per each item
    
      
    
 

1. add game to catalog
    1. post /games
    2. post /games/{gameId}/achievements
    3. send notification to aggregator
    
2. user is buying game
    1. post /shoppingCartItems (keep shopping cart status?)
    2. post invoice to start purchase procedure
    3. post /shoppingCartItems - status??? to keep "invoice sent operation"
    3. wait fot