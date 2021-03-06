/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.asofar.views.clientes;

import ec.com.asofar.dao.SeCiudadJpaController;
import ec.com.asofar.dao.SeLocalidadClienteJpaController;
import ec.com.asofar.dao.SePaisJpaController;
import ec.com.asofar.dao.SeProvinciaJpaController;
import ec.com.asofar.daoext.ObtenerDTO;
import ec.com.asofar.dto.SeCiudad;
import ec.com.asofar.dto.SeClientes;
import ec.com.asofar.dto.SeEmpresa;
import ec.com.asofar.dto.SeLocalidadCliente;
import ec.com.asofar.dto.SePais;
import ec.com.asofar.dto.SeProvincia;
import ec.com.asofar.dto.SeSucursal;
import ec.com.asofar.dto.SeUsuarios;
import ec.com.asofar.util.EntityManagerUtil;
import java.awt.MouseInfo;
import java.awt.Point;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author admin1
 */
public class Localidad_editar extends javax.swing.JDialog {

    int x, y;
    String valor = "";
    List<SePais> Pais;
    SePaisJpaController Pc = new SePaisJpaController(EntityManagerUtil.ObtenerEntityManager());
    List<SeCiudad> Ciudad;
    SeCiudadJpaController Cc = new SeCiudadJpaController(EntityManagerUtil.ObtenerEntityManager());
    List<SeProvincia> Provincia;
    SeProvinciaJpaController Prc = new SeProvinciaJpaController(EntityManagerUtil.ObtenerEntityManager());
    SeLocalidadCliente LocalidadCliente;
    SeLocalidadCliente localidadclientes = new SeLocalidadCliente();
    SeLocalidadClienteJpaController Lc = new SeLocalidadClienteJpaController(EntityManagerUtil.ObtenerEntityManager());
    SeClientes cliente;
    SeUsuarios usu;
    SeEmpresa emp;
    SeSucursal suc;

    public Localidad_editar(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        setUndecorated(true);
        initComponents();
        setLocationRelativeTo(null);
        Pais = Pc.findSePaisEntities();
        Ciudad = Cc.findSeCiudadEntities();
        Provincia = Prc.findSeProvinciaEntities();
        llenarComboPais(Pais);
        llenarComboCiudad(Ciudad);
        llenarComboProvincia(Provincia);
    }

    public Localidad_editar(java.awt.Frame parent, boolean modal, SeUsuarios us, SeEmpresa em, SeSucursal su, SeClientes cl, SeLocalidadCliente lc) {
        super(parent, modal);
        setUndecorated(true);
        initComponents();
        setLocationRelativeTo(null);
        Pais = Pc.findSePaisEntities();
        Ciudad = Cc.findSeCiudadEntities();
        Provincia = Prc.findSeProvinciaEntities();
        usu = us;
        emp = em;
        suc = su;
        localidadclientes = lc;
        System.out.println("localidad " + localidadclientes.getIdLocalidadCliente());
        cliente = cl;
        llenar();
        llenarComboPais(Pais);
        llenarComboCiudad(Ciudad);
        llenarComboProvincia(Provincia);
    }

