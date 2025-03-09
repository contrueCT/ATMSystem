package controller;

import javax.json.Json;
import javax.json.JsonObject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.ATMService;
import service.ServiceImpl.ATMServiceImpl;
import util.MyJsonReader;
import static util.AccountsChoice.choice.*;

import java.io.IOException;
import java.math.BigDecimal;

/**
 * @author confff
 */
@WebServlet("/accounts/*")
public class AccountServlet extends HttpServlet {
    ATMService atmService = new ATMServiceImpl();
    MyJsonReader myJsonReader = new MyJsonReader();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getPathInfo();
        if ("welcome".equals(path.split("/")[path.split("/").length - 2])) {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/pages/welcome.html");
            dispatcher.forward(request, response);
            return;
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("/pages/account.html");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String path = request.getPathInfo();

        try{
            switch (path.split("/")[path.split("/").length - 1]){
                case DEPOSIT:
                    deposit(request,response);
                    break;
                case WITHDRAW:
                    withdraw(request,response);
                    break;
                case TRANSFER:
                    transfer(request,response);
                    break;
                case CHECK_BALANCE:
                    checkBalance(request,response);
                default:
                    break;
            }


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void deposit(HttpServletRequest request, HttpServletResponse response) throws Exception {
        JsonObject jsonObject = myJsonReader.getJsonObject(request);

        String cardId = jsonObject.getString("cardId", "");
        BigDecimal amount = jsonObject.getJsonNumber("amount").bigDecimalValue();
        Boolean isSuccess;
        isSuccess = atmService.deposit(cardId,amount);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        if(isSuccess){
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("{\"message\":\"Deposit successful\"}");
        }else{
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"message\":\"Deposit failed\"}");
        }
    }

    private void withdraw(HttpServletRequest request, HttpServletResponse response) throws Exception {
        JsonObject jsonObject = myJsonReader.getJsonObject(request);

        BigDecimal amount = jsonObject.getJsonNumber("amount").bigDecimalValue();
        String cardId = jsonObject.getString("cardId", "");
        Boolean isSuccess;
        isSuccess = atmService.withdraw(cardId,amount);
        //这里可以化简一点
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        if(isSuccess){
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("{\"message\":\"Withdraw successful\"}");
        } else{
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"message\":\"Withdraw failed\"}");
        }
    }

    private void transfer(HttpServletRequest request, HttpServletResponse response) throws Exception {
        JsonObject jsonObject = myJsonReader.getJsonObject(request);

        BigDecimal amount = jsonObject.getJsonNumber("amount").bigDecimalValue();
        String cardId = jsonObject.getString("cardId", "");
        String targetCardId = jsonObject.getString("targetCardId", "");
        Boolean isSuccess;
        isSuccess = atmService.transfer(cardId,amount,targetCardId);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        if(isSuccess){
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("{\"message\":\"Transfer successful\"}");
        }
        else{
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"message\":\"Transfer failed\"}");
        }
    }

    public void checkBalance(HttpServletRequest request, HttpServletResponse response) throws Exception {
        JsonObject jsonObject = myJsonReader.getJsonObject(request);

        String cardId = jsonObject.getString("cardId", "");

        BigDecimal balance = atmService.checkBalance(cardId);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        JsonObject jsonResponse;
        if(balance!=null){
            jsonResponse = Json.createObjectBuilder()
                    .add("message", "check balance successful")
                    .add("balance", balance.toString())
                    .build();
            response.setStatus(HttpServletResponse.SC_OK);
        }
        else{
            jsonResponse = Json.createObjectBuilder()
                    .add("message", "check balance failed")
                    .build();
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        response.getWriter().write(jsonResponse.toString());
    }

}
