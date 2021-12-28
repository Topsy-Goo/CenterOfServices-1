package ru.gb.antonov.mvc;

import ru.gb.antonov.Stopable;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import static ru.gb.antonov.Factory.lnprint;

abstract public class CosHttpServer implements Runnable, Stopable, Closeable {

    protected int port;
    protected ServerSocket serverSocket;
    protected Socket       soc;
    protected InputStream  ist;
    protected OutputStream ost;
    protected String       threadName;

    //protected CosHttpServer (Socket socket) throws IOException {
    //}

    protected static class MyHttpHeader {
        String function;
        String path;
        String              protocol;
        Map<String, String> headers;
        /*@Override public String toString () {
            StringBuilder sb = new StringBuilder (String.format("%s %s %s", function, path, protocol));
            headers.forEach((k,v)->sb.append (String.format ("%s: %s", k,v)));
            return sb.toString();
        }*/
    }

    @Override public void stop () {
        try {
            if (serverSocket != null && !serverSocket.isClosed())
                serverSocket.close();
        }
        catch (IOException e) { e.printStackTrace(); }
        finally { serverSocket = null; }
    }

    @Override public void close () {
        try {
            if (ist != null)                 ist.close();
            if (ost != null) { ost.flush();  ost.close(); }
            if (soc != null)                 soc.close();
        }
        catch (IOException e) {e.printStackTrace();}
        finally { ist = null;      ost = null;      soc = null; }
    }

/** Отсылаем браузеру статус присланного им запроса. */
    protected void sendStatus (int code, String description) throws IOException {
        String response = String.format(
            "HTTP/1.1 %d %s\r\n"+
            "Content-Length: 0\r\n"+
            "Connection: close\r\n\r\n", code, description);
        sendLine (response);
        lnprint ("["+threadName+"]: sendStatus() - "+ response);
    }

/** Считываем заголовок html-запроса в формате ключ — значение. */
    protected MyHttpHeader readInputHeader (BufferedReader br) throws IOException {
        String line;
        MyHttpHeader myHeader = new MyHttpHeader();
        parseHttpHeader1stLine (line = br.readLine(), myHeader);
        lnprint ("\t["+threadName+"]: "+ line);
        Map<String, String> headers = new HashMap<>();

        while (null != (line = br.readLine()) && !line.isEmpty()) { //< наверное, не стоит делать line.trim().
            lnprint ("\t["+threadName+"]: "+ line);
            //Добавляем строку в словарь:
            int index = line.indexOf (": ");
            if (index > 0 && index < line.length() -2)
                headers.put (line.substring(0, index), line.substring(index +2));
        }
        myHeader.headers = headers;
        return myHeader;
    }

/** Парсим первую строку html-запроса. */
    private void parseHttpHeader1stLine (String firstLine, MyHttpHeader myHeader) {
        String[] ars = firstLine.split ("\\s", 3);
        int length = ars.length;
        myHeader.function = (length > 0) ? ars[0] : null;
        myHeader.path     = (length > 1) ? ars[1] : null;
        myHeader.protocol = (length > 2) ? ars[2] : null;
    }

    protected void sendLine (String line) throws IOException {
        ost.write (line.getBytes());
        ost.flush();
    }
}
