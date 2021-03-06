/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package components;

import components.*;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Ricky
 * Class ini digunakan untuk DAO dari RMI Server
 * RMI Server juga digunakan untuk melakukan binding dengan client
 */

//This is the implementation of JDBC View
public class PersonDAORMIServer extends UnicastRemoteObject implements PersonDAO {
    List<Person> persons;
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
    static final String DB_URL = "jdbc:mysql://localhost/PL";
    static final String USER = "root";
    static final String PASS = "";
    private Connection conn = null;
    private Statement stmt = null;
    private String sql = "";
    
    private Registry reg;
    
    //Konstruktor kelas
    public PersonDAORMIServer() throws RemoteException{
        persons = new ArrayList<Person>();
        populatePerson(persons);
        initRMI();
    }
    
    public PersonDAORMIServer(String type) throws RemoteException{
        persons = new ArrayList<Person>();
        populatePerson(persons);
        
    }
    
    //Method ini digunakan untuk inisiasi dalam melakukan binding server RMI
    public void initRMI() throws RemoteException{        
        try {
            reg = LocateRegistry.createRegistry(1099);
            reg.rebind("server", this);
//            reg.re
//            reg.rebind("server", );
            reg.lookup("server");
        } catch (NotBoundException ex) {
            Logger.getLogger(PersonDAORMIServer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (AccessException ex) {
            Logger.getLogger(PersonDAORMIServer.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
    
    //retrive list of Persons from the database
    @Override
    public List<Person> getAllPersons() {
        return persons;
    }
    
    //Method untuk menghapus person
    @Override
    public void deletePerson(Person person) {
        setUpConnection();
        try {
            sql = "DELETE FROM Person WHERE PersonID = "+person.getPersonID();
                  
            int rowAffected = stmt.executeUpdate(sql);
            if (rowAffected > 0) {
                persons.clear();
                populatePerson(persons);
            }
        } catch (SQLException se){
            System.out.println("Delete Person: "+se.toString());
        } finally {
            //finally block used to close resources
            try{
                if(stmt!=null)
                    stmt.close();
            }catch(SQLException se1){
            }// nothing we can do
            try{
                 if(conn!=null)
                 conn.close();
            }catch(SQLException se2){
                 se2.printStackTrace();
            }//end finally try
        } // end try
    }
    
    //Method digunakan untuk get person
    @Override
    public Person getPerson(int pid) {
     return persons.get(pid);

    }
    
    //Method unguk mengupdate person
    @Override
    public void updatePerson(Person person) {
        setUpConnection();
        try {
            sql = "UPDATE Person "
                    + "SET "
                    + "PersonID = '"+person.getPersonID()+ "', "
                    + "firstName = '"+person.getFirstName()+"', "
                    + "lastName = '"+person.getLastName()+"', "
                    + "sport = '"+person.getSport()+"', "
                    + "years = '"+person.getYears()+"', "
                    + "vegetarian = '"+((person.isVeg()) ? 1 : 0 )+"' "
                    + "WHERE PersonID = '"+person.getPersonID()+"'";
                  
            int rowAffected = stmt.executeUpdate(sql);
            if (rowAffected > 0) {
                persons.clear();
                populatePerson(persons);
            }
        } catch (SQLException se){
            System.out.println("Update Person: "+se.toString());
        } finally {
            //finally block used to close resources
            try{
                if(stmt!=null)
                    stmt.close();
            }catch(SQLException se1){
            }// nothing we can do
            try{
                 if(conn!=null)
                 conn.close();
            }catch(SQLException se2){
                 se2.printStackTrace();
            }//end finally try
        } // end try
    }
    
    //Method digunakan untuk adding person
    @Override
    public void addPerson(Person person){
        int id = getId();
        person.setPersonID(id);
        System.out.println("id is: "+id);
        
        setUpConnection();
        try {
            sql = "INSERT INTO Person ( firstName, lastName, sport, years, vegetarian)"
                    + "VALUES ('"
//                    +getId()+","
                    + person.getFirstName()+"','"
                    + person.getLastName()+"','"
                    + person.getSport()+"','"
                    + person.getYears()+"','"
                    + ((person.isVeg()) ? 1 : 0 )+"')";
            int rowAffected = stmt.executeUpdate(sql);
            if (rowAffected > 0) persons.add(person);

        } catch (SQLException se){
            System.out.println("add Person: "+se.toString());
        } finally {
            //finally block used to close resources
            try{
                if(stmt!=null)
                    stmt.close();
            }catch(SQLException se1){
            }// nothing we can do
            try{
                 if(conn!=null)
                 conn.close();
            }catch(SQLException se2){
                 se2.printStackTrace();
            }//end finally try
        } // end try
        
    }
    
    public void populatePerson(List pList){
        setUpConnection();
        
        try {
           sql = "SELECT PersonID, firstName, lastName, sport, years, vegetarian FROM Person";
            //sql = "SELECT MAX(PersonID) AS PersonID FROM Person";
            //ResultSet rs;
           ResultSet rs = stmt.executeQuery(sql);
              

            //STEP 5: Extract data from result set
            while(rs.next()){
                //Retrieve by column name
                pList.add(new Person(rs.getInt("PersonID"),
                                    rs.getString("firstName"),
                                    rs.getString("lastName"),
                                    rs.getString("sport"),
                                    rs.getInt("years"),
                                    (rs.getInt("vegetarian") != 0)));
            }
            //STEP 6: Clean-up environment
            rs.close();
            stmt.close();
            conn.close();
            System.out.println("Database connection closed ....");
        } catch (SQLException se){
            System.out.println("Populate Person: "+se.toString());
        } finally {
            //finally block used to close resources
            try{
                if(stmt!=null)
                    stmt.close();
            }catch(SQLException se1){
            }// nothing we can do
            try{
                 if(conn!=null)
                 conn.close();
            }catch(SQLException se2){
                 se2.printStackTrace();
            }//end finally try
        } // end try
    }
    
    //Method untuk setting koneksi ke database
    public void setUpConnection(){
        try {
            //STEP 2: Register JDBC driver
           Class.forName("com.mysql.jdbc.Driver");
            
           //STEP 3: Open a connection
           System.out.println("Connecting to database...");
           conn = DriverManager.getConnection(DB_URL,USER,PASS);
           stmt = conn.createStatement();
        } catch (SQLException se) {
             System.out.println("Setup Connection: "+se.toString());
        } catch (ClassNotFoundException se) {
            System.out.println("Setup Connection: "+se.toString());
        } 
    }
    
    //Method untuk get ID tertinggi
    private int getId(){
        int lastid = 0;
        setUpConnection();
        try {
            
           sql = "SELECT MAX(PersonID) AS PersonID FROM Person";
           ResultSet rs = stmt.executeQuery(sql);
           if (rs.next()) {
                lastid = rs.getInt(1);
           } else {
                // throw an exception from here
           }

            //STEP 6: Clean-up environment
            rs.close();
            stmt.close();
            conn.close();
            System.out.println("Database connection closed ....");
        } catch (SQLException se){
            System.out.println("Populate Person: "+se.toString());
        } finally {
            //finally block used to close resources
            try{
                if(stmt!=null)
                    stmt.close();
            }catch(SQLException se1){
            }// nothing we can do
            try{
                 if(conn!=null)
                 conn.close();
            }catch(SQLException se2){
                 se2.printStackTrace();
            }//end finally try
        } // end try
        return (lastid+1);
    }
}
