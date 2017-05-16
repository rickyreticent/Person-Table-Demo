/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TableMVC;

import components.Person;
import java.util.List;

/**
 *
 * @author nrr
 */

//For Command in Controller Class
public interface ControllerInterface {
    public List<Person> getAllPersons();
    public Person getPerson(int pid);
    void addPerson(Person person);
    void updatePerson(Person person);
    void deletePerson(Person person);
}
