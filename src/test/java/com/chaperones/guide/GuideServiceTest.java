package com.chaperones.guide;

import com.chaperones.activity.Activity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GuideServiceTest {
    //    @InjectMocks
//    GuideService underTest;
//
//    @Mock
//    GuideDAO mockdao;
//
//    @BeforeEach
//    public void init() {
//        MockitoAnnotations.openMocks(underTest);
//
//    }
    private GuideService underTest;
    private GuideDAO mockDAO;

    @BeforeEach
    void setUp() {
        mockDAO = Mockito.mock(GuideDAO.class);
        underTest = new GuideService(mockDAO);
    }

    @Test
    void addGuide() {
        // Given
        Guide testGuide = new Guide(1, "blah", "01424 346816", "blah@gmail.com");
        List<Guide> testList = new ArrayList<>(Arrays.asList());

        // When
        when(mockDAO.getAll()).thenReturn(testList);
        when(mockDAO.add(any())).thenReturn(1);
        underTest.addGuide(testGuide);
        // Then
        verify(mockDAO, times(1)).add(testGuide);
    }

    @Test
    void addGuideWhenPhoneNumberAndEmailIsSameAsExistingGuides() {
        // Given
        List<Guide> testList = new ArrayList<>();
        Guide testGuide = new Guide(1, "blah", "01424 346816", "blah@gmail.com");
        testList.add(testGuide);
        Guide addedGuide = new Guide(2, "la", "01424 346816", "blah@gmail.com");

        // When
        //doThrow(IllegalStateException.class).when(mockDAO).add(addedGuide);
        //when(mockDAO.add(addedGuide)).thenThrow(IllegalArgumentException.class);
        when(mockDAO.getAll()).thenReturn(testList);
        when(mockDAO.add(any())).thenReturn(1);
        IllegalStateException ex = assertThrows(IllegalStateException.class, () -> {
            underTest.addGuide(addedGuide);
        });
        // Then
        assertEquals("Guide already exists", ex.getMessage());
        verify(mockDAO, never()).add(addedGuide);

    }

    @Test
    void returnAllGuides() {
        // Given
        Guide testGuide = new Guide(1, "blah", "01424 346816", "blah@gmail.com");
        Guide testGuide1 = new Guide(2, "la", "01424 346889", "la@gmail.com");
        List<Guide> testList = new ArrayList<>(Arrays.asList(testGuide, testGuide1));

        // When
        when(mockDAO.getAll()).thenReturn(testList);
        underTest.allGuides();
        // Then
        verify(mockDAO, times(1)).getAll();

    }

    @Test
    void returnAllGuidesWhenNull() {
        // Given

        List<Guide> testList = new ArrayList<>(Arrays.asList());

        // When
        when(mockDAO.getAll()).thenReturn(testList);
        underTest.allGuides();
        // Then
        verify(mockDAO, times(1)).getAll();

    }

    @Test
    void getGuideById() {

        // Given
        Guide expected = new Guide(1, "blah", "01424 346816", "blah@gmail.com");

        // When
        when(mockDAO.getById(1)).thenReturn(expected);
        Guide actual = underTest.guideById(1);


        // Then
        verify(mockDAO, times(1)).getById(1);
        assertThat(actual).isEqualTo(expected);

        //OR

        //Given
//        Guide testGuide = new Guide(1, "blah", "01424 346816", "blah@gmail.com");
//        Guide testGuide1 = new Guide(2, "la", "01424 346889", "la@gmail.com");
//        List<Guide> testList = new ArrayList<>(Arrays.asList(testGuide, testGuide1));

        // When
//        when(mockDAO.getAll()).thenReturn(testList);
//        when(mockDAO.getById(anyInt())).thenReturn(testGuide);
//        underTest.guideById(1);

        // Then
//        verify(mockDAO,times(1)).getById(1);
    }

    @Test
    void getGuideByIdWhenIdDoesNotExist() {

        // Given
        Guide testGuide = new Guide(1, "blah", "01424 346816", "blah@gmail.com");
//        List<Guide> testList = new ArrayList<>(Arrays.asList(testGuide));
        // When
        when(mockDAO.getById(2)).thenReturn(null);
        GuideDoesNotExistException ex = assertThrows(GuideDoesNotExistException.class, () -> {
            underTest.guideById(2);
        });
        // Then
        assertEquals("This guide does not exist", ex.getMessage());
        verify(mockDAO, times(1)).getById(2);

    }

    @Test
    void updateGuideById() {
        // Given
        Guide testGuide = new Guide(1, "blah", "01424 346816", "blah@gmail.com");
        Guide changedTestGuide = new Guide(1, "blah", "01424 346889", "blah@gmail.com");
        List<Guide> testList = new ArrayList<>(Arrays.asList(testGuide));

        // When
        when(mockDAO.getById(1)).thenReturn(testGuide);
        when(mockDAO.updateById(1, changedTestGuide)).thenReturn(1);
        underTest.updateGuide(1, changedTestGuide);
        // Then
        verify(mockDAO, times(1)).updateById(1, changedTestGuide);
    }

    @Test
    void updateGuideByIdWhenIdDoesNotExist() {
        // Given
        Guide testGuide = new Guide(1, "blah", "01424 346816", "blah@gmail.com");
        Guide changedTestGuide = new Guide(3, "blah", "01424 346889", "blah@gmail.com");
        List<Guide> testList = new ArrayList<>(Arrays.asList(testGuide));

        // When
        when(mockDAO.getById(3)).thenReturn(null);
        when(mockDAO.updateById(3, changedTestGuide)).thenReturn(0);
        GuideDoesNotExistException ex = assertThrows(GuideDoesNotExistException.class, () -> {
            underTest.updateGuide(3, changedTestGuide);
        });

        // Then
        assertEquals("This guide does not exist", ex.getMessage());
        verify(mockDAO, times(1)).getById(3);
        verify(mockDAO, never()).updateById(3, changedTestGuide);

    }

    @Test
    void updateGuideByIdWhenDAODoesNotReturn1() {
        // Given
        Guide testGuide = new Guide(1, "blah", "01424 346816", "blah@gmail.com");
        Guide changedTestGuide = new Guide(1, "blah", "01424 346889", "blah@gmail.com");
        List<Guide> testList = new ArrayList<>(Arrays.asList(testGuide));

        // When
        when(mockDAO.getById(1)).thenReturn(testGuide);
        when(mockDAO.updateById(1, changedTestGuide)).thenReturn(0);
        IllegalStateException ex = assertThrows(IllegalStateException.class, () -> {
            underTest.updateGuide(1, changedTestGuide);
        });

        // Then
        assertEquals("Unable to update this guide", ex.getMessage());
        verify(mockDAO, times(1)).getById(1);
        verify(mockDAO, times(1)).updateById(1, changedTestGuide);

    }

    @Test
    void deleteGuide() {
        // Given
        Guide testGuide = new Guide(1, "blah", "01424 346816", "blah@gmail.com");
        Guide testGuide1 = new Guide(2, "la", "01424 346889", "la@gmail.com");
        List<Guide> testList = new ArrayList<>(Arrays.asList(testGuide, testGuide1));
        // When
        when(mockDAO.getAll()).thenReturn(testList);
        when(mockDAO.getById(1)).thenReturn(testGuide);
        when(mockDAO.deleteById(1)).thenReturn(1);
        underTest.deleteGuide(1);


        // Then
        verify(mockDAO, times(1)).getById(1);
        verify(mockDAO, times(1)).deleteById(1);

    }

    @Test
    void deleteGuideWhenDAOSideDoesNotReturn1() {
        // Given
        Guide testGuide = new Guide(1, "blah", "01424 346816", "blah@gmail.com");
        Guide testGuide1 = new Guide(2, "la", "01424 346889", "la@gmail.com");
        List<Guide> testList = new ArrayList<>(Arrays.asList(testGuide, testGuide1));
        // When
        when(mockDAO.getAll()).thenReturn(testList);
        when(mockDAO.getById(1)).thenReturn(testGuide);
        when(mockDAO.deleteById(1)).thenReturn(0);
        IllegalStateException ex = assertThrows(IllegalStateException.class, () -> {
            underTest.deleteGuide(1);
        });

        // Then
        assertEquals("Unable to delete this guide", ex.getMessage());
        verify(mockDAO, times(1)).deleteById(1);
    }

    @Test
    void getGuidesActivitiesThatAreActive() {
//        // Given
//        Guide testGuide = new Guide(1, "blah", "01424 346816", "blah@gmail.com");
//        List<Guide> testList = new ArrayList<>(Arrays.asList(testGuide));
//        List<Activity> testActivities = new ArrayList<>();
//        Activity activity = new Activity(1,1,2,"Kew Gardens","test", LocalDate.of(2022,03,12), LocalTime.of(13,0,00),"1hr",40.00, 20, false);
//        testActivities.add(activity);
//        // When
//        when(mockDAO.getAll()).thenReturn(testList);
//        when(mockDAO.getById(1)).thenReturn(testGuide);
//        when(mockDAO.allActivities(1, false)).thenReturn(testActivities);
//        List<Activity> actual = underTest.guidesActivities(1, false);
//        // Then
//        verify(mockDAO, times(1)).allActivities(1, false);
//        assertThat(actual).isEqualTo(testActivities);


        // Given
        Guide testGuide = new Guide(1, "blah", "01424 346816", "blah@gmail.com");
        Activity activity = new Activity(1, 1, 2, "Kew Gardens", "test", LocalDate.of(2022, 03, 12), LocalTime.of(13, 0, 00), "1hr", 40.00, 20, false);
        Activity activity1 = new Activity(2, 1, 2, "test gardens", "test", LocalDate.of(2022, 03, 12), LocalTime.of(13, 0, 00), "1hr", 40.00, 20, true);
        List<Activity> testActivities = new ArrayList<>(Arrays.asList(activity, activity1));
        List<Activity> selected = new ArrayList<>();

        Integer id = 1;
        boolean cancelled = false;

        //when
        when(mockDAO.getById(id)).thenReturn(testGuide);
        for (Activity activ : testActivities) {
            if (Objects.equals(activ.getGuide_id(), id) && activ.getCancelled() == cancelled) {
                selected.add(activ);
            }
        }
        when(mockDAO.allActivities(1, false)).thenReturn(selected);
        List<Activity> actual = underTest.guidesActivities(id, cancelled);

        // Then
        List<Activity> expected = new ArrayList<>(List.of(activity));
        assertThat(actual).isEqualTo(expected);

    }
}