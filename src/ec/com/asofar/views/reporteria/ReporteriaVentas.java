/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.asofar.views.reporteria;

import com.mysql.jdbc.Connection;
import com.toedter.calendar.JCalendar;
import ec.com.asofar.daoext.ReporteComprasDTO;
import ec.com.asofar.daoext.ReporteFacturaDTO;
import ec.com.asofar.daoext.ReporteriaExt;
import ec.com.asofar.dto.SeEmpresa;
import ec.com.asofar.dto.SeSucursal;
import ec.com.asofar.dto.SeUsuarios;
import ec.com.asofar.util.ClaseReporte;
import ec.com.asofar.util.Conexion;
import ec.com.asofar.util.Tablas;
import java.awt.AWTKeyStroke;
import java.awt.Dimension;
import java.awt.KeyboardFocusManager;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.swing.JRViewer;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author admin1
 */
public class ReporteriaVentas extends javax.swing.JDialog {

    int ancho = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
    int alto = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;
    String buscar = "";
    /**
     * Creates new form ReporteriaCompras
     */
    SeUsuarios usu;
    SeEmpresa emp;
    SeSucursal suc;
    int x, y;
    ReporteriaExt rep = new ReporteriaExt();
    List<ReporteFacturaDTO> itemList = null;

    public ReporteriaVentas(java.awt.Frame parent, boolean modal) {
        super(parent, modal = false);
        initComponents();
        this.setLocationRelativeTo(this);
        cargartabala();
//        itemList = rep.reporteFactura();
        //Tablas.listarReporteCompras(itemList, tbaReporteCompra);
        //java.util.Date fechaParseada= new SimpleDateFormat("yyyy/MM/dd").parse(tuFecha);
        Chooser1.setDate(rep.fechaActual());
        Chooser2.setDate(rep.fechaActual());
        total();
        Keypress_jDateChoooser();
    }

    public ReporteriaVentas(java.awt.Frame parent, boolean modal, SeUsuarios us, SeEmpresa em, SeSucursal su) {
        super(parent, modal = false);
        setUndecorated(true);
        initComponents();
        this.setLocationRelativeTo(null);
        usu = us;
        emp = em;
        suc = su;
        cargartabala();
        Chooser1.setDate(rep.fechaActual());
        Chooser2.setDate(rep.fechaActual());
        total();
        Keypress_jDateChoooser();
    }

    public void cargartabala() {

        itemList = new ArrayList<ReporteFacturaDTO>();
        itemList = rep.reporteFactura();
        Tablas.listarReporteFactura(itemList, tbaReporteCompra);

    }

    public void total() {
        Double total_total = 0.00;
        Double total_iva = 0.00;
        Double total_descuento = 0.00;
        for (int i = 0; i < itemList.size(); i++) {
            for (int j = 0; j < tbaReporteCompra.getRowCount(); j++) {
                if (tbaReporteCompra.getValueAt(j, 0).toString().equals(itemList.get(i).getId_factura().toString())) {
                    // System.out.println(tbaReporteCompra.getValueAt(j, 0).toString() + " " + (itemList.get(i).getId_orden_compra().toString()));
                    total_total = total_total + itemList.get(i).getTotal_facturado();
                    Txt_Total.setText(rep.formatoNumero(total_total.toString()));
                }
            }
        }
        for (int i = 0; i < itemList.size(); i++) {
            for (int j = 0; j < tbaReporteCompra.getRowCount(); j++) {
                if (tbaReporteCompra.getValueAt(j, 0).toString().equals(itemList.get(i).getId_factura().toString())) {
                    // System.out.println(tbaReporteCompra.getValueAt(j, 0).toString() + " " + (itemList.get(i).getId_orden_compra().toString()));
                    total_iva = total_iva + itemList.get(i).getTotal_iva();
                    txtiva.setText(rep.formatoNumero(total_iva.toString()));
                }
            }
        }
        for (int i = 0; i < itemList.size(); i++) {
            for (int j = 0; j < tbaReporteCompra.getRowCount(); j++) {
                if (tbaReporteCompra.getValueAt(j, 0).toString().equals(itemList.get(i).getId_factura().toString())) {
                    // System.out.println(tbaReporteCompra.getValueAt(j, 0).toString() + " " + (itemList.get(i).getId_orden_compra().toString()));
                    total_descuento = total_descuento + itemList.get(i).getTotal_facturado();
                    txtdescuento.setText(rep.formatoNumero(total_descuento.toString()));
                }
            }
        }

    }

