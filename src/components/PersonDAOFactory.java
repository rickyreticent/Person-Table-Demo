/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package components;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nrr
 */

//This Class used to set the Menu Factory of the Person
public class PersonDAOFactory {
    private static PersonDAO pDao = null;
    private static String serverAddress;
    public static PersonDAO getDAO(String pdao){
        System.out.println("model "+pdao.toString());
        
        //The selection of the View Factory
        if (pdao.equalsIgnoreCase("manual")){
            pDao = new PersonDAOImpl();
        } else if (pdao.equals("jdbc")) {
            pDao = new PersonDAOJDBCImpl();
        } else if (pdao.equals("jdbcrmi")) {
            try {
                pDao = new PersonDAORMIServer();
            } catch (RemoteException ex ) {
//                Logger.getLogger(PersonDAOFactory.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Only one server can run at the same time.\n"
                        + "Program will exit automatically.");
                System.exit(0);
            }
        } 

        else if (pdao.equals("xmlrpc")) {
//            pDao = new PersonDAOXMLRPCImpl();
            pDao = new PersonDAOXMLRPC();
        } else if (pdao.equals("xmlrpcphp")) {
            pDao = new PersonDAOXMLRPCPHPImpl();
        }
        return pDao;
    }
    
    public static PersonDAO getDAO(String pdao, String serverAddress) throws NotBoundException{
        System.out.println("model "+pdao.toString());
        System.out.println("server address : "+serverAddress);
        
        //The selection of the View Factory
        if(pdao.equals("rmiclient")){
            try {
                Registry reg = LocateRegistry.getRegistry(serverAddress,1099);
                pDao = (PersonDAO) reg.lookup("server");
                
            } catch (RemoteException ex) {
                Logger.getLogger(PersonDAOFactory.class.getName()).log(Level.SEVERE, null, ex);
                
            }
            
        }  else if (pdao.equals("xmlrpc")) {
//            pDao = new PersonDAOXMLRPCImpl();
            pDao = new PersonDAOXMLRPC();
        }
        
        return pDao;
    }
}
