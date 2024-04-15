package web.lab4.server.model;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.persistence.*;
import java.util.Date;

@Entity
public class PointCheck {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name="id", nullable=false)
    private int id;

    @Column(name="x", nullable=false)
    private int x;

    @Column(name="y", nullable=false)
    private double y;

    @Column(name="r", nullable=false)
    private int r;

    @Column(name="is_hit", nullable=false)
    private boolean isHit;

    @Column(name="created_at", nullable=false)
    private Date createdAt;

    @ManyToOne
    private User user;

    public PointCheck() {

    }

    public PointCheck(int x, double y, int r) {
        this.x = x;
        this.y = y;
        this.r = r;
    }

    public PointCheck(int x, double y, int r, boolean isHit) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.isHit = isHit;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public int getR() {
        return r;
    }

    public void setR(int r) {
        this.r = r;
    }

    public boolean getIsHit() {
        return isHit;
    }

    public void setIsHit(boolean hit) {
        isHit = hit;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public JsonObject toJSONObject() {
        return Json.createObjectBuilder()
            .add("x", x)
            .add("y", y)
            .add("r", r)
            .add("isHit", isHit)
            .add("createdAt", createdAt.getTime())
            .build();
    }
}
