package controller;

import javax.json.JsonReader;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import service.ATMService;
import service.ServiceImpl.ATMServiceImpl;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonWriter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

/**
 * @author confff
 */
@WebServlet("/welcome/*")
public class WelcomeServlet extends HttpServlet {
    private final ATMService atmService = new ATMServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/pages/welcome.html");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getPathInfo();
        String choice = path.split("/")[path.split("/").length - 1];
        if ("login".equals(choice)) {
            login(request,response);
        }else {
            register(request,response);
        }
    }

    private void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        StringBuilder buffer = new StringBuilder();
        BufferedReader reader = request.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            buffer.append(line);
        }
        String jsonData = buffer.toString();

        JsonReader jsonReader = Json.createReader(new StringReader(jsonData));
        JsonObject jsonObject = jsonReader.readObject();

        String cardId = jsonObject.getString("cardId", "");
        String password = jsonObject.getString("password", "");
        System.out.println("cardId: " + cardId);
        System.out.println("password: " + password);
        jsonReader.close();

        Boolean isSuccess;
        isSuccess = atmService.login(cardId,password);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        if(isSuccess){
            JsonObject jsonObjects = Json.createObjectBuilder()
                    .add("status", "success")
                    .add("message", "登录成功")
                    .add("redirect", "/accounts/page")
                    .build();

            response.setStatus(HttpServletResponse.SC_OK);
            try (JsonWriter jsonWriter = Json.createWriter(response.getWriter())) {
                jsonWriter.writeObject(jsonObjects);
            }
        }
        else{
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"message\":\"Login failed\"}");
        }
    }

    private void register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        StringBuilder buffer = new StringBuilder();
        BufferedReader reader = request.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            buffer.append(line);
        }
        String jsonData = buffer.toString();

        JsonReader jsonReader = Json.createReader(new StringReader(jsonData));
        JsonObject jsonObject = jsonReader.readObject();

        String name = jsonObject.getString("name", "");
        String phone = jsonObject.getString("phone", "");
        String idCard = jsonObject.getString("idCard", "");
        String password = jsonObject.getString("password", "");
        String cardId = jsonObject.getString("cardId", "");

        jsonReader.close();

        Boolean isSuccess;
        isSuccess = atmService.register(name,phone,idCard,cardId,password);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        if(isSuccess){
            JsonObject jsonObjects = Json.createObjectBuilder()
                    .add("status", "success")
                    .add("message", "注册成功")
                    .add("redirect", "/accounts/page")
                    .build();

            response.setStatus(HttpServletResponse.SC_OK);
            try (JsonWriter jsonWriter = Json.createWriter(response.getWriter())) {
                jsonWriter.writeObject(jsonObjects);
            }
        }else{
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"message\":\"Register failed\"}");
        }
    }

}

