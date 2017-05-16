/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package components;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nrr
 */

//This is the implementation of Manual View
public class PersonDAOImpl implements PersonDAO {

    List<Person> persons;
    
    public PersonDAOImpl(){
        persons = new ArrayList<Person>();
        Person p1 = new Person(0,"Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false));
        Person p2 = new Person(1,"John", "Doe", "Rowing", new Integer(3), new Boolean(true));
        Person p3 = new Person(2,"Sue", "Black", "Knitting", new Integer(2), new Boolean(false));
        Person p4 = new Person(3,"Jane", "White", "Speed reading", new Integer(20), new Boolean(true));
        Person p5 = new Person(4,"Joe", "Brown", "Pool", new Integer(10), new Boolean(false));
        persons.add(p1);
        persons.add(p2);
        persons.add(p3);
        persons.add(p4);
        persons.add(p5);
    }
    
    //retrive list of Persons from the database
    @Override
    public List<Person> getAllPersons() {
        return persons;
    }
    
    @Override
    public void deletePerson(Person person) {
        persons.remove(person.getPersonID());
        System.out.println("Person: ID " + person.getPersonID()
                           +", deleted from database");
    }
    
    @Override
    public Person getPerson(int pid) {
        return persons.get(pid);
    }
    
    @Override
    public void updatePerson(Person person) {
        persons.get(person.getPersonID()).setFirstName(person.getFirstName());
        System.out.println("Student: ID " + person.getPersonID()
                           +", updated in the database");
    }

    @Override
    public void addPerson(Person person) {
        person.setPersonID(persons.size()+1) ;
        persons.add(persons.size(),person);
    }
    
    @Override
    public void populatePerson(List pList) throws RemoteException{

    }
    
    @Override
    public void setUpConnection(){
//        setUpConnection();
    }
    
}
