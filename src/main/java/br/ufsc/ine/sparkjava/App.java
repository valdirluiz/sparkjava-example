package br.ufsc.ine.sparkjava;

import static spark.Spark.*;

import java.util.Optional;

import com.google.gson.Gson;


public class App {
	
	private static Gson gson = new Gson();
	private static PersonController personController = PersonController.getInstance();
	
	
    public static void main( String[] args ){
    	
    	port(8082);
    	
    	get("/person", (request, response) -> {
    	    return personController.getAll();
    	}, gson::toJson);
    	
    	get("/person/:uuid", (request, response) -> {
    		String uuid = request.params(":uuid");
    		Optional<Person> optional = personController.getByUuid(uuid);
    		if(optional.isPresent()){
    			return optional.get();
    		}
    		response.status(400);
    		return null;
    	}, gson::toJson);

    	post("/person", (request, response) -> {
    		return personController.createPerson(request);
    	}, gson::toJson);

    	put("/person", (request, response) -> {
    		return personController.updatePerson(request);
    	}, gson::toJson);

    	delete("/person", (request, response) -> {
    		return null;
    	});
    	
    	after((req, res) -> {
    		  res.type("application/json");
    	});
    	
    }

}
