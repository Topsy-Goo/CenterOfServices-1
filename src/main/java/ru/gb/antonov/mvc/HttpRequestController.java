package ru.gb.antonov.mvc;

import org.json.JSONObject;
import ru.gb.antonov.MainApp;
import ru.gb.antonov.structs.Causes;
import ru.gb.antonov.structs.CosOperations;
import ru.gb.antonov.structs.Customer;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import static ru.gb.antonov.Factory.*;

/** Класс-контроллер предназначен для обработки html-запросов, приходящих на определённый порт. Порт
указывается при создании экземпляра класса. Объект работает в потоке main.
Для обработки каждого запроса создаётся один поток.<p>
В текущей версии приложения класс используется как синглтон, но при необходимости возможно
запустить на другом порте ещё один HttpRequestController. */
public class HttpRequestController extends CosHttpServer {

    public static long requestCounter = 0L;

    private HttpRequestController (Socket socket) throws IOException {
        soc = socket;
        ist = socket.getInputStream();
        ost = socket.getOutputStream();
    }

/** Садимся на локальный порт и обрабатываем все запросы, приходящие на него. */
    public static void go (int port) throws IOException {
        ServerSocket serverSocket = new ServerSocket (port);
        while (!serverSocket.isClosed()) {
            Socket socket = serverSocket.accept();
            HttpRequestController rc = new HttpRequestController (socket);
            new Thread (rc).start();
        }
    }

    @Override public void run () {
        threadName = Thread.currentThread().getName();
        long requestNumber = requestCounter++;
        lnprint ("["+threadName+"]: Получен новый запрос rc"+ requestNumber +".");

        try (InputStreamReader reader = new InputStreamReader(ist);
             BufferedReader br = new BufferedReader (reader);)
        {
            MyHttpHeader myHeader = readInputHeader (br);
            lnprintf ("["+threadName+"]: получен запрос: %s %s", myHeader.function, myHeader.path);
            String function = myHeader.function;

            if (function.equals ("POST") && myHeader.path.equals ("/request")) {
                sendOkStatusWithOrigin (myHeader.headers.get("Origin"));  //< браузер не отдаст стулья без предоплаты
                String body = readInputBody (br);
                lnprint("["+threadName+"]: Вот что нам прислали: "+ body);
                takeCustomerToReceptionist (body);
            }
            else if (function.equals ("OPTIONS")) {
                sendCorsRequest (myHeader.headers.get("Access-Control-Request-Method"),
                                 myHeader.headers.get("Origin"));
            }
            else sendStatus (400, "BAD_REQUEST");
        }
        catch (IOException e) { e.printStackTrace(); }
        finally {
            close();
            lnprint ("["+threadName+"]: ** Запрос rc"+requestNumber+" обработан. **");
        }
    }

/** Отвечаем на CORS-запрос (preflight request).
@param function значение для поля Access-Control-Allow-Methods.
@param origin значение для поля Access-Control-Allow-Origin. */
    protected void sendCorsRequest (String function, String origin) throws IOException {
        String response = String.format (
            "HTTP/1.1 200 OK\r\n"+
            "Access-Control-Allow-Methods: %s\r\n"+
            "Access-Control-Allow-Origin: %s\r\n"+
            "Access-Control-Allow-Headers: Content-type\r\n"+
            "Connection: keep-alive\r\n\r\n", function, origin);
        sendLine (response);
    }

/** Ответ на POST-запрос, которму предшествовал CORS-запрос.<p>
Говорят, что если POST-запросу предшествовал CORS-запрос, то на него нужно отвечать,
указывая в заголовке такой же Access-Control-Allow-Origin, какой был в ответе на CORS-запрос. Иначе, де,
браузер не позволит скрипту воспользоваться результатами ответа. */
    protected void sendOkStatusWithOrigin (String origin) throws IOException {
        if (isStringEmpty (origin))
            throw new IllegalAccessError();
        String response = String.format(
            "HTTP/1.1 200 OK\r\n"+
            "Content-Length: 0\r\n"+
            "Access-Control-Allow-Origin: %s\r\n" +
            "Connection: close\r\n\r\n", origin);
        sendLine (response);
        lnprint ("["+threadName+"]: sendStatus() - "+ response);
    }

/** Считываем полезную нагрузку html-запроса.<p>
Предполагаем, что заголовок уже считан и указатель BufferedReader'а стоит на начале нужного нам блока данных.
Также предполагаем, что данные являются текстом. */
    protected String readInputBody (BufferedReader br) throws IOException {
        StringBuilder sb = new StringBuilder();
        String s;
        while (null != (s = br.readLine()))
            sb.append(s);
        return sb.toString();
    }

    private void takeCustomerToReceptionist (String body) {
        JSONObject jo = new JSONObject (body);
        Customer customer = new Customer(
                jo.getLong ("id"),
                Causes.valueOf (jo.getString ("cause")),
                CosOperations.valueOf (jo.getString ("operation")));
        lnprint ("["+threadName+"]: создан Customer: "+ customer);
        MainApp.getReceptionist().makeService (customer);
    }
}
