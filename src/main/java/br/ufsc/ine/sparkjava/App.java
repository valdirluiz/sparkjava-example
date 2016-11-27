package br.ufsc.ine.sparkjava;

import com.google.gson.Gson;
import spark.Redirect;

import static spark.Spark.*;


public class App {
	
	private static Gson gson = new Gson();
	private static PersonController personController = PersonController.getInstance();
	
	
    public static void main( String[] args ){
    	
    	port(8084);

		redirect.get("/old-person-resource", "/person", Redirect.Status.MOVED_PERMANENTLY);
    	
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
		        res.header("Content-Encoding", "gzip");
    	});
    	
    }

}
