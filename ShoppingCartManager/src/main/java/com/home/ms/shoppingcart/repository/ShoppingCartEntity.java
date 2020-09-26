package com.home.ms.shoppingcart.repository;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity contains data about user's shopping cart, userId is unique key and is used as primary key
 */
@Entity
@Table(name = "shoppingcarts")
public class ShoppingCartEntity {

  @Id
  @Column(name = "user_id", length = 36)
  private String userId;

  @Column(name = "status")
  @Enumerated(EnumType.STRING)
  private ShoppingCartStatus status;

  @OneToMany(
      mappedBy = "userId",
      cascade = CascadeType.ALL,
      orphanRemoval = true,
      fetch = FetchType.EAGER)
  private List<ShoppingCartEntityElement> elements;

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public ShoppingCartStatus getStatus() {
    return status;
  }

  public void setStatus(ShoppingCartStatus status) {
    this.status = status;
  }

  public List<ShoppingCartEntityElement> getElements() {
    return elements;
  }

  public void setElements(List<ShoppingCartEntityElement> elements) {
    this.elements = elements;
  }

  public void addElement(ShoppingCartEntityElement element) {
    if (elements == null) {
      elements = new ArrayList<>();
    }
    // todo:: copy to new element before adding? or make immutable??
    elements.add(element);
  }
}
