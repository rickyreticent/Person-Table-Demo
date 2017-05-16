/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MenuMVC;

import TableMVC.ControllerInterface;
import TableMVC.PersonModel;
import TableMVC.PersonTableViewPanel;
import components.Person;
import java.util.List;

/**
 *
 * @author Ricky
 */
public class PersonController implements ControllerInterface {
    private PersonModel model;          // menggunakan model
    private PersonTableViewPanel view;  // menggunakan view
    //Konstruktor untuk memanggil controller tanpa parameter
    public PersonController(){}
    
    //Konstruktor yang dipanggil oleh PersonModel dengan parameter model dan view
    public PersonController(PersonModel mdl, PersonTableViewPanel v){
        this.model = mdl;
        this.view = v;
    }
    
    //Method untuk menambah model class
    public void addModel(PersonModel mdl){
        this.model = mdl;
    }
    
    //Method untuk menambah view class dari Controller
    public void addView(PersonTableViewPanel view){
        this.view = view;
        view.addController(this);
    }
    
    //Method untuk inisiasi tabel model
    public void initTableModel(){
        view.setTableModel(model.getTableModel());
    }
    
    //Method untuk mengaktifasi view
    public void activateView(){
        view.activate();
    }
    
    //Method untuk mendapatkan semua data dari database
    public List<Person> getAllPersons() {
        return model.getAllPersons();
    }

    //Method untk mendapatkan data person
    public Person getPerson(int pid) {
        return model.getPerson(pid);
    }

    //Method untuk menambah person
    public void addPerson(Person person) {
        model.addPerson(person);
    }

    //Method untuk mengupdate person
    public void updatePerson(Person person) {
        model.updatePerson(person);
    }

    //Method untuk menghapus person
    public void deletePerson(Person person) {
        model.deletePerson(person);
    }
    
}
