package webServer;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Pranit Anand
 */
import View.ISchoolFacade;
import View.SchoolFacade;
import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import exceptions.NotFoundException;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import model.Person;
import model.RoleSchool;

public class WebServer {

    static int port = 9999;
    static String ip = "10.119.226.16";
    static String publicFolder = "src/htmlFiles/";
    static SchoolFacade facade;
    private static final boolean DEVELOPMENT_MODE = true;

    public void run() throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(ip, port), 0);
    //REST Routes

        //HTTP Server Routes
        server.createContext("/pages", new HandlerFileServer());
        server.createContext("/Person", new HandlerGetAllPersons());

        server.createContext("/Role", new HandlerAddRoleById());

        server.start();

    }

    public static void main(String[] args) throws Exception {
        if (args.length >= 3) {
            port = Integer.parseInt(args[0]);
            ip = args[1];
            publicFolder = args[2];
        }
        new WebServer().run();
    }

    private static class HandlerGetAllPersons implements HttpHandler {

        public HandlerGetAllPersons() {
            facade = SchoolFacade.getFacade(false);
            if (DEVELOPMENT_MODE) {
               // facade.createTestData();
                facade.getPersonsAsJSON();

            }

        }

        public void handle(HttpExchange he) throws IOException {
            String response = "";
            int status = 200;
            String method = he.getRequestMethod().toUpperCase();
            switch (method) {
                case "GET":
                    try {
                        String path = he.getRequestURI().getPath();
                        int lastIndex = path.lastIndexOf("/");
                        if (lastIndex > 0) {  //person/id
                            String idStr = path.substring(lastIndex + 1);
                            int id = Integer.parseInt(idStr);
                            response = facade.getPersonAsJson(id);
                        } else { // person
                            response = facade.getPersonsAsJSON();
                        }
                    } //                    catch (NotFoundException nfe) {
                    //                        response = nfe.getMessage();
                    //                        status = 404;
                    //                    }
                    catch (NumberFormatException nfe) {
                        response = "Id is not a number";
                        status = 404;
                    }
                    break;
                case "POST":
                    try {
                        InputStreamReader isr = new InputStreamReader(he.getRequestBody(), "utf-8");
                        BufferedReader br = new BufferedReader(isr);
                        String jsonQuery = br.readLine();
                        if (jsonQuery.contains("<") || jsonQuery.contains(">")) {
                            //Simple anti-Martin check :-)
                            throw new IllegalArgumentException("Illegal characters in input");
                        }
                        Person p = facade.addPersonFromGson(jsonQuery);
                        if (p.getPhone().length() > 50 || p.getFirstName().length() > 50 || p.getLastName().length() > 70) {
                            //Simple anti-Martin check :-)
                            throw new IllegalArgumentException("Input contains to many characters");
                        }
                        response = new Gson().toJson(p);
                    } catch (IllegalArgumentException iae) {
                        status = 400;
                        response = iae.getMessage();
                    } catch (IOException e) {
                        status = 500;
                        response = "Internal Server Problem";
                    }
                    break;
                case "PUT":
                    break;
                case "DELETE":
                    try {
                        String path = he.getRequestURI().getPath();
                        int lastIndex = path.lastIndexOf("/");
                        if (lastIndex > 0) {  //person/id
                            int id = Integer.parseInt(path.substring(lastIndex + 1));
                            Person pDeleted = facade.delete(id);
                            response = new Gson().toJson(pDeleted);
                        } else {
                            status = 400;
                            response = "<h1>Bad Request</h1>No id supplied with request";
                        }
                    } //                    catch (NotFoundException nfe) {
                    //                                            status = 404;
                    //                                            response = nfe.getMessage();
                    // } 
                    catch (NumberFormatException nfe) {
                        response = "Id is not a number";
                        status = 404;
                    }
                    break;
            }
            he.getResponseHeaders().add("Content-Type", "application/json");
            he.sendResponseHeaders(status, 0);
            try (OutputStream os = he.getResponseBody()) {
                os.write(response.getBytes());
            }
        }
    }

    private static class HandlerGetPersonById implements HttpHandler {

        @Override
        public void handle(HttpExchange he) throws IOException {
            String requestedURI = he.getRequestURI().toString();
            String id = requestedURI.substring(requestedURI.lastIndexOf("/") + 1);

            int idGotten = Integer.parseInt(id);
            try {
                String response;
                response = facade.getPersonAsJson(idGotten);
                he.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
                he.getResponseHeaders().add("Content-Type", "application/json");
                he.sendResponseHeaders(200, response.length());
                try (PrintWriter pw = new PrintWriter(he.getResponseBody())) {
                    pw.print(response);
                } catch (Exception e) {
                    e.getMessage();

                }
            } catch (NumberFormatException nfe) {
                nfe.getMessage();

            }

        }
    }

    private static class HandlerAddRoleById implements HttpHandler {

        public HandlerAddRoleById() {
        }

        @Override
        public void handle(HttpExchange he) throws IOException {
            String response = "";
            int status = 200;
            try {
                InputStreamReader isr = new InputStreamReader(he.getRequestBody(), "utf-8");
                BufferedReader br = new BufferedReader(isr);
                String jsonQuery = br.readLine();
                int id = 0;
                if (jsonQuery.contains("<") || jsonQuery.contains(">")) {
                    //Simple anti-Martin check :-)
                System.out.println(jsonQuery);
                    throw new IllegalArgumentException("Illegal characters in input");
                }
                String path = he.getRequestURI().getPath();
                int lastIndex = path.lastIndexOf("/");
                if (lastIndex > 0) {  //person/id
                    String idStr = path.substring(lastIndex + 1);
                    id = Integer.parseInt(idStr);
                }
                RoleSchool p = facade.addRoleFromGson(jsonQuery, id);
                response = new Gson().toJson(p);
            } catch (IllegalArgumentException iae) {
                status = 400;
                response = iae.getMessage();
            } catch (IOException e) {
                status = 500;
                response = "Internal Server Problem";
            }
        }
    }

