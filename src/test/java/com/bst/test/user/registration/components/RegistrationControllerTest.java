package com.bst.test.user.registration.components;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestExecutionListeners.MergeMode;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.bst.configuration.user.registration.UserRegistrationConfiguration;
import com.bst.configuration.utility.UtilityConfiguration;
import com.bst.user.authentication.components.UserService;
import com.bst.user.registration.entities.RegistrationToken;
import com.bst.utility.services.EmailService;
import com.bst.utility.testlib.SeleniumTest;
import com.bst.utility.testlib.SeleniumTestExecutionListener;
import com.bst.utility.testlib.SnapshotListener;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { UserRegistrationConfiguration.class, SecurityDisabler.class, ServletInitializer.class,
		UtilityConfiguration.class })
@SeleniumTest(driver = ChromeDriver.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestExecutionListeners(listeners = { SnapshotListener.class,
		SeleniumTestExecutionListener.class }, mergeMode = MergeMode.MERGE_WITH_DEFAULTS)
@TestPropertySource("classpath:registration-controller-test.properties")
public class RegistrationControllerTest {

	@MockBean
	private UserService userServiceMock;

	@Autowired
	private WebDriver driver;

	@MockBean(name = "emailService")
	private EmailService emailServiceMock;

	@LocalServerPort
	private int port;

	@Autowired
	private WebApplicationContext context;

	@Test
	public void registerAndSendEmail() throws Exception {
		this.driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

		this.driver.get(this.url("/signup"));
		this.driver.findElement(By.id("user-registration-email")).sendKeys("test@mail.com");
		this.driver.findElement(By.id("signup-button")).click();

		final ArgumentCaptor<Object> dtoObject = ArgumentCaptor.forClass(Object.class);
		final ArgumentCaptor<String> templateName = ArgumentCaptor.forClass(String.class);
		Mockito.verify(this.emailServiceMock, Mockito.timeout(500).times(1)).sendMessage(ArgumentMatchers.any(),
				templateName.capture(), ArgumentMatchers.any(), ArgumentMatchers.any(), ArgumentMatchers.any(),
				dtoObject.capture(), ArgumentMatchers.any());

		SnapshotListener.expect(templateName.getValue()).toMatchSnapshot();

		final RegistrationToken token = (RegistrationToken) dtoObject.getValue();
		final String registrationLink = this
				.url("/auth/signup-continue?email=" + token.getEmail() + "&hash=" + token.getHash());
		this.driver.get(registrationLink);

		this.driver.findElement(By.id("auth-continue-password")).sendKeys("password");
		this.driver.findElement(By.id("auth-continue-confirm-password")).sendKeys("password");
		this.driver.findElement(By.id("auth-continue-name")).sendKeys("John Doe");
		this.driver.findElement(By.id("auth-complete-button")).click();

		this.driver.findElement(By.id("auth-signin"));
	}

	@BeforeEach
	public void setup() {
		MockMvcBuilders.webAppContextSetup(this.context).build();
	}

	public String url(final String path) {
		return "http://localhost:" + this.port + path;
	}

}
