/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package components;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
/**
 *
 * @author nrr
 */

//This class is used to Controlled the Data Object
public interface PersonDAO extends Remote {
    public List<Person> getAllPersons() throws RemoteException;;
        
    public Person getPerson(int pid) throws RemoteException;;
    
    public void addPerson(Person person)throws RemoteException;;
        
    public void updatePerson(Person person)throws RemoteException;;
    
    public void deletePerson(Person person)throws RemoteException;;
        
    public void populatePerson(List persons)throws RemoteException;;
        
    public void setUpConnection() throws RemoteException;;
}
