package es.studium.Juego;

public class Baraja {
    //Atributos
    private Carta cartas[];
    private int siguienteCarta;
    //Cartas
    public static final int NumCartas = 40;
 
    public Baraja() {
        this.cartas = new Carta[NumCartas];
        this.siguienteCarta = 0;
        crearBaraja(); //Creamos la baraja
        barajar(); // barajamos la baraja
    }
  //Crea la baraja ordenada solo es necesario crearla 1 vez
    private void crearBaraja() {
    	//Creamos la tabla palos y almacenamos los palos de la clase carta.
        String[] palos = Carta.Palos;
      //Recorremos los palos, se recorrera 4 veces.
        for (int i = 0; i < palos.length; i++) {
 
        	//Dentro de cada palo recorremos las cartas, se recorrera 12 veces.
            for (int j = 0; j < Carta.LimiteCartaPalo; j++) {
            	//Si J es 7 0 8 no hace nada (Con ello quitamos las cartas 8 y 9, ya que en el array contamos desde 0)
                if (!(j == 7 || j == 8)) {
                    if (j >= 9) {
                        //Solo para la sota, caballo y rey
                    	//Al saltarnos el 7 y el 8 tenemos que restar 2 a partir de esos numeros
                        cartas[((i * (Carta.LimiteCartaPalo - 2)) + (j - 2))] = new Carta(j + 1, palos[i]);
                        //System.out.println(cartas[((i * (Carta.LimiteCartaPalo - 2)) + (j - 2))]);
                    } else {
                        //Para los casos de 0 a 6
						//En la primera i=0*((LimiteCartaPalo=12)-2)+j=0
						//Es decir i * (Limite-2) + J
						//		   0	*  10	  +	0 = 0
						//		   0	*  10	  + 1 = 1
						// si i vale 1
						//			1	*  10	  +	0 = 10
						//		  	1	*  10	  + 1 = 11
						//con esto consegimos los numeros del array y metemos la carta.
                        cartas[((i * (Carta.LimiteCartaPalo - 2)) + j)] = new Carta(j + 1, palos[i]);
                        //System.out.println(cartas[((i * (Carta.LimiteCartaPalo - 2)) + j)]);
                    }
                }
            }
        }
    }
     //Baraja todas las cartas
    public void barajar() {
    	//System.out.println("Barajando cartas");
        int posAleatoria = 0;
        Carta aux;
        //Recorro las cartas
        for (int i = 0; i < cartas.length; i++) {
            posAleatoria = Modelo.generaNumeroEnteroAleatorio(0, NumCartas - 1);//Genera un numero aleatorio del 0 al 39
            aux = cartas[i];//Coje la carta guardada en la posicion i y la guarda en un auxiliar
            cartas[i] = cartas[posAleatoria];//sustituye la carta en la posicion i por la de la posicion aleatoria
            cartas[posAleatoria] = aux;//pone la carta guardada en el auxiliar en la posicion aleatoria
           // System.out.println(cartas[i]);
        }
        //La posición vuelve al inicio
        this.siguienteCarta = 0;
    }
    //Devuelve la posicion de la siguiente carta
    public Carta siguienteCarta() {
        Carta c = null;
        if (siguienteCarta == NumCartas) {
            System.out.println("Ya no hay mas cartas, baraja de nuevo");
        } else {
            c = cartas[siguienteCarta++];
        }
        return c;
    }
    //Indica el numero de cartas que hay disponibles
    public int cartasDisponible() {
        return NumCartas - siguienteCarta;
    }
}