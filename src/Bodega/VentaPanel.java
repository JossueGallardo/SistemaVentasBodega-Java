/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Bodega;

/**
 *
 * @author Daniel
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
public class VentaPanel extends javax.swing.JPanel {

    /**
     * Creates new form VentaPanel
     */
    public VentaPanel() {
         initComponents();
        cargarProductosYMedidas();
    }

     // Método para cargar productos y medidas en los ComboBox
    private void cargarProductosYMedidas() {
        String url = "jdbc:postgresql://localhost:5432/Bodegas";
        String user = "postgres";
        String password = "20213123Juan";

        String sql = "SELECT DISTINCT nombre, medida FROM productos";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            // Limpiar los ComboBox antes de cargar datos
            unidadMedidaCB.removeAllItems();
            categoriaCB.removeAllItems();

            // Llenar los ComboBox con datos únicos
            while (rs.next()) {
                String nombre = rs.getString("nombre");
                String medida = rs.getString("medida");

                if (nombre != null && !nombre.isEmpty()) {
                    unidadMedidaCB.addItem(nombre);
                }
                if (medida != null && !medida.isEmpty()) {
                    categoriaCB.addItem(medida);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar productos y medidas.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método para realizar la venta
    private void realizarVenta() {
    String producto = (String) unidadMedidaCB.getSelectedItem();
    String medida = (String) categoriaCB.getSelectedItem();
    String cantidadStr = nombreProducto.getText();

    if (producto == null || producto.isEmpty() || medida == null || medida.isEmpty() || cantidadStr.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    int cantidad;
    try {
        cantidad = Integer.parseInt(cantidadStr);
        if (cantidad <= 0) {
            JOptionPane.showMessageDialog(this, "La cantidad debe ser un número positivo.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "La cantidad debe ser un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    String url = "jdbc:postgresql://localhost:5432/Bodegas";
    String user = "postgres";
    String password = "20213123Juan";

    // Consultas SQL
    String sqlConsulta = "SELECT id, stock FROM productos WHERE nombre = ? AND medida = ?";
    String sqlActualizar = "UPDATE productos SET stock = stock - ? WHERE id = ?";
    String sqlInsertarVenta = "INSERT INTO ventas (id_producto, cantidad, fecha) VALUES (?, ?, CURRENT_DATE)";

    try (Connection conn = DriverManager.getConnection(url, user, password);
         PreparedStatement psConsulta = conn.prepareStatement(sqlConsulta);
         PreparedStatement psActualizar = conn.prepareStatement(sqlActualizar);
         PreparedStatement psInsertarVenta = conn.prepareStatement(sqlInsertarVenta)) {

        // Consultar el stock actual
        psConsulta.setString(1, producto);
        psConsulta.setString(2, medida);
        ResultSet rs = psConsulta.executeQuery();

        if (rs.next()) {
            int idProducto = rs.getInt("id");
            int stockActual = rs.getInt("stock");

            if (cantidad > stockActual) {
                JOptionPane.showMessageDialog(this, "Stock insuficiente. Solo hay " + stockActual + " " + medida + " disponibles.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Actualizar el stock en la base de datos
            psActualizar.setInt(1, cantidad);
            psActualizar.setInt(2, idProducto);
            psActualizar.executeUpdate();

            // Registrar la venta en la tabla 'ventas'
            psInsertarVenta.setInt(1, idProducto);
            psInsertarVenta.setInt(2, cantidad);
            psInsertarVenta.executeUpdate();

            JOptionPane.showMessageDialog(this, "Venta realizada exitosamente. Se vendieron " + cantidad + " " + medida + " de " + producto + ".");
        } else {
            JOptionPane.showMessageDialog(this, "Producto no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
        }

    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error al realizar la venta.", "Error", JOptionPane.ERROR_MESSAGE);
    }
}
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lateralAz = new javax.swing.JPanel();
        icono = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        lblIngresoProductos = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel1 = new javax.swing.JLabel();
        nombreProducto = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        categoriaCB = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        unidadMedidaCB = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        hecho = new javax.swing.JButton();

        setBackground(new java.awt.Color(236, 236, 236));
        setMinimumSize(new java.awt.Dimension(760, 490));
        setPreferredSize(new java.awt.Dimension(740, 480));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lateralAz.setBackground(new java.awt.Color(64, 119, 119));

        icono.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/bodegaManu.png"))); // NOI18N

        jLabel7.setFont(new java.awt.Font("Ebrima", 0, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("BODEGA MANUELITA");

        javax.swing.GroupLayout lateralAzLayout = new javax.swing.GroupLayout(lateralAz);
        lateralAz.setLayout(lateralAzLayout);
        lateralAzLayout.setHorizontalGroup(
            lateralAzLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(lateralAzLayout.createSequentialGroup()
                .addGap(85, 85, 85)
                .addComponent(jLabel7)
                .addContainerGap(89, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, lateralAzLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(icono)
                .addGap(126, 126, 126))
        );
        lateralAzLayout.setVerticalGroup(
            lateralAzLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, lateralAzLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(icono)
                .addContainerGap(344, Short.MAX_VALUE))
        );

        add(lateralAz, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 0, 350, 500));

        lblIngresoProductos.setFont(new java.awt.Font("Ebrima", 0, 24)); // NOI18N
        lblIngresoProductos.setText("VENTAS");
        add(lblIngresoProductos, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 50, -1, -1));

        jSeparator1.setForeground(new java.awt.Color(0, 102, 102));
        add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 110, 330, 10));

        jLabel1.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N
        jLabel1.setText("Nombre");
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 220, -1, -1));

        nombreProducto.setFont(new java.awt.Font("Ebrima", 0, 12)); // NOI18N
        nombreProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nombreProductoActionPerformed(evt);
            }
        });
        add(nombreProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 270, 175, -1));

        jLabel3.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N
        jLabel3.setText("Medida");
        add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 170, -1, -1));

        categoriaCB.setFont(new java.awt.Font("Ebrima", 0, 12)); // NOI18N
        categoriaCB.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        add(categoriaCB, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 170, 175, -1));

        jLabel4.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N
        jLabel4.setText("Cantidad");
        add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 270, -1, -1));

        unidadMedidaCB.setFont(new java.awt.Font("Ebrima", 0, 12)); // NOI18N
        unidadMedidaCB.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        add(unidadMedidaCB, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 220, 175, -1));

        jLabel2.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N
        jLabel2.setText("Detalle");
        add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 130, 50, -1));

        hecho.setBackground(new java.awt.Color(0, 102, 102));
        hecho.setFont(new java.awt.Font("Ebrima", 0, 12)); // NOI18N
        hecho.setForeground(new java.awt.Color(255, 255, 255));
        hecho.setText("HECHO");
        hecho.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hechoActionPerformed(evt);
            }
        });
        add(hecho, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 330, -1, -1));
    }// </editor-fold>//GEN-END:initComponents

    private void nombreProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nombreProductoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nombreProductoActionPerformed

    private void hechoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hechoActionPerformed
        realizarVenta();
    }//GEN-LAST:event_hechoActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> categoriaCB;
    private javax.swing.JButton hecho;
    private javax.swing.JLabel icono;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JPanel lateralAz;
    private javax.swing.JLabel lblIngresoProductos;
    private javax.swing.JTextField nombreProducto;
    private javax.swing.JComboBox<String> unidadMedidaCB;
    // End of variables declaration//GEN-END:variables
}
