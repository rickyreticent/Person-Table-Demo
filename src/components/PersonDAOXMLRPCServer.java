/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package components;

/**
 *
 * @author Ricky
 * Class ini untuk Server dari XMLRPC
 */
import java.io.IOException;
import helma.xmlrpc.WebServer;
import helma.xmlrpc.XmlRpc;

/**
 * Write a description of class PersonDAOXMLRPCServer here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class PersonDAOXMLRPCServer {
    
    public PersonDAOXMLRPCServer(){
        try {

            XmlRpc.setDriver("org.apache.xerces.parsers.SAXParser");

            // Start the server, using built-in version
            System.out.println("Attempting to start XML-RPC Server...");
//            WebServer server = new WebServer(Integer.parseInt(args[0]));
            WebServer server = new WebServer(Integer.parseInt("8899"));

            System.out.println("Started successfully.");
            // Register our handler class as area
            server.addHandler("PersonDAO", new PersonHandler());


            System.out.println(
                    "Registered PersonHandler class to PersonDAO.");

            System.out.println("Now accepting requests. (Halt program to stop.)");
        } catch (ClassNotFoundException e) {
            System.out.println("Could not locate SAX Driver");

        } catch (IOException e) {
            System.out.println("Could not start server: "
                    + e.getMessage());
        }
    }

    public static void main(String[] args) {
        try {
            XmlRpc.setDriver("org.apache.xerces.parsers.SAXParser");

            // Start the server, using built-in version
            System.out.println("Attempting to start XML-RPC Server...");
//            WebServer server = new WebServer(Integer.parseInt(args[0]));
            WebServer server = new WebServer(Integer.parseInt("8899"));

            System.out.println("Started successfully.");
            // Register our handler class as area
            server.addHandler("PersonDAO", new PersonHandler());


            System.out.println(
                    "Registered PersonHandler class to PersonDAO.");

            System.out.println("Now accepting requests. (Halt program to stop.)");
        } catch (ClassNotFoundException e) {
            System.out.println("Could not locate SAX Driver");

        } catch (IOException e) {
            System.out.println("Could not start server: "
                    + e.getMessage());
        }
    }
}
