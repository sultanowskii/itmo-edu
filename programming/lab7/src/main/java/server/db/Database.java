package server.db;

import lib.date.ZonedDateTimeConverter;
import lib.schema.*;
import org.postgresql.ds.PGSimpleDataSource;
import server.schema.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashSet;
import java.util.concurrent.locks.ReentrantLock;

public class Database {
    private PGSimpleDataSource dataSource;
    private final ReentrantLock lock = new ReentrantLock();

    public Database(String host, int port, String dbName, String username, String password) {
        this.dataSource = new PGSimpleDataSource();
        dataSource.setServerNames(new String[]{host});
        dataSource.setPortNumbers(new int[]{port});
        dataSource.setDatabaseName(dbName);
        dataSource.setUser(username);
        dataSource.setPassword(password);
    }

    public User addUser(String login, String hashedPassword) throws SQLException {
        lock.lock();
        Connection connection;
        try {
            connection = dataSource.getConnection();

            var statement = connection.prepareStatement(
                "INSERT INTO users (login, hashed_password) VALUES (?, ?) RETURNING id"
            );
            statement.setString(1, login);
            statement.setString(2, hashedPassword);

            if (!statement.execute()) {
                throw new SQLException("Can't add user. Try again later.");
            }

            ResultSet resultSet = statement.getResultSet();

            if (!resultSet.next()) {
                throw new SQLException("Can't add user. Try again later.");
            }

            int id = resultSet.getInt("id");
            if (id > 0) {
                return new User(id, login, hashedPassword);
            }
        } catch (SQLException e) {
            throw new SQLException("Can't add user. Try again later. Reason: " + e.getMessage());
        } finally {
            lock.unlock();
        }

        return null;
    }

    public boolean loginIsAvailable(String login) throws SQLException {
        lock.lock();
        Connection connection;
        try {
            connection = dataSource.getConnection();

            var statement = connection.prepareStatement(
                "SELECT COUNT(*) FROM users WHERE login = ?"
            );
            statement.setString(1, login);

            if (!statement.execute()) {
                throw new SQLException("Can't fetch users. Try again later.");
            }

            ResultSet resultSet = statement.getResultSet();

            if (!resultSet.next()) {
                throw new SQLException("Can't fetch users. Try again later.");
            }

            return resultSet.getInt(1) == 0;
        } catch (SQLException e) {
            throw new SQLException("Can't get user. Reason: " + e.getMessage());
        } finally {
            lock.unlock();
        }
    }

    public User getUser(String login, String hashedPassword) throws SQLException {
        lock.lock();
        Connection connection;
        try {
            connection = dataSource.getConnection();

            var statement = connection.prepareStatement(
                "SELECT id, login, hashed_password FROM users WHERE login = ? AND hashed_password = ?"
            );
            statement.setString(1, login);
            statement.setString(2, hashedPassword);

            if (!statement.execute()) {
                throw new SQLException("Can't get user. Try again later.");
            }

            ResultSet resultSet = statement.getResultSet();

            if (!resultSet.next()) {
                return null;
            }

            var user = new User();
            user.setID(resultSet.getInt("id"));
            user.setLogin(resultSet.getString("login"));
            user.setHashedPassword(resultSet.getString("hashed_password"));
            return user;
        } catch (SQLException e) {
            throw new SQLException("Can't get user. Reason: " + e.getMessage());
        } finally {
            lock.unlock();
        }
    }

    private int addLocation(User user, Location location) throws SQLException {
        lock.lock();
        Connection connection;
        try {
            connection = dataSource.getConnection();

            var statement = connection.prepareStatement(
                "INSERT INTO location (x, y, name, owner_id) VALUES (?, ?, ?, ?) RETURNING id"
            );
            statement.setDouble(1, location.getX());
            statement.setInt(2, location.getY());
            statement.setString(3, location.getName());
            statement.setInt(4, user.getID());

            if (!statement.execute()) {
                throw new SQLException("Can't add location. Try again later.");
            }

            ResultSet resultSet = statement.getResultSet();

            if (!resultSet.next()) {
                throw new SQLException("Can't add location. Try again later.");
            }

            location.setOwnerID(user.getID());

            return resultSet.getInt("id");
        } catch (SQLException e) {
            throw new SQLException("Can't add location. Reason: " + e.getMessage());
        } finally {
            lock.unlock();
        }
    }

    private int addCoordinates(User user, Coordinates coordinates) throws SQLException {
        lock.lock();
        Connection connection;
        try {
            connection = dataSource.getConnection();

            var statement = connection.prepareStatement(
                "INSERT INTO coordinates (x, y, owner_id) VALUES (?, ?, ?) RETURNING id"
            );
            statement.setFloat(1, coordinates.getX());
            statement.setInt(2, coordinates.getY());
            statement.setInt(3, user.getID());

            if (!statement.execute()) {
                throw new SQLException("Can't add coordinates. Try again later.");
            }

            ResultSet resultSet = statement.getResultSet();

            if (!resultSet.next()) {
                throw new SQLException("Can't add coordinates. Try again later.");
            }

            coordinates.setOwnerID(user.getID());

            return resultSet.getInt("id");
        } catch (SQLException e) {
            throw new SQLException("Can't add coordinates. Reason: " + e.getMessage());
        } finally {
            lock.unlock();
        }
    }

