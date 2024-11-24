package com.enigmacamp.mastermenu.serviceTests;

import com.enigmacamp.mastermenu.model.entity.TransactionDetail;
import com.enigmacamp.mastermenu.repository.TransactionDetailRepository;
import com.enigmacamp.mastermenu.service.impl.TransactionDetailImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionDetailImplTest {

    @Mock
    private TransactionDetailRepository transactionDetailRepository;

    @InjectMocks
    private TransactionDetailImpl transactionDetailService;

    @Test
    void getTransactionDetailById_ShouldReturnTransactionDetail_WhenIdExists() {
        // Arrange
        String id = "123";
        TransactionDetail transactionDetail = new TransactionDetail();
        transactionDetail.setId(id);

        when(transactionDetailRepository.getTransactionDetailById(id)).thenReturn(transactionDetail);

        // Act
        TransactionDetail result = transactionDetailService.getTransactionDetailById(id);

        // Assert
        assertNotNull(result);
        assertEquals(id, result.getId());
        verify(transactionDetailRepository, times(1)).getTransactionDetailById(id);
    }

    @Test
    void getTransactionDetailById_ShouldThrowException_WhenIdDoesNotExist() {
        // Arrange
        String id = "123";
        when(transactionDetailRepository.getTransactionDetailById(id)).thenReturn(null);

        // Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            transactionDetailService.getTransactionDetailById(id);
        });
        assertEquals("Transaction Detail Not Found", exception.getMessage());
        verify(transactionDetailRepository, times(1)).getTransactionDetailById(id);
    }

    @Test
    void saveTransactionDetail_ShouldSaveAndReturnTransactionDetail() {
        // Arrange
        TransactionDetail transactionDetail = new TransactionDetail();
        transactionDetail.setId("123");

        when(transactionDetailRepository.save(transactionDetail)).thenReturn(transactionDetail);

        // Act
        TransactionDetail result = transactionDetailService.saveTransactionDetail(transactionDetail);

        // Assert
        assertNotNull(result);
        assertEquals(transactionDetail.getId(), result.getId());
        verify(transactionDetailRepository, times(1)).save(transactionDetail);
    }

    @Test
    void updateTransactionDetail_ShouldUpdateAndReturnTransactionDetail_WhenIdExists() {
        // Arrange
        TransactionDetail transactionDetail = new TransactionDetail();
        transactionDetail.setId("123");

        when(transactionDetailRepository.existsById(transactionDetail.getId())).thenReturn(true);
        when(transactionDetailRepository.save(transactionDetail)).thenReturn(transactionDetail);

        // Act
        TransactionDetail result = transactionDetailService.updateTransactionDetail(transactionDetail);

        // Assert
        assertNotNull(result);
        assertEquals(transactionDetail.getId(), result.getId());
        verify(transactionDetailRepository, times(1)).existsById(transactionDetail.getId());
        verify(transactionDetailRepository, times(1)).save(transactionDetail);
    }

    @Test
    void updateTransactionDetail_ShouldThrowException_WhenIdDoesNotExist() {
        // Arrange
        TransactionDetail transactionDetail = new TransactionDetail();
        transactionDetail.setId("123");

        when(transactionDetailRepository.existsById(transactionDetail.getId())).thenReturn(false);

        // Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            transactionDetailService.updateTransactionDetail(transactionDetail);
        });
        assertEquals("Transaction Detail Not Found", exception.getMessage());
        verify(transactionDetailRepository, times(1)).existsById(transactionDetail.getId());
        verify(transactionDetailRepository, never()).save(transactionDetail);
    }

    @Test
    void deleteTransactionDetail_ShouldDeleteTransactionDetail_WhenIdExists() {
        // Arrange
        String id = "123";

        when(transactionDetailRepository.existsById(id)).thenReturn(true);
        doNothing().when(transactionDetailRepository).deleteById(id);

        // Act
        transactionDetailService.deleteTransactionDetail(id);

        // Assert
        verify(transactionDetailRepository, times(1)).existsById(id);
        verify(transactionDetailRepository, times(1)).deleteById(id);
    }

    @Test
    void deleteTransactionDetail_ShouldThrowException_WhenIdDoesNotExist() {
        // Arrange
        String id = "123";

        when(transactionDetailRepository.existsById(id)).thenReturn(false);

        // Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            transactionDetailService.deleteTransactionDetail(id);
        });
        assertEquals("Transaction Detail Not Found", exception.getMessage());
        verify(transactionDetailRepository, times(1)).existsById(id);
        verify(transactionDetailRepository, never()).deleteById(id);
    }
}

