package com.space.repository;

import com.space.model.Ship;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface ShipRepo extends JpaRepository<Ship, Integer> {

  String query = "SELECT c FROM ship c " +
          "WHERE (:name is null or c.name like %:name%) " +
          "and (:planet is null or c.planet like %:planet%) " +
          "and (:shipType is null or c.shipType = :shipType) " +
          "and c.prodDate between :after and :before ";


  @Query(query)
  List<Ship> findShipWithPagination(
          @Param("name") String name,
          @Param("planet") String planet,
          @Param("shipType") String shipType,
          @Param("after") Date after,
          @Param("before") Date before,
          Pageable page);

  @Query(query)
  List<Ship> findShip(
          @Param("name") String name,
          @Param("planet") String planet,
          @Param("shipType") String shipType,
          @Param("after") Date after,
          @Param("before") Date before);
}

