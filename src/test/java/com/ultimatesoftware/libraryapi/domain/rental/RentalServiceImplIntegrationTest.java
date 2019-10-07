package com.ultimatesoftware.libraryapi.domain.rental;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;

@RunWith(SpringRunner.class)
public class RentalServiceImplIntegrationTest {

    @TestConfiguration
    static class RentalServiceImplTestContextConfiguration {
        @Bean
        public RentalService rentalService() {
            return new RentalServiceImpl();
        }
    }

    @Autowired
    private RentalService rentalService;

    @MockBean
    private RentalRepository rentalRepository;

    @Before
    public void setup() {
        Rental rental = new Rental(null, null, LocalDate.now());
        Mockito.when(rentalRepository.getOne(1l)).thenReturn(rental);

        Mockito.when(rentalRepository.findAll()).thenReturn(Arrays.asList(rental));
    }

    @Test
    public void getRentalById() {
        Rental rental = rentalService.getRentalById(1l);
        Assert.assertTrue(rental != null);

    }

    @Test
    public void getAllRentals() {
        Collection<Rental> rentals = rentalService.getAllRentals();
        Assert.assertTrue(rentals.size() == 1);
    }

}
