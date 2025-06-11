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
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableModel;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class CrearPanel extends javax.swing.JPanel {

    /**
     * Creates new form CrearPanel
     */
    public CrearPanel() {
        initComponents();
          inicializarComboBoxes();           
        cargarProductosEnTabla(); 
    }
 private void cargarProductosEnTabla() {
        // Datos de conexión
        String url = "jdbc:postgresql://localhost:5432/Bodegas";
        String user = "postgres";
        String password = "20213123Juan";

        String sql = "SELECT nombre, descripcion, categoria, medida, stock FROM productos";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            // Obtener el modelo de la tabla directamente
            DefaultTableModel modelo = (DefaultTableModel) jTable1.getModel();

            // Limpiar la tabla antes de cargar nuevos datos
            modelo.setRowCount(0);

            // Agregar filas al modelo de la tabla
            while (rs.next()) {
                Object[] fila = {
                    rs.getString("nombre"),
                    rs.getString("descripcion"),
                    rs.getString("categoria"),
                    rs.getString("medida"),
                    rs.getInt("stock")
                };
                modelo.addRow(fila);
            }

        } catch (Exception e) {
            e.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(this, "Error al cargar los productos.", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }

    private void inicializarComboBoxes() {
        // Categorías predefinidas
        categoriaCB.addItem("Alimentos");
        categoriaCB.addItem("Bebidas");
        categoriaCB.addItem("Electrónicos");
        categoriaCB.addItem("Ropa");
        categoriaCB.addItem("Otros");

        // Unidades de medida predefinidas
        unidadMedidaCB.addItem("Kg");
        unidadMedidaCB.addItem("Litros");
        unidadMedidaCB.addItem("Unidades");
        unidadMedidaCB.addItem("Cajas");
        unidadMedidaCB.addItem("Paquetes");
    }
// Método para guardar el producto en la base de datos
     private void guardarProducto() {
        // Datos de conexión a la base de datos
        String url = "jdbc:postgresql://localhost:5432/Bodegas";
        String user = "postgres";
        String password = "20213123Juan";

        // Recoger los datos del formulario
        String nombre = nombreProducto.getText();
        String descripcion = descripcionProducto.getText();
        String categoria = categoriaCB.getSelectedItem().toString();
        String medida = unidadMedidaCB.getSelectedItem().toString();
        String stockStr = stockInicial.getText();
        int stock;

        // Validar que el stock sea un número válido
        try {
            stock = Integer.parseInt(stockStr);
            if (stock < 0) {
                JOptionPane.showMessageDialog(this, "El stock no puede ser negativo.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El stock debe ser un número entero.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validar que los campos obligatorios estén llenos
        if (nombre.isEmpty() || categoria.isEmpty() || medida.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Insertar el producto en la base de datos
        String sql = "INSERT INTO productos (nombre, descripcion, categoria, medida, stock) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nombre);
            ps.setString(2, descripcion);
            ps.setString(3, categoria);
            ps.setString(4, medida);
            ps.setInt(5, stock);

            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Producto guardado exitosamente.");

            // Limpiar el formulario después de guardar
            limpiarFormulario();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al guardar el producto.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método para limpiar los campos del formulario
    private void limpiarFormulario() {
        nombreProducto.setText("");
        descripcionProducto.setText("");
        categoriaCB.setSelectedIndex(0);
        unidadMedidaCB.setSelectedIndex(0);
        stockInicial.setText("");
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        nombreProducto = new javax.swing.JTextField();
        descripcionProducto = new javax.swing.JTextField();
        categoriaCB = new javax.swing.JComboBox<>();
        unidadMedidaCB = new javax.swing.JComboBox<>();
        guardarProducto = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        stockInicial = new javax.swing.JTextField();
        lateralAz = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        icono = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        lblIngresoProductos = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();

        setBackground(new java.awt.Color(236, 236, 236));
        setMinimumSize(new java.awt.Dimension(760, 490));
        setPreferredSize(new java.awt.Dimension(740, 480));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N
        jLabel1.setText("Nombre");
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(39, 139, -1, -1));

        jLabel2.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N
        jLabel2.setText("Descripción");
        add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(39, 186, -1, -1));

        jLabel3.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N
        jLabel3.setText("Categoría");
        add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(39, 234, -1, -1));

        jLabel4.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N
        jLabel4.setText("Medida");
        add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(39, 278, -1, -1));

        nombreProducto.setFont(new java.awt.Font("Ebrima", 0, 12)); // NOI18N
        nombreProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nombreProductoActionPerformed(evt);
            }
        });
        add(nombreProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 140, 175, -1));

        descripcionProducto.setFont(new java.awt.Font("Ebrima", 0, 12)); // NOI18N
        descripcionProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                descripcionProductoActionPerformed(evt);
            }
        });
        add(descripcionProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 180, 175, -1));

        categoriaCB.setFont(new java.awt.Font("Ebrima", 0, 12)); // NOI18N
        categoriaCB.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        categoriaCB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                categoriaCBActionPerformed(evt);
            }
        });
        add(categoriaCB, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 230, 175, -1));

        unidadMedidaCB.setFont(new java.awt.Font("Ebrima", 0, 12)); // NOI18N
        unidadMedidaCB.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        unidadMedidaCB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                unidadMedidaCBActionPerformed(evt);
            }
        });
        add(unidadMedidaCB, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 270, 175, -1));

        guardarProducto.setBackground(new java.awt.Color(0, 102, 102));
        guardarProducto.setFont(new java.awt.Font("Ebrima", 0, 12)); // NOI18N
        guardarProducto.setForeground(new java.awt.Color(255, 255, 255));
        guardarProducto.setText("GUARDAR");
        guardarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                guardarProductoActionPerformed(evt);
            }
        });
        add(guardarProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 390, -1, -1));

        jLabel5.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N
        jLabel5.setText("Stock Inicial");
        add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(39, 330, -1, -1));

        stockInicial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stockInicialActionPerformed(evt);
            }
        });
        add(stockInicial, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 330, 175, -1));

        lateralAz.setBackground(new java.awt.Color(64, 119, 119));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null},
                {null},
                {null},
                {null}
            },
            new String [] {
                "Productos Ingresados"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setResizable(false);
        }

        icono.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/bodegaManu.png"))); // NOI18N

        jLabel6.setFont(new java.awt.Font("Ebrima", 0, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("BODEGA MANUELITA");

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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, lateralAzLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(icono)
                .addGap(126, 126, 126))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, lateralAzLayout.createSequentialGroup()
                .addContainerGap(29, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
            .addGroup(lateralAzLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(lateralAzLayout.createSequentialGroup()
                    .addGap(89, 89, 89)
                    .addComponent(jLabel6)
                    .addContainerGap(90, Short.MAX_VALUE)))
        );
        lateralAzLayout.setVerticalGroup(
            lateralAzLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, lateralAzLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(icono)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(39, Short.MAX_VALUE))
            .addGroup(lateralAzLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(lateralAzLayout.createSequentialGroup()
                    .addGap(237, 237, 237)
                    .addComponent(jLabel6)
                    .addContainerGap(238, Short.MAX_VALUE)))
        );

        add(lateralAz, new org.netbeans.lib.awtextra.AbsoluteConstraints(363, -4, 350, 500));

        lblIngresoProductos.setFont(new java.awt.Font("Ebrima", 0, 24)); // NOI18N
        lblIngresoProductos.setText("INGRESO DE LOS PRODUCTOS");
        add(lblIngresoProductos, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, -1, -1));

        jSeparator1.setForeground(new java.awt.Color(0, 102, 102));
        add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, 330, 10));
    }// </editor-fold>//GEN-END:initComponents

    private void nombreProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nombreProductoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nombreProductoActionPerformed

    private void descripcionProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_descripcionProductoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_descripcionProductoActionPerformed

    private void categoriaCBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_categoriaCBActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_categoriaCBActionPerformed

    private void unidadMedidaCBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_unidadMedidaCBActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_unidadMedidaCBActionPerformed

    private void stockInicialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stockInicialActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_stockInicialActionPerformed

    private void guardarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_guardarProductoActionPerformed
        guardarProducto();
    }//GEN-LAST:event_guardarProductoActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> categoriaCB;
    private javax.swing.JTextField descripcionProducto;
    private javax.swing.JButton guardarProducto;
    private javax.swing.JLabel icono;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable jTable1;
    private javax.swing.JPanel lateralAz;
    private javax.swing.JLabel lblIngresoProductos;
    private javax.swing.JTextField nombreProducto;
    private javax.swing.JTextField stockInicial;
    private javax.swing.JComboBox<String> unidadMedidaCB;
    // End of variables declaration//GEN-END:variables
}
