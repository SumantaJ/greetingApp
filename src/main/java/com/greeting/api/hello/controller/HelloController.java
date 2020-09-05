package com.greeting.api.hello.controller;

import static com.greeting.api.constants.Constants.DEFAULT_ID;
import static com.greeting.api.constants.Constants.GREETING_NOT_FOUND;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.greeting.api.core.exception.ResourceNotFoundException;
import com.greeting.api.hello.domain.Greeting;
import com.greeting.api.hello.service.HelloWorldService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value = "Greetings Controller")
public class HelloController {

	private HelloWorldService helloWorldService;

	public HelloController(HelloWorldService helloWorldService) {
		this.helloWorldService = helloWorldService;
	}

	@GetMapping(value = "/hello")
	@ResponseBody
	@ApiOperation(value = "fetches the default greeting.", response = Greeting.class)
	public Greeting helloWorld() {
		return getGreeting(DEFAULT_ID);
	}

	@GetMapping(value = "/hello/{id}")
	@ResponseBody
	@ApiOperation(value = "fetches a greeting with a given id.", response = Greeting.class)
	public Greeting getGreeting(@PathVariable String id) {
		return helloWorldService.getGreeting(id).orElseThrow(() -> new ResourceNotFoundException(GREETING_NOT_FOUND));
	}

	@PostMapping(value = "/hello")
	@ResponseBody
	@ApiOperation(value = "Creates a greeting with a given message.", response = Greeting.class)
	public Greeting createGreeting(@RequestBody Greeting request) {
		return helloWorldService.createGreeting(request);
	}

	@PutMapping(value = "/hello")
	@ResponseBody
	@ApiOperation(value = "Updates an existing greeting with a given id and message.", response = Greeting.class)
	public Greeting updateGreeting(@RequestBody Greeting request) {
		return helloWorldService.updateGreeting(request)
				.orElseThrow(() -> new ResourceNotFoundException(GREETING_NOT_FOUND));
	}
}
