package ssp;
import java.util.LinkedList;

public class TischSyncMonitor<E> implements Tisch<E> {

    private int tischMaxSize;
    private LinkedList<E> tisch;
    private String name = "";//der Name des letzten Thread der die put() Methode ausgeführt hat

    //      | Schere | Stein | Papier
    //------|---------------------------
    //Schere|    u   | Stein |  Schere
    //Stein |  Stein |   u   | Papier
    //Papier| Schere | Papier|   u
    // unentschieden = 0 | Spieler1 gewonnen = 1 | Spieler2 gewonnen = 2
    private int[][] auswertung = {{0, 2, 1}, {1, 0, 2}, {2, 1, 0}};

    //Index 0 = Unentschiden | Index 1 = Spieler1 | Index 2 = Spieler2
    private int[] ergebnisse = new int[3];

    public TischSyncMonitor(int tischSize){
        tischMaxSize = tischSize;
        tisch = new LinkedList<>();
    }

    @Override
    public synchronized void put(E item) throws InterruptedException {
        //die zweite Überprüfung verhindert, dass der selbe Thread zweimal etwas auf den Tisch legen will und somit mit sich selbst spielen würde.
        while (tisch.size() == tischMaxSize || name == Thread.currentThread().getName()){
            this.wait();
        }

        tisch.add(item);
        System.err.println("           ENTER: "
        + Thread.currentThread().getName()
        + " hat ein Objekt in den Puffer gelegt. Aktuelle Puffergroeße: "
        + tisch.size());

        this.notifyAll();
        name = Thread.currentThread().getName(); //aktualisiert name mit dem Namen des letzten Thread
    }

    @Override
    public synchronized void judge() throws InterruptedException {
        E item1;
        E item2;
        int res;

        while (tisch.size() < 2){
            this.wait(); // --> Warten in der Wait-Queue und Monitor des
            // Puffer freigeben
        }

        item1 = tisch.removeFirst();
        item2 = tisch.removeFirst();
        res = auswertung[getIndex(item1)][ getIndex(item2)];
        ergebnisse[res]++;

        System.err.println("      REMOVE: "
        + Thread.currentThread().getName()
        + " hat ein Objekt aus dem Puffer entnommen. Aktuelle Puffergroeße: "
        + tisch.size());

        this.notifyAll();
    }

    public int getIndex(E item){
        int res;
        if(item == SpielObjekt.SCHERE){
            res = 0;
        }else if(item ==SpielObjekt.STEIN){
            res = 1;
        }else{ // item == SpielObjekt.PAPIER
            res = 2;
        }
        return res;
    }

    @Override
    public int[] getErgebnisse(){
        return ergebnisse;
    }
}
