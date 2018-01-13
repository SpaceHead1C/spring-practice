package ru.nd.web;

import com.google.common.collect.ImmutableList;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ru.nd.model.StackoverflowWebsite;
import ru.nd.service.StackoverflowService;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class) // for creating mocks
public class StackoverflowControllerTest {
    @Mock // it's @Autowired in StackoverflowController
    private StackoverflowService stackoverflowService;

    @InjectMocks
    StackoverflowController sut;

    // Don't need because Mock created already
//    @Before
//    public void setUp() throws Exception {
//        sut = new StackoverflowController();
//    }

    // Unit test
    @Test
    public void getListOfProviders() throws Exception {
        //prepare
        // example how to use a Mock
        when(stackoverflowService.findAll()).thenReturn(ImmutableList.of());
        //testing
        List<StackoverflowWebsite> listOfProviders = sut.getListOfProviders();
        //validate
        verify(stackoverflowService).findAll();
    }
}