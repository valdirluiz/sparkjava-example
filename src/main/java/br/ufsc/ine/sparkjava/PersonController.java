package br.ufsc.ine.sparkjava;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.google.gson.Gson;

import spark.Request;
import spark.Response;

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
	
	public List<Person> getAll() {
		return persons;
	}


	public Person getByUuid(Request request, Response response) {
		String uuid = request.params(":uuid");
		Optional<Person> person =  persons.stream().filter(p -> p.getUuid().equals(uuid)).findFirst();
		if(person.isPresent()){
			return person.get();
		} 
		response.status(400);
		return null;
	}

	public String deletePerson(Request request, Response response) {
		String uuid = request.params(":uuid");
		Optional<Person> person =  persons.stream().filter(p -> p.getUuid().equals(uuid)).findFirst();
		if(person.isPresent()){
			persons.remove(person.get());
			response.status(200);
			return "Pessoa removida.";
		} 
		response.status(400);
		
		return "Pessoa não encontrada.";
	}

	
	
	 
}
