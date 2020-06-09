package es.studium.Juego;

public class Carta {
    
    //Atributos
    private int numero;
    private String palo;
     
    //Constantes
    public static final String[] Palos={"ESPADAS", "OROS", "COPAS", "BASTOS"};
    public static final int LimiteCartaPalo=12;
 
    //Constructor
    public Carta(int numero, String palo) {
        this.numero = numero;
        this.palo = palo;
    }
 
    @Override
    public String toString() {
        return numero +"-"+ palo;
    }
     
}