package util;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.math.BigDecimal;

public class MyJsonReader {

    public JsonObject getJsonObject(HttpServletRequest request) throws IOException {
        StringBuilder buffer = new StringBuilder();
        BufferedReader reader = request.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            buffer.append(line);
        }
        String jsonData = buffer.toString();

        JsonReader jsonReader = Json.createReader(new StringReader(jsonData));
        return jsonReader.readObject();
    }

}
