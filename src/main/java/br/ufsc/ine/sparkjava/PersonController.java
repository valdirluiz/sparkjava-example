package br.ufsc.ine.sparkjava;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.google.gson.Gson;

import spark.Request;

public class PersonController {
	
	private Gson gson;
	private static List<Person> persons = new ArrayList<>();
	private static PersonController ourInstance = new PersonController();

	private PersonController(){
		this.gson = new Gson();
	}
	
    public static PersonController getInstance() {
        return ourInstance;
    }

	public Person createPerson(Request request) {
		Person person = this.gson.fromJson(request.body(), Person.class);
		person.setUuid(UUID.randomUUID().toString());
		persons.add(person);
		return person;
	}
	
	public Person updatePerson(Request request) {
		Person person = this.gson.fromJson(request.body(), Person.class);
		persons.remove(person);
		persons.add(person);
		return person;
	}
	
	@SuppressWarnings("static-access")
	public List<Person> getAll() {
		return this.persons;
	}

	@SuppressWarnings("static-access")
	public Optional<Person> getByUuid(String uuid) {
		return this.persons.stream().filter(p -> p.getUuid().equals(uuid)).findFirst();
	}

	
	
	 
}
