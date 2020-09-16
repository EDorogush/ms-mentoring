package com.home.ms.invoice;

import com.home.ms.invoice.exception.SendMessageException;

public interface MessageBrokerMessageProducer<T> {
  /**
   *
   * @param dataToSend
   * @return
   * @throws SendMessageException
   */
  void sendMessage(T dataToSend);
}
