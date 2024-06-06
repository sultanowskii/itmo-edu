package web.lab3.server.util;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

import javax.management.ObjectName;
import java.lang.management.ManagementFactory;
import java.util.HashMap;
import java.util.Map;

@Named
@ApplicationScoped
public class MBeanManager {
    private final Map<String, ObjectName> beans = new HashMap<>();

    public void addBean(String name, Object bean) {
        try {
            var objName = new ObjectName(String.format("web.lab3.server:type=%s,name=%s", bean.getClass().getName(), name));
            ManagementFactory.getPlatformMBeanServer().registerMBean(bean, objName);
            beans.put(name, objName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void removeBean(String name) {
        if (!beans.containsKey(name)) {
            return;
        }

        try {
            var objName = beans.get(name);
            ManagementFactory.getPlatformMBeanServer().unregisterMBean(objName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
