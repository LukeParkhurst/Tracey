package edu.northeastern.tracey.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * PointOfInterest Class: Creates Point of Interest objects with the required fields (below).
 * **Also can add or remove PointsOfInterest from the database.
 */
@Document
public class Location {

  @Id
  private int id;

  @Field
  private String name;
  @Field
  private float lat;
  @Field
  private float lon;


  public Location(String name, int id, float lat, float lon) {
    this.name = name;
    this.id = id;
    this.lat = lat;
    this.lon = lon;
  }

  /**
   * 2nd Constructor: ** currently undefined
   */
  public Location() {

  }

  /**
   * Getters & Setters For user to get and edit the fields below.
   */
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public float getLat() {
    return lat;
  }

  public void setLat(float latitude) {
    this.lat = latitude;
  }

  public float getLon() {
    return lon;
  }

  public void setLon(float longitude) {
    this.lon = longitude;
  }

  /**
   * addPointOFInterest Description: creates a new PointOfInterest / "place" object and adds it to
   * the database. ** if the place doesn't already exist in the DB. **
   */
  public void addLocation(String name, float locationID, float latitude, float longitude) {

  }

  /**
   * addPointOFInterest Description: searches the database for the given place name and ID. If
   * found, it gets removed from the DB.
   */
  public void removeLocation(String name, String locationID) {

  }

  /**
   * toString Description: String format of the details of a Point of Interest.
   */
  @Override
  public String toString() {
    return "Location{" +
            "locationID='%s'" + id + '\'' +
            ", name='" + name + '\'' +
            ", latitude=" + lat +
            ", longitude=" + lon +
            '}';
  }

}