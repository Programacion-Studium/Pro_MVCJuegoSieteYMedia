package es.studium.Juego;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
public class Juego extends Frame implements WindowListener, MouseListener
{
	Baraja baraja = new Baraja();
	Modelo modelo=new Modelo();
	int pedircarta=0, pedircarta02=0, partida=1;
	int plantarseJ1=0, plantarseJ2=0;
	int puntuacionpartida=0;
	
	Connection conexion = null;
	
	private static final long serialVersionUID = 1L;
	// Las coordenadas del los botones
	int posX1=650, posY1=360, posCX1=130, posCY1=39;
	int posX2=660, posY2=410, posCX2=110, posCY2=39;
	int posX3=640, posY3=300, posCX3=165, posCY3=39;
	// Las coordenadas del lugar donde pulse
	int pulX, pulY;
	//Aumento cartas
	int aumento=75;
	// Declarar el objeto Image
	Image imagen;
	Image imagenJugador1;
	Image imagenJugador2;
	Image pedirCarta;
	Image plantarse;
	Image ronda;
	Image nueva;
	// Declarar el objeto Toolkit para manejo de imágenes
	Toolkit herramienta;
	String Jugador1;
	public Juego(String jugador)
	{
		Jugador1=jugador;
		setTitle("Juego");
		setLocation(250,250);
		setSize(820,470);
		setResizable(false);
		addWindowListener(this);
		addMouseListener(this);
		// Establecer el método de trabajo con imágenes
		herramienta = getToolkit();
		// Especificar la ruta de la imagen o subirla a la carpeta del programa
		imagen = herramienta.getImage("Fondo2.jpg");
		pedirCarta = herramienta.getImage("pedircarta.jpg");
		plantarse = herramienta.getImage("plantarse.jpg");
		ronda = herramienta.getImage("ronda.jpg");
		nueva = herramienta.getImage("nueva.jpg");
		imagenJugador2 = herramienta.getImage("./img/"+modelo.cartasjugador1+".gif");
		setVisible(true);
}
	public void paint(Graphics g)
	{
		//Dibujar la imagen de fondo
		g.drawImage(imagen,10,40,this);
		//Dibujar puntuacion y ronda
		g.drawString("Puntos "+puntuacionpartida+"", 640, 60);
		if (partida<=10) {
		g.drawString("Ronda "+partida+"/10", 640, 80);}
		//Dibujar botones
		g.drawImage(pedirCarta,650,360,this);
		g.drawImage(plantarse,660,410,this);
		//Dibujar botones
		if (partida<10)
		{
			g.drawImage(ronda,640,300,this);
		}
		else
		{
			g.drawImage(nueva,640,300,this);
		}
		//Dibujar cuadrados
		//g.drawRect(posX1, posY1, posCX1, posCY1);
		//g.drawRect(posX2, posY2, posCX2, posCY2);
		//g.drawRect(posX3, posY3, posCX3, posCY3);

		//Dibujar las cartas
		for (int i = 0; i < modelo.cartasjugador2.size(); i++) 
		{
			imagenJugador1 = herramienta.getImage("./img/"+modelo.cartasjugador2.get(i)+".gif");
			if (i<8)
			{
				g.drawImage(imagenJugador1,25+aumento*i,40,this);
			}
			else 
			{
				g.drawImage(imagenJugador1,25+aumento*i-525,60,this);
			}
		}
		for (int z = 0; z < modelo.cartasjugador1.size(); z++) 
		{
			imagenJugador2 = herramienta.getImage("./img/"+modelo.cartasjugador1.get(z)+".gif");
			if (z<8)
			{
				g.drawImage(imagenJugador2,25+aumento*z,350,this);
			}
			else 
			{
				g.drawImage(imagenJugador2,25+aumento*z-525,330,this);
			}
		}
	}
	public void windowActivated(WindowEvent we) {}
	public void windowClosed(WindowEvent we) {}
	public void windowClosing(WindowEvent we)
	{
		puntuacionpartida=modelo.puntuaciones();
		conexion = modelo.conectar();
		String sentencia = "INSERT INTO puntuaciones VALUES(null,'"+Jugador1+"','"+puntuacionpartida+"')";
		modelo.puntuacionAlta(conexion, sentencia);
		//System.exit(0);
		setVisible(false);
	}
	public void windowDeactivated(WindowEvent we) {}
	public void windowDeiconified(WindowEvent we) {}
	public void windowIconified(WindowEvent we) {}
	public void windowOpened(WindowEvent we) {}
	public void mouseClicked(MouseEvent me)
	{
		//int posX1=650, posY1=360, posCX1=130, posCY1=39;
		//int posX2=660, posY2=410, posCX2=110, posCY2=39;
		// Obtenemos las coordenadas del lugar donde se ha pulsado con el ratón
		pulX = me.getX();
		pulY = me.getY();
		//Comprobamos que no nos hemos pasado ni nos hemos plantado
		if((modelo.partidaJugador1()<=7.5)&&(plantarseJ1==0)) 
		{
			// Comprobamos si las coordenadas del ratón están entre las del boton Pedir carta
			if((posX1<=pulX)&&(pulX<=posX1+posCX1)&&(posY1<=pulY)&&(pulY<=posY1+posCY1))
			{
				modelo.cartasjugador1.add(baraja.siguienteCarta()+"");
				System.out.println("Jugador 1 coje: "+modelo.cartasjugador1.get(pedircarta02));
				pedircarta02++;
				repaint();
			}
		}
		// Comprobamos si las coordenadas del ratón están entre las del boton Plantarse
		if((posX2<=pulX)&&(pulX<=posX2+posCX2)&&(posY2<=pulY)&&(pulY<=posY2+posCY2))
		{
			plantarseJ1=1;
		}
		//Comprobamos que no nos hemos pasado y que nos hemos plantado
		if ((plantarseJ1==1)&&(modelo.partidaJugador1()<=7.5))
		{
			while ((modelo.partidaJugador2()<7.5)&&(modelo.partidaJugador1()>modelo.partidaJugador2())) { //Indicamos que repita mientras sea menor a 7.5 y sea menor al jugador
				if(modelo.partidaJugador2()<7.5) //comprobamos que es menor de 7.5
				{
					if(modelo.partidaJugador1()>modelo.partidaJugador2()) {//comprobamos que es menor que el jugador
						modelo.cartasjugador2.add(baraja.siguienteCarta()+"");
						pedircarta++;
					}
				}
				modelo.partidaJugador2();
				modelo.quienGana(plantarseJ1);
			}
			if((modelo.partidaJugador2()>=7.5)||(modelo.partidaJugador2()>=modelo.partidaJugador1())) {
				plantarseJ2=1;
			}
			modelo.partidaJugador1();
			modelo.quienGana(plantarseJ1);
			repaint();
			}
		if (partida<=10)//Ponemos un contador de partidas, que cambiara los botones y las acciones a realizar.
		{
			// Comprobamos si las coordenadas del ratón están entre las del boton Siguiente partida o Nueva
			if((posX3<=pulX)&&(pulX<=posX3+posCX3)&&(posY3<=pulY)&&(pulY<=posY3+posCY3))
			{
				if (plantarseJ2==1) {
					puntuacionpartida=modelo.puntuaciones();
				}
				pedircarta=0;
				pedircarta02=0;
				modelo.sumajuego2=0.0;
				modelo.sumajuego1=0.0;
				modelo.cartasjugador2.clear();
				modelo.cartasjugador1.clear(); 
				plantarseJ1=0;
				plantarseJ2=0;
				baraja.barajar();
				partida++;
				repaint();
				
				System.out.println(partida);
			}
		}if (partida==11) {
			puntuacionpartida=modelo.puntuaciones();
			conexion = modelo.conectar();
			String sentencia = "INSERT INTO puntuaciones VALUES(null,'"+Jugador1+"','"+puntuacionpartida+"')";
			modelo.puntuacionAlta(conexion, sentencia);
			
			partida=1;
			modelo.cartasjugador1.clear();
			modelo.cartasjugador2.clear();
			pedircarta=0;
			pedircarta02=0;
			modelo.sumajuego2=0.0;
			modelo.sumajuego1=0.0;
			plantarseJ1=0;
			plantarseJ2=0;
			baraja.barajar();
			modelo.puntuacion=0;
			setVisible(false);
			new Principal();
			
		}
	}
	public void mouseEntered(MouseEvent me) {}
	public void mouseExited(MouseEvent me) {}
	public void mousePressed(MouseEvent me) {}
	public void mouseReleased(MouseEvent me) {}
}