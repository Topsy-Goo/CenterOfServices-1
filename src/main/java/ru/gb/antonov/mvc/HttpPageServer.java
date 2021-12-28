package ru.gb.antonov.mvc;

import ru.gb.antonov.MainApp;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static ru.gb.antonov.Factory.*;
import static ru.gb.antonov.MainApp.PAGE_DEFAULT;

/** Класс-сервер предназначен для отдачи html-страниц браузеру по запросу. Порт
указывается при создании экземпляра класса. Для работы используется один поток, отличнный от main.<p>
В текущей версии приложения класс используется как синглтон, но при необходимости возможно
запустить на другом порте ещё один HttpPageServer. */
public class HttpPageServer extends CosHttpServer {

    private long requestCounter = 0L;
    public static final String SERVER_NAME = HttpPageServer.class.getSimpleName();

    public HttpPageServer (int p) {
        port = p;
    }

    @Override public void run() {
        threadName = Thread.currentThread().getName() + port;
        try {
            serverSocket = new ServerSocket (port);
            while (!serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();
                soc = socket;
                ist = socket.getInputStream();
                ost = socket.getOutputStream();
                oneJob();
            }
        }
        catch (IOException e) { e.printStackTrace(); }
    }

/** Обрабатываем один запрос от браузера.<p>
Предполагаем, что браузеру нужен какой-то файл, имя которого он указал в заголовке запроса.
Также предполагаем, что кроме заголовка в запросе ничего нет. */
    void oneJob () {
        long requestNumber = requestCounter++;
        lnprint ("["+threadName+"]: ** Получен запрос pg"+requestNumber+" **");

        try (InputStreamReader reader = new InputStreamReader (ist);
             BufferedReader br = new BufferedReader (reader);)
        {
            sendStatus (102, "PROCESSING");   //< без этой строки браузер не желает работать, причём, кажется, ни что другое его не устраивает, даже 200-OK.
            MyHttpHeader myHeader = readInputHeader (br);
            String pathString = myHeader.path;

            if (!isStringEmpty (pathString)) {
                lnprintf ("\t\t[%s]: Запрошен файл: %s.", threadName, pathString);
                if (pathString.equals ("/"))    //< одинокий слэш — это запрос умолчальной страницы
                    pathString = PAGE_DEFAULT;

                URL url = MainApp.class.getResource (pathString);
                if (url != null) {
                    sendFileContents (readFileToString (Paths.get (url.toURI())));
                }
                else {
                    errlnprintf ("\t\t[%s]: ОШИБКА!\tНе найден файл: %s\n", threadName, pathString);
                    sendStatus (404, "NOT_FOUND");  //< …ну нет у нас favicon.ico
                }
            } else sendStatus (400, "BAD_REQUEST");
        }
        catch (IOException | URISyntaxException e) { e.printStackTrace(); }
        finally {
            close();
            lnprint ("["+threadName+"]: ** Запрос pg"+requestNumber+" обработан. **");
        }
    }

/** Сосавляем стандартный заголовок и цепляем к нему файл, вытянутый в строку. Заголовок и файл-строку разделяет пустая строка. */
    private void sendFileContents (String contents) throws IOException {
        if (contents == null)
            contents = "";
        String response = String.format (
            "HTTP/1.1 200 OK\r\n" +
            "Server: %s\r\n" +
            "Content-Type: text/html\r\n"+
            "Content-Length: %d\r\n" +
            "Connection: close\r\n\r\n", SERVER_NAME, contents.length());
        sendLine (response + contents);
        lnprint ("\t\t["+threadName+"]: запрошенный файл отдан.");
    }

/** Вытягиваем в строку текстовый файл. */
    private static String readFileToString (Path path) throws IOException {
        String result = null;
        if (Files.exists (path)) {
            BufferedReader br = Files.newBufferedReader (path, StandardCharsets.UTF_8);
            StringBuilder sb = new StringBuilder();
            br.lines().forEach (sb::append);
            result = sb.toString();
            br.close();
        }
        return result;
    }
}
