/*
Instituto Tecnológico de Costa Rica
Escuela de Computacióm
Grupo 3
Programación Orientada a Objetos
Realizado por: David Hernández Calvo y Javier Fonseca Mora
Fecha de inicio: 17/09/2020
fecha de entrega: 13/10/2020
*/
import java.net.URLEncoder;


import com.google.gson.*;
import com.mashape.unirest.http.HttpResponse;
import java.net.URLEncoder;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.util.Random;


public class API {
    // Crea la conexion con el API y extrae la información al hacer solicitudes a la API
    // Con los metodos instancia directamente el tipo de item que se desea
    public API() {
    }


    public JsonObject getApiValue()  throws Exception{
        Random numR = new Random();
        String host = "https://ali-express1.p.rapidapi.com/search?from=0&limit=200&country=CO&query=Xiaomi";
        String charset = "UTF-8";
        String x_rapidapi_host = "ali-express1.p.rapidapi.com";
        String x_rapidapi_key = "090120794emsh378888d2f9bd5c3p18e5e1jsnf92bc3e3f0ef";

        HttpResponse <JsonNode> response = Unirest.get(host)
                .header("x-rapidapi-host", x_rapidapi_host)
                .header("x-rapidapi-key", x_rapidapi_key)
                .asJson();

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonParser jp = new JsonParser();
        JsonElement je = jp.parse(response.getBody().toString());
        return (JsonObject) je.getAsJsonArray().get(numR.nextInt(20));
    }

    public Items crearArma(String nombre, String desc) throws Exception{
        JsonObject jo = getApiValue();
        int id = jo.get("productId").getAsInt();
        int compra =  ((int) jo.get("productElements").getAsJsonObject().get("price").getAsJsonObject().get("sell_price").getAsJsonObject().get("value").getAsInt())+10;
        int venta = compra - (int)(compra * 0.85);
        int defensa =  ((int) jo.get("productElements").getAsJsonObject().get("image").getAsJsonObject().get("imgWidth").getAsInt())%45;
        int ataque =  ((int) jo.get("productElements").getAsJsonObject().get("image").getAsJsonObject().get("imgHeight").getAsInt())%63;
        String categoria = jo.get("productType").getAsString();
        return new Arma(compra*20, venta*10, nombre, desc, ataque+13/3, defensa/3);
    }
    public Items crearArmadura(String nombre, String desc) throws Exception{
        JsonObject jo = getApiValue();
        int id = jo.get("productId").getAsInt();
        int compra =  ((int) jo.get("productElements").getAsJsonObject().get("price").getAsJsonObject().get("sell_price").getAsJsonObject().get("value").getAsInt())+10;
        int venta = compra - (int)(compra * 0.85);
        int defensa =  ((int) jo.get("productElements").getAsJsonObject().get("image").getAsJsonObject().get("imgWidth").getAsInt())%35;
        int ataque =  ((int) jo.get("productElements").getAsJsonObject().get("image").getAsJsonObject().get("imgHeight").getAsInt())%52;
        return new Armadura(compra*17, venta*12, nombre, desc, (defensa+compra)%30);
    }
    public Items crearConsumible(String nombre, String desc) throws Exception{
        JsonObject jo = getApiValue();
        int id = jo.get("productId").getAsInt();
        int compra =  ((int) jo.get("productElements").getAsJsonObject().get("price").getAsJsonObject().get("sell_price").getAsJsonObject().get("value").getAsInt())+10;
        int venta = compra - (int)(compra * 0.85);
        int defensa =  ((int) jo.get("productElements").getAsJsonObject().get("image").getAsJsonObject().get("imgWidth").getAsInt())%13;
        int ataque =  ((int) jo.get("productElements").getAsJsonObject().get("image").getAsJsonObject().get("imgHeight").getAsInt())%80;
        return new Consumibles(compra*22, venta*8, nombre, desc, id%10, defensa*3/2);
    }
}
