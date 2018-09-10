package com.bst.user.registration.components.test;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.bst.user.registration.components.RegistrationController;
import com.bst.user.registration.components.RegistrationService;

@RunWith(SpringJUnit4ClassRunner.class)
@PropertySource("classpath:registration-controller-test.properties")
@SpringBootTest(classes = RegistrationController.class)
public class RegistrationControllerTest {

	@MockBean
	private RegistrationService registrationService;
	
	@Autowired
	private RegistrationController registrationController;

    @Test
    public void testMockCreation(){
        assertNotNull(registrationService);
        assertNotNull(registrationController);
    }
}
