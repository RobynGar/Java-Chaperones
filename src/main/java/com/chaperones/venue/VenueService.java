package com.chaperones.venue;

import com.chaperones.activity.Activity;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VenueService {

    private VenueDAO venueDAO;

    public VenueService(@Qualifier("venuePostgres") VenueDAO venueDAO) {
        this.venueDAO = venueDAO;
    }

    public void addNewVenue(Venue venue) {
        List<Venue> allVenues = venueDAO.getAll();
        for (Venue v : allVenues) {
            if (venue.getName().equalsIgnoreCase(v.getName())) {
                throw new IllegalStateException("Venue already exists");
            }
        }
        int added = venueDAO.add(venue);

        if (added != 1) {
            throw new IllegalStateException("Venue could not be added");
        }
    }

    public List<Venue> getAllVenues() {
        return venueDAO.getAll();
    }

    public List<Activity> getActivitiesAtVenue(Integer id, boolean cancelled) {
        if (venueDAO.getById(id) == null) {
            throw new VenueNotFoundException("Venue with id " + id + " could not be found");
        }

        return venueDAO.getActivities(id, cancelled);
    }

    public Venue getVenueById(Integer id) {
        Venue selected = venueDAO.getById(id);
        if (selected == null){
            throw new VenueNotFoundException("Venue with id " + id + " could not be found");
        } else return selected;
    }

    public void updateVenueById(Integer id, Venue venue) {
        if (venueDAO.getById(id) == null) {
            throw new VenueNotFoundException("Venue with id " + id + " could not be found");
        }

        int result = venueDAO.updateById(id, venue);

        if (result != 1) {
            throw new IllegalStateException("Venue with id " + id + " could not be updated");
        }
    }

    public void deleteVenueById(Integer id) {
        if (venueDAO.getById(id) == null) {
            throw new VenueNotFoundException("Venue with id " + id + " could not be found");
        }

        int result = venueDAO.deleteById(id);

        if (result != 1) {
            throw new IllegalStateException("Venue with id " + id + " could not be deleted");
        }
    }
}