    public void llenar() {
        txt_direccion_cliente.setText(localidadclientes.getDirreccionCliente());
        txt_direccion_entrega.setText(localidadclientes.getDirreccionEntrega());
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
        jLabel3 = new javax.swing.JLabel();
        cbx_pais = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        cbx_Ciudad = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        cbx_provincia = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        txt_direccion_cliente = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txt_direccion_entrega = new javax.swing.JTextField();
        btn_guardar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));

        jLabel3.setBackground(java.awt.Color.red);
        jLabel3.setFont(new java.awt.Font("Ubuntu", 1, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(254, 254, 254));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("LOCALIDAD EDITAR");
        jLabel3.setOpaque(true);
        jLabel3.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jLabel3MouseDragged(evt);
            }
        });
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel3MousePressed(evt);
            }
        });

        cbx_pais.setFont(new java.awt.Font("Ubuntu", 1, 14)); // NOI18N

        jLabel1.setFont(new java.awt.Font("Ubuntu", 1, 14)); // NOI18N
        jLabel1.setText("PAIS:");

        jLabel2.setFont(new java.awt.Font("Ubuntu", 1, 14)); // NOI18N
        jLabel2.setText("CIUDAD:");

        cbx_Ciudad.setFont(new java.awt.Font("Ubuntu", 1, 14)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Ubuntu", 1, 14)); // NOI18N
        jLabel4.setText("PROVINCIA:");

        cbx_provincia.setFont(new java.awt.Font("Ubuntu", 1, 14)); // NOI18N

        jButton1.setBackground(new java.awt.Color(254, 254, 254));
        jButton1.setFont(new java.awt.Font("Ubuntu", 1, 10)); // NOI18N
        jButton1.setForeground(new java.awt.Color(1, 1, 1));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ec/com/asofar/icon/salir_Mesa de trabajo 10.jpg"))); // NOI18N
        jButton1.setText("SALIR");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        txt_direccion_cliente.setFont(new java.awt.Font("Ubuntu", 1, 14)); // NOI18N
        txt_direccion_cliente.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_direccion_clienteFocusLost(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Ubuntu", 1, 14)); // NOI18N
        jLabel5.setText("DIRECCION DE CLIENTE:");

        jLabel6.setFont(new java.awt.Font("Ubuntu", 1, 14)); // NOI18N
        jLabel6.setText("DIRECCION DE ENTREGA:");

        txt_direccion_entrega.setFont(new java.awt.Font("Ubuntu", 1, 14)); // NOI18N
        txt_direccion_entrega.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_direccion_entregaFocusLost(evt);
            }
        });

        btn_guardar.setFont(new java.awt.Font("Ubuntu", 1, 10)); // NOI18N
        btn_guardar.setForeground(new java.awt.Color(1, 1, 1));
        btn_guardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ec/com/asofar/icon/GUARDAR_Mesa de trabajo 1.png"))); // NOI18N
        btn_guardar.setText("GUARDAR");
        btn_guardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_guardarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_direccion_cliente, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_direccion_entrega, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(cbx_provincia, javax.swing.GroupLayout.Alignment.LEADING, 0, 140, Short.MAX_VALUE)
                                .addComponent(cbx_Ciudad, javax.swing.GroupLayout.Alignment.LEADING, 0, 140, Short.MAX_VALUE)
                                .addComponent(cbx_pais, javax.swing.GroupLayout.Alignment.LEADING, 0, 140, Short.MAX_VALUE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_guardar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(62, 62, 62))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_direccion_cliente, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_direccion_entrega, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbx_pais, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbx_Ciudad, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbx_provincia, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_guardar, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14))
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
    public void llenarComboPais(List<SePais> Pais) {
        for (int i = 0; i < Pais.size(); i++) {
            if (!"I".equals(Pais.get(i).getEstado())) {
                cbx_pais.addItem(Pais.get(i).getNombre());
                if (localidadclientes.getIdPais().getNombre() == Pais.get(i).getNombre()) {
                    cbx_pais.setSelectedItem(Pais.get(i).getNombre());
                }
            }
        }
    }

    public void llenarComboProvincia(List<SeProvincia> Provincia) {
        for (int i = 0; i < Provincia.size(); i++) {
            if (!"I".equals(Provincia.get(i).getNombre())) {
                cbx_provincia.addItem(Provincia.get(i).getNombre());
                if (localidadclientes.getIdProvincia().getNombre() == Provincia.get(i).getNombre()) {
                    cbx_provincia.setSelectedItem(Provincia.get(i).getNombre());
                }
            }
        }
    }

    public void llenarComboCiudad(List<SeCiudad> Ciudad) {
        for (int i = 0; i < Ciudad.size(); i++) {
            if (!"I".equals(Ciudad.get(i).getNombre())) {
                cbx_Ciudad.addItem(Ciudad.get(i).getNombre());
                if (localidadclientes.getIdCiudad().getNombre() == Ciudad.get(i).getNombre()) {
                    cbx_Ciudad.setSelectedItem(Ciudad.get(i).getNombre());
                }
            }
        }
    }
    private void jLabel3MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseDragged
        Point point = MouseInfo.getPointerInfo().getLocation();
        setLocation(point.x - x, point.y - y);
    }//GEN-LAST:event_jLabel3MouseDragged

    private void jLabel3MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MousePressed
        x = evt.getX();
        y = evt.getY();
    }//GEN-LAST:event_jLabel3MousePressed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        setVisible(false);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btn_guardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_guardarActionPerformed
        java.util.Date fechaActual = new java.util.Date();
        SePais pais = new SePais();
        pais = ObtenerDTO.ObtenerSePais(cbx_pais.getSelectedItem().toString());
        System.out.println("pais " + pais);
        SeProvincia provincia = new SeProvincia();
        provincia = ObtenerDTO.ObtenerSeProvincia(cbx_provincia.getSelectedItem().toString());
        System.out.println("provincia " + provincia);
        SeCiudad ciudad = new SeCiudad();
        ciudad = ObtenerDTO.ObtenerSeCiudad(cbx_Ciudad.getSelectedItem().toString());
        localidadclientes.setDirreccionCliente(txt_direccion_cliente.getText());
        localidadclientes.setIdCliente(cliente);
        localidadclientes.setDirreccionEntrega(txt_direccion_entrega.getText());
        localidadclientes.setIdCiudad(ciudad);
        localidadclientes.setIdProvincia(provincia);
        localidadclientes.setIdPais(pais);
        localidadclientes.setEstado("A");
        localidadclientes.setUsuarioCreacion(usu.getUsuario());
        localidadclientes.setFechaCreacion(fechaActual);
        try {
            Lc.edit(localidadclientes);
            JOptionPane.showMessageDialog(null, "GUARDADO EXITOSAMENTE");
            setVisible(false);
        } catch (Exception ex) {
            Logger.getLogger(Localidad_agregar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btn_guardarActionPerformed

    private void txt_direccion_clienteFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_direccion_clienteFocusLost
        txt_direccion_cliente.setText(txt_direccion_cliente.getText().toUpperCase());
    }//GEN-LAST:event_txt_direccion_clienteFocusLost

    private void txt_direccion_entregaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_direccion_entregaFocusLost
        txt_direccion_entrega.setText(txt_direccion_entrega.getText().toUpperCase());
    }//GEN-LAST:event_txt_direccion_entregaFocusLost
 
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
            java.util.logging.Logger.getLogger(Localidad_editar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Localidad_editar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Localidad_editar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Localidad_editar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Localidad_editar dialog = new Localidad_editar(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btn_guardar;
    private javax.swing.JComboBox<String> cbx_Ciudad;
    private javax.swing.JComboBox<String> cbx_pais;
    private javax.swing.JComboBox<String> cbx_provincia;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField txt_direccion_cliente;
    private javax.swing.JTextField txt_direccion_entrega;
    // End of variables declaration//GEN-END:variables
}
