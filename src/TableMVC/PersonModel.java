/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TableMVC;

import components.Person;
import components.PersonDAO;
import components.PersonDAOFactory;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author nrr
 */
//This is The Observable Class in MVC 
public class PersonModel extends java.util.Observable implements ControllerInterface {
    private final PersonDAO personDao;
    public final AbstractTableModel model;
    
    //Constructor to get the parameter from PersonDAOFactory
    public PersonModel(String mdl){
       this.personDao = PersonDAOFactory.getDAO(mdl);
       this.model = new PersonTableModel(personDao);
    }
    
    public PersonModel(String mdl, String serverAddress) throws NotBoundException{
       this.personDao = PersonDAOFactory.getDAO(mdl, serverAddress);
       this.model = new PersonTableModel(personDao);
    }
    
    
    //Make a table model (AbstractTableModel)
    public AbstractTableModel getTableModel(){
        return model;
    }

    //Make List from data in getAllPersons
    public List<Person> getAllPersons() {
        try {
            return personDao.getAllPersons();
        } catch (RemoteException ex) {
            Logger.getLogger(PersonModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    //Generate the data from getPerson in personDAO
    public Person getPerson(int pid) {
        try {
            return personDao.getPerson(pid);
        } catch (RemoteException ex) {
            Logger.getLogger(PersonModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    //Adding data person, then notify Observer for the update to generate in the view
    public void addPerson(Person person) {
        try {
            personDao.addPerson(person);
        } catch (RemoteException ex) {
            Logger.getLogger(PersonModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        setChanged();
        notifyObservers();
    }

    //Updating data person, then notify Observer for the update to generate in the view
    public void updatePerson(Person person) {
        try {
            personDao.updatePerson(person);
        } catch (RemoteException ex) {
            Logger.getLogger(PersonModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        setChanged();
        notifyObservers();
    }

    //Deleting data person, then notify Observer for the update to generate in the view
    public void deletePerson(Person person) {
        try {
            personDao.deletePerson(person);
        } catch (RemoteException ex) {
            Logger.getLogger(PersonModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        setChanged();
        notifyObservers();
    }
}
