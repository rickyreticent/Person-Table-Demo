/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package components;

import components.*;
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
 * Class ini digunakan untuk implementasi DAO dari RMI Client
 */


public class PersonDAORMIClient extends UnicastRemoteObject implements PersonDAO {
    List<Person> persons;

    PersonDAO rmiInterface ;
    private Registry reg;
    private String serverAddress;
    
    
    //Kostruktor kelas
    public PersonDAORMIClient(String serverAddress) throws RemoteException{
        this.serverAddress=serverAddress;
        initRMIClient();
        persons = new ArrayList<Person>();
        populatePerson(persons);
    }
    
    //Method ini untuk inisiasi client dalam mencari server
    public void initRMIClient() throws RemoteException{        
        try {

            reg = LocateRegistry.getRegistry(serverAddress, 1099);

            rmiInterface = (PersonDAO)reg.lookup("server");
        } catch (NotBoundException ex) {
            Logger.getLogger(PersonDAORMIClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (AccessException ex) {
            Logger.getLogger(PersonDAORMIClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    //retrive list of Persons from the database
    @Override
    public List<Person> getAllPersons() throws RemoteException {
//        return persons;
        return rmiInterface.getAllPersons();
    }
    
    //Method digunakan untuk menghapus person
    @Override
    public void deletePerson(Person person) throws RemoteException {
        rmiInterface.deletePerson(person);
//        rmiInterface.
    }
    
    //Method digunakan untuk mendapat person
    @Override
    public Person getPerson(int pid) throws RemoteException {
//     return persons.get(pid);
     return rmiInterface.getPerson(pid);
       /* if(pid > 0){
            return persons.get(pid);
        } else { return persons.get(1); }*/
    }
    
    //Method digunakan untuk update Person
    @Override
    public void updatePerson(Person person) throws RemoteException {
        rmiInterface.updatePerson(person);
    }
    
    //Method digunakan untuk add person
    @Override
    public void addPerson(Person person) throws RemoteException{
        rmiInterface.addPerson(person);
    }
    
    public void populatePerson(List pList) throws RemoteException{

        rmiInterface.populatePerson(pList);
    }
    
    //Unused method
    public void setUpConnection(){
//        try {
//            rmiInterface.setUpConnection();
//        } catch (RemoteException ex) {
//            Logger.getLogger(PersonDAORMIClient.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }
    
    
}
