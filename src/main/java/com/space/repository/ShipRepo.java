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
          "and (c.prodDate between :after and :before) " +
          "and (:isUsed is null or c.isUsed = :isUsed) " +
          "and (c.speed between :minSpeed and :maxSpeed) " +
          "and (c.crewSize between :minCrewSize and :maxCrewSize) " +
          "and (c.rating between :minRating and :maxRating) "
          ;

  @Query(query)
  List<Ship> findShipWithPagination(
          @Param("name") String name,
          @Param("planet") String planet,
          @Param("shipType") String shipType,
          @Param("after") Date  after,
          @Param("before") Date before,
          @Param("isUsed") Boolean isUsed,
          @Param("minSpeed") Double minSpeed,
          @Param("maxSpeed") Double maxSpeed,
          @Param("minCrewSize") Integer minCrewSize,
          @Param("maxCrewSize") Integer maxCrewSize,
          @Param("minRating") Double minRating,
          @Param("maxRating") Double maxRating,
          Pageable page);

  @Query(query)
  List<Ship> findShip(
          @Param("name") String name,
          @Param("planet") String planet,
          @Param("shipType") String shipType,
          @Param("after") Date after,
          @Param("before") Date before,
          @Param("isUsed") Boolean isUsed,
          @Param("minSpeed") Double minSpeed,
          @Param("maxSpeed") Double maxSpeed,
          @Param("minCrewSize") Integer minCrewSize,
          @Param("maxCrewSize") Integer maxCrewSize,
          @Param("minRating") Double minRating,
          @Param("maxRating") Double maxRating
  );
}

