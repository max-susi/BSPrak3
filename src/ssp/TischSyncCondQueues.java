package ssp;
import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TischSyncCondQueues<E> implements Tisch<E>{
    private int tischMaxSize;
    private LinkedList<E> tisch;
    private String name = "";

    //      | Schere | Stein | Papier
    //------|---------------------------
    //Schere|    u   | Stein |  Schere
    //Stein |  Stein |   u   | Papier
    //Papier| Schere | Papier|   u
    // unentschieden = 0 | links gewonnen = 1 | rechts gewonnen = 2
    private int[][] auswertung = {{0, 2, 1}, {1, 0, 2}, {2, 1, 0}};

    //Index 0 = Unentschiden | Index 1 = Spieler1 | Index 2 = Spieler2
    private int[] ergebnisse = new int[3];

    private final Lock tischLock = new ReentrantLock();
    private final Condition notFull = tischLock.newCondition();
    private final Condition notEmpty = tischLock.newCondition();

    public TischSyncCondQueues(int tischSize){
        tischMaxSize = tischSize;
        tisch = new LinkedList<E>();
    }

    @Override
    public void put(E item) throws InterruptedException {
        // Zugriff auf Buffer sperren
        tischLock.lockInterruptibly();
        try{
            while (tisch.size() == tischMaxSize || name == Thread.currentThread().getName()){ // zweite Condition reduziert die Spiele extrem
                notFull.await(); // Warte auf Bedingung "not full" (--> eigene
                // Warteschlange!) und gib Zugriff frei
            }
            tisch.add(item);
            System.err
                    .println("          ENTER: "
                            + Thread.currentThread().getName()
                            + " hat ein Objekt in den Puffer gelegt. Aktuelle Puffergroesse: "
                            + tisch.size());

            // Gezielt einen wartenden Consumer wecken (spezielle
            // Warteschlange!)
            notEmpty.signal();
            name = Thread.currentThread().getName();
        }finally {
            tischLock.unlock();
        }
    }

    @Override
    public void judge() throws InterruptedException {
        E item1 = null;
        E item2 = null;
        int res;

        tischLock.lockInterruptibly();
        try {
            //Solange Puffer weniger als 2 Elemente hat ==> warten
            while(tisch.size() < 2){
                notEmpty.await();  // Warte auf Bedingung "not empty" (--> eigene
                // Warteschlange!) und gib Zugriff frei
            }
            item1 = tisch.removeFirst();
            item2 = tisch.removeFirst();

            res = auswertung[getIndex(item1)][getIndex(item2)];
            ergebnisse[res]++;

            System.err
                    .println("          REMOVE: "
                            + Thread.currentThread().getName()
                            + " hat ein Objekt aus dem Puffer entnommen. Aktuelle Puffergroesse: "
                            + tisch.size());
            // Gezielt einen wartenden Producer wecken (spezielle
            // Warteschlange!)
            notFull.signal();
        } finally {
            tischLock.unlock();
        }

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
