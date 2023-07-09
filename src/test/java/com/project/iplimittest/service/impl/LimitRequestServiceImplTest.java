package com.project.iplimittest.service.impl;

import com.project.iplimittest.configuration.LimitConfiguration;
import com.project.iplimittest.exception.LimitExceededException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class LimitRequestServiceImplTest {

    @InjectMocks
    private LimitRequestServiceImpl limitRequestService;

    @Mock
    private LimitConfiguration mockLimitConfiguration;
    
    @Autowired
    private LimitConfiguration limitConfiguration;

    @Test
    public void testCheckLimitForRequestsWithException() {
        when(mockLimitConfiguration.getExecutingTime()).thenReturn(limitConfiguration.getExecutingTime());
        when(mockLimitConfiguration.getNumberOfRequests()).thenReturn(limitConfiguration.getExecutingTime());

        for (int i = 0; i < limitConfiguration.getExecutingTime(); i++) {
            limitRequestService.checkLimitForRequests();
        }

        Assertions.assertThrows(LimitExceededException.class,
                () -> limitRequestService.checkLimitForRequests());
    }

}