package ssp;

public class TischServer {
    public final int SIMULATION_TIME = 1000;// Simulationsdauer in ms
    public final int TISCH_SIZE = 2; // Groesse des Tischpuffers
    //public Tisch<SpielObjekt> tisch = new TischSyncMonitor<>(TISCH_SIZE);
    public Tisch<SpielObjekt> tisch = new TischSyncCondQueues<>(TISCH_SIZE);

    public static void main(String[] args){
                TischServer ts = new TischServer();
                int[] res = ts.startSimulation();
                System.out.println("Unentschieden: " + res[0]);
                System.out.println("Siege Spieler1: " + res[1]);
                System.out.println("Siege Spieler2: " + res[2]);
                int ges = res[0] + res[1] + res[2];
                System.out.println("Gesamte Anzahl der Spiele: " + ges);
    }

    public int[] startSimulation(){
        System.err.println("-------------------- START -------------------");
        System.err.println("-------------------- Typ: " + tisch.getClass());

        SchiedsrRichter schiri = new SchiedsrRichter(tisch);
        schiri.setName("Schiedsrichter");
        schiri.start();

        Spieler s1 = new Spieler(tisch);
        s1.setName("Spieler 1");
        s1.start();
        Spieler s2 = new Spieler(tisch);
        s2.setName("Spieler 2");
        s2.start();

        try{
            Thread.sleep(SIMULATION_TIME);
            System.err.println("-------------------- ENDE -------------------");

            schiri.interrupt();
            s1.interrupt();
            s2.interrupt();
        }catch (InterruptedException ex ){

        }
        return tisch.getErgebnisse();
    }
}
