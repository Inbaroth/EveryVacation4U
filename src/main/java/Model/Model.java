package Model;

import Database.*;
import javafx.scene.control.Alert;

import java.sql.SQLException;
import java.util.ArrayList;

import java.util.Observable;
import java.util.regex.Pattern;

public class Model extends Observable {

   protected String DBName ;





    //public enum errorType {PASSWORD_USERS_NOT_MATCH, PASSWORDS_NOT_MATCH, USER_NOT_EXIST}

    /**
     * Constructor for class Model
     * The constructor create a new database with the name "Vacation4U"
     * and create a new table by the name "Users"
     */
    public Model(String DBName ) {
        this.DBName = DBName;
        DB db = new DB(this.DBName);
    }




}
