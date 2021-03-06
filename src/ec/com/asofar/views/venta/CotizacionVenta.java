/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.asofar.views.venta;

import ec.com.asofar.dao.InDetalleMovimientoJpaController;
import ec.com.asofar.dao.InMovimientosJpaController;
import ec.com.asofar.dao.SeClientesJpaController;
import ec.com.asofar.dao.SeLocalidadClienteJpaController;
import ec.com.asofar.dao.VeFacturaDetalleJpaController;
import ec.com.asofar.daoext.JoinProductoVenta;
import ec.com.asofar.daoext.JoinProductoVentaExt;
import ec.com.asofar.daoext.ObtenerDTO;
import ec.com.asofar.daoext.SeClientesExt;
import ec.com.asofar.dto.InDetalleMovimiento;
import ec.com.asofar.dto.InDetalleMovimientoPK;
import ec.com.asofar.dto.InMotivos;
import ec.com.asofar.dto.InMovimientos;
import ec.com.asofar.dto.InTipoDocumento;
import ec.com.asofar.dto.InTipoMovimiento;
import ec.com.asofar.dto.PrPrestaciones;
import ec.com.asofar.dto.SeClientes;
import ec.com.asofar.dto.SeEmpresa;
import ec.com.asofar.dto.SeLocalidadCliente;
import ec.com.asofar.dto.SeSucursal;
import ec.com.asofar.dto.SeUsuarios;
import ec.com.asofar.dto.VeFactura;
import ec.com.asofar.dto.VeFacturaDetalle;
import ec.com.asofar.dto.VeFacturaDetallePK;
import ec.com.asofar.util.ClaseReporte;
import ec.com.asofar.util.EntityManagerUtil;
import ec.com.asofar.util.Formato_Numeros;
import ec.com.asofar.util.Tablas;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import static java.awt.print.Printable.NO_SUCH_PAGE;
import static java.awt.print.Printable.PAGE_EXISTS;
import java.awt.print.PrinterException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

/**
 *
 * @author nuevouser
 */
public class CotizacionVenta extends javax.swing.JDialog {

    int x, y;
    /**
     * Creates new form CotizacionVenta
     */
    SeUsuarios usu;
    SeEmpresa emp;
    Date d = new Date();
    SeSucursal suc;
    List<SeClientes> ListCedula = null;
    SeClientes Clientes = null;
    List<SeClientes> Cliente;
    SeClientesExt selectCliente = new SeClientesExt(EntityManagerUtil.ObtenerEntityManager());
    List<SeLocalidadCliente> LocalidadCliente;
    SeLocalidadClienteJpaController Lc = new SeLocalidadClienteJpaController(EntityManagerUtil.ObtenerEntityManager());
    SeClientesJpaController Cc = new SeClientesJpaController(EntityManagerUtil.ObtenerEntityManager());
    List<VeFacturaDetalle> listaDetFactura = new ArrayList<VeFacturaDetalle>();
    Double VGTsubtotal, VGTtotal, VGTiva, VGTdescuento;
    int Cont = 1;
    Double precio, precioIva, total, descuento, subtotal;
    BigInteger cantidad, cantidadModi, idCliente, id_uni, cantidadStock;
    List<JoinProductoVenta> ListProdVent = null;
    JoinProductoVentaExt selectProdVent = new JoinProductoVentaExt();
    List<JoinProductoVenta> ListProdVent2 = null;
    JoinProductoVentaExt selectProdVent2 = new JoinProductoVentaExt();
    PrPrestaciones objetoPrestacion = new PrPrestaciones();
    JoinProductoVenta objJoinProVen = new JoinProductoVenta();

