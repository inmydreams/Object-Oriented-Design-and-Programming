import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class NewCon {
    private static Connection con;
    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        }catch(ClassNotFoundException e1) {
            System.out.println(e1); 
        }
    }
    
public static Connection connect(){
    try{
        con=DriverManager.getConnection("jdbc:mysql://localhost:3306/movie","root","kara18");
    }catch(SQLException e2){
        System.out.println(e2);
    }
    return con;
}

public static void close() throws SQLException{
    if(!con.isClosed())
        con.close();
}

private NewCon(){
}
}
