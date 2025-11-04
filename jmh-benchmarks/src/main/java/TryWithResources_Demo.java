import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TryWithResources_Demo {
	
	public static void main(String[] args) {
		try(BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
				Connection con=DriverManager.getConnection("","","");) {
			
			System.out.println("enter data ");
			String s=br.readLine();
		}
		catch(IOException|SQLException e) {
			e.printStackTrace();
		}
		

	}

}
