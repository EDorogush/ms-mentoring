package com.home.ms.shoppingcart.service.invoice;

import com.home.ms.shoppingcart.service.exception.SendRequestException;

public interface MessageBrokerMessageProducer<T> {
  /**
   * @param dataToSend
   * @return
   * @throws SendRequestException
   */
  void sendMessage(T dataToSend);
}
