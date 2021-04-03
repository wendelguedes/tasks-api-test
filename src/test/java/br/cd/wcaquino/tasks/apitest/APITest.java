package br.cd.wcaquino.tasks.apitest;

import org.hamcrest.CoreMatchers;
import org.junit.BeforeClass;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class APITest {
	
	@BeforeClass
	public static void setup() {
		RestAssured.baseURI = "http://192.168.0.2:8001/tasks-backend";
	}

	@Test
	public void deveRetornarTarefas() {
		RestAssured.given()
				.log().all()
			.when()
				.get("/todo")
			.then()
				.statusCode(200)
				.log().all();
	}
	
	@Test
	public void deveAdicionarTarefas() {
		RestAssured.given()
			.body("{ \"task\": \"Teste via api\", \"dueDate\": \"2021-05-03\" }")
			.contentType(ContentType.JSON)
				.log().all()
			.when()
				.post("/todo")
			.then()
				.statusCode(201)
				.log().all();
	}
	
	@Test
	public void naoDeveAdicionarTarefaInvalida() {
		RestAssured.given()
			.body("{ \"task\": \"Teste via api\", \"dueDate\": \"2010-05-03\" }")
			.contentType(ContentType.JSON)
				.log().all()
			.when()
				.post("/todo")
			.then()
				.log().all()
				.statusCode(400)
				.body("message", CoreMatchers.is("Due date must not be in past"));
			
	}


}

