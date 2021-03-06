package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import model.UserList;

public class DataSaver {
	/**
	 * This method is dedicated to serializing the UserList object and saves to disk using the Serializable interface
	 * @param ULL A UserList object consisting of all the users -> primary object that permeates through all other controllers and is also the object being serialized. Consists of all application information.
	 *
	 */
	public static void save(UserList ULL){
		try
        {    
            //System.out.println("Entered the try condition");
            String pa = System.getProperty("user.dir")+File.separator+"src"+File.separator+"model";
			//System.out.println("ADMIN CONT " + pa);
            FileOutputStream file = new FileOutputStream(pa+File.separator+"UserData.dat"); 
            //System.out.println("the filepath was something that was recognized");
            ObjectOutputStream out = new ObjectOutputStream(file);  
            // Method for serialization of object 
            out.writeObject(ULL); 
            out.close(); 
            file.close(); 
            //System.out.println("Object has been serialized"); 
            
        } 
          
        catch(IOException ex) { 
            System.out.println("IOException is caught"); 
        } 
	}
	/**
	 * Responsible for deserializing data stored in disk and create a UserList object consisting of all the users. 
	 * @return A userlist after deserializing the data. Consists of all application data.
	 */
	public static UserList load(){
		UserList UL = null;
		try {
			String pa = System.getProperty("user.dir")+File.separator+"src"+File.separator+"model" + File.separator + "UserData.dat";
			//System.out.println(pa);
			FileInputStream FIS = new FileInputStream(pa);
			//System.out.println("FIS CREATED" + FIS.available());
			if (FIS.available() == 0){
				UL = new UserList();
				FIS.close();
				return UL;
			}
			//System.out.println("FIS NOT NULL");
			ObjectInputStream in = new ObjectInputStream(FIS);
			//System.out.println("after the output stream read");
	        UL = (UserList) in.readObject(); 
	        //System.out.println(UL.toString());
	        //System.out.println("The deserialzation of the empty list worked");
	        FIS.close();
	        in.close();
	        return UL;
		}
		catch(Exception e) {
	//		e.printStackTrace();
			System.out.println("There was an error deserializing the data");
		}
		return UL;
	}
}