    public CotizacionVenta(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    public CotizacionVenta(java.awt.Frame parent, boolean modal, SeUsuarios us, SeEmpresa em, SeSucursal su) {
        super(parent, modal);
        setUndecorated(true);
        initComponents();
        setLocationRelativeTo(null);
        usu = us;
        emp = em;
        suc = su;
        consFinal();
    }

    public class PrintEpson implements Printable {

        public List<String> getPrinters() {
            DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
            PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
            PrintService printServices[] = PrintServiceLookup.lookupPrintServices(flavor, pras);
            List<String> printerList = new ArrayList<String>();
            for (PrintService printerService : printServices) {
                printerList.add(printerService.getName());
            }
            return printerList;
        }

        @Override
        public int print(Graphics g, PageFormat pf, int page)
                throws PrinterException {
            if (page > 0) {
                /* We have only one page, and 'page' is zero-based */
                return NO_SUCH_PAGE;
            }
            /*
             * User (0,0) is typically outside the imageable area, so we must
             * translate by the X and Y values in the PageFormat to avoid clipping
             */
            Graphics2D g2d = (Graphics2D) g;
            g2d.translate(pf.getImageableX(), pf.getImageableY());
            /* Now we perform our rendering */
            g.setFont(new Font("Arial", 4, 4));
            g.drawString("Hello world !", 0, 10);
            return PAGE_EXISTS;
        }

        public void printString(String printerName, String text) {
            // find the printService of name printerName
            DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
            PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
            PrintService printService[] = PrintServiceLookup.lookupPrintServices(flavor, pras);
            PrintService service = findPrintService("JAPOS1", printService);

            DocPrintJob job = service.createPrintJob();
            try {
                byte[] bytes;
                // important for umlaut chars
                bytes = text.getBytes("CP437");
                Doc doc = new SimpleDoc(bytes, flavor, null);
                job.print(doc, null);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        public void printBytes(String printerName, byte[] bytes) {
            DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
            PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
            PrintService printService[] = PrintServiceLookup.lookupPrintServices(flavor, pras);
            PrintService service = findPrintService(printerName, printService);
            DocPrintJob job = service.createPrintJob();
            try {
                Doc doc = new SimpleDoc(bytes, flavor, null);
                job.print(doc, null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private PrintService findPrintService(String printerName,
                PrintService[] services) {
            for (PrintService service : services) {
                if (service.getName().equalsIgnoreCase(printerName)) {
                    return service;
                }
            }
            return null;
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        txtNombre = new javax.swing.JTextField();
        txtTelefono = new javax.swing.JTextField();
        txtEmail = new javax.swing.JTextField();
        txtApellido = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtIdentificacion = new javax.swing.JTextField();
        btnbuscar = new javax.swing.JButton();
        txt_idCliente = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        txtDireccion = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        txtTipoIdent = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txtSubtotal = new javax.swing.JLabel();
        txtTotal = new javax.swing.JLabel();
        txtIva = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        txtDescuento = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tba_detalle = new javax.swing.JTable();
        btn_agregar_prod = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        txt_NumeroCaja = new javax.swing.JTextField();
        txt_NombreCaja = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true), "DATOS DEL CLIENTE:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 14))); // NOI18N

        txtNombre.setEditable(false);
        txtNombre.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        txtNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreActionPerformed(evt);
            }
        });
        txtNombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombreKeyTyped(evt);
            }
        });

        txtTelefono.setEditable(false);
        txtTelefono.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        txtTelefono.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTelefonoActionPerformed(evt);
            }
        });

        txtEmail.setEditable(false);
        txtEmail.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        txtEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEmailActionPerformed(evt);
            }
        });
        txtEmail.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtEmailKeyTyped(evt);
            }
        });

        txtApellido.setEditable(false);
        txtApellido.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        txtApellido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtApellidoActionPerformed(evt);
            }
        });
        txtApellido.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtApellidoKeyTyped(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Ubuntu", 1, 14)); // NOI18N
        jLabel7.setText("IDENTIFICACION:");

        jLabel8.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel8.setText("NOMBRE:");

        jLabel9.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel9.setText("APELLIDO: ");

        jLabel10.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel10.setText("CORREO: ");

        jLabel11.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel11.setText("TELEFONO: ");

        jLabel12.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel12.setText("Codigo:");

        txtIdentificacion.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        txtIdentificacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdentificacionActionPerformed(evt);
            }
        });
        txtIdentificacion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtIdentificacionKeyTyped(evt);
            }
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtIdentificacionKeyPressed(evt);
            }
        });

        btnbuscar.setBackground(new java.awt.Color(254, 254, 254));
        btnbuscar.setFont(new java.awt.Font("Ubuntu", 1, 10)); // NOI18N
        btnbuscar.setForeground(new java.awt.Color(1, 1, 1));
        btnbuscar.setText("CLIENTES");
        btnbuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnbuscarActionPerformed(evt);
            }
        });

        txt_idCliente.setEditable(false);

        jLabel17.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel17.setText("DIRECCION:");

        txtDireccion.setEditable(false);
        txtDireccion.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        txtDireccion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDireccionActionPerformed(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel18.setText("Nª: ");

        txtTipoIdent.setEditable(false);
        txtTipoIdent.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        txtTipoIdent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTipoIdentActionPerformed(evt);
            }
        });
        txtTipoIdent.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTipoIdentKeyTyped(evt);
            }
        });

        jButton1.setBackground(new java.awt.Color(254, 254, 254));
        jButton1.setFont(new java.awt.Font("Ubuntu", 1, 10)); // NOI18N
        jButton1.setForeground(new java.awt.Color(1, 1, 1));
        jButton1.setText("CONSUMIDOR FINAL");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnbuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel12)
                                    .addComponent(jLabel18))
                                .addGap(37, 37, 37)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txt_idCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtIdentificacion, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel9))
                                .addGap(12, 12, 12)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtNombre)
                                    .addComponent(txtApellido))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel11)
                            .addComponent(jLabel10)
                            .addComponent(jLabel7)
                            .addComponent(jLabel17))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtTelefono)
                            .addComponent(txtTipoIdent)
                            .addComponent(txtEmail)
                            .addComponent(txtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnbuscar)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txt_idCliente)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTipoIdent, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtIdentificacion, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtApellido, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jLabel1.setBackground(java.awt.Color.red);
        jLabel1.setFont(new java.awt.Font("Ubuntu", 1, 20)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(254, 254, 254));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("VENTA ");
        jLabel1.setOpaque(true);
        jLabel1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jLabel1MouseDragged(evt);
            }
        });
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel1MousePressed(evt);
            }
        });

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true), "DETALLE DE FACTURA", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 14))); // NOI18N

        jLabel13.setFont(new java.awt.Font("Ubuntu", 1, 14)); // NOI18N
        jLabel13.setText("SUBTOTAL: ");

        jLabel14.setFont(new java.awt.Font("Ubuntu", 1, 14)); // NOI18N
        jLabel14.setText("IVA  : ");

        jLabel15.setFont(new java.awt.Font("Ubuntu", 1, 14)); // NOI18N
        jLabel15.setText("TOTAL: ");

        txtSubtotal.setFont(new java.awt.Font("Ubuntu", 1, 14)); // NOI18N
        txtSubtotal.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        txtTotal.setFont(new java.awt.Font("Ubuntu", 1, 14)); // NOI18N
        txtTotal.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        txtIva.setFont(new java.awt.Font("Ubuntu", 1, 14)); // NOI18N
        txtIva.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel16.setFont(new java.awt.Font("Ubuntu", 1, 14)); // NOI18N
        jLabel16.setText("DESCUENTO:");

        txtDescuento.setFont(new java.awt.Font("Ubuntu", 1, 14)); // NOI18N
        txtDescuento.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13)
                    .addComponent(jLabel14)
                    .addComponent(jLabel16)
                    .addComponent(jLabel15))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtIva, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDescuento, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSubtotal, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSubtotal, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(txtDescuento, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtIva, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true), "DETALLE DE FACTURA", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 14))); // NOI18N

        tba_detalle.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tba_detalle.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        tba_detalle.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tba_detalleMousePressed(evt);
            }
        });
        tba_detalle.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tba_detalleKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tba_detalleKeyTyped(evt);
            }
        });
        jScrollPane2.setViewportView(tba_detalle);

        btn_agregar_prod.setFont(new java.awt.Font("Ubuntu", 1, 10)); // NOI18N
        btn_agregar_prod.setForeground(new java.awt.Color(1, 1, 1));
        btn_agregar_prod.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ec/com/asofar/icon/agregar_Mesa de trabajo 1.png"))); // NOI18N
        btn_agregar_prod.setText("AGREGAR");
        btn_agregar_prod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_agregar_prodActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_agregar_prod, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(353, 353, 353))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(btn_agregar_prod, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE))
        );

        jButton2.setBackground(new java.awt.Color(254, 254, 254));
        jButton2.setFont(new java.awt.Font("Ubuntu", 1, 10)); // NOI18N
        jButton2.setForeground(new java.awt.Color(1, 1, 1));
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ec/com/asofar/icon/salir_Mesa de trabajo 10.jpg"))); // NOI18N
        jButton2.setText("SALIR ");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        txt_NumeroCaja.setEditable(false);

        txt_NombreCaja.setEditable(false);

        jLabel19.setFont(new java.awt.Font("Ubuntu", 1, 14)); // NOI18N
        jLabel19.setText("N° :");

        jLabel20.setFont(new java.awt.Font("Ubuntu", 1, 14)); // NOI18N
        jLabel20.setText("CAJA :");

        jButton3.setBackground(new java.awt.Color(254, 254, 254));
        jButton3.setFont(new java.awt.Font("Ubuntu", 1, 10)); // NOI18N
        jButton3.setForeground(new java.awt.Color(1, 1, 1));
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ec/com/asofar/icon/nuevo_Mesa de trabajo 1.png"))); // NOI18N
        jButton3.setText("NUEVO");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ec/com/asofar/icon/cotizacion.png"))); // NOI18N
        jButton4.setText("COTIZAR");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_NumeroCaja, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(txt_NombreCaja, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(148, 148, 148)
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(153, 153, 153)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_NumeroCaja, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_NombreCaja, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreActionPerformed

    private void txtNombreKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreKeyTyped
        char validar = evt.getKeyChar();
        if ((validar < 'a' || validar > 'z') && (validar < 'A' || validar > 'Z')) {
            evt.consume();
        }
    }//GEN-LAST:event_txtNombreKeyTyped

    private void txtTelefonoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTelefonoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTelefonoActionPerformed

    private void txtEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEmailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEmailActionPerformed

    private void txtEmailKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtEmailKeyTyped

    }//GEN-LAST:event_txtEmailKeyTyped

    private void txtApellidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtApellidoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtApellidoActionPerformed

    private void txtApellidoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtApellidoKeyTyped
        char validar = evt.getKeyChar();
        if ((validar < 'a' || validar > 'z') && (validar < 'A' || validar > 'Z')) {
            evt.consume();
        }
    }//GEN-LAST:event_txtApellidoKeyTyped

    private void txtIdentificacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdentificacionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdentificacionActionPerformed

    private void txtIdentificacionKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIdentificacionKeyTyped
        char validar = evt.getKeyChar();
        if (Character.isLetter(validar)) {
            getToolkit().beep();
            evt.consume();
        }
    }//GEN-LAST:event_txtIdentificacionKeyTyped
    public void CargarCliente2() {
        String cedula = txtIdentificacion.getText();
        ListCedula = selectCliente.obtenerClienteVenta(cedula);

        for (int i = 0; i < ListCedula.size(); i++) {

            txtApellido.setText(ListCedula.get(i).getPrimerApellido() + " "
                    + ListCedula.get(i).getSegundoApellido());
            txtNombre.setText(ListCedula.get(i).getPrimerNombre());
            txt_idCliente.setText(ListCedula.get(i).getIdClientes().toString());
            txtEmail.setText(ListCedula.get(i).getSeLocalidadClienteList().get(i).getEmail());
            txtTelefono.setText(ListCedula.get(i).getSeLocalidadClienteList().get(i).getTelefono());
            txtDireccion.setText(ListCedula.get(i).getSeLocalidadClienteList().get(i).getDirreccionCliente());
            txtTipoIdent.setText(ListCedula.get(i).getIdTipoIndentificacion().getNombreIdentificacion());
        }
    }
    private void txtIdentificacionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIdentificacionKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            CargarCliente2();
        }
    }//GEN-LAST:event_txtIdentificacionKeyPressed

    private void txtDireccionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDireccionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDireccionActionPerformed

    private void txtTipoIdentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTipoIdentActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTipoIdentActionPerformed

    private void txtTipoIdentKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTipoIdentKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTipoIdentKeyTyped
    public void consFinal() {
        Cliente = Cc.findSeClientesEntities();
        for (int i = 0; i < Cliente.size(); i++) {

            if (Cliente.get(i).getIdClientes() == 1) {
//                System.out.println("clie " + Cliente.get(i).getPrimerNombre());
                txtNombre.setText(Cliente.get(i).getPrimerNombre());
                txtApellido.setText(Cliente.get(i).getPrimerApellido());
                txtIdentificacion.setText("9999999999999");
                txt_idCliente.setText(Cliente.get(i).getIdClientes().toString());
                txtTipoIdent.setText("********************************");
                txtEmail.setText("********************************");
                txtDireccion.setText("********************************");
                txtTelefono.setText("********************************");
            }
        }
    }
    private void jLabel1MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseDragged

    }//GEN-LAST:event_jLabel1MouseDragged

    private void jLabel1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MousePressed

    }//GEN-LAST:event_jLabel1MousePressed
    private void Totalizar() {
        Double t = 0.0;
        Double p = 0.0;
        if (tba_detalle.getRowCount() > 0) {
            for (int i = 0; i < tba_detalle.getRowCount(); i++) {
                p = listaDetFactura.get(i).getValorTotal();
                t += p;
//                Formato_Numeros.formatoNumero(t.toString());
                VGTtotal = t;
                txtTotal.setText(Formato_Numeros.formatoNumero(t.toString()));
//                txtTotal.setText(t.toString());
            }
        }
    }

    private void TotalizarIva() {
        Double t = 0.0;
        Double p = 0.0;
        if (tba_detalle.getRowCount() > 0) {
            for (int i = 0; i < tba_detalle.getRowCount(); i++) {
                p = listaDetFactura.get(i).getValorIva();
                t += p;
//                t=Double.valueOf(Formato_Numeros.formatoNumero(t.toString()));
                VGTiva = t;
                txtIva.setText(Formato_Numeros.formatoNumero(t.toString()));
//                txtIva.setText(t.toString());
            }
        }
    }

    private void TotalizarDescuento() {
        Double t = 0.0;
        Double p = 0.0;
        if (tba_detalle.getRowCount() > 0) {
            for (int i = 0; i < tba_detalle.getRowCount(); i++) {
                p = listaDetFactura.get(i).getValorDescuento();
                t += p;
//                Formato_Numeros.formatoNumero(t.toString());
                VGTdescuento = t;
                txtDescuento.setText(Formato_Numeros.formatoNumero(t.toString()));
//                txtDescuento.setText(t.toString());
            }
        }
    }

    private void TotalizarSubtotal() {
        Double t = 0.0;
        Double p = 0.0;
        Double pre = 0.0;
        BigInteger cant = null;
        Double subtotal = 0.0;
        if (tba_detalle.getRowCount() > 0) {
            for (int i = 0; i < tba_detalle.getRowCount(); i++) {
                pre = listaDetFactura.get(i).getPrecioUnitarioVenta();
                cant = listaDetFactura.get(i).getCantidad();

                p = listaDetFactura.get(i).getSubtotal();
                t += p;
                VGTsubtotal = t;
                txtSubtotal.setText(Formato_Numeros.formatoNumero(t.toString()));
//                txtSubtotal.setText(t.toString());
            }
        }
    }
    private void tba_detalleMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tba_detalleMousePressed
        int row = tba_detalle.rowAtPoint(evt.getPoint());
        int col = tba_detalle.columnAtPoint(evt.getPoint());
        if (evt.getClickCount() == 1) {
            if (tba_detalle.getModel().getColumnClass(col).equals(JButton.class)) {
                try {

                    int r = JOptionPane.showConfirmDialog(null, "Desea eliminar este producto?", "", JOptionPane.YES_OPTION);
                    if (r == JOptionPane.YES_OPTION) {
                        int i = tba_detalle.getSelectedRow();

                        listaDetFactura.remove(i);

                        Tablas.llenarDetalleVenta(tba_detalle, listaDetFactura);
                        Totalizar();
                        TotalizarIva();
                        TotalizarDescuento();
                        TotalizarSubtotal();
                        //                        listaDetFactura.remove(i);
                        Cont = Cont - 1;
                        for (int j = 0; j < listaDetFactura.size(); j++) {
                            Cont = j + 1;
                            listaDetFactura.get(j).getVeFacturaDetallePK().setLineaDetalle(Cont);
                        }
                        /**/
                        //                        Totalizar();
                        //                        TotalizarIva();
                        //                        TotalizarDescuento();
                        //                        TotalizarSubtotal();
                        //                        Tablas.llenarDetalleVenta(tba_detalle, listaDetFactura);
                    }
                } catch (Exception e) {
                }
            }
        }
    }//GEN-LAST:event_tba_detalleMousePressed

    private void tba_detalleKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tba_detalleKeyReleased
        try {
            int i = tba_detalle.getSelectedRow();
            Double descuentoT = 0.00;
            String valor = (String) tba_detalle.getValueAt(i, 3);

            BigInteger cantidadMod = new BigInteger(valor);
            cantidadModi = cantidadMod;
            Long id = (Long) tba_detalle.getValueAt(i, 1);
            ListProdVent = selectProdVent.listarProductoVenta();
            for (int j = 0; j < ListProdVent.size(); j++) {
                if (ListProdVent.get(j).getId_prestacion().equals(id)) {
                    //                    System.out.println("cantidadaadd "+id);
                    BigInteger ca = BigInteger.valueOf(ListProdVent.get(j).getSaldo_actual());

                    int out = cantidadMod.compareTo(ca);
                    //            int out = cantidadMod.compareTo(cantidadStock);
                    if (out == 1) {

                        JOptionPane.showMessageDialog(null, "Verifique Stock");
                        listaDetFactura.get(i).setCantidad(BigInteger.valueOf(1));
                        Tablas.llenarDetalleVenta(tba_detalle, listaDetFactura);

                    } else {

                        String ivaS = tba_detalle.getValueAt(i, 6).toString();
                        Double ivaT = Double.valueOf(ivaS.replace(",", "."));
                        String precioS = tba_detalle.getValueAt(i, 4).toString();
                        Double precioT = Double.valueOf(precioS.replace(",", "."));

                        //                        String descuentoS = tba_detalle.getValueAt(i, 5).toString();
                        //                        Double descuentoT = Double.valueOf(descuentoS.replace(",", "."));
                        ListProdVent2 = selectProdVent2.listarProductoVenta();
                        for (int k = 0; k < ListProdVent2.size(); k++) {
                            //                            System.out.println("lista /// " + ListProdVent2.get(k).getValor_descuento());
                            if (ListProdVent2.get(k).getId_prestacion() == ListProdVent.get(j).getId_prestacion()) {
                                //                                System.out.println("valor " + ListProdVent2.get(k).getValor_descuento());
                                descuentoT = ListProdVent2.get(k).getValor_descuento();
                            }
                        }

                        Double IvaMod = calcularIvaItemCantMod(cantidadModi, ivaT, precioT);
                        Double Desc = calcularDescuentoItemCantMod(cantidadModi, descuentoT);
                        Double subt = calcularSubtotalItemCantMod(cantidadModi, precioT);
                        Double total = calcularTotalItemCantMod(cantidadModi, IvaMod, Desc, precioT);

                        listaDetFactura.get(i).setCantidad(cantidadMod);
                        listaDetFactura.get(i).setValorIva(IvaMod);
                        listaDetFactura.get(i).setSubtotal(subt);
                        listaDetFactura.get(i).setValorTotal(total);
                        listaDetFactura.get(i).setValorDescuento(Desc);
                        Tablas.llenarDetalleVenta(tba_detalle, listaDetFactura);

                        Totalizar();
                        TotalizarIva();
                        TotalizarDescuento();
                        TotalizarSubtotal();
                    }/**/

                }/**/

            }/**/

        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_tba_detalleKeyReleased
    public Double calcularIvaItemCantMod(BigInteger cantMod, Double posicionIva, Double posicionPrecio) {

//        BigInteger cant;
        Double pre;
        Double precioIva2 = null;
        String aplica = objetoPrestacion.getAplicaIva();

        if (posicionIva != 0.0) {
//            cant = cantidad;
            pre = posicionPrecio;
            precioIva2 = (cantMod.doubleValue() * pre) * 12 / 100;
        } else {
            precioIva2 = 0.0;
        }
        return precioIva2;
    }

    public Double calcularDescuentoItemCantMod(BigInteger cantMod, Double descuento) {
        Double descuent = null;
        descuent = cantMod.doubleValue() * descuento;
        return descuent;
    }

    public Double calcularSubtotalItemCantMod(BigInteger cantMod, Double PrecioIva) {
        Double subto = null;
        subto = cantMod.doubleValue() * PrecioIva;
        return subto;
    }

    public Double calcularTotalItemCantMod(BigInteger cantMod, Double PrecioIva, Double desc, Double precio) {

        Double pre;
        Double total2;
        /**/
        pre = precio;
        total2 = (cantMod.doubleValue() * pre) + PrecioIva - desc;

        return total2;
    }

    private void tba_detalleKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tba_detalleKeyTyped
        char car = evt.getKeyChar();
        if (car < '0' || car > '9') {
            evt.consume();
        }

    }//GEN-LAST:event_tba_detalleKeyTyped

    private void btn_agregar_prodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_agregar_prodActionPerformed
        ConsultaProductoVenta ingre = new ConsultaProductoVenta(new javax.swing.JFrame(), true);
        ingre.setVisible(true);
        objJoinProVen = ingre.obtObjProdVent();
        //        if (objJoinProVen != null) {
        //        } else {
        //
        //            JOptionPane.showMessageDialog(null, "Seleccione");
        //        }
        if (validarProductos("" + (objJoinProVen.getId_prestacion())).equals("si")) {
            JOptionPane.showMessageDialog(rootPane, "El producto ya se fue seleccionado!");
        } else {
            cantidadStock = BigInteger.valueOf(objJoinProVen.getSaldo_actual());
            System.out.println(" fff " + cantidadStock);

            VeFacturaDetalle FactDeta = new VeFacturaDetalle();
            FactDeta.setVeFacturaDetallePK(new VeFacturaDetallePK());
            FactDeta.getVeFacturaDetallePK().setIdPrestaciones(objJoinProVen.getId_prestacion());

            FactDeta.setDescripcion(objJoinProVen.getNombre_producto());
            cantidad = BigInteger.ONE;
            precio = objJoinProVen.getValor_venta();
            precioIva = calcularIvaItem();
            descuento = objJoinProVen.getValor_descuento();
            //            if (objJoinProVen.getSaldo_actual() != 0) {
            FactDeta.setCantidad(BigInteger.ONE);
            //            }
            //            else {
            //                JOptionPane.showMessageDialog(null, " Verifique Stock");
            //                FactDeta.setCantidad(BigInteger.ZERO);
            //            }
            FactDeta.setValorDescuento(descuento);
            FactDeta.setValorIva(precioIva);
            FactDeta.setPrecioUnitarioVenta(objJoinProVen.getValor_venta());
            subtotal = calcularSubtotal();
            total = calcularTotalItem();
            FactDeta.setValorTotal(total);
            FactDeta.setSubtotal(subtotal);

            listaDetFactura.add(FactDeta);
            //            listaDetFacturaPrueba.add(FactDeta);

            for (int i = 0; i < listaDetFactura.size(); i++) {
                Cont = i + 1;
                FactDeta.getVeFacturaDetallePK().setLineaDetalle(Cont);
            }

            Tablas.llenarDetalleVenta(tba_detalle, listaDetFactura);
            Totalizar();
            TotalizarIva();
            TotalizarDescuento();
            TotalizarSubtotal();
        }
    }//GEN-LAST:event_btn_agregar_prodActionPerformed

    public Double calcularTotalItem() {
        BigInteger cant;
        Double pre;
        Double preIva;
        Double total2;
        Double desc;
        /**/
        preIva = calcularIvaItem();
        cant = cantidad;
        pre = precio;
        desc = descuento;
        total2 = (cant.doubleValue() * pre) + preIva - desc;

        return total2;
    }

    public Double calcularSubtotal() {
        BigInteger cant;
        Double pre;
        Double subt;
        cant = cantidad;
        pre = precio;
        subt = cant.doubleValue() * pre;

        return subt;
    }

    public String validarProductos(String datos) {
        String obj1 = "no";

        for (int i = 0; i < listaDetFactura.size(); i++) {

            if (datos.equals("" + (listaDetFactura.get(i).getVeFacturaDetallePK().getIdPrestaciones()))) {
                obj1 = "si";

                break;
            }

        }

        return obj1;

    }

    public Double calcularIvaItem() {
        BigInteger cant;
        Double pre;
        Double precioIva = null;
        String aplica = objJoinProVen.getAplica_iva();
        if (aplica.equals("SI")) {
            cant = cantidad;
            pre = precio;
            precioIva = (cant.doubleValue() * pre) * 12 / 100;
        } else {
            precioIva = 0.0;
        }
        return precioIva;
    }
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        setVisible(false);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        limpiar();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void btnbuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnbuscarActionPerformed

        ConsultarClienteVenta ingre = new ConsultarClienteVenta(new javax.swing.JFrame(), true, usu, emp, suc);
        ingre.setVisible(true);
        Clientes = ingre.getObjeto();

        try {

            String cedula = Clientes.getNumeroIdentificacion().toString();
            ListCedula = selectCliente.obtenerClienteVenta(cedula);

            SeLocalidadCliente localidad = new SeLocalidadCliente();

            localidad = Clientes.getSeLocalidadClienteList().get(0);

            txtIdentificacion.setText(Clientes.getNumeroIdentificacion());
            txtApellido.setText(Clientes.getPrimerApellido() + " "
                    + Clientes.getSegundoApellido());
            txtNombre.setText(Clientes.getPrimerNombre());
            txt_idCliente.setText(Clientes.getIdClientes().toString());
            txtEmail.setText(localidad.getEmail());
            txtTelefono.setText(localidad.getTelefono());
            txtDireccion.setText(localidad.getDirreccionCliente());
            txtTipoIdent.setText(Clientes.getIdTipoIndentificacion().getNombreIdentificacion());
            //            }
        } catch (Exception e) {
            Logger.getLogger(Venta.class.getName()).log(Level.SEVERE, null, e);
        }
    }//GEN-LAST:event_btnbuscarActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        consFinal();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed

//        System.out.println("*********" + txtTotal.getText().toString() + "******");
        if ("0.0".equals(txtTotal.getText().toString())) {
            JOptionPane.showMessageDialog(null, "LLENE TODOS LOS CAMPOS!");
        } else {
            int r = JOptionPane.showConfirmDialog(null, "¿Esta seguro de los Datos?", "", JOptionPane.YES_NO_OPTION);
            if (r == JOptionPane.YES_OPTION) {
//                VeFactura cabFact = new VeFactura();
//                InMovimientos pkMovimiento = null;
//                VeFacturaDetalle detFact = new VeFacturaDetalle();
//                VeFacturaDetalleJpaController detFactController = new VeFacturaDetalleJpaController(EntityManagerUtil.ObtenerEntityManager());
//                cabFact.setSeSucursal(suc);
//                cabFact.setIdCliente(new BigInteger(txt_idCliente.getText().toString()));
//                cabFact.setSubtotal(VGTsubtotal);
//                cabFact.setTotalIva(VGTiva);
//                cabFact.setTotalDescuento(VGTdescuento);
//                cabFact.setTotalFacturado(VGTtotal);
//                cabFact.setIdUsuario(BigInteger.valueOf(usu.getIdUsuario()));
//                cabFact.setUsuarioCreacion(usu.getUsuario());
//                cabFact.setEstado("A");
//                cabFact.setDespachado("SI");
//                try {
//                    for (int i = 0; i < listaDetFactura.size(); i++) {
//                        detFact.setVeFacturaDetallePK(new VeFacturaDetallePK());
//                        detFact.getVeFacturaDetallePK().setLineaDetalle(listaDetFactura.get(i).getVeFacturaDetallePK().getLineaDetalle());
//                        detFact.getVeFacturaDetallePK().setIdPrestaciones(listaDetFactura.get(i).getVeFacturaDetallePK().getIdPrestaciones());
//                        detFact.getVeFacturaDetallePK().setIdUnidadServicio(objJoinProVen.getId_unidad_servicio().longValue());
//                        detFact.setDescripcion(listaDetFactura.get(i).getDescripcion());
//                        detFact.setCantidad(listaDetFactura.get(i).getCantidad());
//                        detFact.setPrecioUnitarioVenta(listaDetFactura.get(i).getPrecioUnitarioVenta());
//                        detFact.setSubtotal(listaDetFactura.get(i).getSubtotal());
//                        detFact.setValorIva(listaDetFactura.get(i).getValorIva());
//                        detFact.setValorDescuento(listaDetFactura.get(i).getValorDescuento());
//                        detFact.setValorTotal(listaDetFactura.get(i).getValorTotal());
//                        detFact.setEstado("A");
//                        detFact.setUsuarioCreacion(usu.getUsuario());
//                    }
/////////// AGREGANDO A MOVIMIENTO
//                    InMovimientosJpaController cabMovController = new InMovimientosJpaController(EntityManagerUtil.ObtenerEntityManager());
//                    InDetalleMovimientoJpaController detMovController = new InDetalleMovimientoJpaController(EntityManagerUtil.ObtenerEntityManager());
//                    InMovimientos cabMovimiento = new InMovimientos();
//                    InTipoMovimiento tipoMovimiento = ObtenerDTO.ObtenerInTipoMovimiento("VENTAS");
//                    InTipoDocumento tipoDocumento = ObtenerDTO.ObtenerDocumentoPedido("FACTURA");
//                    InMotivos tipoMotivos = ObtenerDTO.ObtenerInMotivos("VENTA CLIENTE FINAL");
//                    InDetalleMovimiento detMovimiento = new InDetalleMovimiento();
                try {
                    /*
                     240  
                     160 
                     ASOFAR institucion acojedora
                     tutor institucional  el de asofar 
                     tutor academico --------
                     noviembre a marzo yaaaaaaa
                     */
//                        cabMovimiento.setSeSucursal(suc);
//                        cabMovimiento.setInTipoDocumento(tipoDocumento);
//                        cabMovimiento.setInTipoMovimiento(tipoMovimiento);
//                        cabMovimiento.setInMotivos(tipoMotivos);
//                        cabMovimiento.setEstado("F");
//                        cabMovimiento.setUsuarioCreacion(usu.getUsuario());
//                        for (int i = 0; i < listaDetFactura.size(); i++) {
//                            detMovimiento.setInMovimientos(pkMovimiento);
//                            detMovimiento.setInDetalleMovimientoPK(new InDetalleMovimientoPK()); // inicializar pk
//                            detMovimiento.getInDetalleMovimientoPK().setLineaDetalle(listaDetFactura.get(i).getVeFacturaDetallePK().getLineaDetalle());
//                            detMovimiento.setDescripcion(listaDetFactura.get(i).getDescripcion());
//                            detMovimiento.setCantidad(listaDetFactura.get(i).getCantidad());
//                            detMovimiento.setPrecioUnitario(BigDecimal.valueOf(listaDetFactura.get(i).getPrecioUnitarioVenta()));
//                            detMovimiento.setEstado("A");
//                            detMovimiento.setUsuarioCreacion(usu.getUsuario());
////                            detMovController.create(detMovimiento);
//                        }
                    java.sql.Date fechact = new java.sql.Date(d.getTime());
                    String empresa = emp.getNombreComercial();
                    String sucursal = suc.getNombreComercial();
                    String ruc = emp.getRuc();
                    String direccion = suc.getDireccion();
                    String idFactura = "";/*   */
//                    System.out.println(""+idFactura);///

                    int im = JOptionPane.showConfirmDialog(null, "¿Desea Imprimir la factura?", "", JOptionPane.YES_NO_OPTION);
                    if (im == JOptionPane.YES_OPTION) {
                        CotizacionVenta.PrintEpson printerService = new CotizacionVenta.PrintEpson();
                        System.out.println(printerService.getPrinters());
                        printerService.printString("EPSON-TM-T20II", "------------------------------------------\n\n");
                        printerService.printString("EPSON-TM-T20II", " *     FARMACIA " + empresa + " " + sucursal + "    *\n");
                        printerService.printString("EPSON-TM-T20II", "------------------------------------------\n");
                        printerService.printString("EPSON-TM-T20II", "         Direccion: " + direccion + "\n");
                        printerService.printString("EPSON-TM-T20II", "               RUC: " + ruc + "\n");
                        printerService.printString("EPSON-TM-T20II", "  N° CAJA: " + txt_NumeroCaja.getText() + "          CAJA:" + txt_NombreCaja.getText() + "\n");
                        printerService.printString("EPSON-TM-T20II", "   CODIGO DE VENTA: " + txt_idCliente.getText() + "\n");
                        printerService.printString("EPSON-TM-T20II", "    IDENTIFICACION: " + txtTipoIdent.getText() + "\n");
                        printerService.printString("EPSON-TM-T20II", " N° IDENTIFICACION: " + txtIdentificacion.getText() + "\n");
                        printerService.printString("EPSON-TM-T20II", "    NOMBRE DE CLTE: " + txtNombre.getText() + "\n");
                        printerService.printString("EPSON-TM-T20II", "  APELLIDO DE CLTE: " + txtApellido.getText() + "\n");
                        printerService.printString("EPSON-TM-T20II", "    CORREO DE CLTE: " + txtEmail.getText() + "\n");
                        printerService.printString("EPSON-TM-T20II", "  TELEFONO DE CLTE: " + txtTelefono.getText() + "\n");
                        printerService.printString("EPSON-TM-T20II", " DIRECCION DE CLTE: " + txtDireccion.getText() + "\n");
                        printerService.printString("EPSON-TM-T20II", "------------------------------------------\n");
                        printerService.printString("EPSON-TM-T20II", "Producto             Cant Precio SubTotal\n");
                        for (int i = 0; i < tba_detalle.getRowCount(); i++) {
                            if (!"0,00".equals(tba_detalle.getValueAt(i, 6).toString())) {
                                printerService.printString("EPSON-TM-T20II", tba_detalle.getValueAt(i, 2).toString() + "\n");
//                                printerService.printString("EPSON-TM-T20II",tba_detalle.getValueAt(i, 2).toString() + "  " + tba_detalle.getValueAt(i, 3).toString() + "  " + tba_detalle.getValueAt(i, 4).toString() + "  " + tba_detalle.getValueAt(i, 7).toString() + "  " + tba_detalle.getValueAt(i, 8).toString() + "\n");
                                printerService.printString("EPSON-TM-T20II", "                       " + tba_detalle.getValueAt(i, 3).toString() + "  " + tba_detalle.getValueAt(i, 4).toString() + "  " + tba_detalle.getValueAt(i, 8).toString() + "   *" + "\n");
                            } else {
                                printerService.printString("EPSON-TM-T20II", tba_detalle.getValueAt(i, 2).toString() + "\n");
//                                printerService.printString("EPSON-TM-T20II",tba_detalle.getValueAt(i, 2).toString() + "  " + tba_detalle.getValueAt(i, 3).toString() + "  " + tba_detalle.getValueAt(i, 4).toString() + "  " + tba_detalle.getValueAt(i, 7).toString() + "  " + tba_detalle.getValueAt(i, 8).toString() + "\n");
                                printerService.printString("EPSON-TM-T20II", "                       " + tba_detalle.getValueAt(i, 3).toString() + "  " + tba_detalle.getValueAt(i, 4).toString() + "  " + tba_detalle.getValueAt(i, 8).toString() + "\n");
                            }
                        }
                        printerService.printString("EPSON-TM-T20II", "------------------------------------------\n");
                        printerService.printString("EPSON-TM-T20II", "                     SUBTOTAL: " + txtSubtotal.getText() + "\n");
                        printerService.printString("EPSON-TM-T20II", "                    DESCUENTO: " + txtDescuento.getText() + "\n");
                        printerService.printString("EPSON-TM-T20II", "                          IVA: " + txtIva.getText() + "\n");
                        printerService.printString("EPSON-TM-T20II", "                        TOTAL: " + txtTotal.getText() + "\n");
                        printerService.printString("EPSON-TM-T20II", "------------------------------------------\n");
                        printerService.printString("EPSON-TM-T20II", "--------- GRACIAS POR PREFERIRNOS --------\n");
                        printerService.printString("EPSON-TM-T20II", "\n");
                        printerService.printString("EPSON-TM-T20II", "\n");
                        printerService.printString("EPSON-TM-T20II", "\n");
                        printerService.printString("EPSON-TM-T20II", "\n");
                        printerService.printString("EPSON-TM-T20II", "\n");
                        printerService.printString("EPSON-TM-T20II", "\n");
                        printerService.printString("EPSON-TM-T20II", "\n");
                        printerService.printString("EPSON-TM-T20II", "\n");
                        printerService.printString("EPSON-TM-T20II", "\n");
                        printerService.printString("EPSON-TM-T20II", "\n");
                        printerService.printString("EPSON-TM-T20II", "\n");
                        ArrayList listap = new ArrayList();
                        for (int i = 0; i < tba_detalle.getRowCount(); i++) {
                            ClaseReporte clase = new ClaseReporte(empresa, sucursal, idFactura, direccion, ruc, txt_NumeroCaja.getText(), txt_NombreCaja.getText(), txt_idCliente.getText(), txtTipoIdent.getText(), txtIdentificacion.getText(), txtEmail.getText(), txtNombre.getText(), txtTelefono.getText(), txtApellido.getText(), txtDireccion.getText(),
                                    tba_detalle.getValueAt(i, 0).toString(),
                                    tba_detalle.getValueAt(i, 1).toString(),
                                    tba_detalle.getValueAt(i, 2).toString(),
                                    tba_detalle.getValueAt(i, 3).toString(),
                                    tba_detalle.getValueAt(i, 4).toString(),
                                    tba_detalle.getValueAt(i, 5).toString(),
                                    tba_detalle.getValueAt(i, 6).toString(),
                                    tba_detalle.getValueAt(i, 7).toString(),
                                    tba_detalle.getValueAt(i, 8).toString(),
                                    txtSubtotal.getText(), txtDescuento.getText(), txtIva.getText(), txtTotal.getText());
                            listap.add(clase);
                        }
                        JasperReport jreport = (JasperReport) JRLoader.loadObject("Reportes/Venta.jasper");
                        JasperPrint jprint = JasperFillManager.fillReport(jreport, null, new JRBeanCollectionDataSource(listap));
                        JasperExportManager.exportReportToPdfFile(jprint, System.getProperty("user.dir") + "/ReporteDeFacturas/" + "CI." + txtIdentificacion.getText() + " Factura#" + idFactura + " Fecha:" + fechact.toString() + ".pdf");
                    } else {
                        ArrayList listap = new ArrayList();
                        for (int i = 0; i < tba_detalle.getRowCount(); i++) {
                            ClaseReporte clase = new ClaseReporte(empresa, sucursal, idFactura, direccion, ruc, txt_NumeroCaja.getText(), txt_NombreCaja.getText(), txt_idCliente.getText(), txtTipoIdent.getText(), txtIdentificacion.getText(), txtEmail.getText(), txtNombre.getText(), txtTelefono.getText(), txtApellido.getText(), txtDireccion.getText(),
                                    tba_detalle.getValueAt(i, 0).toString(),
                                    tba_detalle.getValueAt(i, 1).toString(),
                                    tba_detalle.getValueAt(i, 2).toString(),
                                    tba_detalle.getValueAt(i, 3).toString(),
                                    tba_detalle.getValueAt(i, 4).toString(),
                                    tba_detalle.getValueAt(i, 5).toString(),
                                    tba_detalle.getValueAt(i, 6).toString(),
                                    tba_detalle.getValueAt(i, 7).toString(),
                                    tba_detalle.getValueAt(i, 8).toString(),
                                    txtSubtotal.getText(), txtDescuento.getText(), txtIva.getText(), txtTotal.getText());
                            listap.add(clase);
                        }
                        JasperReport jreport = (JasperReport) JRLoader.loadObject("Reportes/Venta.jasper");
                        JasperPrint jprint = JasperFillManager.fillReport(jreport, null, new JRBeanCollectionDataSource(listap));
                        JasperExportManager.exportReportToPdfFile(jprint, System.getProperty("user.dir") + "/ReporteDeFacturas/" + "CI." + txtIdentificacion.getText() + " Factura#" + idFactura + " Fecha:" + fechact.toString() + ".pdf");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                JOptionPane.showMessageDialog(null, " REALIZADO CON EXITO");
                limpiar();
            } else {
            }
        }
    }//GEN-LAST:event_jButton4ActionPerformed
    public void limpiar() {
        consFinal();
        listaDetFactura.clear();
        Tablas.llenarDetalleVenta(tba_detalle, listaDetFactura);
        txtSubtotal.setText("");
        txtDescuento.setText("");
        txtIva.setText("");
        txtTotal.setText("");
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(CotizacionVenta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CotizacionVenta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CotizacionVenta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CotizacionVenta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                CotizacionVenta dialog = new CotizacionVenta(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_agregar_prod;
    private javax.swing.JButton btnbuscar;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tba_detalle;
    private javax.swing.JTextField txtApellido;
    private javax.swing.JLabel txtDescuento;
    private javax.swing.JTextField txtDireccion;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtIdentificacion;
    private javax.swing.JLabel txtIva;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JLabel txtSubtotal;
    private javax.swing.JTextField txtTelefono;
    private javax.swing.JTextField txtTipoIdent;
    private javax.swing.JLabel txtTotal;
    private javax.swing.JTextField txt_NombreCaja;
    private javax.swing.JTextField txt_NumeroCaja;
    private javax.swing.JTextField txt_idCliente;
    // End of variables declaration//GEN-END:variables
}