    public int addPerson(User user, Person person) throws SQLException {
        lock.lock();
        Connection connection;
        try {
            connection = dataSource.getConnection();

            var coordinatesID = this.addCoordinates(user, person.getCoordinates());
            var locationID = this.addLocation(user, person.getLocation());

            var statement = connection.prepareStatement(
                "INSERT INTO person (name, coordinates_id, creation_date, height, passport_id, eye_color, nationality, location_id, owner_id) VALUES (?, ?, ?, ?, ?, ?::color, ?::country, ?, ?) RETURNING id"
            );

            statement.setString(1, person.getName());
            statement.setInt(2, coordinatesID);
            statement.setTimestamp(3, ZonedDateTimeConverter.toDBTimestamp(person.getCreationDate()));
            statement.setLong(4, person.getHeight());
            statement.setString(5, person.getPassportID());
            statement.setString(6, person.getEyeColor().name());
            statement.setString(7, person.getNationality().name());
            statement.setInt(8, locationID);
            statement.setInt(9, user.getID());

            if (!statement.execute()) {
                throw new SQLException("Can't add person. Try again later.");
            }

            ResultSet resultSet = statement.getResultSet();

            if (!resultSet.next()) {
                throw new SQLException("Can't add person. Try again later.");
            }

            person.setOwnerID(user.getID());

            return resultSet.getInt("id");
        } catch (SQLException e) {
            throw new SQLException("Can't add person. Reason: " + e.getMessage());
        } finally {
            lock.unlock();
        }
    }

    private boolean updateLocation(User user, int locationID, Location location) throws SQLException {
        lock.lock();
        Connection connection;
        try {
            connection = dataSource.getConnection();

            var statement = connection.prepareStatement(
                "UPDATE location SET x = ?, y = ?, name = ? WHERE id = ? AND owner_id = ?"
            );
            statement.setDouble(1, location.getX());
            statement.setInt(2, location.getY());
            statement.setString(3, location.getName());
            statement.setInt(4, locationID);
            statement.setInt(5, user.getID());
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new SQLException("Can't update location with id=" + locationID + ". Reason: " + e.getMessage());
        } finally {
            lock.unlock();
        }
    }

    private boolean updateCoordinates(User user, int coordinatesID, Coordinates coordinates) throws SQLException {
        lock.lock();
        Connection connection;
        try {
            connection = dataSource.getConnection();

            var statement = connection.prepareStatement(
                "UPDATE coordinates SET x = ?, y = ? WHERE id = ? AND owner_id = ?"
            );
            statement.setFloat(1, coordinates.getX());
            statement.setInt(2, coordinates.getY());
            statement.setInt(3, coordinatesID);
            statement.setInt(4, user.getID());
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new SQLException("Can't update coordinates with id=" + coordinatesID + ". Reason: " + e.getMessage());
        } finally {
            lock.unlock();
        }
    }

    public boolean updatePerson(User user, int personID, Person person) throws SQLException {
        lock.lock();
        Connection connection;
        try {
            connection = dataSource.getConnection();

            var statementGetRelated = connection.prepareStatement(
                "SELECT coordinates_id, location_id FROM person WHERE id = ? AND owner_id = ?"
            );
            statementGetRelated.setInt(1, personID);
            statementGetRelated.setInt(2, user.getID());

            if (!statementGetRelated.execute()) {
                throw new SQLException("Can't fetch location and coordinates of person. Is it still presented?");
            }
            var resultSet = statementGetRelated.getResultSet();

            if (!resultSet.next()) {
                throw new SQLException("Can't fetch location and coordinates of person. Is it still presented?");
            }

            int coordinatesID = resultSet.getInt("coordinates_id");
            int locationID = resultSet.getInt("location_id");

            this.updateCoordinates(user, coordinatesID, person.getCoordinates());
            this.updateLocation(user, locationID, person.getLocation());

            var statement = connection.prepareStatement(
                "UPDATE person SET name = ?, height = ?, passport_id = ?, eye_color = ?::color, nationality = ?::country WHERE id = ? AND owner_id = ?"
            );

            statement.setString(1, person.getName());
            statement.setLong(2, person.getHeight());
            statement.setString(3, person.getPassportID());
            statement.setString(4, person.getEyeColor().name());
            statement.setString(5, person.getNationality().name());
            statement.setInt(6, personID);
            statement.setInt(7, user.getID());

            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new SQLException("Can't update person with id=" + personID + ". Reason: " + e.getMessage());
        } finally {
            lock.unlock();
        }
    }

