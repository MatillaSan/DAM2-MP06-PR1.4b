package com.project;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.json.JsonWriter;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Main metodos = new Main();

        try (JsonReader jsonReader = Json.createReader(new FileReader("data/llibres_input.json"))) {
            JsonArray jsonArray = jsonReader.readArray();

            // Ejercicio 0
            Map<Integer, Map<String, Object>> booksMap = metodos.leerJson(jsonArray);
            System.out.println(booksMap);

            // Ejercicio 1
            jsonArray = metodos.modificarJson(jsonArray);

            // Ejercicio 2
            jsonArray = metodos.agregarDatos(jsonArray);

            // Ejercicio 3
            jsonArray = metodos.borrarDatos(jsonArray);

            // Ejercicio 4
            metodos.guardarDatos(jsonArray);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public Map<Integer, Map<String, Object>> leerJson(JsonArray jsonArray) {
        Map<Integer, Map<String, Object>> booksMap = new HashMap<>();

        for (JsonObject jsonObject : jsonArray.getValuesAs(JsonObject.class)) {
            int id = jsonObject.getInt("id");
            String título = jsonObject.getString("títol");
            String autor = jsonObject.getString("autor");
            int año = jsonObject.getInt("any");

            Map<String, Object> bookData = new HashMap<>();
            bookData.put("Título", título);
            bookData.put("Autor", autor);
            bookData.put("Año", año);

            booksMap.put(id, bookData);
        }

        return booksMap;
    }

    public JsonArray modificarJson(JsonArray jsonArray) {
        for (JsonObject jsonObject : jsonArray.getValuesAs(JsonObject.class)) {

            if (jsonObject.getInt("id") == 1) {
                jsonObject = Json.createObjectBuilder(jsonObject)
                        .add("any", 1995)
                        .build();
            }
        }

        return jsonArray;
    }

    public JsonArray agregarDatos(JsonArray jsonArray) {
        JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();

        for (JsonObject jsonObject : jsonArray.getValuesAs(JsonObject.class)) {
            jsonArrayBuilder.add(jsonObject);
        }

        jsonObjectBuilder
                .add("id", 4)
                .add("títol", "Històries de la ciutat")
                .add("autor", "Miquel Soler")
                .add("any", 2022);

        jsonArrayBuilder.add(jsonObjectBuilder.build());
        jsonArray = jsonArrayBuilder.build();

        return jsonArray;
    }

    public JsonArray borrarDatos(JsonArray jsonArray) {
        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();

        for (JsonObject jsonObject : jsonArray.getValuesAs(JsonObject.class)) {
            if (jsonObject.getInt("id") != 2)
                jsonArrayBuilder.add(jsonObject);
        }

        jsonArray = jsonArrayBuilder.build();

        return jsonArray;
    }

    public void guardarDatos(JsonArray jsonArray) {
        try (JsonWriter jsonWriter = Json.createWriter(new FileWriter("data/json_file_sortida.json"))) {
            jsonWriter.writeArray(jsonArray);
            System.out.println("Dades guardades amb èxit!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
