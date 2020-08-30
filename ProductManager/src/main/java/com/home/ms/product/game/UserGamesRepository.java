package com.home.ms.product.game;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

interface UserGamesRepository extends JpaRepository<UserGamesEntity, String> {

  List<UserGamesEntity> findAllByUserId(String userId);
}