    public LinkedHashSet<Person> getAllPersons() throws SQLException {
        lock.lock();
        Connection connection;
        try {
            connection = dataSource.getConnection();

            var statement = connection.prepareStatement(
                "SELECT " +
                "person.id id, " +
                "person.name name, " +
                "person.creation_date creation_date, " +
                "person.height height, " +
                "person.passport_id passport_id, " +
                "person.eye_color eye_color, " +
                "person.nationality nationality, " +
                "location.id location_id, " +
                "location.x location_x, " +
                "location.y location_y, " +
                "location.name location_name, " +
                "coordinates.id coordinates_id, " +
                "coordinates.x coordinates_x, " +
                "coordinates.y coordinates_y " +
                "FROM person " +
                "INNER JOIN location ON person.location_id = location.id " +
                "INNER JOIN coordinates ON person.coordinates_id = coordinates.id "
            );

            statement.execute();

            LinkedHashSet<Person> persons = new LinkedHashSet<>();

            var resultSet = statement.getResultSet();

            while (resultSet.next()) {
                var coordinates = new Coordinates();
                coordinates.setX(resultSet.getFloat("coordinates_x"));
                coordinates.setY(resultSet.getInt("coordinates_y"));
                coordinates.setID(resultSet.getInt("coordinates_id"));

                var location = new Location();
                location.setX(resultSet.getDouble("location_x"));
                location.setY(resultSet.getInt("location_y"));
                location.setName(resultSet.getString("location_name"));
                location.setID(resultSet.getInt("location_id"));

                Person person = new Person();
                person.setID(resultSet.getInt("id"));
                person.setName(resultSet.getString("name"));
                person.setCreationDate(ZonedDateTimeConverter.fromDBTimestamp(resultSet.getTimestamp("creation_date")));
                person.setHeight(resultSet.getLong("height"));
                person.setPassportID(resultSet.getString("passport_id"));
                person.setEyeColor(Color.valueOf(resultSet.getString("eye_color")));
                person.setNationality(Country.valueOf(resultSet.getString("nationality")));
                person.setCoordinates(coordinates);
                person.setLocation(location);

                persons.add(person);
            }

            return persons;
        } catch (SQLException e) {
            throw new SQLException("Can't fetch persons. Reason: " + e.getMessage());
        } finally {
            lock.unlock();
        }
    }

    public int deleteAllPersons(User user) throws SQLException {
        lock.lock();
        Connection connection;
        try {
            connection = dataSource.getConnection();

            var statement = connection.prepareStatement(
                "DELETE FROM person WHERE owner_id = ?"
            );
            statement.setInt(1, user.getID());
            int deletedCount = statement.executeUpdate();
            statement.getResultSet();

            var statementDeleteCoordinates = connection.prepareStatement(
                "DELETE FROM coordinates WHERE owner_id = ?"
            );
            statementDeleteCoordinates.setInt(1, user.getID());
            statementDeleteCoordinates.execute();
            statement.getResultSet();

            var statementDeleteLocation = connection.prepareStatement(
                "DELETE FROM location WHERE owner_id = ?"
            );
            statementDeleteLocation.setInt(1, user.getID());
            statementDeleteLocation.execute();
            statement.getResultSet();

            return deletedCount;
        } catch (SQLException e) {
            throw new SQLException("Can't delete persons. Reason: " + e.getMessage());
        } finally {
            lock.unlock();
        }
    }

    public boolean deletePersonByID(User user, int personID) throws SQLException {
        lock.lock();
        Connection connection;
        try {
            connection = dataSource.getConnection();

            var statement = connection.prepareStatement(
                "DELETE FROM person WHERE owner_id = ? AND id = ? RETURNING coordinates_id, location_id"
            );
            statement.setInt(1, user.getID());
            statement.setInt(2, personID);
            statement.execute();

            var resultSet = statement.getResultSet();

            if (!resultSet.next()) {
                throw new SQLException(
                    "Can't fetch location and coordinates of person. Probably there is no person with such id that belongs to you."
                );
            }

            int coordinatesID = resultSet.getInt("coordinates_id");
            int locationID = resultSet.getInt("location_id");

            var statementDeleteCoordinates = connection.prepareStatement(
                "DELETE FROM coordinates WHERE owner_id = ? AND id = ?"
            );
            statementDeleteCoordinates.setInt(1, user.getID());
            statementDeleteCoordinates.setInt(2, coordinatesID);
            statementDeleteCoordinates.execute();
            statement.getResultSet();

            var statementDeleteLocation = connection.prepareStatement(
                "DELETE FROM location WHERE owner_id = ? AND id = ?"
            );
            statementDeleteLocation.setInt(1, user.getID());
            statementDeleteLocation.setInt(2, locationID);
            statementDeleteLocation.execute();
            statement.getResultSet();

            return true;
        } catch (SQLException e) {
            throw new SQLException("Can't delete person with id=" + personID + ". Reason: " + e.getMessage());
        } finally {
            lock.unlock();
        }
    }
}
