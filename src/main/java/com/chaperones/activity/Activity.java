package com.chaperones.activity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class Activity {
    private Integer id;
    private Integer guide_id;
    private Integer venue_id;
    private String name;
    private String description;
    private LocalDate date;
    private LocalTime time;
    private String duration;
    private Double price;
    private Integer capacity;
    private Boolean cancelled;

// this is a constructor
    public Activity(Integer id, Integer guide_id, Integer venue_id, String name, String description, LocalDate date, LocalTime time, String duration, Double price, Integer capacity, Boolean cancelled) {
        this.id = id;
        this.guide_id = guide_id;
        this.venue_id = venue_id;
        this.name = name;
        this.description = description;
        this.date = date;
        this.time = time;
        this.duration = duration;
        this.price = price;
        this.capacity = capacity;
        this.cancelled = cancelled;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGuide_id() {
        return guide_id;
    }

    public void setGuide_id(Integer guide_id) {
        this.guide_id = guide_id;
    }

    public Integer getVenue_id() {
        return venue_id;
    }

    public void setVenue_id(Integer venue_id) {
        this.venue_id = venue_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Boolean getCancelled() {
        return cancelled;
    }

    public void setCancelled(Boolean cancelled) {
        this.cancelled = cancelled;
    }


    @Override
    public String toString() {
        return "Activity{" +
                "id=" + id +
                ", guide_id=" + guide_id +
                ", venue_id=" + venue_id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", duration='" + duration + '\'' +
                ", price=" + price +
                ", capacity=" + capacity +
                ", cancelled=" + cancelled +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Activity activity = (Activity) o;
        return Objects.equals(id, activity.id) && Objects.equals(guide_id, activity.guide_id) && Objects.equals(venue_id, activity.venue_id) && Objects.equals(name, activity.name) && Objects.equals(description, activity.description) && Objects.equals(date, activity.date) && Objects.equals(time, activity.time) && Objects.equals(duration, activity.duration) && Objects.equals(price, activity.price) && Objects.equals(capacity, activity.capacity) && Objects.equals(cancelled, activity.cancelled);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, guide_id, venue_id, name, description, date, time, duration, price, capacity, cancelled);
    }
}
