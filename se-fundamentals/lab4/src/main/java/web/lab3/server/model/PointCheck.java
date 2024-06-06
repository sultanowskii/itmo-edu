package web.lab3.server.model;

import jakarta.inject.Named;
import jakarta.persistence.*;
import java.util.Date;

@Entity
@Named
public class PointCheck {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name="id", nullable=false)
    private int id;

    @Column(name="x", nullable=false)
    private double x;

    @Column(name="y", nullable=false)
    private double y;

    @Column(name="r", nullable=false)
    private double r;

    @Column(name="is_hit", nullable=false)
    private boolean isHit;

    @Column(name="created_at", nullable=false)
    private Date createdAt;

    public PointCheck() {

    }

    public PointCheck(double x, double y, double r) {
        this.x = x;
        this.y = y;
        this.r = r;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getR() {
        return r;
    }

    public void setR(double r) {
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
}
