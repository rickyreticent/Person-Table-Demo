/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package components;

/**
 *
 * @author Ricky
 * Class ini untuk Handler dari XMLRPC
 */
import java.util.Hashtable;
import java.util.Vector;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

/**
 * Write a description of class PersonHandler here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class PersonHandler implements PersonAdapter {

    // instance variables - replace the example below with your own
    Vector persons;
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/PL";
    static final String USER = "root";
    static final String PASS = "";
    private Connection conn = null;
    private Statement stmt = null;
    private String sql = "";

//    
    /**
     * Constructor for objects of class PersonHandler
     */
    public PersonHandler() {
        // initialise instance variables
        persons = new Vector();
        populatePerson(persons);
    }

    public Vector getAllPersons() {
        return persons;
    }

    public Hashtable getPerson(int pid) {
        return (Hashtable) persons.get(pid);
    }
    
    public void addPerson(Hashtable person) {
        int id = getId();
        System.out.println("id is: " + id);
        setUpConnection();
        try {
            sql = "INSERT INTO Person (firstName, lastName, sport, years, vegetarian)"
                    + "VALUES ('"
                    + person.get("firstName") + "','"
                    + person.get("lastName") + "','"
                    + person.get("sport") + "','"
                    + person.get("years") + "','"
                    + (((Integer)person.get("vegetarian")==1)? 1 : 0 ) + "')";
            System.out.println("Halo : "+(((Integer)person.get("vegetarian")==1)? 1 : 0 ));

            int rowAffected = stmt.executeUpdate(sql);
            if (rowAffected > 0) {
                persons.clear();
                persons.add(person);
            }
        } catch (SQLException se) {
            System.out.println("add Person: " + se.toString());
            System.out.println(person.get("vegetarian"));
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException se1) {
            }// nothing we can do
            try {
                if (conn != null) {

                }
                conn.close();
            } catch (SQLException se2) {
                se2.printStackTrace();
            }//end finally try
        } // end try
    }

    public void updatePerson(Hashtable person) {
        setUpConnection();
        try {
            sql = "UPDATE Person "
                    + "SET "
                    + "PersonID = '" + person.get("PersonID") + "', "
                    + "firstName = '" + person.get("firstName") + "', "
                    + "lastName = '" + person.get("lastName") + "', "
                    + "sport = '" + person.get("sport") + "', "
                    + "years = '" + person.get("years") + "', "
                    + "vegetarian = '" + (((Integer)person.get("vegetarian")==1)? 1 : 0 ) + "'"
                    + "WHERE PersonID = '" + person.get("PersonID") + "'";
            

            int rowAffected = stmt.executeUpdate(sql);
            if (rowAffected > 0) {
                persons.clear();
                populatePerson(persons);
            }
        } catch (SQLException se) {
            System.out.println("Update Person: " + se.toString());
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException se1) {
            }// nothing we can do
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se2) {
                se2.printStackTrace();
            }//end finally try
        } // end try
    }

    public void deletePerson(Hashtable person) {
        setUpConnection();
        try {
            sql = "DELETE FROM Person WHERE PersonID = " + person.get("PersonID");

            int rowAffected = stmt.executeUpdate(sql);
            if (rowAffected > 0) {
                persons.clear();
                populatePerson(persons);
            }
        } catch (SQLException se) {
            System.out.println("Delete Person: " + se.toString());
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException se1) {
            }// nothing we can do
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se2) {
                se2.printStackTrace();
            }//end finally try
        } // end try
    }

    private void populatePerson(Vector pVec) {
        setUpConnection();

        try {
            sql = "SELECT PersonID, firstName, lastName, sport, years, vegetarian FROM Person";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                //Retrieve by column name
                Hashtable person = new Hashtable();
                person.put("PersonID", rs.getInt("PersonID"));
                person.put("firstName", rs.getString("firstName"));
                person.put("lastName", rs.getString("lastName"));
                person.put("sport", rs.getString("sport"));
                person.put("years", rs.getInt("years"));
                person.put("vegetarian", rs.getInt("vegetarian"));
                pVec.addElement(person);
            }
            rs.close();
            stmt.close();
            conn.close();
            System.out.println("Database connection closed ....");
        } catch (SQLException se) {
            System.out.println("Populate Person: " + se.toString());
        } finally {
//finally block used to close resources
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException se1) {
            }// nothing we can do
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se2) {
                se2.printStackTrace();
            }//end finally try
        } // end try
    }

    //Method ini untuk setting koneksi xmlrpc ke database
    private void setUpConnection() {
        try {
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");
            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();
        } catch (ClassNotFoundException | SQLException se) {
            System.out.println("Setup Connection: " + se.toString());
        }
    }

    private int getId() {
        int lastid = 0;
        setUpConnection();
        try {
            ResultSet rs = stmt.executeQuery("SELECT LAST_INSERT_ID()");
            rs = stmt.executeQuery(sql);
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
        } catch (SQLException se) {
            System.out.println("Populate Person: " + se.toString());
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException se1) {
            }// nothing we can do
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se2) {
                se2.printStackTrace();
            }//end finally try
        } // end try
        return (lastid + 1);
    }
}
