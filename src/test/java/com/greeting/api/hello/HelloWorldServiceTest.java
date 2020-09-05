package com.greeting.api.hello;


import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.greeting.api.core.exception.EntityValidationException;
import com.greeting.api.hello.domain.Greeting;
import com.greeting.api.hello.service.HelloWorldService;

import static com.greeting.api.constants.Constants.DEFAULT_ID;
import static com.greeting.api.constants.Constants.DEFAULT_MESSAGE;

import java.util.Optional;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HelloWorldServiceTest {
	
	private static final String HELLO_LUKE = "Hello Luke";
	private static final String EMPTY_MESSAGE = "";
	private static final String EMPTY_ID = "";

	@Autowired
	private HelloWorldService helloService;
	

	@Test
	public void getDefaultGreetingIsOK() {
		Optional<Greeting> greeting = helloService.getDefaultGreeting();
		
		Assert.assertTrue(greeting.isPresent());
		Assert.assertEquals(DEFAULT_ID, greeting.get().getId());
		Assert.assertEquals(DEFAULT_MESSAGE, greeting.get().getMessage());
	}
	
	@Test
	public void getGreetingOKWhenValidRequest() {
		Greeting request = new Greeting(HELLO_LUKE);

		Greeting created = helloService.createGreeting(request);
		Optional<Greeting> retreived = helloService.getGreeting(created.getId());

		Assert.assertEquals(created.getId(), retreived.get().getId());
		Assert.assertEquals(created.getMessage(), retreived.get().getMessage());
	}
	
	@Test
	public void getGreetingReturnEmptyWhenIdNotPresent() {
		Optional<Greeting> retreived = helloService.getGreeting(UUID.randomUUID().toString());

		Assert.assertFalse(retreived.isPresent());
	}
	
	@Test(expected = EntityValidationException.class)
	public void getGreetingsWithInvalidIdThrowsException() {
		final String invalidId = "InvalidID";
		helloService.getGreeting(invalidId);
	}
	
	@Test(expected = EntityValidationException.class)
	public void getGreetingWithEmptyIDThrowsException(){
		helloService.getGreeting("");
	}

	@Test(expected = EntityValidationException.class)
	public void getGreetingWithNullIDThrowsException() {
		helloService.getGreeting(null);
	}


	@Test(expected = EntityValidationException.class)
	public void createGreetingWithEmptyMessageThrowsException() {
		helloService.createGreeting(new Greeting(EMPTY_MESSAGE));
	}

	@Test(expected = EntityValidationException.class)
	public void createGreetingWithNullMessageThrowsException() {
		helloService.createGreeting(new Greeting(null));
	}

	@Test
	public void createGreetingOKWhenValidRequest() {
		Greeting request = new Greeting(HELLO_LUKE);

		Greeting created = createGreeting(request);
		Assert.assertEquals(HELLO_LUKE, created.getMessage());
	}
	
	@Test
	public void updateGreetingOKWhenValidRequest() {
		Greeting request = new Greeting(HELLO_LUKE);
		Greeting created = createGreeting(request);
		
		Assert.assertEquals(HELLO_LUKE, created.getMessage());
		
		created.setMessage("Updated Message");
		Optional<Greeting> updated = helloService.updateGreeting(created);
		
		Assert.assertTrue(updated.isPresent());
		Assert.assertEquals(created.getId(), updated.get().getId());
		Assert.assertEquals(created.getMessage(), updated.get().getMessage());
	}
	
	@Test
	public void updateGreetingReturnEmptyWhenIdNotFound() {
		Greeting greeting = new Greeting(UUID.randomUUID().toString(), HELLO_LUKE);
		
		Optional<Greeting> retreived = helloService.updateGreeting(greeting);
		
		Assert.assertFalse(retreived.isPresent());
	}
	
	@Test(expected = EntityValidationException.class)
	public void updateGreetingThrowsExceptionWhenIdInvalid() {
		Greeting request = new Greeting("InvalidId", HELLO_LUKE);
		
		helloService.updateGreeting(request);
	}
	
	@Test(expected = EntityValidationException.class)
	public void updateGreetingThrowsExceptionWhenEmptyId() {
		Greeting greeting = new Greeting(EMPTY_ID, EMPTY_MESSAGE);
		
		helloService.updateGreeting(greeting);
	}

	@Test(expected = EntityValidationException.class)
	public void updateGreetingThrowsExceptionWhenIdNull() {
		Greeting greeting = new Greeting(null,EMPTY_MESSAGE);
		
		helloService.updateGreeting(greeting);
	}

	@Test(expected = EntityValidationException.class)
	public void updateGreetingThrowsExceptionWhenEmptyMessage() {
		Greeting greeting = new Greeting(UUID.randomUUID().toString(), EMPTY_MESSAGE);
		
		helloService.updateGreeting(greeting);
	}

	@Test(expected = EntityValidationException.class)
	public void updateGreetingThrowsExceptionWhenMessageNull() {
		Greeting greeting = new Greeting(UUID.randomUUID().toString(), null);
		
		helloService.updateGreeting(greeting);
	}

	
	private Greeting createGreeting(Greeting greeting) {
		return helloService.createGreeting(greeting);
	}
}
