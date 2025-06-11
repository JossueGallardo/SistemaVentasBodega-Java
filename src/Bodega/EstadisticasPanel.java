/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Bodega;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
public class EstadisticasPanel extends javax.swing.JPanel {

    /**
     * Creates new form EstadisticasPanel
     */
    public EstadisticasPanel() {
         initComponents();
        cargarCategorias();
    }

    
    // Método para cargar categorías en el ComboBox
    private void cargarCategorias() {
        String url = "jdbc:postgresql://localhost:5432/Bodegas";
        String user = "postgres";
        String password = "20213123Juan";

        String sql = "SELECT DISTINCT categoria FROM productos";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            // Limpiar el ComboBox antes de cargar datos
            categoriaCB.removeAllItems();

            // Agregar todas las categorías al ComboBox
            categoriaCB.addItem("Todas"); // Opción para no filtrar por categoría
            while (rs.next()) {
                categoriaCB.addItem(rs.getString("categoria"));
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar las categorías.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método para obtener estadísticas
   private void obtenerEstadisticas() {
    String categoriaSeleccionada = (String) categoriaCB.getSelectedItem();
    boolean filtrarPorCategoria = categoriaSeleccionada != null && !categoriaSeleccionada.equals("Todas");

    String url = "jdbc:postgresql://localhost:5432/Bodegas";
    String user = "postgres";
    String password = "20213123Juan";

    // Consultas SQL
    String sqlProductoMasVendido = "SELECT p.nombre, SUM(v.cantidad) AS total_vendido "
                                 + "FROM ventas v "
                                 + "JOIN productos p ON v.id_producto = p.id "
                                 + (filtrarPorCategoria ? "WHERE p.categoria = ? " : "")
                                 + "GROUP BY p.nombre "
                                 + "ORDER BY total_vendido DESC "
                                 + "LIMIT 1";

    String sqlTotalVentas = "SELECT SUM(v.cantidad) AS total_ventas FROM ventas v "
                          + (filtrarPorCategoria ? "JOIN productos p ON v.id_producto = p.id WHERE p.categoria = ?" : "");

    String sqlPromedioVentas = "SELECT AVG(v.cantidad) AS promedio_ventas FROM ventas v "
                             + (filtrarPorCategoria ? "JOIN productos p ON v.id_producto = p.id WHERE p.categoria = ?" : "");

    String sqlVentasSemanales = "SELECT SUM(v.cantidad) AS total_semanal "
                               + "FROM ventas v "
                               + (filtrarPorCategoria ? "JOIN productos p ON v.id_producto = p.id WHERE p.categoria = ? AND " : "WHERE ")
                               + "v.fecha >= CURRENT_DATE - INTERVAL '7 days'";

    try (Connection conn = DriverManager.getConnection(url, user, password)) {

        // Producto más vendido
        try (PreparedStatement ps = conn.prepareStatement(sqlProductoMasVendido)) {
            if (filtrarPorCategoria) ps.setString(1, categoriaSeleccionada);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                jTextPane1.setText(rs.getString("nombre")); // Producto más vendido
            } else {
                jTextPane1.setText("N/A");
            }
        }

        // Total de ventas
        try (PreparedStatement ps = conn.prepareStatement(sqlTotalVentas)) {
            if (filtrarPorCategoria) ps.setString(1, categoriaSeleccionada);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                jTextPane2.setText(String.valueOf(rs.getInt("total_ventas"))); // Total de ventas
            } else {
                jTextPane2.setText("0");
            }
        }

        // Promedio de ventas
        try (PreparedStatement ps = conn.prepareStatement(sqlPromedioVentas)) {
            if (filtrarPorCategoria) ps.setString(1, categoriaSeleccionada);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                jTextPane3.setText(String.valueOf(rs.getDouble("promedio_ventas"))); // Promedio de ventas
            } else {
                jTextPane3.setText("0");
            }
        }

        // Ventas semanales
        try (PreparedStatement ps = conn.prepareStatement(sqlVentasSemanales)) {
            if (filtrarPorCategoria) ps.setString(1, categoriaSeleccionada);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                jTextPane4.setText(String.valueOf(rs.getInt("total_semanal"))); // Ventas semanales
            } else {
                jTextPane4.setText("0");
            }
        }

    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error al obtener estadísticas.", "Error", JOptionPane.ERROR_MESSAGE);
    }
}
    // Evento del botón CONSULTAR

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextPane2 = new javax.swing.JTextPane();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextPane3 = new javax.swing.JTextPane();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTextPane4 = new javax.swing.JTextPane();
        consultar = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        icono = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        lblIngresoProductos = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        categoriaCB = new javax.swing.JComboBox<>();

        setBackground(new java.awt.Color(236, 236, 236));
        setMinimumSize(new java.awt.Dimension(760, 490));
        setPreferredSize(new java.awt.Dimension(740, 480));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N
        jLabel1.setText("Producto más vendido");
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 180, -1, -1));

        jLabel2.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N
        jLabel2.setText("Total de ventas");
        add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 240, -1, -1));

        jLabel3.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N
        jLabel3.setText("Valor promedio de ventas");
        add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 290, -1, -1));

        jLabel4.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N
        jLabel4.setText("Total ventas semanales");
        add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 340, -1, -1));

        jScrollPane1.setViewportView(jTextPane1);

        add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 180, 190, -1));

        jScrollPane2.setViewportView(jTextPane2);

        add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 240, 120, -1));

        jLabel6.setFont(new java.awt.Font("Ebrima", 0, 12)); // NOI18N
        jLabel6.setText("Resultado");
        add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 220, -1, -1));

        jLabel8.setFont(new java.awt.Font("Ebrima", 0, 12)); // NOI18N
        jLabel8.setText("Categoría");
        add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 220, -1, -1));

        jScrollPane3.setViewportView(jTextPane3);

        add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 290, 190, -1));

        jScrollPane4.setViewportView(jTextPane4);

        add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 340, 190, -1));

        consultar.setBackground(new java.awt.Color(0, 102, 102));
        consultar.setFont(new java.awt.Font("Ebrima", 0, 12)); // NOI18N
        consultar.setForeground(new java.awt.Color(255, 255, 255));
        consultar.setText("CONSULTAR");
        consultar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                consultarActionPerformed(evt);
            }
        });
        add(consultar, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 400, 100, -1));

        jPanel1.setBackground(new java.awt.Color(64, 119, 119));

        icono.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/bodegaManu.png"))); // NOI18N

        jLabel7.setFont(new java.awt.Font("Ebrima", 0, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("BODEGA MANUELITA");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel7)
                .addContainerGap(32, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(icono)
                .addGap(65, 65, 65))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(icono)
                .addContainerGap(339, Short.MAX_VALUE))
        );

        add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 0, 230, 490));

        lblIngresoProductos.setFont(new java.awt.Font("Ebrima", 0, 24)); // NOI18N
        lblIngresoProductos.setText("CONSULTAS");
        add(lblIngresoProductos, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 50, -1, -1));

        jSeparator1.setForeground(new java.awt.Color(0, 102, 102));
        add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 120, 480, 10));

        categoriaCB.setFont(new java.awt.Font("Ebrima", 0, 12)); // NOI18N
        categoriaCB.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        add(categoriaCB, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 240, 160, -1));
    }// </editor-fold>//GEN-END:initComponents

    private void consultarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_consultarActionPerformed
        obtenerEstadisticas();
    }//GEN-LAST:event_consultarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> categoriaCB;
    private javax.swing.JButton consultar;
    private javax.swing.JLabel icono;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextPane jTextPane1;
    private javax.swing.JTextPane jTextPane2;
    private javax.swing.JTextPane jTextPane3;
    private javax.swing.JTextPane jTextPane4;
    private javax.swing.JLabel lblIngresoProductos;
    // End of variables declaration//GEN-END:variables
}
