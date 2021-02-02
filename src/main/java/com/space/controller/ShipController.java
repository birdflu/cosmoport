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
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
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
          @RequestParam(value = "pageSize", required = false, defaultValue = "5")
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
      System.out.println("errors = " + errors.getAllErrors().stream()
              .map(DefaultMessageSourceResolvable::getDefaultMessage)
              .collect(Collectors.joining(",")));
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    System.out.println("NO ERRORS");
    //Ship ship = new Ship(name, planet, shipType.name(), new Date(prodDate), isUsed, speed, crewSize);
    System.out.println("ship = " + ship);

    if (ship.getRating() == null)
      ship.setRating(0.0);

    shipRepo.save(ship);
    return ResponseEntity.status(HttpStatus.OK).body(ship);
  }


  @GetMapping("{id}")
  @ResponseBody
  public ResponseEntity<Ship>  getShipById(@PathVariable Long id) {
    Optional<Ship> ship = shipRepo.findById(id);

    if (ship.equals(Optional.empty()))
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);

    return ResponseEntity.status(HttpStatus.OK).body(ship.get());
  }
}