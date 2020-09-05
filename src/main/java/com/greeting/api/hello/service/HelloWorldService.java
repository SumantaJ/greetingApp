package com.greeting.api.hello.service;

import org.springframework.stereotype.Service;

import com.greeting.api.core.validation.EntityValidator;
import com.greeting.api.hello.domain.Greeting;

import static com.greeting.api.constants.Constants.DEFAULT_ID;
import static com.greeting.api.constants.Constants.DEFAULT_MESSAGE;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class HelloWorldService {

	private Map<String, Greeting> greetings;

	private EntityValidator entityValidator;

	public HelloWorldService(EntityValidator entityValidator) {
		this.entityValidator = entityValidator;

		this.greetings = new HashMap<>(1);
		save(getDefault());
	}

	/**
	 * Returns the default Greeting
	 * 
	 * @return new Greeting object with the default values
	 */
	private static Greeting getDefault() {
		return new Greeting(DEFAULT_ID, DEFAULT_MESSAGE);
	}

	/**
	 * Creates a new greeting with a new message
	 * 
	 * @param request Greeting object
	 * @return Optional with new greeting if the creation was successful or empty otherwise
	 */
	public Greeting createGreeting(Greeting request) {
		entityValidator.validateUpdate(request);

		request.setId(UUID.randomUUID().toString());
		return save(request);
	}

	/**
	 * Obtains a greeting with a given Id
	 * 
	 * @param id unique identifier of an Id
	 * @return Optional with the found Greeting or empty otherwise
	 */
	public Optional<Greeting> getGreeting(String id) {
		entityValidator.validateId(id, Greeting.class);
		Greeting greeting = greetings.get(id);

		return (greeting == null) ? Optional.empty() : Optional.of(greeting);
	}
	
	/**
	 * Updates a Greeting with a given Id
	 * 
	 * @param request Greeting object with the new message and Id
	 * @return Optional with modified greeting if the update was successful or empty
	 *         otherwise
	 */
	public Optional<Greeting> updateGreeting(Greeting request) {
		entityValidator.validateUpdate(request.getId(), request);
		Greeting greeting = greetings.get(request.getId());
		if (greeting == null) {
			return Optional.empty();
		}
		this.greetings.put(request.getId(), request);

		return Optional.of(greetings.get(request.getId()));
	}

	/**
	 * get default Greeting
	 * 
	 * @return Optional with modified greeting if the update was successful or empty
	 *         otherwise
	 */
	public Optional<Greeting> getDefaultGreeting() {
		return getGreeting(DEFAULT_ID);
	}

	/**
	 * Saves a new Greeting
	 * 
	 * @param greeting new greeting
	 * @return newly created greeting
	 */
	private Greeting save(Greeting greeting) {
		this.greetings.put(greeting.getId(), greeting);

		return greeting;
	}
}
