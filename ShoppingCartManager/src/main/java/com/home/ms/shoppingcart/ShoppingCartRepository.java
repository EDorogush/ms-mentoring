package com.home.ms.shoppingcart;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCartEntity, String> {

    //    @Query("select * from User u where u.firstname = :firstname or u.lastname = :lastname")
//    User findByLastnameOrFirstname(@Param("lastname") String lastname,
//                                   @Param("firstname") String firstname);
    List<ShoppingCartEntity> findByUserId(String userId);
}