//private static class HandlerAddPerson implements HttpHandler {
//
//    public HandlerAddPerson() {
//    }
//}
//
//private static class HandlerAddRoleById implements HttpHandler {
//
//    public HandlerAddRoleById() {
//    }
//}
//
//private static class HandlerDeletePersonById implements HttpHandler {
//
//    public HandlerDeletePersonById() {
//    }
//}
    class HandlerFileServer implements HttpHandler {

        @Override
        public void handle(HttpExchange he) throws IOException {
            String requestedFile = he.getRequestURI().toString();
            String f = requestedFile.substring(requestedFile.lastIndexOf("/") + 1);
            String extension = f.substring(f.lastIndexOf("."));
            String mime = "";
            switch (extension) {
                case ".pdf":
                    mime = "application/pdf";
                    break;
                case ".png":
                    mime = "image/png";
                    break;
                case ".js":
                    mime = "text/javascript";
                    break;
                case ".html":
                    mime = "text/html";
                    break;
                case ".jar":
                    mime = "application/java-archive";
                    break;
            }
            File file = new File(publicFolder + f);
            System.out.println(publicFolder + f);
            byte[] bytesToSend = new byte[(int) file.length()];
            String errorMsg = null;
            int responseCode = 200;
            try {
                BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
                bis.read(bytesToSend, 0, bytesToSend.length);
            } catch (IOException ie) {
                errorMsg = "<h1>404 Not Found</h1>No context found for request";
            }
            if (errorMsg == null) {
                Headers h = he.getResponseHeaders();
                h.set("Content-Type", mime);
            } else {
                responseCode = 404;
                bytesToSend = errorMsg.getBytes();

            }
            he.sendResponseHeaders(responseCode, bytesToSend.length);
            try (OutputStream os = he.getResponseBody()) {
                os.write(bytesToSend, 0, bytesToSend.length);
            }
        }
    }
}
