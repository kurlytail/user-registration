package com.bst.user.registration.components.test;

import static com.bst.utility.testlib.SnapshotListener.expect;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
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
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.bst.configuration.user.registration.UserRegistrationConfiguration;
import com.bst.configuration.utility.UtilityConfiguration;
import com.bst.user.authentication.components.UserService;
import com.bst.user.registration.entities.RegistrationToken;
import com.bst.utility.components.EmailService;
import com.bst.utility.testlib.SeleniumTest;
import com.bst.utility.testlib.SeleniumTestExecutionListener;
import com.bst.utility.testlib.SnapshotListener;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { UserRegistrationConfiguration.class, SecurityDisabler.class,
		ServletInitializer.class, UtilityConfiguration.class })
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

	@BeforeEach
	public void setup() {
		MockMvcBuilders.webAppContextSetup(context).build();
	}

	public String url(String path) {
		return "http://localhost:" + port + path;
	}

	@Test
	public void registerAndSendEmail() throws Exception {
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

		driver.get(url("/signup"));
		driver.findElement(By.id("user-registration-email")).sendKeys("test@mail.com");
		driver.findElement(By.id("signup-button")).click();

		ArgumentCaptor<Object> dtoObject = ArgumentCaptor.forClass(Object.class);
		ArgumentCaptor<String> templateName = ArgumentCaptor.forClass(String.class);
		verify(emailServiceMock, timeout(500).times(1)).sendMessage(any(), templateName.capture(), any(), any(), any(),
				dtoObject.capture(), any());

		expect(templateName.getValue()).toMatchSnapshot();

		RegistrationToken token = (RegistrationToken) dtoObject.getValue();
		String registrationLink = url("/auth/signup-continue?email=" + token.getEmail()
				+ "&hash=" + token.getHash());
		driver.get(registrationLink);

		driver.findElement(By.id("auth-continue-password")).sendKeys("password");
		driver.findElement(By.id("auth-continue-confirm-password")).sendKeys("password");
		driver.findElement(By.id("auth-continue-name")).sendKeys("John Doe");
		driver.findElement(By.id("auth-complete-button")).click();

		driver.findElement(By.id("auth-signin"));
	}

}
