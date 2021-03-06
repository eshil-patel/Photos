package controller;
import model.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.Serializable;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Photos;

public class LoginController {
	@FXML
	private TextField username;
	@FXML
	private Button loginbutton;
	/**
	 * A method to deal with user pressing enter at the textbox. 
	 * @param ae Action Event
	 * @throws Exception
	 */
	@FXML
	public void onEnter(ActionEvent ae)throws Exception{
		login(ae);
	}
	public static UserList UL;
	public static SwitchPage switchpage;
	/**
	 * Starts the logincontroller page
	 * @throws FileNotFoundException
	 */
	public void start() throws FileNotFoundException{
		UL = DataSaver.load();
		switchpage=new SwitchPage();
	}
	/**
	 * Button handler method for "login" button
	 * Checks whether the username is admin -> shows admin controller page and list of admins
	 * 							      stock -> shows the nonadmin controller with the stock photos in an album
	 * If neither, checks whether the user exists in userlist and logs in
	 * @param event
	 * @throws Exception
	 */
	public void login(ActionEvent event) throws Exception  {
		String input=username.getText().trim();
		username.setText("");
		if(input.equals("admin")) {
			AdminController.initializeUserList(UL);
			switchpage.showScreen("/view/AdminPage.fxml",event);
		}
		else if(input.equals("stock")) {
			User user;
			if(UL.hasUser("stock")) {
				user = UL.getUser("stock");
			}
			else {
				user = new User("stock");
				Album album = new Album("stock");
				File[] stock = getPhotoLocation();
				for (File photo:stock) {
					if(photo.isFile()) {
						Photo p = new Photo(photo.getAbsolutePath(),photo.lastModified());
						System.out.println(photo.getAbsolutePath());
						album.addPhoto(p);
					}
				}
				user.addAlbum(album);
				UL.addUser(user);
				DataSaver.save(UL);
			}
			NonAdminController.initializePage(UL, user);
			switchpage.showScreen("/view/NonAdminPage.fxml", event);
		}
		else if(UL.hasUser(input)) {
			User user=UL.getUser(input);
			NonAdminController.initializePage(UL, user);
			switchpage.showScreen("/view/NonAdminPage.fxml", event);
		}
		else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setContentText("The username is not found. Please check spelling or contact an administrator");
			alert.showAndWait();
			return;
		}
	}
	/**
	 * Used in the callback function of the login button. Used for finding the absolute path for the stock photos.
	 * @return returns the list of Files and their paths. 
	 */
	public File[] getPhotoLocation() {
		List<File> filepaths = new ArrayList<File>();
		String workingDir = System.getProperty("user.dir");
		String[] edit=workingDir.split("\\\\photos");
		workingDir=edit[0];
		String stock=workingDir+"\\StockPhotos";
		File stockphotos = new File(stock);
		File[] photos = stockphotos.listFiles();
		return photos;
	}
}
