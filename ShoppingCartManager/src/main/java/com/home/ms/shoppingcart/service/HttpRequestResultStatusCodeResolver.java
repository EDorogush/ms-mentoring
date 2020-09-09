package com.home.ms.shoppingcart.service;

import com.home.ms.shoppingcart.service.purchasehistory.RequestDetail;

@FunctionalInterface
public interface HttpRequestResultStatusCodeResolver<V> {
  V resolveStatusCode(Integer statusCode, RequestDetail requestDetail);
}
