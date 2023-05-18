package server.schema;

public class User {
    private int id;
    private String login;
    private String hashedPassword;

    public User() {

    }

    public User(int id, String login, String hashedPassword) {
        this.id = id;
        this.login = login;
        this.hashedPassword = hashedPassword;
    }

    public int getID() {
        return this.id;
    }

    public String getLogin() {
        return this.login;
    }

    public String getHashedPassword() {
        return this.hashedPassword;
    }

    public void setID(int id) {
        this.id = id;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }
}
