package es.studium.Juego;
import java.awt.Button;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.Panel;
import java.awt.TextField;

import javax.swing.JFrame;
public class Vista extends Frame {
	private static final long serialVersionUID = 1L;
	//Creamos la barra de menu
		MenuBar barraMenu = new MenuBar();

		//Creamos los elementos del menu
		Menu mnuJuego = new Menu("Juego");
		Menu mnuLista = new Menu("Lista");
		Menu mnuAyuda = new Menu("Ayuda");
		//Creamos los elementos del menu trabajo
		MenuItem mniJugar = new MenuItem("Jugar");
		MenuItem mniListaJugadores = new MenuItem("Mejores Jugadores");
		MenuItem mniAyuda = new MenuItem("Ayuda");
	// Elementos del diálogo
	Dialog dlgUsuario = new Dialog(this, "Jugemos", true);
	JFrame dlgUsuariosLista = new JFrame("Lista de jugadores");
	
	Panel pnlUsuario = new Panel();
	Panel pnlUsuarioLista = new Panel();
	
	
	Button btnAceptar = new Button("Aceptar");
	Button btnLimpiar = new Button("Limpiar");
	Label lblNombreJugador = new Label("Nombre Jugador:");
	TextField txtNombreJugador = new TextField(20);
	
	// Campos de texto para introducir el valor y exponente del monomio

	public Vista() {
		setTitle("Menu juego");
		setLayout(new FlowLayout());
		//Añadimos los elementos de trabajo a menu trabajo
		
		mnuJuego.add(mniJugar);
		mnuLista.add(mniListaJugadores);
		mnuAyuda.add(mniAyuda);
		//Anadimos los menus a la barra de menu
		barraMenu.add(mnuJuego);
		barraMenu.add(mnuLista);
		barraMenu.add(mnuAyuda);
		
		//mostramos la barra de menu
				setMenuBar(barraMenu);
		// Montar los paneles
		
		
		pnlUsuario.add(lblNombreJugador);
		pnlUsuario.add(txtNombreJugador);
		pnlUsuario.add(btnAceptar);
		pnlUsuario.add(btnLimpiar);
		
		
		// Establecer el tamaño del Frame
		setSize(400, 150);
		
		
		// Mostrar el Frame en pantalla
		setVisible(true);
	}
}