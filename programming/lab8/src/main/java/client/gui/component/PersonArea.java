package client.gui.component;

import lib.schema.Person;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

public class PersonArea extends JPanel {
    private final List<PersonVisualization> persons;
    private PersonVisualization selectedPerson;
    private List<Consumer<PersonVisualization>> selectionListeners;

    public PersonArea() {
        this.persons = new ArrayList<>();
        this.selectedPerson = null;
        this.selectionListeners = new CopyOnWriteArrayList<>();

        this.setBackground(Color.WHITE);

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int centerX = getWidth() / 2;
                int centerY = getHeight() / 2;
                for (PersonVisualization person : persons) {
                    if (person.containsPoint(e.getPoint(), centerX, centerY)) {
                        setSelectedPerson(person);
                        repaint();
                        return;
                    }
                    setSelectedPerson(null);
                    repaint();
                }
            }
        });
    }
    public void clearPersons() {
        persons.clear();
        repaint();
    }

    public void addPerson(PersonVisualization person) {
        this.persons.add(person);
    }


    public void addPersonWithAnimation(PersonVisualization person) {
        addPerson(person);

        Timer timer = new Timer(20, actionEvent -> {
            var alpha = person.getAlpha();
            if (alpha < 255) {
                person.setAlpha(alpha + 5);
            } else {
                ((Timer) actionEvent.getSource()).stop();
            }
            repaint();
        });

        timer.setInitialDelay(500);
        timer.start();
    }

    public void removePerson(PersonVisualization person) {
        this.persons.remove(person);
        if (this.selectedPerson == person) {
            this.setSelectedPerson(null);
        }
    }

    public void removePersonByID(int personID) {
        var person = persons.stream().filter(p -> p.getPersonID() == personID).findFirst();
        person.ifPresent(this::removePerson);
    }

    public void replacePerson(PersonVisualization oldPerson, PersonVisualization newPerson) {
        persons.add(newPerson);
        persons.remove(oldPerson);
        if (this.selectedPerson == oldPerson) {
            this.setSelectedPerson(newPerson);
        }
        this.repaint();
    }

    public void replacePersonByID(int personID, PersonVisualization person) {
        this.persons.add(person);

        var oldPersonOptional = this.persons.stream().filter(p -> p.getPersonID() == personID).findFirst();
        if (oldPersonOptional.isPresent()) {
            var oldPerson = oldPersonOptional.get();
            this.persons.remove(oldPerson);
            if (selectedPerson == oldPerson) {
                this.setSelectedPerson(person);
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;

        for (PersonVisualization person : this.persons) {
            person.draw(g, centerX, centerY);
            if (person == selectedPerson) {
                person.drawSelection((Graphics2D) g.create(), centerX, centerY);
            }
        }
    }

    public PersonVisualization getSelectedPerson() {
        return this.selectedPerson;
    }

    public void setSelectedPerson(PersonVisualization person) {
        this.selectedPerson = person;
        this.notifySelectionListeners(this.selectedPerson);
    }

    public void addSelectionListener(Consumer<PersonVisualization> listener) {
        this.selectionListeners.add(listener);
    }

    public void notifySelectionListeners(PersonVisualization person) {
        for (var listener : this.selectionListeners) {
            listener.accept(person);
        }
    }

    public static class PersonVisualization {
        private final Color SKIN_COLOR = new Color(255, 235, 192);
        private Person person;
        private Color color;
        private int alpha = 0;

        public PersonVisualization(Person person, Color color) {
            this.person = person;
            this.color = color;
        }

        public int getPersonID() {
            return this.person.getID();
        }

        public int getOwnerID() {
            return this.person.getOwnerID();
        }

        public Color getColor() {
            return this.color;
        }

        public int getAlpha() {
            return this.alpha;
        }

        public void setAlpha(int alpha) {
            this.alpha = alpha;
        }

        public boolean containsPoint(Point point, int centerX, int centerY) {
            int size = (int)this.person.getHeight();
            int coordinatesX = (int)this.person.getCoordinatesX();
            int coordinatesY = this.person.getCoordinatesY();

            int bodyWidth = size;
            int bodyHeight = size;

            int x = centerX + coordinatesX - bodyWidth / 2;
            int y = centerY + coordinatesY - bodyHeight / 2;

            int headDiameter = size / 3;
            int headX = centerX + coordinatesX - headDiameter / 2;
            int headY = centerY + coordinatesY - bodyHeight / 2 - headDiameter;

            Rectangle bodyRect = new Rectangle(x, y, bodyWidth, bodyHeight);
            Rectangle headRect = new Rectangle(headX, headY, headDiameter, headDiameter);

            return bodyRect.contains(point) || headRect.contains(point);
        }

        public void draw(Graphics g, int centerX, int centerY) {
            int size = (int)this.person.getHeight();
            int coordinatesX = (int)this.person.getCoordinatesX();
            int coordinatesY = this.person.getCoordinatesY();

            var color = new Color(this.color.getRed(), this.color.getGreen(), this.color.getBlue(), alpha);
            var skinColor = new Color(SKIN_COLOR.getRed(), SKIN_COLOR.getGreen(), SKIN_COLOR.getBlue(), alpha);

            int bodyWidth = size;
            int bodyHeight = size;
            int headDiameter = size / 3;

            int x = centerX + coordinatesX - bodyWidth / 2;
            int y = centerY + coordinatesY - bodyHeight / 2;

            // Draw body
            g.setColor(color);
            g.fillRect(x, y, bodyWidth, bodyHeight);

            // Draw hands
            int handDiameter = size / 5;
            int handY = y + bodyHeight / 2 - handDiameter / 2;
            g.setColor(skinColor);
            g.fillOval(x - handDiameter, handY, handDiameter, handDiameter);
            g.fillOval(x + bodyWidth, handY, handDiameter, handDiameter);

            // Draw head
            int headX = centerX + coordinatesX - headDiameter / 2;
            int headY = centerY + coordinatesY - bodyHeight / 2 - headDiameter;
            g.setColor(skinColor);
            g.fillOval(headX, headY, headDiameter, headDiameter);
        }

        public void drawSelection(Graphics2D g2d, int centerX, int centerY) {
            int size = (int)this.person.getHeight();
            int coordinatesX = (int)this.person.getCoordinatesX();
            int coordinatesY = this.person.getCoordinatesY();

            int bodyWidth = size;
            int bodyHeight = size;

            int x = centerX + coordinatesX - bodyWidth / 2;
            int y = centerY + coordinatesY - bodyHeight / 2;

            int headDiameter = size / 3;
            int headX = centerX + coordinatesX - headDiameter / 2;
            int headY = centerY + coordinatesY - bodyHeight / 2 - headDiameter;

            int handDiameter = size / 5;
            int handY = y + bodyHeight / 2 - handDiameter / 2;

            // white fill
            g2d.setColor(Color.WHITE);
            g2d.setStroke(new BasicStroke(3));
            // body
            g2d.fillRect(x, y, bodyWidth, bodyHeight);
            // head
            g2d.fillOval(headX, headY, headDiameter, headDiameter);
            // hands
            g2d.fillOval(x - handDiameter, handY, handDiameter, handDiameter);
            g2d.fillOval(x + bodyWidth, handY, handDiameter, handDiameter);

            // black outline
            g2d.setColor(Color.BLACK);
            g2d.setStroke(new BasicStroke(1));
            // body
            g2d.drawRect(x, y, bodyWidth, bodyHeight);
            // head
            g2d.drawOval(headX, headY, headDiameter, headDiameter);
            // hands
            g2d.drawOval(x - handDiameter, handY, handDiameter, handDiameter);
            g2d.drawOval(x + bodyWidth, handY, handDiameter, handDiameter);
        }
    }
}
