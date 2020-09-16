package com.home.ms.shoppingcart.service;

public interface MessageBrokerMessageProducer<T> {
  /**
   * @param dataToSend
   * @return
   * @throws com.home.ms.shoppingcart.service.exception.SendMessageException
   */
  void sendMessage(T dataToSend);
}
