package eu.offermann.gradle.extension;

/**
 * @author off
 *
 */
public class DbUnitPluginExtension {

	/**
	 * The database url
	 */
	private String url = "jdbc:postgresql://localhost:5432/pamelv";
	
	/**
	 * The database user
	 */
    private String user = "postgres";
    
    /**
     * The database password
     */
    private String password = "postgres";
    
    /**
     * The database driver
     */
    private String driver = "org.postgresql.Driver";
	
    /**
     * The Path to the export or import File 
     */
    private String filePath = "d:/dump.xml";
    
    /**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	/**
	 * @return the user
	 */
	public String getUser() {
		return user;
	}
	/**
	 * @param user the user to set
	 */
	public void setUser(String user) {
		this.user = user;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return the driver
	 */
	public String getDriver() {
		return driver;
	}
	/**
	 * @param driver the driver to set
	 */
	public void setDriver(String driver) {
		this.driver = driver;
	}
	/**
	 * @return the filePath
	 */
	public String getFilePath() {
		return filePath;
	}
	/**
	 * @param filePath the filePath to set
	 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
}
