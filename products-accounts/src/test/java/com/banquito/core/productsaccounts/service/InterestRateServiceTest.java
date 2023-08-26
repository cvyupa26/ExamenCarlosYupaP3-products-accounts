package com.banquito.core.productsaccounts.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.banquito.core.productsaccounts.model.InterestRate;
import com.banquito.core.productsaccounts.repository.InterestRateRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import java.math.BigDecimal;
import java.util.*;

@SpringBootTest
public class InterestRateServiceTest {

    @InjectMocks
    private InterestRateService interestRateService;

    @Mock
    private InterestRateRepository interestRateRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testListAllActives() {
        List<InterestRate> activeRates = new ArrayList<>();


        when(interestRateRepository.findByState("ACT")).thenReturn(activeRates);

        List<InterestRate> result = interestRateService.listAllActives();

        assertEquals(activeRates.size(), result.size());

        verify(interestRateRepository, times(1)).findByState("ACT");
    }

    @Test
    public void testObtainById() {
        InterestRate interestRate = new InterestRate();
        interestRate.setId(1);
        interestRate.setName("Test Rate");
        interestRate.setInterestRate(new BigDecimal("0.05"));
        interestRate.setState("ACTIVE");

        when(interestRateRepository.findById(1)).thenReturn(Optional.of(interestRate));

        InterestRate result = interestRateService.obtainById(1);

        assertEquals(interestRate.getName(), result.getName());
        assertEquals(interestRate.getInterestRate(), result.getInterestRate());
    }

    @Test
    public void testCreate() {
        InterestRate newInterestRate = new InterestRate();
        newInterestRate.setName("New Rate");
        newInterestRate.setInterestRate(new BigDecimal("0.07"));
        newInterestRate.setState("ACTIVE");
        newInterestRate.setStart(new Date());

        assertDoesNotThrow(() -> interestRateService.create(newInterestRate));

        verify(interestRateRepository, times(1)).save(newInterestRate);
    }

    @Test
    public void testUpdate() {
        InterestRate existingRate = new InterestRate();
        existingRate.setId(1);
        existingRate.setName("Old Rate");
        existingRate.setInterestRate(new BigDecimal("0.05"));
        existingRate.setState("ACTIVE");
        existingRate.setStart(new Date());

        InterestRate updatedRate = new InterestRate();
        updatedRate.setName("Updated Rate");
        updatedRate.setInterestRate(new BigDecimal("0.08"));
        updatedRate.setState("ACTIVE");
        updatedRate.setStart(new Date());

        when(interestRateRepository.findById(1)).thenReturn(Optional.of(existingRate));

        assertDoesNotThrow(() -> interestRateService.update(1, updatedRate));

        verify(interestRateRepository, times(1)).save(existingRate);
    }

    @Test
    public void testInactivate() {
        InterestRate existingRate = new InterestRate();
        existingRate.setId(1);
        existingRate.setName("Rate to Inactivate");
        existingRate.setInterestRate(new BigDecimal("0.06"));
        existingRate.setState("ACTIVE");
        existingRate.setStart(new Date());

        when(interestRateRepository.findById(1)).thenReturn(Optional.of(existingRate));

        assertDoesNotThrow(() -> interestRateService.inactivate(1));

        verify(interestRateRepository, times(1)).save(existingRate);
    }
}
