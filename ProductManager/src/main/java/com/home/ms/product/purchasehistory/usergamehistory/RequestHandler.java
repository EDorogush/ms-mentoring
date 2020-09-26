package com.home.ms.product.purchasehistory.usergamehistory;

import java.net.URI;
import java.net.http.HttpRequest;

public interface RequestHandler<K, V> {

  K performRequest(HttpRequest request, int timeout);

  HttpRequest prepareRequest(V dataToPost);

  String getRequestURI();
}
