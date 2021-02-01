package com.space.model;

import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.Date;

@Component
@Entity(name = "ship")
public class Ship {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private String name;
  private String  planet;
  private String shipType;
  private Date prodDate;
  private boolean isUsed;
  private Double speed;
  private Integer crewSize;
  private Double rating;

  public Ship() {

  }

  public Ship(String name, String planet, String shipType, Date prodDate, boolean isUsed,
              Double speed, Integer crewSize) {
    this.name = name;
    this.planet = planet;
    this.shipType = shipType;
    this.prodDate = prodDate;
    this.isUsed = isUsed;
    this.speed = speed;
    this.crewSize = crewSize;
  }

  public Integer getId() {
    return id;
  }

//  public void setId(Integer id) {
//    this.id = id;
//  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPlanet() {
    return planet;
  }

  public void setPlanet(String planet) {
    this.planet = planet;
  }

  public String getShipType() {
    return shipType;
  }

  public void setShipType(String shipType) {
    this.shipType = shipType;
  }

  public Date getProdDate() {
    return prodDate;
  }

  public void setProdDate(Date prodDate) {
    this.prodDate = prodDate;
  }

  public boolean isUsed() {
    return isUsed;
  }

  public void setUsed(boolean used) {
    isUsed = used;
  }

  public Double getSpeed() {
    return speed;
  }

  public void setSpeed(Double speed) {
    this.speed = speed;
  }

  public Integer getCrewSize() {
    return crewSize;
  }

  public void setCrewSize(Integer crewSize) {
    this.crewSize = crewSize;
  }

  public Double getRating() {
    return rating;
  }

  public void setRating(Double rating) {
    this.rating = rating;
  }

  @Override
  public String toString() {
    return "Ship{" +
            "name='" + name + '\'' +
            ", planet='" + planet + '\'' +
            ", shipType='" + shipType + '\'' +
            ", prodDate=" + prodDate +
            ", isUsed=" + isUsed +
            ", speed=" + speed +
            ", crewSize=" + crewSize +
            ", rating=" + rating +
            '}';
  }
}
