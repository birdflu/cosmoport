package com.space.controller;

import com.space.model.Ship;
import com.space.model.ShipType;
import com.space.repository.ShipRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping(path = "/rest/ships")
public class ShipController {

  @Autowired
  private ShipRepo shipRepo;

  @GetMapping
  public @ResponseBody
  List<Ship> getAllShips(
          @RequestParam(value = "name", required = false)
                  String name,
          @RequestParam(value = "planet", required = false)
                  String planet,
          @RequestParam(value = "shipType", required = false)
                  ShipType shipType,
          @RequestParam(value = "after", required = false)
                  Long after,
          @RequestParam(value = "before", required = false)
                  Long before,
          @RequestParam(value = "isUsed", required = false)
                  Boolean isUsed,
          @RequestParam(value = "minSpeed", required = false)
                  Double minSpeed,
          @RequestParam(value = "maxSpeed", required = false)
                  Double maxSpeed,
          @RequestParam(value = "minCrewSize", required = false)
                  Integer minCrewSize,
          @RequestParam(value = "maxCrewSize", required = false)
                  Integer maxCrewSize,
          @RequestParam(value = "minRating", required = false)
                  Double minRating,
          @RequestParam(value = "maxRating", required = false)
                  Double maxRating,
          @RequestParam(value = "order", required = false, defaultValue = "ID")
                  String order,
          @RequestParam(value = "pageNumber", required = false, defaultValue = "0")
                  Integer pageNumber,
          @RequestParam(value = "pageSize", required = false, defaultValue = "3")
                  Integer pageSize
  ) {
    Sort sort = new Sort(Sort.Direction.ASC, ShipOrder.valueOf(order).getFieldName());
    Pageable page = PageRequest.of(pageNumber, pageSize, sort);

    return shipRepo.findShipWithPagination(
            name,
            planet,
            shipType == null ? null : shipType.name(),
            after == null ? new Date(0) : new Date(after),
            before == null ? new Date((long) (1e+14)) : new Date(before),
            isUsed,
            minSpeed == null ? 0.00 : minSpeed,
            maxSpeed == null ? 1.00 : maxSpeed,
            minCrewSize == null ? 0 : minCrewSize,
            maxCrewSize == null ? Integer.MAX_VALUE : maxCrewSize,
            minRating == null ? 0.00 : minRating,
            maxRating == null ? 50.00 : maxRating,
            page);
  }

  @GetMapping("/count")
  public @ResponseBody
  Integer getCountShips(
          @RequestParam(value = "name", required = false)
                  String name,
          @RequestParam(value = "planet", required = false)
                  String planet,
          @RequestParam(value = "shipType", required = false)
                  ShipType shipType,
          @RequestParam(value = "after", required = false)
                  Long after,
          @RequestParam(value = "before", required = false)
                  Long before,
          @RequestParam(value = "isUsed", required = false)
                  Boolean isUsed,
          @RequestParam(value = "minSpeed", required = false)
                  Double minSpeed,
          @RequestParam(value = "maxSpeed", required = false)
                  Double maxSpeed,
          @RequestParam(value = "minCrewSize", required = false)
                  Integer minCrewSize,
          @RequestParam(value = "maxCrewSize", required = false)
                  Integer maxCrewSize,
          @RequestParam(value = "minRating", required = false)
                  Double minRating,
          @RequestParam(value = "maxRating", required = false)
                  Double maxRating
  ) {

    return shipRepo.findShip(
            name,
            planet,
            shipType == null ? null : shipType.name(),
            after == null ? new Date(0) : new Date(after),
            before == null ? new Date((long) (1e+14)) : new Date(before),
            isUsed,
            minSpeed == null ? 0.00 : minSpeed,
            maxSpeed == null ? 1.00 : maxSpeed,
            minCrewSize == null ? 0 : minCrewSize,
            maxCrewSize == null ? Integer.MAX_VALUE : maxCrewSize,
            minRating == null ? 0.00 : minRating,
            maxRating == null ? 50.00 : maxRating
    ).size();
  }

  @PostMapping()
  public ResponseEntity<Ship> save(@Valid @RequestBody Ship ship, Errors errors) {
    if (errors.hasErrors()) {
      return getBadRequestHttpStatus(errors);
    }
    if (ship.getSpeed() != null && ship.getProdDate() != null)
      ship.setRating(getRating(ship.getSpeed(), ship.isUsed(), ship.getProdDate()));

    shipRepo.save(ship);
    return ResponseEntity.status(HttpStatus.OK).body(ship);
  }


  @GetMapping("{id}")
  @ResponseBody
  public ResponseEntity<Ship> getShipById(@PathVariable Long id) {
    if (id == null || id <= 0) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    Optional<Ship> ship = shipRepo.findById(id);

    if (ship.equals(Optional.empty()))
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);

    return ResponseEntity.status(HttpStatus.OK).body(ship.get());
  }

  @PostMapping("{id}")
  public ResponseEntity<Ship> updateShipById(@PathVariable Long id,
                                             @Valid @RequestBody Ship ship, Errors errors) {
    if (id == null || id <= 0) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    Optional<Ship> foundShip = shipRepo.findById(id);
    if (foundShip.equals(Optional.empty()))
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);

    if (errors.hasErrors()) {
      if (foundShip != null &&
              ship.getName() == null &&
              ship.getPlanet() == null &&
              ship.getShipType() == null &&
              ship.getProdDate() == null &&
              ship.getSpeed() == null &&
              ship.getCrewSize() == null){
        return ResponseEntity.status(HttpStatus.OK).body(foundShip.get());
      } else {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ship);
      }
    }

    Ship updatedShip = foundShip.get();
    if (ship.getName() != null)
      updatedShip.setName(ship.getName());
    if (ship.getPlanet() != null)
      updatedShip.setPlanet(ship.getPlanet());
    if (ship.getShipType() != null)
      updatedShip.setShipType(ship.getShipType());
    if (ship.getProdDate() != null)
      updatedShip.setProdDate(ship.getProdDate());
    updatedShip.setUsed(ship.isUsed());
    if (ship.getSpeed() != null)
      updatedShip.setSpeed(ship.getSpeed());
    if (ship.getCrewSize() != null)
      updatedShip.setCrewSize(ship.getCrewSize());
    if (ship.getSpeed() != null && ship.getProdDate() != null)
      updatedShip.setRating(getRating(ship.getSpeed(), ship.isUsed(), ship.getProdDate()));

    shipRepo.save(updatedShip);

    return ResponseEntity.status(HttpStatus.OK).body(updatedShip);
  }

  @DeleteMapping("{id}")
  public ResponseEntity<Ship> deleteShipById(@PathVariable Long id) {
    if (id == null || id <= 0) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    Optional<Ship> deletedShip = shipRepo.findById(id);
    if (deletedShip.equals(Optional.empty()))
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);

    shipRepo.deleteById(id);
    return ResponseEntity.status(HttpStatus.OK).body(null);
  }

  private ResponseEntity<Ship> getBadRequestHttpStatus(Errors errors) {
    System.out.println("Model validation errors : " + errors.getAllErrors().stream()
            .map(DefaultMessageSourceResolvable::getDefaultMessage)
            .collect(Collectors.joining(", ")));
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
  }

  private Double getRating(Double speed, boolean isUsed, Date prodDate) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(prodDate);
    int year = calendar.get(Calendar.YEAR);
    double rating = (double) (Math.round(100 *
            80 * speed * (isUsed ? 0.5 : 1) / (3019 - year + 1)
    )) / 100;
    return rating;
  }

}