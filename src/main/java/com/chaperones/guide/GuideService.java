package com.chaperones.guide;

import com.chaperones.activity.Activity;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GuideService {
    private GuideDAO guideDAO;

    public GuideService(@Qualifier("guidePostgres") GuideDAO guideDAO) {
        this.guideDAO = guideDAO;
    }

    public void addGuide(Guide guide) {
        // check the guide does not already exist
        //if the guide does not exist we need to add the guide
    // get all guides and loop through them
        List<Guide> guidesList = guideDAO.getAll();
        for (Guide exist : guidesList) {
            //if a guide with the same phone number or email is already in the list of all the guides then the guide already exists. It could be a phone and (&&) email.
            if (exist.getPhoneNumber().equals(guide.getPhoneNumber()) || exist.getEmail().equalsIgnoreCase(guide.getEmail())) {
                throw new IllegalStateException("Guide already exists");
            }
        }
        //if they do not already exist add the guide
        int adding = guideDAO.add(guide);
        //if the method in the dao does not give a 1 which should be the number of lines affected by adding a guide then user should be alerted that the guide has not been added
        if (adding != 1) {
            throw new IllegalStateException("Guide could not be added");
        }

    }
    
    public List<Guide> allGuides(){
        //check list is not empty if it is alert user
        List<Guide> guides = guideDAO.getAll();
        if(guides == null){
            throw new IllegalStateException("There are no guides");
        }
        return guides;
    }
   public Guide guideById(Integer id){
       Guide selected = guideDAO.getById(id);
        if (selected == null) {
           throw new GuideDoesNotExistException("This guide does not exist");
        } else return selected;
   }
   public void updateGuide(Integer id, Guide guide){
       //check if the guide exists if they do not throw exception saying so
       if (guideDAO.getById(id) == null) {
           throw new GuideDoesNotExistException("This guide does not exist");
       }
       //call on the method to update a guide by their id in the dao,
       // passing through the arguments this method accepts which is
       // the id and the guide information that is to be changed
       int updated = guideDAO.updateById(id, guide);
       // check the confirmation that guide has been updated if not
       // inform user that this has not happened
       if(updated != 1){
           throw new IllegalStateException("Unable to update this guide");
       }
   }
   public void deleteGuide(Integer id){
       //check if the guide exists
       if (guideDAO.getById(id) == null) {
           throw new GuideDoesNotExistException("This guide does not exist");
       }

       int deleted = guideDAO.deleteById(id);

       if(deleted != 1){
           throw new IllegalStateException("Unable to delete this guide");
       }
   }
   //get all activities assigned to a guide
    public List<Activity> guidesActivities(Integer id, boolean cancelled){
        //check if the guide exists
        if (guideDAO.getById(id) == null) {
            throw new GuideDoesNotExistException("This guide does not exist");
        }
        return guideDAO.allActivities(id, cancelled);
    }
}
