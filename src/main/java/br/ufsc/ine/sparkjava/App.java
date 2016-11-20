package br.ufsc.ine.sparkjava;

import static spark.Spark.after;
import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;
import static spark.Spark.put;

import com.google.gson.Gson;


public class App {
	
	private static Gson gson = new Gson();
	private static PersonController personController = PersonController.getInstance();
	
	
    public static void main( String[] args ){
    	
    	port(8084);
    	
    	get("/person", (request, response) -> {
    	    return personController.getAll();
    	}, gson::toJson);
    	
    	get("/person/:uuid", (request, response) -> {
    		return personController.getByUuid(request, response);
    	}, gson::toJson);

    	post("/person", (request, response) -> {
    		return personController.createPerson(request);
    	}, gson::toJson);

    	put("/person", (request, response) -> {
    		return personController.updatePerson(request);
    	}, gson::toJson);

    	delete("/person/:uuid", (request, response) -> {
    		return personController.deletePerson(request, response);
    	}, gson::toJson);
    	
    	after((req, res) -> {
    		  res.type("application/json");
    	});
    	
    }

}
