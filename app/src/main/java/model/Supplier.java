package model;

/**
 * Created by Alexandru on 6/17/2017.
 */

public class Supplier {

    private long id;
    private String name;
    private String email;


    public Supplier() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
