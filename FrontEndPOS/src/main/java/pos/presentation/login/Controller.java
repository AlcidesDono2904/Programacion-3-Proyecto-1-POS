package pos.presentation.login;

import pos.Application;
import pos.logic.Service;

import javax.swing.*;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Controller {
    View view;

    public Controller(View v){
        this.view = v;
        this.view.setController(this);
    }

    public void login(String usuario, String clave)throws Exception{
        if(Service.instance().login(usuario,clave)){
            JOptionPane.showMessageDialog(null,"login exitoso");
            //abrir ventana para POS
            Application.window = new JFrame();
            JTabbedPane tabbedPane = new JTabbedPane();
            Application.window.setContentPane(tabbedPane);

            Application.window.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
            }
        });

        /*pos.presentation.clientes.Model clientesModel= new pos.presentation.clientes.Model();
        pos.presentation.clientes.View clientesView = new pos.presentation.clientes.View();
        Application.clientesController = new pos.presentation.clientes.Controller(clientesView,clientesModel);
        Icon clientesIcon = new ImageIcon(Application.class.getResource("/pos/presentation/icons/client.png"));*/
//----------------------------------------------------------------------------------------------------------------------
       /* pos.presentation.cajeros.Model cajerosModel= new pos.presentation.cajeros.Model();
        pos.presentation.cajeros.View cajerosView = new pos.presentation.cajeros.View();
        Application.cajerosController = new pos.presentation.cajeros.Controller(cajerosView,cajerosModel);
        Icon cajerosIcon = new ImageIcon(Application.class.getResource("/pos/presentation/icons/cajero.png"));*/
//----------------------------------------------------------------------------------------------------------------------
        pos.presentation.productos.Model productosModel= new pos.presentation.productos.Model();
        pos.presentation.productos.View  productosView = new pos.presentation.productos.View();
        Application.productosController = new pos.presentation.productos.Controller(productosView,productosModel);
        Icon productosIcon = new ImageIcon(Application.class.getResource("/pos/presentation/icons/productos.png"));
//----------------------------------------------------------------------------------------------------------------------
        /*pos.presentation.facturacion.Model facturacionModel = new pos.presentation.facturacion.Model();
        pos.presentation.facturacion.View  facturacionView = new pos.presentation.facturacion.View(Application.clientesController,Application.cajerosController);
        Application.facturacionController = new pos.presentation.facturacion.Controller(facturacionView,facturacionModel);
        Icon facturaIcon = new ImageIcon(Application.class.getResource("/pos/presentation/icons/factura.png"));*/

//----------------------------------------------------------------------------------------------------------------------
       /* pos.presentation.historico.ModelHistorico historicoModel = new pos.presentation.historico.ModelHistorico();
        pos.presentation.historico.View historicoView = new pos.presentation.historico.View();
        Application.historicoController = new pos.presentation.historico.ControllerHistorico(historicoView,historicoModel);
        Icon historialIcon = new ImageIcon(Application.class.getResource("/pos/presentation/icons/historial.png"));*/


//----------------------------------------------------------------------------------------------------------------------
        /*pos.presentation.estadistica.Model estadisticaModel= new pos.presentation.estadistica.Model();
        pos.presentation.estadistica.View estadisticaView = new pos.presentation.estadistica.View();
        Application.estadisticaController= new pos.presentation.estadistica.Controller(estadisticaView,estadisticaModel);*/


//----------------------------------------------------------------------------------------------------------------------
        //PESTAÑAS
        //tabbedPane.addTab("Factura",facturaIcon,facturacionView.getPanel());
       // tabbedPane.addTab("Clientes  ",clientesIcon,clientesView.getPanel());
       // tabbedPane.addTab("Cajeros  ",cajerosIcon,cajerosView.getPanel());
        tabbedPane.addTab("Productos",productosIcon,productosView.getPanel());
       // tabbedPane.addTab("Estadistica",clientesIcon,estadisticaView.getPanel());
       // tabbedPane.addTab("Historico", historialIcon,historicoView.getPanel());
//----------------------------------------------------------------------------------------------------------------------
        tabbedPane.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JTabbedPane sourceTabbedPane = (JTabbedPane) e.getSource();
                int index = sourceTabbedPane.getSelectedIndex();
                if (sourceTabbedPane.getTitleAt(index).equals("Factura")) {
                    try {
                        Application.facturacionController.show();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

//----------------------------------------------------------------------------------------------------------------------

        Application.window.setSize(1600,900);
        Application.window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Application.window.setIconImage((new ImageIcon(Application.class.getResource("presentation/icons/icon.png"))).getImage());
        Application.window.setTitle("POS: Point Of Sale");
        Application.window.setVisible(true);

        Application.loginWindow.dispose();
        }else{
            JOptionPane.showMessageDialog(null,"credenciales incorrectos");
        }
    }
}
