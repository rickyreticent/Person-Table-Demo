/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package components;

import java.io.Serializable;

/**
 *
 * @author nrr
 * 27042016 added implements Serializable interface
 */
//Note: Serializable used to turning an existing object into a byte array
//This class used for generaliz ed of the data
public class Person implements Serializable {
    private int PersonID;
    private String firstName;
    private String lastName;
    private String sport;
    private int years;
    private boolean vegetarian;
    
    public Person(){}
    
    //Constructor to get the parameter that's needed
    public Person(int pid, String firstname, String lastname, String sport, int y, boolean v){
        this.PersonID = pid;
        this.firstName = firstname;
        this.lastName = lastname;
        this.sport = sport;
        this.years = y;
        this.vegetarian = v;
    }
    
    //set the Person ID
    public void setPersonID(int pid){
        this.PersonID = pid;
    }
    
    //Then get the value from setPersonID
    public int getPersonID(){ return PersonID; }
    
    //set the Firstname
    public void setFirstName(String fname){
        this.firstName = fname;
    }
    
    //Then get the value from getFirstName
    public String getFirstName(){ return firstName; }
    
    //set the LastNAme
    public void setLastName(String lname){
        this.lastName = lname;
    }
    
    //Then get the value from setLastName
    public String getLastName(){ return lastName; }
    
    //set the Sport Value
    public void setSport(String spt){
        this.sport = spt;
    }
    
    //Then get the value from setSport
    public String getSport(){ return sport; }
    
    //set the Years Value
    public void setYears(int y){
        this.years = y;
    }
    
    //Then get the value from getYears
    public int getYears(){ return years; }
    
    //set the Vegetarian value
    public void setVeg(boolean v){
        this.vegetarian = v;
    }
    
    //Then get the value from Vegetarian
    public boolean isVeg(){ return vegetarian; }
}
