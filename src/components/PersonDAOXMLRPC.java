/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package components;

/**
 *
 * @author Ricky
 * 
 * Class ini digunakan untuk DAO pada XMLRPC
 */
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;
import java.io.IOException;
import java.util.*;
import helma.xmlrpc.XmlRpc;
import helma.xmlrpc.XmlRpcClient;
import helma.xmlrpc.XmlRpcException;

/**
 *
 * @author nrr
 */
public class PersonDAOXMLRPC implements PersonDAO {

    List<Person> persons;
    String address = "http://localhost:8899/";

    public PersonDAOXMLRPC() {
        persons = new ArrayList<>();
        populatePerson(persons);
    }

    @Override
    public List<Person> getAllPersons() throws RemoteException {

        return persons;
    }

    @Override
    public Person getPerson(int pid) throws RemoteException {
        return persons.get(pid);
    }

    
    @Override    
    public void addPerson(Person person) throws RemoteException {
        // convert Person into Hashtable
        // convert Person into Hashtable
        Hashtable p = new Hashtable();
        int x = persons.size()+1;
        p.put("PersonID", x);
        p.put("firstName", person.getFirstName());
        p.put("lastName", person.getLastName());
        p.put("sport", person.getSport());
        p.put("years", person.getYears());
        p.put("vegetarian", person.isVeg()?1:0);
        Vector params = new Vector();
        params.addElement(p);
        try {
            // Create the client, identifying the server
            XmlRpcClient client =
                new XmlRpcClient(address);
            // Issue a request
            client.execute("PersonDAO.addPerson", params);
            // Report the results
            System.out.println("Person has been added");
//            persons.clear();
            populatePerson(persons);
        } catch (IOException e) {
            System.out.println("IO Exception: " + e.getMessage(  ));
        } catch (XmlRpcException e) {
            System.out.println("Exception within XML-RPC: " + e.getMessage(  ));
        }
    }

    @Override
    public void updatePerson(Person person) throws RemoteException {
        // convert Person into Hashtable
        Hashtable p = new Hashtable();
        p.put("PersonID", person.getPersonID());
        p.put("firstName", person.getFirstName());
        p.put("lastName", person.getLastName());
        p.put("sport", person.getSport());
        p.put("years", person.getYears());
        p.put("vegetarian", (person.isVeg()) ? 1:0);
        Vector params = new Vector();
        params.addElement(p);
        try {
            // Create the client, identifying the server
            XmlRpcClient client
                    = new XmlRpcClient(address);
            // Issue a request
            client.execute("PersonDAO.updatePerson", params);

            // Report the results
            System.out.println("Person has been updated");
            persons.clear();
            populatePerson(persons);
        } catch (IOException e) {
            System.out.println("IO Exception: " + e.getMessage());
        } catch (XmlRpcException e) {
            System.out.println("Exception within XML-RPC: " + e.getMessage());

 }
 }

 @Override
    public void deletePerson(Person person) throws RemoteException {
        // convert Person into Hashtable
        Hashtable p = new Hashtable();
        p.put("PersonID", person.getPersonID());
        Vector params = new Vector();
        params.addElement(p);
        try {
            // Create the client, identifying the server
            XmlRpcClient client
                    = new XmlRpcClient(address);
 // Issue a request
            client.execute("PersonDAO.deletePerson", params);

            // Report the results
            System.out.println("Person has been deleted");
            persons.clear();
            try{
            populatePerson(persons);
            }catch(NullPointerException e){
                System.out.println(e);
            }
        } catch (IOException e) {
            System.out.println("IO Exception: " + e.getMessage());
        } catch (XmlRpcException e) {
            System.out.println("Exception within XML-RPC: " + e.getMessage());
        }
    }

    public void populatePerson(List pList) {

        try {
            // Create the client, identifying the server
            XmlRpcClient client = new XmlRpcClient(address);

            // Create the request parameters using user input
            Vector params = new Vector();
            // Issue a request
            Vector result = (Vector) client.execute("PersonDAO.getAllPersons", params);

            // Iterate
            Iterator it = result.iterator();
            while (it.hasNext()) {
                Hashtable rs = (Hashtable) it.next();
                pList.add(new Person((Integer) rs.get("PersonID"),
                        rs.get("firstName").toString(),
                        rs.get("lastName").toString(),
                        rs.get("sport").toString(),
                        (Integer) rs.get("years"),
                        ((Integer) rs.get("vegetarian") != 0)));
            }

            // Report the results
            System.out.println("List of persons have been populated");

        } catch (IOException e) {
            System.out.println("IO Exception: " + e.getMessage());
        } catch (XmlRpcException e) {
            System.out.println("Exception within XML-RPC: " + e.getMessage());
        }
    }
    
    public void setUpConnection(){
        
    }

}