    public void refrescar() {
        buscar = buscar1.getText();
        Tablas.filtro(buscar, tbaReporteCompra);
    }

    private void Keypress_jDateChoooser() { //salto al siguiente campo
        HashSet<AWTKeyStroke> conjForward = new HashSet<AWTKeyStroke>(
                Chooser2.getFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS));
        conjForward.add(AWTKeyStroke.getAWTKeyStroke(KeyEvent.VK_ENTER, 0));
        conjForward.add(AWTKeyStroke.getAWTKeyStroke(KeyEvent.VK_DOWN, 0));
        Chooser2.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, conjForward);

    }

    public void busquedaChosserQuery() {
        buscar1.setText("");
        tbaReporteCompra.setRowSorter(null);

        String valor = rep.getFecha(Chooser1);
        String valor2 = rep.getFecha(Chooser2);

        if (valor == null || valor2 == null) {
            JOptionPane.showMessageDialog(rootPane, "INGRESE LAS FECHAS CORRECTAS");
        } else {

            int x = valor.compareTo(valor2);
            System.out.println("valor " + x);
            switch (x) {
                case -1:
                    System.out.println("correcto");
                    itemList = new ArrayList<ReporteFacturaDTO>();
                    itemList = rep.reporteFacturaFechas(valor, valor2);
                    Tablas.listarReporteFactura(itemList, tbaReporteCompra);
                    break;
                case 0:
                    System.out.println("correcto");
                    itemList = new ArrayList<ReporteFacturaDTO>();
                    itemList = rep.reporteFacturaFechas(valor, valor2);
                    Tablas.listarReporteFactura(itemList, tbaReporteCompra);
                    break;
                case -2:
                    System.out.println("correcto");
                    itemList = new ArrayList<ReporteFacturaDTO>();
                    itemList = rep.reporteFacturaFechas(valor, valor2);
                    Tablas.listarReporteFactura(itemList, tbaReporteCompra);
                    break;
                default:
                    System.out.println("error");
                    JOptionPane.showMessageDialog(rootPane, "INGRESE LAS FECHAS CORRECTAS \nINGRESE FECHA DESDE - HASTA");
                    break;
            }
        }
        total();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        buscar1 = new javax.swing.JTextField();
        Txt_Total = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        btnSalir2 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        tblProduc = new javax.swing.JScrollPane();
        tbaReporteCompra = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        txtiva = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        Chooser1 = new com.toedter.calendar.JDateChooser();
        Chooser2 = new com.toedter.calendar.JDateChooser();
        jLabel4 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        BtnBuscar1 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        txtdescuento = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));

        buscar1.setFont(new java.awt.Font("Ubuntu", 1, 14)); // NOI18N
        buscar1.setPreferredSize(new java.awt.Dimension(6, 28));
        buscar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscar1ActionPerformed(evt);
            }
        });
        buscar1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                buscar1KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                buscar1KeyReleased(evt);
            }
        });

        Txt_Total.setEditable(false);
        Txt_Total.setFont(new java.awt.Font("Ubuntu", 1, 14)); // NOI18N
        Txt_Total.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        jLabel2.setFont(new java.awt.Font("Ubuntu", 1, 14)); // NOI18N
        jLabel2.setText("TOTAL VENTAS:");

        btnSalir2.setBackground(new java.awt.Color(254, 254, 254));
        btnSalir2.setFont(new java.awt.Font("Ubuntu", 1, 10)); // NOI18N
        btnSalir2.setForeground(new java.awt.Color(1, 1, 1));
        btnSalir2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ec/com/asofar/icon/salir_Mesa de trabajo 10.jpg"))); // NOI18N
        btnSalir2.setText("SALIR");
        btnSalir2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalir2ActionPerformed(evt);
            }
        });

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        tbaReporteCompra.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        tbaReporteCompra.setFont(new java.awt.Font("Ubuntu", 1, 10)); // NOI18N
        tbaReporteCompra.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tbaReporteCompra.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tbaReporteCompraMousePressed(evt);
            }
        });
        tblProduc.setViewportView(tbaReporteCompra);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(tblProduc, javax.swing.GroupLayout.PREFERRED_SIZE, 900, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tblProduc, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jLabel3.setFont(new java.awt.Font("Ubuntu", 1, 14)); // NOI18N
        jLabel3.setText("TOTAL IVA:");

        txtiva.setEditable(false);
        txtiva.setFont(new java.awt.Font("Ubuntu", 1, 14)); // NOI18N
        txtiva.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        jLabel1.setFont(new java.awt.Font("Ubuntu", 1, 14)); // NOI18N
        jLabel1.setText("ENTRE");

        Chooser1.setDateFormatString("yyyy/MM/dd");
        Chooser1.setFont(new java.awt.Font("Ubuntu", 1, 14)); // NOI18N

        Chooser2.setDateFormatString("yyyy/MM/dd");
        Chooser2.setFont(new java.awt.Font("Ubuntu", 1, 14)); // NOI18N
        Chooser2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Chooser2KeyPressed(evt);
            }
        });

        jLabel4.setBackground(java.awt.Color.red);
        jLabel4.setFont(new java.awt.Font("Ubuntu", 1, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(254, 254, 254));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("REPORTE VENTAS");
        jLabel4.setOpaque(true);
        jLabel4.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jLabel4MouseDragged(evt);
            }
        });
        jLabel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel4MousePressed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(254, 254, 254));
        jButton2.setFont(new java.awt.Font("Ubuntu", 1, 10)); // NOI18N
        jButton2.setForeground(new java.awt.Color(1, 1, 1));
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ec/com/asofar/icon/imprimir_mesa.png"))); // NOI18N
        jButton2.setText("IMPRIMIR");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        BtnBuscar1.setFont(new java.awt.Font("Ubuntu", 1, 10)); // NOI18N
        BtnBuscar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ec/com/asofar/icon/buscar_Mesa de trabajo 1.png"))); // NOI18N
        BtnBuscar1.setText("BUSCAR");
        BtnBuscar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnBuscar1ActionPerformed(evt);
            }
        });
        BtnBuscar1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnBuscar1KeyPressed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Ubuntu", 1, 14)); // NOI18N
        jLabel5.setText("FILTRO:");

        txtdescuento.setEditable(false);
        txtdescuento.setFont(new java.awt.Font("Ubuntu", 1, 14)); // NOI18N
        txtdescuento.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        jLabel6.setFont(new java.awt.Font("Ubuntu", 1, 14)); // NOI18N
        jLabel6.setText("TOTAL DESCUENTO:");

        jButton3.setBackground(new java.awt.Color(254, 254, 254));
        jButton3.setFont(new java.awt.Font("Ubuntu", 1, 10)); // NOI18N
        jButton3.setForeground(new java.awt.Color(1, 1, 1));
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ec/com/asofar/icon/excel.png"))); // NOI18N
        jButton3.setText("  EXCEL");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(Txt_Total, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(18, 18, 18)
                        .addComponent(txtdescuento, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(txtiva, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(Chooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Chooser2, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BtnBuscar1, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(60, 60, 60)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buscar1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnSalir2, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(46, 46, 46)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(49, 49, 49)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(235, 235, 235))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(Chooser1, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                        .addComponent(Chooser2, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(buscar1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel5)
                        .addComponent(BtnBuscar1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txtdescuento, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtiva, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Txt_Total, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(14, 14, 14))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnSalir2, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void buscar1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_buscar1KeyReleased
        refrescar();
        total();
    }//GEN-LAST:event_buscar1KeyReleased

    private void btnSalir2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalir2ActionPerformed
        this.setVisible(false);
    }//GEN-LAST:event_btnSalir2ActionPerformed

    private void tbaReporteCompraMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbaReporteCompraMousePressed
        int x = 0;
        ReporteFacturaDTO obj = new ReporteFacturaDTO();
        if (evt.getClickCount() == 2) {
            x = tbaReporteCompra.getSelectedRow();
            for (int i = 0; i < itemList.size(); i++) {
                if (tbaReporteCompra.getValueAt(x, 0).toString().equals(itemList.get(i).getId_factura().toString())) {
                    obj = itemList.get(i);
                    break;
                }
            }
            if (obj != null) {
                //JOptionPane.showMessageDialog(null, "el id es: "+obj.getId_orden_compra());
                ReporteriaDetalleFactura win = new ReporteriaDetalleFactura(new javax.swing.JFrame(), true, obj, usu, emp, suc);
                System.out.println(" yy " + obj.getForma_pago());
                win.setVisible(true);
                cargartabala();
            } else {
//                System.out.println("no encontramos al puto id error capa 8");
                System.out.println("id error ");
            }
        }
    }//GEN-LAST:event_tbaReporteCompraMousePressed

    private void jLabel4MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseDragged
        Point point = MouseInfo.getPointerInfo().getLocation();
        setLocation(point.x - x, point.y - y);
    }//GEN-LAST:event_jLabel4MouseDragged

    private void jLabel4MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MousePressed
        x = evt.getX();
        y = evt.getY();
    }//GEN-LAST:event_jLabel4MousePressed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        ArrayList lista = new ArrayList();
        String dia = Integer.toString(Chooser1.getCalendar().get(Calendar.DAY_OF_MONTH));
        String mes = Integer.toString(Chooser1.getCalendar().get(Calendar.MONTH));
        String año = Integer.toString(Chooser1.getCalendar().get(Calendar.YEAR));
        String fecha = (dia + "-" + mes + "-" + año);
        String dia2 = Integer.toString(Chooser2.getCalendar().get(Calendar.DAY_OF_MONTH));
        String mes2 = Integer.toString(Chooser2.getCalendar().get(Calendar.MONTH));
        String año2 = Integer.toString(Chooser2.getCalendar().get(Calendar.YEAR));
        String fecha2 = (dia2 + "-" + mes2 + "-" + año2);

        for (int i = 0; i < tbaReporteCompra.getRowCount(); i++) {
            ClaseReporte creporte = new ClaseReporte(fecha,
                    fecha2,
                    tbaReporteCompra.getValueAt(i, 0).toString(),
                    tbaReporteCompra.getValueAt(i, 1).toString(),
                    tbaReporteCompra.getValueAt(i, 2).toString(),
                    tbaReporteCompra.getValueAt(i, 3).toString(),
                    tbaReporteCompra.getValueAt(i, 4).toString(),
                    tbaReporteCompra.getValueAt(i, 5).toString(),
                    tbaReporteCompra.getValueAt(i, 6).toString(),
                    tbaReporteCompra.getValueAt(i, 7).toString(),
                    txtdescuento.getText(), txtiva.getText(), Txt_Total.getText());
            lista.add(creporte);
        }
        try {

            JasperReport report = (JasperReport) JRLoader.loadObject(getClass().getResource("/Reportes/ReporteriaVentas.jasper"));
            JasperPrint jprint = JasperFillManager.fillReport(report, null, new JRBeanCollectionDataSource(lista));
            JDialog ventana = new JDialog();
            net.sf.jasperreports.view.JRViewer jviewer = new net.sf.jasperreports.view.JRViewer(jprint);
            ventana.add(jviewer);
            ventana.setSize(new Dimension(ancho / 2, alto / 2));
            ventana.setLocationRelativeTo(null);
            ventana.setVisible(true);
            jviewer.setFitWidthZoomRatio();
        } catch (JRException ex) {
            Logger.getLogger(ReporteriaVentas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void BtnBuscar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBuscar1ActionPerformed
        busquedaChosserQuery();

    }//GEN-LAST:event_BtnBuscar1ActionPerformed

    private void buscar1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_buscar1KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            System.out.println("lalalalalal fuck");
            refrescar();
            total();
        }
    }//GEN-LAST:event_buscar1KeyPressed

    private void buscar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscar1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_buscar1ActionPerformed

    private void Chooser2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Chooser2KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            System.out.println("sss");
        }
    }//GEN-LAST:event_Chooser2KeyPressed

    private void BtnBuscar1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnBuscar1KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            System.out.println("valeeeeeeeeeeeeeeeeeeeee");
        }
    }//GEN-LAST:event_BtnBuscar1KeyPressed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        reporte();
    }//GEN-LAST:event_jButton3ActionPerformed

    public static void reporte() {

        int r = JOptionPane.showConfirmDialog(null, "¿Generar Reporte?", "", JOptionPane.YES_NO_OPTION);

        if (r == JOptionPane.YES_OPTION) {
            Workbook book = new XSSFWorkbook();
            Sheet sheet = book.createSheet("ventas");

            try {
                InputStream is = new FileInputStream("src\\ec\\com\\asofar\\imagenes\\3.png");
                byte[] bytes = IOUtils.toByteArray(is);
                int imgIndex = book.addPicture(bytes, Workbook.PICTURE_TYPE_PNG);
                is.close();

                CreationHelper help = book.getCreationHelper();
                Drawing draw = sheet.createDrawingPatriarch();

                ClientAnchor anchor = help.createClientAnchor();
                anchor.setCol1(0);
                anchor.setRow1(1);
                Picture pict = draw.createPicture(anchor, imgIndex);
                pict.resize(2, 2);

                CellStyle tituloEstilo = book.createCellStyle();
                tituloEstilo.setAlignment(HorizontalAlignment.CENTER);
                tituloEstilo.setVerticalAlignment(VerticalAlignment.CENTER);
                Font fuenteTitulo = book.createFont();
                fuenteTitulo.setFontName("Arial");
                fuenteTitulo.setBold(true);
                fuenteTitulo.setFontHeightInPoints((short) 14);
                tituloEstilo.setFont(fuenteTitulo);

                Row filaTitulo = sheet.createRow(3);
                Cell celdaTitulo = filaTitulo.createCell(3);
                celdaTitulo.setCellStyle(tituloEstilo);
                celdaTitulo.setCellValue("Reporte Ventas");

                sheet.addMergedRegion(new CellRangeAddress(1, 2, 1, 1));

                String[] cabecera = new String[]{"COD.FACT", "CAJA", "FECHA FACTURA", "COMERCIAL", "SUBTOTAL", "T.DESCUENTO", "T.IVA", "T.FACTURA", "ESTADO"};

                CellStyle headerStyle = book.createCellStyle();
                headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
                headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                headerStyle.setBorderBottom(BorderStyle.THIN);
                headerStyle.setBorderLeft(BorderStyle.THIN);
                headerStyle.setBorderRight(BorderStyle.THIN);
                headerStyle.setBorderBottom(BorderStyle.THIN);

                Font font = book.createFont();
                font.setFontName("Arial");
                font.setBold(true);
                font.setColor(IndexedColors.WHITE.getIndex());
                font.setFontHeightInPoints((short) 12);
                headerStyle.setFont(font);

                Row filaEncabezados = sheet.createRow(8);

                for (int i = 0; i < cabecera.length; i++) {
                    Cell celdaEnzabezado = filaEncabezados.createCell(i);
                    celdaEnzabezado.setCellStyle(headerStyle);
                    celdaEnzabezado.setCellValue(cabecera[i]);
                }

                Conexion con = new Conexion();
                PreparedStatement ps;
                ResultSet rs;
                Connection conn = con.getConexion();

                int numFilaDatos = 9;

                CellStyle datosEstilo = book.createCellStyle();
                datosEstilo.setBorderBottom(BorderStyle.THIN);
                datosEstilo.setBorderLeft(BorderStyle.THIN);
                datosEstilo.setBorderRight(BorderStyle.THIN);
                datosEstilo.setBorderBottom(BorderStyle.THIN);

                ps = conn.prepareStatement("SELECT vf.id_factura , vc.nombre, vf.fecha_facturacion, ss.nombre_comercial, vf.subtotal, vf.total_descuento,\n"
                        + "vf.total_iva, vf.total_facturado, vc.estado\n"
                        + "FROM ve_factura vf\n"
                        + "INNER JOIN ve_caja vc on vf.id_caja = vc.id_caja\n"
                        + "INNER JOIN se_sucursal ss on vf.id_sucursal= ss.id_sucursal;");
                rs = ps.executeQuery();

                int numCol = rs.getMetaData().getColumnCount();

                while (rs.next()) {
                    Row filaDatos = sheet.createRow(numFilaDatos);

                    for (int a = 0; a < numCol; a++) {

                        Cell CeldaDatos = filaDatos.createCell(a);
                        CeldaDatos.setCellStyle(datosEstilo);

                        if (a == 0) {
                            CeldaDatos.setCellValue(rs.getInt(a + 1));
                        } else {
                            CeldaDatos.setCellValue(rs.getString(a + 1));
                        }
                    }
                    /*Cell celdaImporte = filaDatos.createCell(4);
                celdaImporte.setCellStyle(datosEstilo);
                celdaImporte.setCellFormula(String.format("C%d+D%d", numFilaDatos + 1, numFilaDatos + 1));
                     */
                    numFilaDatos++;

                }
                conn.close();
                sheet.autoSizeColumn(0);
                sheet.autoSizeColumn(1);
                sheet.autoSizeColumn(2);
                sheet.autoSizeColumn(3);
                sheet.autoSizeColumn(4);
                sheet.autoSizeColumn(5);
                sheet.autoSizeColumn(6);
                sheet.autoSizeColumn(7);
                sheet.autoSizeColumn(8);

                sheet.setZoom(150);

                FileOutputStream fileOut = new FileOutputStream("ReporteVentas.xlsx");
                book.write(fileOut);
                fileOut.close();

            } catch (FileNotFoundException ex) {
                Logger.getLogger(ReporteriaVentas.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(ReporteriaVentas.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(ReporteriaVentas.class.getName()).log(Level.SEVERE, null, ex);
            }
            JOptionPane.showMessageDialog(null, "Generado con exito");
        } else {
            JOptionPane.showMessageDialog(null, "Error al generar el reporte");
        }

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
            java.util.logging.Logger.getLogger(ReporteriaVentas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ReporteriaVentas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ReporteriaVentas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ReporteriaVentas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ReporteriaVentas dialog = new ReporteriaVentas(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton BtnBuscar1;
    private com.toedter.calendar.JDateChooser Chooser1;
    private com.toedter.calendar.JDateChooser Chooser2;
    private javax.swing.JTextField Txt_Total;
    private javax.swing.JButton btnSalir2;
    private javax.swing.JTextField buscar1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTable tbaReporteCompra;
    private javax.swing.JScrollPane tblProduc;
    private javax.swing.JTextField txtdescuento;
    private javax.swing.JTextField txtiva;
    // End of variables declaration//GEN-END:variables
}
