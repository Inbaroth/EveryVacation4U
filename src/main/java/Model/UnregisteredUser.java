package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UnregisteredUser extends User {

    public UnregisteredUser() {
    }

    public UnregisteredUser(String databaseName) {
        this.DBName = databaseName;
    }

    public String signIn(String userName, String password){
        RegisteredUser ans = super.searchUser(userName);
        if (ans != null){
            if (!password.equals(ans.getPassword())) {
                return "הסיסמאות אינן תואמות";
            }
            else{
                return userName;
            }
        }
        else{
            return "שם משתמש לא קיים במערכת";
        }
    }

}
