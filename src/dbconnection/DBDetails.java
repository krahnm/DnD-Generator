package dbconnection;
public class DBDetails
{

/*Change the username to be your own login name */

    public static final String username = "krahnm";

/* Change the password to be your student number.  
DO NOT USE YOUR NORMAL PASSWORD*/

    public  static final  String password = "1053674";


	

/*Change nothing here */
   public static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";  
   public static final String DB_URL = String.format("jdbc:mysql://dursley.socs.uoguelph.ca:3306/%s?useLegacyDatetimeCode=false&serverTimezone=America/New_York",username);




	}
