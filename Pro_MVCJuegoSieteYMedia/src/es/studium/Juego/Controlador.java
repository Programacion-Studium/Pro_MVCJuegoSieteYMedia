package es.studium.Juego;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
public class Controlador implements WindowListener, ActionListener 
{
	String BaseDatos="empresa";
	String UsuarioDB="root";
	String ClaveBD="Studium.2019;";
	int contador = 0;
	Vista vista = null;
	Modelo modelo = null;
	Connection conexion = null;
	public Controlador(Vista vista, Modelo modelo) 
	{
		this.vista = vista;
		this.modelo = modelo;
		// Añadir los Listeners a las opciones de menú, a los botones, al dialog y al Frame
		vista.mniJugar.addActionListener(this);
		vista.mniListaJugadores.addActionListener(this);
		vista.mniAyuda.addActionListener(this);
		vista.btnAceptar.addActionListener(this);
		vista.btnLimpiar.addActionListener(this);
		
		
		vista.dlgUsuario.addWindowListener(this);
		vista.addWindowListener(this);
	}
	@Override
	public void actionPerformed(ActionEvent evento) 
	{
		Object a = evento.getSource();
		// Evento de menu Jugar.
		if (a.equals(vista.mniJugar)) 
		{
			// Asigno la etiqueta al botón
			
						vista.btnAceptar.setLabel("Aceptar");
						vista.btnLimpiar.setLabel("Limpiar");
						// Añadimos el panel al Dialog
						vista.dlgUsuario.add(vista.pnlUsuario);
						// Asignamos un tamaño al Dialog
						vista.dlgUsuario.setSize(190, 150);
						// Hacemos visible el Dialog
						vista.dlgUsuario.setVisible(true);
		}
		// Evento de menu Lista Jugadores.
		if (a.equals(vista.mniListaJugadores)) 
		{
			vista.dlgUsuariosLista.setLayout(new FlowLayout());
			conexion = modelo.conectar();
			String[] data1 = modelo.consultarJugadores(conexion).split("#");
			modelo.desconectar(conexion);
			 //creamos el arreglo de objetos que contendra el
			 //contenido de las columnas
			 Object[] data = new Object[3];
			// creamos el modelo de Tabla
			 DefaultTableModel dtm= new DefaultTableModel();
			 // se crea la Tabla con el modelo DefaultTableModel
			 final JTable table = new JTable(dtm);
			 // insertamos las columnas
			 dtm.addColumn("Nombre Jugador");
			 dtm.addColumn("Puntuacion");
			 // insertamos el contenido de las columnas
			 for(int row = 0; row < data1.length;) {
			 data[0] = data1[row];
			 data[1] = data1[row+1];
			 dtm.addRow(data);
			 row=row+2;
			 }
			 //se define el tamaño
			 table.setPreferredScrollableViewportSize(new Dimension(400, 120));
			 //Creamos un JscrollPane y le agregamos la JTable
			 JScrollPane scrollPane = new JScrollPane(table);
			 //Agregamos el JScrollPane al contenedor
			 vista.dlgUsuariosLista.getContentPane().add(scrollPane, BorderLayout.CENTER);
			 //manejamos la salida
			 vista.dlgUsuariosLista.addWindowListener(new WindowAdapter() {});
			vista.dlgUsuariosLista.setResizable(false);
			vista.dlgUsuariosLista.setLocationRelativeTo(null);
			table.setEnabled(false); //Evita que se pueda editar la tabla.
			// Añadimos el panel al Dialog
			vista.dlgUsuariosLista.add(vista.pnlUsuarioLista);
			// Asignamos un tamaño al Dialog
			vista.dlgUsuariosLista.setSize(500, 200);
			// Hacemos visible el Dialog
			vista.dlgUsuariosLista.setVisible(true);
		}
		// Evento de menu Ayuda.
		if (a.equals(vista.mniAyuda)) 
		{
			modelo.Ayuda();
				}
		// Evento de boton aceptar
		if (a.equals(vista.btnAceptar)) 
		{

			String nombreJugador = vista.txtNombreJugador.getText();
			System.out.println(nombreJugador.length()
					);
			if(nombreJugador.length()>0)
			{
			vista.setVisible(false);
			new Juego(nombreJugador);
			}
		}
		// Evento de boton Borrar.
		Object ab = evento.getSource();
		if (ab.equals(vista.btnLimpiar)) 
		{
			vista.txtNombreJugador.selectAll();
			vista.txtNombreJugador.setText("");
			
		}
		
	}

	@Override
	public void windowActivated(WindowEvent arg0) 
	{
		// TODO Auto-generated method stub
	}
	@Override
	public void windowClosed(WindowEvent arg0) 
	{
		// TODO Auto-generated method stub
	}
	@Override
	public void windowClosing(WindowEvent we) 
	{
		//Lo pongo para forzar a salir.
		if (vista.dlgUsuario.isActive() || vista.dlgUsuariosLista.isActive())
		{
			// Funciones que inician el valor de los componentes del Dialog.
			// Elimina todos los componentes del Dialog.
			vista.dlgUsuario.removeAll();
			vista.dlgUsuario.setVisible(false);
			vista.dlgUsuariosLista.removeAll();
			vista.dlgUsuariosLista.setVisible(false);
		} else if ((vista.dlgUsuario.isActive()) & (vista.dlgUsuariosLista.isActive()))
		{
			// Funciones que inician el valor de los componentes del Dialog.
			// Elimina todos los componentes del Dialog.
			vista.dlgUsuario.removeAll();
			vista.dlgUsuario.setVisible(false);
			vista.dlgUsuariosLista.removeAll();
			vista.dlgUsuariosLista.setVisible(false);
		} else if (vista.dlgUsuariosLista.isActive()) 
		{
			// Funciones que inician el valor de los componentes del Dialog.
			// Elimina todos los componentes del Dialog.
			vista.dlgUsuariosLista.removeAll();
			// Ocultamos el cuadro de diálogo.
			vista.dlgUsuariosLista.setVisible(false);
		}else if (vista.dlgUsuario.isActive()) 
		{
			// Funciones que inician el valor de los componentes del Dialog.
			// Elimina todos los componentes del Dialog.
			vista.dlgUsuario.removeAll();
			// Ocultamos el cuadro de diálogo.
			vista.dlgUsuario.setVisible(false);
		} 
		else
		{
			System.exit(0);
		}
		
	}
	@Override
	public void windowDeactivated(WindowEvent arg0) 
	{
		// TODO Auto-generated method stub
	}
	@Override
	public void windowDeiconified(WindowEvent arg0) 
	{
		// TODO Auto-generated method stub
	}
	@Override
	public void windowIconified(WindowEvent arg0) 
	{
		// TODO Auto-generated method stub
	}
	@Override
	public void windowOpened(WindowEvent arg0) 
	{
		// TODO Auto-generated method stub
	}
}
