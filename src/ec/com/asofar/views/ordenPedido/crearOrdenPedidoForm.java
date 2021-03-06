/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.asofar.views.ordenPedido;

import ec.com.asofar.dao.CoDetalleOrdenPedidoJpaController;
import ec.com.asofar.dao.CoOrdenPedidoJpaController;
import ec.com.asofar.dao.CoProveedoresJpaController;
import ec.com.asofar.dao.InTipoDocumentoJpaController;
import ec.com.asofar.daoext.ObtenerDTO;
import ec.com.asofar.daoext.OrdenPedidoDaoExt;
import ec.com.asofar.daoext.ProductoCadena;
import ec.com.asofar.daoext.ordenPedidoEXT;
import ec.com.asofar.dto.CoDetalleOrdenPedido;
import ec.com.asofar.dto.CoDetalleOrdenPedidoPK;
import ec.com.asofar.dto.CoOrdenPedido;
import ec.com.asofar.dto.CoProveedores;
import ec.com.asofar.dto.InTipoDocumento;
import ec.com.asofar.dto.PrProductos;
import ec.com.asofar.dto.SeEmpresa;
import ec.com.asofar.dto.SeSucursal;
import ec.com.asofar.dto.SeUsuarios;
import ec.com.asofar.util.EntityManagerUtil;
import ec.com.asofar.util.Tablas;
import ec.com.asofar.views.producto.ConsultarProducto;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.Timer;

/**
 *
 * @author admin1
 */
public class crearOrdenPedidoForm extends javax.swing.JDialog {

    int x, y;
    SeUsuarios seUsuario;
    SeEmpresa seEmpresa;
    SeSucursal seSucursal;

    Date d = new Date();
    SeEmpresa se = new SeEmpresa();
    ordenPedidoEXT ordenExt = new ordenPedidoEXT(EntityManagerUtil.ObtenerEntityManager());
    CoProveedoresJpaController proveedorcontroller = new CoProveedoresJpaController(EntityManagerUtil.ObtenerEntityManager());
    InTipoDocumentoJpaController movcontroller = new InTipoDocumentoJpaController(EntityManagerUtil.ObtenerEntityManager());

    CoOrdenPedido cOrden;
    OrdenPedidoDaoExt idCabecera = new OrdenPedidoDaoExt(EntityManagerUtil.ObtenerEntityManager());

    List<CoDetalleOrdenPedido> listadet = new ArrayList<CoDetalleOrdenPedido>();
    List<CoOrdenPedido> listcab;
    PrProductos objetopro = new PrProductos();

    int contFilas = 1;

    public crearOrdenPedidoForm(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(null);
        txtFecha.setText(FechaActual());

//        txtCod.setText(String.format("%06d", ordenExt.obtenerNumeroOrden()));
        CargarProveedor();
//        CargarDocumento();

        Timer tiempo = new Timer(100, new crearOrdenPedidoForm.horas());
        tiempo.start();

    }

    public crearOrdenPedidoForm(java.awt.Frame parent, boolean modal, SeUsuarios us, SeEmpresa em, SeSucursal su) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(null);
        txtFecha.setText(FechaActual());
        CargarProveedor();
//        CargarDocumento();

        seUsuario = us;
        seEmpresa = em;
        seSucursal = su;

