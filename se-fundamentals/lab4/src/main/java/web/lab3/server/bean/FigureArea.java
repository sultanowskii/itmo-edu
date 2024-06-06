package web.lab3.server.bean;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import web.lab3.server.model.Coordinates;
import web.lab3.server.util.FigureAreaComputer;
import web.lab3.server.util.MBeanManager;

import javax.management.ObjectName;
import java.lang.management.ManagementFactory;

@Named
@ApplicationScoped
public class FigureArea implements FigureAreaMBean {

    @Inject
    MBeanManager mBeanManager;
    Coordinates current = new Coordinates();

    @PostConstruct
    public void init() {
        mBeanManager.addBean("figureArea", this);
    }

    @PreDestroy
    public void destroy() {
        mBeanManager.removeBean("figureArea");
    }

    public void setCoordinates(PointChecker pc) {
        current = pc.getCurrent();
    }

    @Override
    public double getArea() {
        var x = current.getX();
        var y = current.getY();
        var r = current.getR();

        return FigureAreaComputer.computeFigureArea(r);
    }
}
