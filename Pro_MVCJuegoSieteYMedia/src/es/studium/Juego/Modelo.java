package es.studium.Juego;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
public class Modelo {
	Double sumajuego2=0.0, sumajuego1=0.0;
	ArrayList<String> cartasjugador2 = new ArrayList<String>();
	ArrayList<String> cartasjugador1 = new ArrayList<String>();
	int puntuacion=0;
	
	String BaseDatos="juego";
	String login="root";
	String password="Studium.2019;";
	String driver = "com.mysql.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/"+BaseDatos+"?useJDBCCompliantTimezoneShift=true&serverTimezone=UTC&useSSL=false";
	
	Connection connection = null;
	Statement statement = null;
	ResultSet rs = null;

	  private Carta cartas[];
	  private int siguienteCarta;
	   //Cartas
    public static final int NumCartas = 40;
 
    public void Baraja() {
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
	
	
	
	public Connection conectar()
	{
		try
		{
			//Cargar los controladores para el acceso a la BD
			Class.forName(driver);
			//Establecer la conexión con la BD Empresa
			connection = DriverManager.getConnection(url, login, password);
		}
		catch (ClassNotFoundException cnfe)
		{
			System.out.println("Error 1-"+cnfe.getMessage());
		}
		catch (SQLException sqle)
		{
			System.out.println("Error 2-"+sqle.getMessage());
		}
		return connection;
	}
	public void puntuacionAlta(Connection c, String sentencia)
	{
		try
		{
			//Crear una sentencia
			statement = c.createStatement();
			statement.executeUpdate(sentencia);
			//Crear un objeto ResultSet para guardar lo obtenido
			//y ejecutar la sentencia SQL
		}
		catch (SQLException sqle)
		{
			System.out.println("Error 2-"+sqle.getMessage());
		}
	}
	public String consultarJugadores(Connection c)
	{
		String resultado = "";
		try
		{
			String sentencia = "SELECT * FROM puntuaciones order by puntuacion desc";
			//Crear una sentencia
			statement = c.createStatement();
			//Crear un objeto ResultSet para guardar lo obtenido
			//y ejecutar la sentencia SQL
			rs = statement.executeQuery(sentencia);
			while (rs.next())
			{
				resultado = resultado + rs.getString("nombre") + "#" +
						rs.getInt("puntuacion") + "#";
			}
		}
		catch (SQLException sqle)
		{
			System.out.println("Error 2-"+sqle.getMessage());
		}
		return (resultado);
	}
	public void desconectar(Connection c)
	{
		try
		{
			if(c!=null)
			{
				c.close();
			}
		}
		catch (SQLException e)
		{
			System.out.println("Error 3-"+e.getMessage());
		}
	}
    public static int generaNumeroEnteroAleatorio(int minimo, int maximo) {
        int num = (int) (Math.random() * (minimo - (maximo + 1)) + (maximo + 1));
        return num;
    }
    public void quienGana(int plantarseJ1) {
		if(sumajuego1>7.5) {
			System.out.println("Jugador 1 se ha pasado");
			System.out.println("Felicidades, Jugador 2 ha ganado");
		} else if (plantarseJ1==1)
		{
			if(sumajuego2==7.5) {
				System.out.println("Felicidades, Jugador 2 ha ganado");
			}else if(sumajuego2.equals(sumajuego1)) {
				System.out.println("Felicidades, Jugador 2 ha ganado");
			}else if(sumajuego1==7.5) {
				if(sumajuego2==7.5) {
					System.out.println("Felicidades, Jugador 2 ha ganado");
				}else {
					System.out.println("Felicidades, Jugador 1 ha ganado");
				}
			}else if(sumajuego1<7.5) {
				if((sumajuego2<7.5)&&(sumajuego1<sumajuego2)) {
					System.out.println("Felicidades, Jugador 2 ha ganado");
				}else if(sumajuego2>7.5){
					System.out.println("Felicidades, Jugador 1 ha ganado");
				}
			}
		}
	}
	public int puntuaciones() {
		if(sumajuego1>7.5) {
			System.out.println("Jugador 1 se ha pasado");
			System.out.println("Felicidades, Jugador 2 ha ganado");
		} else
		{
			if(sumajuego2==7.5) {
				System.out.println("Felicidades, Jugador 2 ha ganado");
			}else if(sumajuego2.equals(sumajuego1)) {
				System.out.println("Felicidades, Jugador 2 ha ganado");
			}else if(sumajuego1==7.5) {
				if(sumajuego2==7.5) {
					System.out.println("Felicidades, Jugador 2 ha ganado");
				}else {
					System.out.println("Felicidades, Jugador 1 ha ganado");
					puntuacion+=100;
				}
			}else if(sumajuego1<7.5) {
				if((sumajuego2<7.5)&&(sumajuego1<sumajuego2)) {
					System.out.println("Felicidades, Jugador 2 ha ganado");
				}else if(sumajuego2>7.5){
					System.out.println("Felicidades, Jugador 1 ha ganado");
					puntuacion+=75;
				}
			}
		}
		return puntuacion;
	}
	public Double partidaJugador1() {
		for (int i = 0; i < cartasjugador1.size(); i++) 
		{
			String[] splitJuego1= cartasjugador1.get(i).split("-");
			if(Integer.parseInt(splitJuego1[0])<=7) 
			{
				switch(i+1) {
				case 1:
					sumajuego1=Double.parseDouble(splitJuego1[0]);
					break;
				case 2:
					sumajuego1=sumajuego1+Double.parseDouble(splitJuego1[0]);
					break;
				case 3:
					sumajuego1=sumajuego1+Double.parseDouble(splitJuego1[0]);
					break;
				case 4:
					sumajuego1=sumajuego1+Double.parseDouble(splitJuego1[0]);
					break;
				case 5:
					sumajuego1=sumajuego1+Double.parseDouble(splitJuego1[0]);
					break;
				case 6:
					sumajuego1=sumajuego1+Double.parseDouble(splitJuego1[0]);
					break;
				case 7:
					sumajuego1=sumajuego1+Double.parseDouble(splitJuego1[0]);
					break;
				case 8:
					sumajuego1=sumajuego1+Double.parseDouble(splitJuego1[0]);
					break;
				case 9:
					sumajuego1=sumajuego1+Double.parseDouble(splitJuego1[0]);
					break;
				case 10:
					sumajuego1=sumajuego1+Double.parseDouble(splitJuego1[0]);
					break;
				case 11:
					sumajuego1=sumajuego1+Double.parseDouble(splitJuego1[0]);
					break;
				case 12:
					sumajuego1=sumajuego1+Double.parseDouble(splitJuego1[0]);
					break;
				}
			}else if(Integer.parseInt(splitJuego1[0])>7)
			{
				switch(i+1) {
				case 1:
					sumajuego1=0.5;
					break;
				case 2:
					sumajuego1=sumajuego1+0.5;
					break;
				case 3:
					sumajuego1=sumajuego1+0.5;
					break;
				case 4:
					sumajuego1=sumajuego1+0.5;
					break;
				case 5:
					sumajuego1=sumajuego1+0.5;
					break;
				case 6:
					sumajuego1=sumajuego1+0.5;
					break;
				case 7:
					sumajuego1=sumajuego1+0.5;
					break;
				case 8:
					sumajuego1=sumajuego1+0.5;
					break;
				case 9:
					sumajuego1=sumajuego1+0.5;
					break;
				case 10:
					sumajuego1=sumajuego1+0.5;
					break;
				case 11:
					sumajuego1=sumajuego1+0.5;
					break;
				case 12:
					sumajuego1=sumajuego1+0.5;
					break;
				}
			}
		}
		return this.sumajuego1;
	}
	public double partidaJugador2() {
		for (int i = 0; i < cartasjugador2.size(); i++) 
		{
			String[] splitJuego2= cartasjugador2.get(i).split("-");
			if(Integer.parseInt(splitJuego2[0])<=7) 
			{
				switch(i+1) {
				case 1:
					sumajuego2=Double.parseDouble(splitJuego2[0]);
					break;
				case 2:
					sumajuego2=sumajuego2+Double.parseDouble(splitJuego2[0]);
					break;
				case 3:
					sumajuego2=sumajuego2+Double.parseDouble(splitJuego2[0]);
					break;
				case 4:
					sumajuego2=sumajuego2+Double.parseDouble(splitJuego2[0]);
					break;
				case 5:
					sumajuego2=sumajuego2+Double.parseDouble(splitJuego2[0]);
					break;
				case 6:
					sumajuego2=sumajuego2+Double.parseDouble(splitJuego2[0]);
					break;
				case 7:
					sumajuego2=sumajuego2+Double.parseDouble(splitJuego2[0]);
					break;
				case 8:
					sumajuego2=sumajuego2+Double.parseDouble(splitJuego2[0]);
					break;
				case 9:
					sumajuego2=sumajuego2+Double.parseDouble(splitJuego2[0]);
					break;
				case 10:
					sumajuego2=sumajuego2+Double.parseDouble(splitJuego2[0]);
					break;
				case 11:
					sumajuego2=sumajuego2+Double.parseDouble(splitJuego2[0]);
					break;
				case 12:
					sumajuego2=sumajuego2+Double.parseDouble(splitJuego2[0]);
					break;
				}
			}else if(Integer.parseInt(splitJuego2[0])>7)
			{
				switch(i+1) {
				case 1:
					sumajuego2=0.5;
					break;
				case 2:
					sumajuego2=sumajuego2+0.5;
					break;
				case 3:
					sumajuego2=sumajuego2+0.5;
					break;
				case 4:
					sumajuego2=sumajuego2+0.5;
					break;
				case 5:
					sumajuego2=sumajuego2+0.5;
					break;
				case 6:
					sumajuego2=sumajuego2+0.5;
					break;
				case 7:
					sumajuego2=sumajuego2+0.5;
					break;
				case 8:
					sumajuego2=sumajuego2+0.5;
					break;
				case 9:
					sumajuego2=sumajuego2+0.5;
					break;
				case 10:
					sumajuego2=sumajuego2+0.5;
					break;
				case 11:
					sumajuego2=sumajuego2+0.5;
					break;
				case 12:
					sumajuego2=sumajuego2+0.5;
					break;
				}
			}
		}
		return sumajuego2;
	}
	public void Ayuda()
	{
			try
			{
				Runtime.getRuntime().exec("hh.exe Juego.chm");
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
	}
}
