package ssp;
public interface Tisch<E> {
    //Legt ein Element auf den Tisch
    public void put(E item) throws InterruptedException;

    //Nimmt zwei Elemente vom Tisch und gibt aus wer gewonnen hat
    public void judge() throws InterruptedException;

    //Returnt ein Array welches die Anzahl der unentschiedenen Spiele, Siege Spieler1 und Siege Spieler2 enthält
    public int[] getErgebnisse();
}
