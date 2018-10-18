# user-registration

Provides user registration functionality via customizeable templates and URIs
Uses EmailService to send templated registration confirmation emails

## Settings

### Templates

#### bst.template.user.registration.signup
Default value: auth-signup

Form variables:
email
reReCaptchaResponse (if captcha is enabled (#bst.user.captchaDisable))

Served from: (#bst.uri.user.registration.signup)

#### bst.template.user.registration.signupConfirm
Default value: auth-signup-confirm

#### bst.template.user.registration.signupContinue
Default value: auth-signup-continue

#### bst.template.user.registration.signin
Default value: auth-signin

### URIs

#### bst.uri.user.registration.signupComplete
Default value: /auth/signup-complete

#### bst.uri.user.registration.signupContinue
Default value: /auth/signup-continue

#### bst.uri.user.registration.signupConfirm
Default value: /auth/signup-confirm

#### bst.uri.user.registration.signup
Default value: /auth/signup
Serves: (#bst.template.user.registration.signup)

### Email templates

#### bst.email.template.user.registration.signupConfirm
Default value: email/auth-signup-confirm

#### bst.email.from
Default value: automator@brainspeedtech.com

### Spring email settings

#### Example

spring.mail.host=127.0.0.1
spring.mail.port=3025
spring.mail.protocol=smtp
spring.mail.username=username
spring.mail.password=secret
spring.mail.properties.mail.transport.protocol=smtp
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
spring.mail.properties.mail.smtp.timeout=8000

### Other settings

#### bst.user.captchaDisable
Default value: false
Verify google captcha response

