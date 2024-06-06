package web.lab3.server.controller;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import web.lab3.server.model.PointCheck;

import java.util.List;

@Named
@ApplicationScoped
public class PointCheckStorageController {
    @PersistenceContext(unitName = "Eclipselink_JPA")
    private EntityManager entityManager;

    @Transactional
    public void savePointCheck(PointCheck pointCheck) {
        // Добавить точку в БД
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(pointCheck);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            System.out.println(e);
        }
    }

    public List<PointCheck> getCheckPointList() {
        return entityManager.createQuery("SELECT p FROM PointCheck p", PointCheck.class).getResultList();
    }
}
