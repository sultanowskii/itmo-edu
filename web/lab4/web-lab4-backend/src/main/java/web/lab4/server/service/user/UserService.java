package web.lab4.server.service.user;

import jakarta.ejb.Stateless;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import web.lab4.server.model.User;

@Stateless
public class UserService {

    @PersistenceContext
    private EntityManager entityManager;

    public boolean createUser(User user) {
        if (isUsernameOccupied(user.getUsername())) {
            return false;
        }

        updateUser(user);

        return true;
    }

    public void updateUser(User user) {
        EntityTransaction tr = entityManager.getTransaction();
        tr.begin();
        entityManager.persist(user);
        tr.commit();
    }

    public User getUserWithUsername(String username) {
        try {
            return entityManager.createQuery("SELECT user FROM User user WHERE user.username = :username", User.class)
                .setParameter("username", username)
                .getSingleResult();
        } catch (NoResultException | NonUniqueResultException ignored) {

        }
        return null;
    }

    public boolean isUserWithPasswordExist(String username, String password) {
        int userCount = entityManager.createQuery("SELECT user FROM User user WHERE user.username = :username AND user.password = :password", User.class)
                .setParameter("username", username)
                .setParameter("password", password)
                .getResultList().size();

        return userCount > 0;
    }

    public boolean isUsernameOccupied(String username) {
        int userCount = entityManager.createQuery("SELECT user FROM User user WHERE user.username = :username", User.class)
            .setParameter("username", username)
            .getResultList().size();

        return userCount > 0;
    }
}