        Timer tiempo = new Timer(100, new crearOrdenPedidoForm.horas());
        tiempo.start();

    }

    class horas implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            java.util.Date sistHora = new java.util.Date();
            String pmAm = "HH:mm:ss";
            SimpleDateFormat format = new SimpleDateFormat(pmAm);
            Calendar hoy = Calendar.getInstance();
            txtHora.setText(String.format(format.format(sistHora), hoy));

        }
    }

    public static String FechaActual() {
        Date fecha = new Date();
        SimpleDateFormat formatoFecha = new SimpleDateFormat("YYYY-MM-dd");
        return formatoFecha.format(fecha);
    }

    public void CargarProveedor() {
        List<CoProveedores> listcaja = proveedorcontroller.findCoProveedoresEntities();
        for (int i = 0; i < listcaja.size(); i++) {
            cbxProveedor.addItem(listcaja.get(i).getNombre());
        }
    }

    public void CargarDocumento() {
//        List<InTipoDocumento> listcaja = movcontroller.findInTipoDocumentoEntities();
//        for (int i = 0; i < listcaja.size(); i++) {
//            cbx_documento.addItem(listcaja.get(i).getNombreDocumento());
//        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        cbxProveedor = new javax.swing.JComboBox<>();
        txtFecha = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtCod = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtObservacion = new javax.swing.JTextArea();
        jButton1 = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        txtHora = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        cbx_FormaPago = new javax.swing.JComboBox<>();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));

        jLabel1.setBackground(java.awt.Color.red);
        jLabel1.setFont(new java.awt.Font("Ubuntu", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(254, 254, 254));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("ORDEN DE PEDIDO");
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

        jPanel3.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));

        jLabel7.setFont(new java.awt.Font("Ubuntu", 1, 14)); // NOI18N
        jLabel7.setText("PROVEEDOR:");

        cbxProveedor.setFont(new java.awt.Font("Ubuntu", 1, 14)); // NOI18N
        cbxProveedor.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--SELECCIONE--" }));

        txtFecha.setEditable(false);
        txtFecha.setFont(new java.awt.Font("Ubuntu", 1, 14)); // NOI18N
        txtFecha.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel8.setFont(new java.awt.Font("Ubuntu", 1, 14)); // NOI18N
        jLabel8.setText("FECHA EMISION:");

        jLabel12.setFont(new java.awt.Font("Ubuntu", 1, 14)); // NOI18N
        jLabel12.setText("CODIGO DE ORDEN:");

        txtCod.setEditable(false);
        txtCod.setFont(new java.awt.Font("Ubuntu", 1, 14)); // NOI18N
        txtCod.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        jLabel13.setFont(new java.awt.Font("Ubuntu", 1, 14)); // NOI18N
        jLabel13.setText("OBSERVACION:");

        txtObservacion.setColumns(20);
        txtObservacion.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        txtObservacion.setRows(5);
        jScrollPane1.setViewportView(txtObservacion);

        jButton1.setBackground(new java.awt.Color(254, 254, 254));
        jButton1.setFont(new java.awt.Font("Ubuntu", 1, 10)); // NOI18N
        jButton1.setForeground(new java.awt.Color(1, 1, 1));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ec/com/asofar/icon/agregar_Mesa de trabajo 1.png"))); // NOI18N
        jButton1.setText("AGREGAR PRODUCTOS");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Ubuntu", 1, 14)); // NOI18N
        jLabel10.setText("HORA EMISION:");

        txtHora.setEditable(false);
        txtHora.setFont(new java.awt.Font("Ubuntu", 1, 14)); // NOI18N
        txtHora.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel11.setFont(new java.awt.Font("Ubuntu", 1, 14)); // NOI18N
        jLabel11.setText("FORMA PAGO :");

        cbx_FormaPago.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "CONTADO", "CREDITO" }));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(46, 46, 46))
                                .addComponent(jLabel12))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel13)
                                .addGap(36, 36, 36)))
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cbxProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtCod, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel10)
                                    .addComponent(jLabel11))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtFecha)
                                    .addComponent(txtHora)
                                    .addComponent(cbx_FormaPago, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(193, 193, 193)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCod, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtHora, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbx_FormaPago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(24, 24, 24)))
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jButton2.setBackground(new java.awt.Color(254, 254, 254));
        jButton2.setFont(new java.awt.Font("Ubuntu", 1, 10)); // NOI18N
        jButton2.setForeground(new java.awt.Color(1, 1, 1));
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ec/com/asofar/icon/GUARDAR_Mesa de trabajo 1.png"))); // NOI18N
        jButton2.setText("GUARDAR Y CONTINUAR");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setBackground(new java.awt.Color(254, 254, 254));
        jButton3.setFont(new java.awt.Font("Ubuntu", 1, 10)); // NOI18N
        jButton3.setForeground(new java.awt.Color(1, 1, 1));
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ec/com/asofar/icon/Cancelar_Mesa de trabajo 1.jpg"))); // NOI18N
        jButton3.setText("CANCELAR");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jPanel2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));

        jTable1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTable1MousePressed(evt);
            }
        });
        jTable1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTable1KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTable1KeyTyped(evt);
            }
        });
        jScrollPane2.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(101, 101, 101)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2)
                        .addGap(60, 60, 60))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

    private void jTable1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable1KeyTyped
        char car = evt.getKeyChar();
        if (car < '0' || car > '9') {
            evt.consume();
        }
    }//GEN-LAST:event_jTable1KeyTyped

    private void jTable1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable1KeyReleased
        try {

            int i = jTable1.getSelectedRow();

            String valor = (String) jTable1.getValueAt(i, 3);

            BigInteger cantidad = new BigInteger(valor);

            listadet.get(i).setCantidadSolicitada(cantidad);

        } catch (Exception e) {
        }
    }//GEN-LAST:event_jTable1KeyReleased

    private void jTable1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MousePressed

        int row = jTable1.rowAtPoint(evt.getPoint());
        int col = jTable1.columnAtPoint(evt.getPoint());
        if (evt.getClickCount() == 1) {
            if (jTable1.getModel().getColumnClass(col).equals(JButton.class)) {
                try {

                    int r = JOptionPane.showConfirmDialog(null, "Desea eliminar este producto?", "", JOptionPane.YES_OPTION);
                    if (r == JOptionPane.YES_OPTION) {
                        int i = jTable1.getSelectedRow();
                        listadet.remove(i);
                        contFilas = contFilas - 1;
                        for (int j = 0; j < listadet.size(); j++) {
                            contFilas = j + 1;
                            listadet.get(j).getCoDetalleOrdenPedidoPK().setLineaDetalle(contFilas);

                        }
                        Tablas.llenarDetalledeOrden(jTable1, listadet);
                    }
                } catch (Exception e) {
                }
            }

        }

    }//GEN-LAST:event_jTable1MousePressed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        int r = JOptionPane.showConfirmDialog(null, "¿Desea Regresar?", "", JOptionPane.YES_NO_OPTION);

        if (r == JOptionPane.YES_OPTION) {
            setVisible(false);

        } else {

        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        int r = JOptionPane.showConfirmDialog(null, "¿Esta seguro de guardar los datos?", "", JOptionPane.YES_NO_OPTION);
        CoOrdenPedido cabOrden = new CoOrdenPedido();
        CoDetalleOrdenPedido detOrden = new CoDetalleOrdenPedido();
        CoOrdenPedidoJpaController cabOrdencontroller = new CoOrdenPedidoJpaController(EntityManagerUtil.ObtenerEntityManager());
        CoDetalleOrdenPedidoJpaController detOrdencontroller = new CoDetalleOrdenPedidoJpaController(EntityManagerUtil.ObtenerEntityManager());

        if (r == JOptionPane.YES_OPTION) {

            if (cbxProveedor.getSelectedItem().toString().equals("--SELECCIONE--")) {
                JOptionPane.showMessageDialog(null, "LLENE PROVEEDOR!");
            } else {

//                if (cbx_documento.getSelectedItem().toString().equals("--SELECCIONE--")) {
//                    JOptionPane.showMessageDialog(null, "LLENE DOCUMENTO!");
//                } else {
                if (txtObservacion.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "LLENE OBSERVACIÒN!");
                } else {
                    if (listadet.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "AGREGUE UN ITEM!");

                    } else {

                        CoProveedores coOrdenp = ObtenerDTO.ObtenerProveedorPedido(cbxProveedor.getSelectedItem().toString());
                        InTipoDocumento coOrdend = ObtenerDTO.ObtenerDocumentoPedido("COMPRAS");

                        cabOrden.setIdProveedor(BigInteger.valueOf(coOrdenp.getIdProveedor()));
                        cabOrden.setIdDocumento(BigInteger.valueOf(coOrdend.getIdTipoDocumento()));
                        cabOrden.setObservacion(txtObservacion.getText());
                        cabOrden.setEstado("P");
                        cabOrden.setFechaEmision(d);
                        cabOrden.setSeSucursal(seSucursal);
                        cabOrden.setFormaPago(cbx_FormaPago.getSelectedItem().toString());

                        cabOrden.setUsuarioCreacion(seUsuario.getUsuario());
                        cabOrden.setFechaCreacion(d);

                        try {

                            CoOrdenPedido pk = idCabecera.guardarPedido(cabOrden);

                            for (int i = 0; i < listadet.size(); i++) {
                                detOrden.setCoOrdenPedido(pk);
                                detOrden.setCantidadSolicitada(listadet.get(i).getCantidadSolicitada());
                                detOrden.setCoDetalleOrdenPedidoPK(new CoDetalleOrdenPedidoPK());
                                detOrden.getCoDetalleOrdenPedidoPK().setIdProducto(listadet.get(i).getCoDetalleOrdenPedidoPK().getIdProducto());
                                detOrden.getCoDetalleOrdenPedidoPK().setLineaDetalle(listadet.get(i).getCoDetalleOrdenPedidoPK().getLineaDetalle());
                                detOrden.setDescripcion(listadet.get(i).getDescripcion());
                                detOrden.setEstado("A");
                                detOrden.setFechaCreacion(d);
                                detOrden.setUsuarioCreacion(seUsuario.getUsuario());
                                detOrden.getCoOrdenPedido().setSeSucursal(seSucursal);
                                detOrden.setFechaCreacion(d);

                                detOrdencontroller.create(detOrden);
                            }

                            JOptionPane.showMessageDialog(null, "GUARDADO EXITOSAMENTE");
                            this.setVisible(false);
                            aprobarOrdendePedidoForm aprobarOrden = new aprobarOrdendePedidoForm(new javax.swing.JFrame(), true, seUsuario, seEmpresa, seSucursal);
                            aprobarOrden.setVisible(true);
                            
                        } catch (Exception ex) {
                            Logger.getLogger(crearOrdenPedidoForm.class.getName()).log(Level.SEVERE, null, ex);
                        }
//                        }
//                        }
                    }

                }

            }

        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        ConsultarProducto cproducto = new ConsultarProducto(new javax.swing.JFrame(), true);
        cproducto.setVisible(true);
        try {

            objetopro = cproducto.getProducto();

            if (validarProductos("" + (objetopro.getPrProductosPK().getIdProducto())).equals("si")) {
                JOptionPane.showMessageDialog(rootPane, "El producto ya se fue seleccionado!");
            } else {

                CoDetalleOrdenPedido detalle = new CoDetalleOrdenPedido();

                detalle.setCoDetalleOrdenPedidoPK(new CoDetalleOrdenPedidoPK());
                detalle.getCoDetalleOrdenPedidoPK().setIdProducto(objetopro.getPrProductosPK().getIdProducto());
                //                detalle.setDescripcion(ProductoCadena.obtenerCadena(objetopro.getPrProductosPK().getIdProducto()));
                detalle.setDescripcion(objetopro.getNombreProducto());
                detalle.setCantidadSolicitada(BigInteger.valueOf(0));

                listadet.add(detalle);
                for (int i = 0; i < listadet.size(); i++) {

                    contFilas = i + 1;

                    detalle.getCoDetalleOrdenPedidoPK().setLineaDetalle(contFilas);

                }

                Tablas.llenarDetalledeOrden(jTable1, listadet);
            }
        } catch (Exception e) {
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jLabel1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MousePressed
        x = evt.getX();
        y = evt.getY();
    }//GEN-LAST:event_jLabel1MousePressed

    private void jLabel1MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseDragged
        Point point = MouseInfo.getPointerInfo().getLocation();
        setLocation(point.x - x, point.y - y);
    }//GEN-LAST:event_jLabel1MouseDragged

    public String validarProductos(String datos) {
        String obj1 = "no";

        for (int i = 0; i < listadet.size(); i++) {

            if (datos.equals("" + (listadet.get(i).getCoDetalleOrdenPedidoPK().getIdProducto()))) {
                obj1 = "si";

                break;
            }

        }

        return obj1;

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
            java.util.logging.Logger.getLogger(crearOrdenPedidoForm.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(crearOrdenPedidoForm.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(crearOrdenPedidoForm.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(crearOrdenPedidoForm.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                crearOrdenPedidoForm dialog = new crearOrdenPedidoForm(new javax.swing.JFrame(), true);
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
    private javax.swing.JComboBox<String> cbxProveedor;
    private javax.swing.JComboBox<String> cbx_FormaPago;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField txtCod;
    private javax.swing.JTextField txtFecha;
    private javax.swing.JTextField txtHora;
    private javax.swing.JTextArea txtObservacion;
    // End of variables declaration//GEN-END:variables
}
