/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package components;

/**
 *
 * @author Ricky
 * Interface ini digunakan untuk data object pada XMLRPC
 */

import java.util.Hashtable;
import java.util.Vector;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;
public interface PersonAdapter {
    public Vector getAllPersons();
    public Hashtable getPerson(int pid);
    public void addPerson(Hashtable person);
    public void updatePerson(Hashtable person);
    public void deletePerson(Hashtable person);
}
