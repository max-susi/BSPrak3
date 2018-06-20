package mensa;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.concurrent.Semaphore;

public class Mensa {

    private ArrayList<Kasse> kassen = new ArrayList<>();
    private ArrayList<Student> studenten = new ArrayList<>();

    private Semaphore auswahl;

    public Mensa(int kassen, int studenten) {

        for (int i = 0; i < kassen; i++) {
            this.getKassen().add( new Kasse(i));
        }

        for (int i = 0; i < studenten; i++) {
            this.getStudenten().add( new Student(i, this));
        }

        auswahl = new Semaphore(1,true);
    }

    public void kassenOeffnen() {
        for (Student student : studenten) {
            student.start();
        }
    }

    public void kassenSchliessen() {
        for (Student student : studenten) {
            student.interrupt();
        }
        for (Student student : studenten) {
            try {
                student.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public Kasse anstellen() {
        try {
            auswahl.acquire();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        Kasse schnellsteKasse = getKassen().stream().min(Comparator.comparingInt(Kasse::getSchlangenlaenge)).get();
        schnellsteKasse.erhoeheSchlangenlaenge();

        auswahl.release();

        return schnellsteKasse;
    }

    public void kasseVerlassen(Kasse kasse) {
        try {
            auswahl.acquire();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        kasse.reduziereSchlangenlaenge();

        auswahl.release();
    }

    public ArrayList<Kasse> getKassen() {
        return kassen;
    }

    public ArrayList<Student> getStudenten() {
        return studenten;
    }
}
