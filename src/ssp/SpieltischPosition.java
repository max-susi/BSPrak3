package ssp;

public class SpieltischPosition {
    private Spieler spieler;
    private Spielobjekt spielobjekt;

    public SpieltischPosition(Spieler spieler, Spielobjekt spielobjekt) {
        this.spieler = spieler;
        this.spielobjekt = spielobjekt;
    }

    public Spieler getSpieler() {
        return spieler;
    }

    public void setSpieler(Spieler spieler) {
        this.spieler = spieler;
    }

    public Spielobjekt getSpielobjekt() {
        return spielobjekt;
    }

    public void setSpielobjekt(Spielobjekt spielobjekt) {
        this.spielobjekt = spielobjekt;
    }
}
