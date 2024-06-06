package web.lab3.server.bean;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import web.lab3.server.controller.PointCheckStorageController;
import web.lab3.server.model.Coordinates;
import web.lab3.server.model.PointCheck;
import web.lab3.server.util.MBeanManager;

import javax.management.Notification;
import javax.management.NotificationBroadcasterSupport;
import java.util.Date;

@Named
@ApplicationScoped
public class PointChecker extends NotificationBroadcasterSupport implements PointCheckerMBean {

    @Inject
    MBeanManager mBeanManager;
    @Inject
    private PointCheckStorageController pointCheckStorageController;
    private int missedPoints = 0;
    Coordinates current = new Coordinates();

    @PostConstruct
    public void init() {
        mBeanManager.addBean("pointChecker", this);
    }

    @PreDestroy
    public void destroy() {
        mBeanManager.removeBean("pointChecker");
    }

    public void checkPoint() {
        var x = current.getX();
        var y = current.getY();
        var r = current.getR();

        var pointCheckResult = new PointCheck();
        pointCheckResult.setX(x);
        pointCheckResult.setY(y);
        pointCheckResult.setR(r);
        pointCheckResult.setIsHit(web.lab3.server.util.PointChecker.isPointIsnideArea(x, y, r));
        pointCheckResult.setCreatedAt(new Date(System.currentTimeMillis()));

        pointCheckStorageController.savePointCheck(pointCheckResult);

        if (!pointCheckResult.getIsHit()) {
            missedPoints++;
            if (missedPoints >= 2) {
                sendNotification(new Notification(
                        "MissedPoints",
                        this,
                        System.currentTimeMillis(),
                        "Two misses in a row"
                ));
                missedPoints = 0;
            }
        } else {
            missedPoints = 0;
        }
    }

    @Override
    public int getTotalPoints() {
        return pointCheckStorageController.getCheckPointList().size();
    }

    @Override
    public int getMissedPoints() {
        return (int) pointCheckStorageController.getCheckPointList().stream().filter(pc -> !pc.getIsHit()).count();
    }

    public Coordinates getCurrent() {
        return current;
    }
}