package Notes;

public class Config {
    /*
     * Database configuration
     */
    private static final String username = "root";
    private static final String password = "";
    private static final String host = "localhost";
    private static final int port = 3306;
    private static final String database = "notes";
    private static final Boolean useSSL = false;

    /**
     * Get the username
     * 
     * @return The username
     */

    public static String getUsername() {
        return username;
    }

    /**
     * Get the password
     * 
     * @return The password
     */

    public static String getPassword() {
        return password;
    }

    /**
     * Get the JDBC string
     * 
     * @return The JDBC string
     */

    public static String getJDBCString() {
        return "jdbc:mysql://" + host + ":" + port + "/" + database + "?useSSL=" + useSSL;
    }
}
