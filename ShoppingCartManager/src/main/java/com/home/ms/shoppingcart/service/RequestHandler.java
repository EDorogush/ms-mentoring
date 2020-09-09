package com.home.ms.shoppingcart.service;

import java.net.http.HttpRequest;

public interface RequestHandler<K, V> {

  K performRequest(HttpRequest request, int timeout);

  HttpRequest prepareRequest(V dataToPost);
  }
