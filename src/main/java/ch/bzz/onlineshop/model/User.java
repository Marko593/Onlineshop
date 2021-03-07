package ch.bzz.onlineshop.model;

/**
 * user class
 * <p>
 * Onlineshop
 *
 * @author Marko Micanovic
 */
public class User {
    private String userUUID;
    private String password;
    private String userName;

    /**
     * gets the uuid from the user
     * @return uuid from user
     */
    public String getUserUUID() {
        return userUUID;
    }

    /**
     * sets the uuid from the user
     * @param userUUID
     */
    public void setUserUUID(String userUUID) {
        this.userUUID = userUUID;
    }

    /**
     * gets the password from the user
     * @return password from user
     */
    public String getPassword() {
        return password;
    }

    /**
     * sets the password from the user
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * gets the username from the user
     * @return username from user
     */
    public String getUserName() {
        return userName;
    }

    /**
     * sets the username from the user
     * @param userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }
}
