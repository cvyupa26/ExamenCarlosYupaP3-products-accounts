package com.banquito.core.branches.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.Optional;
import java.util.ArrayList;
import java.util.List;

import com.banquito.core.branches.exception.CRUDException;
import com.banquito.core.branches.model.Branch;
import com.banquito.core.branches.repository.BranchRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

@ExtendWith(MockitoExtension.class)
@DataMongoTest
public class BranchServiceTest {

    @InjectMocks
    private BranchService branchService;

    @Mock
    private BranchRepository branchRepository;

    @Test
    public void testLookByIdExistingBranch() throws CRUDException {
        String branchId = "branchId1";
        Branch branch = new Branch();
        branch.setId(branchId);
        branch.setCode("branchCode");
        branch.setName("branchName");

        when(branchRepository.findById(branchId)).thenReturn(Optional.of(branch));

        Branch result = branchService.lookById(branchId);

        assertEquals(branchId, result.getId());
        assertEquals("branchCode", result.getCode());
        assertEquals("branchName", result.getName());
    }

    @Test
    public void testLookByCodeExistingBranch() {
        String branchCode = "branchCode";
        Branch branch = new Branch();
        branch.setId("branchId1");
        branch.setCode(branchCode);
        branch.setName("branch1");

        when(branchRepository.findByCode(branchCode)).thenReturn(branch);

        Branch result = branchService.lookByCode(branchCode);

        assertEquals(branchCode, result.getCode());
        assertEquals("branchName", result.getName());
    }

    @Test
    public void testGetAllBranches() {
        List<Branch> branches = new ArrayList<>();
        branches.add(new Branch());
        branches.add(new Branch());
        when(branchRepository.findAll()).thenReturn(branches);
        List<Branch> result = branchService.getAll();
        assertEquals(2, result.size());
    }

    @Test
    public void testCreateBranch() throws CRUDException {
        Branch branch = new Branch();
        branch.setCode("Branch1");
        branch.setName("Branch2");
        branchService.create(branch);
        verify(branchRepository, times(1)).save(branch);
    }

    @Test
    public void testUpdateExistingBranch() throws CRUDException {
        String branchCode = "existingBranchCode";
        Branch existingBranch = new Branch();
        existingBranch.setId("branchId1");
        existingBranch.setCode(branchCode);
        existingBranch.setName("existingBranchName");

        Branch updatedBranch = new Branch();
        updatedBranch.setCode(branchCode);
        updatedBranch.setName("updatedBranchName");

        when(branchRepository.findByCode(branchCode)).thenReturn(existingBranch);

        branchService.update(branchCode, updatedBranch);

        verify(branchRepository, times(1)).save(existingBranch);
        assertEquals("updatedBranchName", existingBranch.getName());
    }
}