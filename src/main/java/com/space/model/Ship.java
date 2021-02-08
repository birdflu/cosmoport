package com.space.model;

import com.space.controller.ShipTypeSubSet;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Date;

@Component
@Entity(name = "ship")
public class Ship {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @NotBlank(message = "The Name must not be empty")
  @Size(min = 1, max = 50, message = "The Name length must be between ${min} and ${max}")
  private String name;
  @NotBlank(message = "The Planet must not be empty")
  @Size(min = 1, max = 50, message = "The Planet length must be between ${min} and ${max}")
  private String  planet;
  @NotNull(message = "The ShipType must not be null")
  @NotEmpty(message = "The ShipType must not be empty")
  @ShipTypeSubSet(anyOf = {ShipType.MERCHANT, ShipType.MILITARY, ShipType.TRANSPORT})
  private String shipType;
  @NotNull(message = "The ProdDate must not be null")
  @Temporal(TemporalType.DATE)
  @ProdDate(message = "The year of prodDate must be between ${min} and ${max}")
  private Date prodDate;
  private boolean isUsed;
  @NotNull(message = "The Speed must not be null")
  @DoubleBetween(min = 0.01, max = 0.99, message = "The Speed must be between ${min} and ${max}")
  private Double speed;
  @NotNull(message = "The CrewSize must not be null")
  @Min(value = 1, message = "The CrewSize must be greater or equal then ${value}" )
  @Max(value = 9999, message = "The CrewSize must be less or equal then ${value}" )
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

  public Long getId() {
    return id;
  }

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
