package com.banquito.core.productsaccounts.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.banquito.core.productsaccounts.exception.CRUDException;
import com.banquito.core.productsaccounts.model.ProductAccount;
import com.banquito.core.productsaccounts.repository.ProductAccountRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
public class ProductAccountServiceTest {

    @InjectMocks
    private ProductAccountService productAccountService;

    @Mock
    private ProductAccountRepository productAccountRepository;

    @Test
    public void testListAllActives() {
        List<ProductAccount> productAccounts = new ArrayList<>();
        productAccounts.add(new ProductAccount());
        productAccounts.add(new ProductAccount());

        when(productAccountRepository.findByState("ACT")).thenReturn(productAccounts);

        List<ProductAccount> result = productAccountService.listAllActives();
        assertNotNull(result);
        assertEquals(productAccounts.size(), result.size());
    }

    @Test
    public void testObtainByIdExisting() {
        ProductAccount product = new ProductAccount();
        product.setId("1");

        when(productAccountRepository.findById("1")).thenReturn(Optional.of(product));


        ProductAccount result = productAccountService.obtainById("1");
        assertNotNull(result);
        assertEquals("1", result.getId());
    }

    @Test
    public void testObtainByIdNonExisting() {

        when(productAccountRepository.findById("999")).thenReturn(Optional.empty());

        assertThrows(CRUDException.class, () -> productAccountService.obtainById("999"));
    }


}