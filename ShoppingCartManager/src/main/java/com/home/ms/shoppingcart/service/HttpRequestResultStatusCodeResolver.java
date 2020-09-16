package com.home.ms.shoppingcart.service;

@FunctionalInterface
public interface HttpRequestResultStatusCodeResolver<V> {
  V resolveStatusCode(Integer statusCode, RequestDetail requestDetail);
}
