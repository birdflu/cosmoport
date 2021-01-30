package com.space.controller;

import com.space.model.Ship;
import com.space.model.ShipType;
import com.space.repository.ShipRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@Controller // This means that this class is a Controller
@RequestMapping(path = "/rest/ships") // This means URL's start with /demo (after Application path)
public class ShipController {

  @Autowired // This means to get the bean called userRepository
  private ShipRepo shipRepo;

/*  @PostMapping(path="/add") // Map ONLY POST Requests
  public @ResponseBody String addNewUser (@RequestParam String name
          , @RequestParam String email) {
    // @ResponseBody means the returned String is the response, not a view name
    // @RequestParam means it is a parameter from the GET or POST request

    User n = new User();
    n.setName(name);
    n.setEmail(email);
    userRepository.save(n);
    return "Saved";
  }*/

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

    Date minDate = new Date(0);
    Date maxDate = new Date((long) (1e+14));

    List<Ship> ships = shipRepo.findShipWithPagination(
            name,
            planet,
            shipType == null ? null : shipType.name(),
            after == null ? minDate : new Date(after),
            before == null ? maxDate : new Date(before),
            isUsed,
            minSpeed == null ? 0.00 : minSpeed,
            maxSpeed == null ? 1.00 : maxSpeed,
            minCrewSize == null ? 0 : minCrewSize,
            maxCrewSize == null ? Integer.MAX_VALUE : maxCrewSize,
            minRating == null ? 0.00 : minRating,
            maxRating == null ? Double.MAX_VALUE : maxRating,
            page);

    return ships;
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

    Date minDate = new Date(0);
    Date maxDate = new Date((long) (1e+14));

    int size = shipRepo.findShip(
            name,
            planet,
            shipType == null ? null : shipType.name(),
            after == null ? minDate : new Date(after),
            before == null ? maxDate : new Date(before),
            isUsed,
            minSpeed == null ? 0.00 : minSpeed,
            maxSpeed == null ? 1.00 : maxSpeed,
            minCrewSize == null ? 0 : minCrewSize,
            maxCrewSize == null ? Integer.MAX_VALUE : maxCrewSize,
            minRating == null ? 0.00 : minRating,
            maxRating == null ? Double.MAX_VALUE : maxRating
      ).size();
    return size;

  }
}