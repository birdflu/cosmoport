package com.space.service;

import com.space.model.Ship;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ShipService {
  private final JdbcTemplate jdbcTemplate;

  @Autowired
  public ShipService(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public List<Ship> index() {
//    return jdbcTemplate.query("SELECT * FROM Ship", new ShipMapper());
    return jdbcTemplate.query("SELECT * FROM ship", new BeanPropertyRowMapper<>(Ship.class));
  }

  public Ship show(int id) {
//   return jdbcTemplate.query("SELECT * FROM Ship WHERE id=?", new Object[]{id}, new ShipMapper())
//           .stream().findAny().orElse(null);
    return jdbcTemplate.query("SELECT * FROM ship WHERE id=?", new Object[]{id},
            new BeanPropertyRowMapper<>(Ship.class)).stream().findAny().orElse(null);
  }

  public void save(Ship Ship) {
//    jdbcTemplate.update("INSERT INTO Ship VALUES (1,?,?,?)",
//            Ship.getName(),
//            Ship.getAge(),
//            Ship.getEmail());
  }

  public void update(int id, Ship updatedShip) {
//    jdbcTemplate.update("UPDATE Ship SET name=?, age =?, email=? WHERE id=?",
//            updatedShip.getName(),
//            updatedShip.getAge(),
//            updatedShip.getEmail(),
//            updatedShip.getId());
  }

  public void delete(int id) {
//    jdbcTemplate.update("DELETE FROM Ship WHERE id=?", id);
  }
}
