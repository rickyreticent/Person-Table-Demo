/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TableMVC;

import components.Person;
import components.PersonDAO;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author nrr
 */

//This class is used to generate the table model view
public class PersonTableModel extends AbstractTableModel {
    private final boolean DEBUG = true;
    private final PersonDAO persons;
    private final String[] columnNames = {"ID","First Name", "Last Name", "Sport", "# of Years", "Vegetarian"};
    
    //Constructor to get the parameter from personDAO
    public PersonTableModel(PersonDAO persons) {
        this.persons = persons;
    }
    
    @Override
    //get the count of Row
    public int getRowCount() {
        try {
            return persons.getAllPersons().size();
            //return 0;
        } catch (RemoteException ex) {
            Logger.getLogger(PersonTableModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    @Override
    //get the count of every column
    public int getColumnCount() {
    //To change body of generated methods, choose Tools | Templates.
        return columnNames.length; 
    }

    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }
    
    @Override
    //Generate and get the value to placed in
    public Object getValueAt(int rowIndex, int columnIndex) {
        Person person;
        try {
            person = persons.getPerson(rowIndex);
            switch(columnIndex){
                case 0: return person.getPersonID();
                case 1: return person.getFirstName();
                case 2: return person.getLastName();
                case 3: return person.getSport();
                case 4: return person.getYears();
                case 5: return person.isVeg();
                default: return "";
            } //To change body of generated methods, choose Tools | Templates.
        } catch (RemoteException ex) {
            Logger.getLogger(PersonTableModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    /*
     * JTable uses this method to determine the default renderer/
     * editor for each cell.  If we didn't implement this method,
     * then the last column would contain text ("true"/"false"),
     * rather than a check box.
     */
    @Override
    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }
    
    /*
     * Don't need to implement this method unless your table's
     * editable.
     */
    @Override
    public boolean isCellEditable(int row, int col) {
        return col >= 2;
    }
    
    /*
     * Don't need to implement this method unless your table's
     * data can change.
     */
    @Override
    public void setValueAt(Object value, int row, int col) {
        if (DEBUG) {
            System.out.println("Setting value at " + row + "," + col
                               + " to " + value
                               + " (an instance of "
                               + value.getClass() + ")");
        }
        Person person;
        try {
            person = persons.getPerson(row);
            switch(col){
                case 0: person.setPersonID((Integer)value); break;
                case 1: person.setFirstName((String)value); break;
                case 2: person.setLastName((String)value); break;
                case 3: person.setSport((String)value); break;
                case 4: person.setYears((Integer)value); break; 
                case 5: person.setVeg((Boolean)value);
            }
        } catch (RemoteException ex) {
            Logger.getLogger(PersonTableModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        fireTableCellUpdated(row, col);
        
        if (DEBUG) {
            System.out.println("New value of data:");
            printDebugData();
        }
    }
    
    private void printDebugData() {
        int numRows = getRowCount();
        int numCols = getColumnCount();
        Person person;
        for (int i=0; i < numRows; i++) {
            System.out.print("    row " + i + ":");
            for (int j=0; j < numCols; j++) {
                try {
                    person = persons.getPerson(i);
                
                    switch(j){
                        case 0: System.out.print("  " + person.getPersonID()); break;
                        case 1: System.out.print("  " + person.getFirstName()); break;
                        case 2: System.out.print("  " + person.getLastName()); break;
                        case 3: System.out.print("  " + person.getSport()); break;
                        case 4: System.out.print("  " + person.getYears()); break;
                        case 5: System.out.print("  " + person.isVeg());
                    }
                } catch (RemoteException ex) {
                    Logger.getLogger(PersonTableModel.class.getName()).log(Level.SEVERE, null, ex);
                }
               
            }
            System.out.println();
        }
        System.out.println("--------------------------");
    }
}
