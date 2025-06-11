/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Bodega;

/**
 *
 * @author Daniel
 */
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
public class ActualizarPanel extends javax.swing.JPanel {

    /**
     * Creates new form ActualizarPanel
     */
    public ActualizarPanel() {
         initComponents();
        inicializarComboBoxes(); // Inicializar ComboBoxes con valores predefinidos
        inicializarTabla(); // Configurar el modelo de la tabla
        cargarProductosEnComboBox(); // Cargar productos en el ComboBox
        cargarProductosEnTabla(); // Cargar productos en la tabla
    }
 // Método para cargar los nombres de los productos en el ComboBox
    private void inicializarComboBoxes() {
        // Limpiar los ComboBoxes antes de agregar los valores predefinidos
        categoriaCB1.removeAllItems();
        unidadMedidaCB.removeAllItems();

        // Categorías predefinidas
        categoriaCB1.addItem("Alimentos");
        categoriaCB1.addItem("Bebidas");
        categoriaCB1.addItem("Electrónicos");
        categoriaCB1.addItem("Ropa");
        categoriaCB1.addItem("Otros");

        // Unidades de medida predefinidas
        unidadMedidaCB.addItem("Kg");
        unidadMedidaCB.addItem("Litros");
        unidadMedidaCB.addItem("Unidades");
        unidadMedidaCB.addItem("Cajas");
        unidadMedidaCB.addItem("Paquetes");
    }

    // Método para inicializar el modelo de la tabla
    private void inicializarTabla() {
    // Configurar el modelo de la tabla
    DefaultTableModel modeloTabla = new DefaultTableModel(
        new Object[][]{}, // Datos iniciales (vacío)
        new String[]{"Nombre", "Descripción", "Categoría", "Medida", "Stock"} // Nombres de columnas
    );
    jTable1.setModel(modeloTabla); // Asignar el modelo a la tabla
}

    // Método para cargar productos en la tabla
   private void cargarProductosEnTabla() {
    // Datos de conexión a la base de datos
    String url = "jdbc:postgresql://localhost:5432/Bodegas";
    String user = "postgres";
    String password = "20213123Juan";

    String sql = "SELECT nombre, descripcion, categoria, medida, stock FROM productos";

    try (Connection conn = DriverManager.getConnection(url, user, password);
         PreparedStatement ps = conn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        // Obtener el modelo de la tabla
        DefaultTableModel modeloTabla = (DefaultTableModel) jTable1.getModel();

        // Limpiar la tabla antes de cargar nuevos datos
        modeloTabla.setRowCount(0);

        // Agregar filas a la tabla
        while (rs.next()) {
            modeloTabla.addRow(new Object[]{
                rs.getString("nombre"),
                rs.getString("descripcion"),
                rs.getString("categoria"),
                rs.getString("medida"),
                rs.getInt("stock")
            });
        }

    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error al cargar los productos.", "Error", JOptionPane.ERROR_MESSAGE);
    }
}

    // Método para cargar los nombres de los productos en el ComboBox
    private void cargarProductosEnComboBox() {
        // Datos de conexión a la base de datos
        String url = "jdbc:postgresql://localhost:5432/Bodegas";
        String user = "postgres";
        String password = "20213123Juan";

        String sql = "SELECT nombre FROM productos";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            // Limpiar el ComboBox antes de cargar datos
            nombreProductoCB.removeAllItems();

            // Agregar nombres de productos al ComboBox
            while (rs.next()) {
                nombreProductoCB.addItem(rs.getString("nombre"));
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar los productos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método para cargar los datos del producto seleccionado
    private void cargarDatosProducto() {
        String productoSeleccionado = (String) nombreProductoCB.getSelectedItem();

        if (productoSeleccionado == null || productoSeleccionado.isEmpty()) {
            return;
        }

        // Datos de conexión a la base de datos
        String url = "jdbc:postgresql://localhost:5432/Bodegas";
        String user = "postgres";
        String password = "20213123Juan";

        String sql = "SELECT * FROM productos WHERE nombre = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, productoSeleccionado);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                // Llenar los campos del formulario con los datos del producto
                nombreProducto.setText(rs.getString("nombre"));
                descripcionProducto.setText(rs.getString("descripcion"));
                categoriaCB1.setSelectedItem(rs.getString("categoria"));
                unidadMedidaCB.setSelectedItem(rs.getString("medida"));
                stockInicial.setText(String.valueOf(rs.getInt("stock")));
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar los datos del producto.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método para actualizar el producto en la base de datos
    private void actualizarProducto() {
        // Obtener datos del formulario
        String productoOriginal = (String) nombreProductoCB.getSelectedItem();
        String nombre = nombreProducto.getText();
        String descripcion = descripcionProducto.getText();
        String categoria = categoriaCB1.getSelectedItem().toString();
        String medida = unidadMedidaCB.getSelectedItem().toString();
        String stockStr = stockInicial.getText();
        int stock;

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

        if (nombre.isEmpty() || categoria.isEmpty() || medida.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Datos de conexión a la base de datos
        String url = "jdbc:postgresql://localhost:5432/Bodegas";
        String user = "postgres";
        String password = "20213123Juan";

        String sql = "UPDATE productos SET nombre = ?, descripcion = ?, categoria = ?, medida = ?, stock = ? WHERE nombre = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nombre);
            ps.setString(2, descripcion);
            ps.setString(3, categoria);
            ps.setString(4, medida);
            ps.setInt(5, stock);
            ps.setString(6, productoOriginal);

            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Producto actualizado exitosamente.");

            // Recargar el ComboBox y la tabla
            cargarProductosEnComboBox();
            cargarProductosEnTabla();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al actualizar el producto.", "Error", JOptionPane.ERROR_MESSAGE);
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

        lateralAz = new javax.swing.JPanel();
        icono = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        nombreProducto = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        descripcionProducto = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        categoriaCB1 = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        guardarAct = new javax.swing.JButton();
        stockInicial = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        unidadMedidaCB = new javax.swing.JComboBox<>();
        lblEditarProductos = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        nombreProductoCB = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setBackground(new java.awt.Color(236, 236, 236));
        setMinimumSize(new java.awt.Dimension(760, 490));
        setPreferredSize(new java.awt.Dimension(740, 480));

        lateralAz.setBackground(new java.awt.Color(64, 119, 119));
        lateralAz.setPreferredSize(new java.awt.Dimension(356, 400));

        icono.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/bodegaManu.png"))); // NOI18N

        jLabel4.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Nombre");

        nombreProducto.setFont(new java.awt.Font("Ebrima", 0, 12)); // NOI18N
        nombreProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nombreProductoActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Descripción");

        descripcionProducto.setFont(new java.awt.Font("Ebrima", 0, 12)); // NOI18N

        jLabel6.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Categoría");

        categoriaCB1.setFont(new java.awt.Font("Ebrima", 0, 12)); // NOI18N
        categoriaCB1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel8.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Stock Inicial");

        guardarAct.setFont(new java.awt.Font("Ebrima", 0, 12)); // NOI18N
        guardarAct.setText("GUARDAR");
        guardarAct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                guardarActActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Medida");

        unidadMedidaCB.setFont(new java.awt.Font("Ebrima", 0, 12)); // NOI18N
        unidadMedidaCB.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout lateralAzLayout = new javax.swing.GroupLayout(lateralAz);
        lateralAz.setLayout(lateralAzLayout);
        lateralAzLayout.setHorizontalGroup(
            lateralAzLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(lateralAzLayout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(lateralAzLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(lateralAzLayout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(unidadMedidaCB, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(lateralAzLayout.createSequentialGroup()
                        .addGroup(lateralAzLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel4)
                            .addComponent(jLabel8))
                        .addGap(30, 30, 30)
                        .addGroup(lateralAzLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(lateralAzLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(descripcionProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(nombreProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(categoriaCB1, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(stockInicial, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(85, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, lateralAzLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(lateralAzLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, lateralAzLayout.createSequentialGroup()
                        .addComponent(icono)
                        .addGap(123, 123, 123))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, lateralAzLayout.createSequentialGroup()
                        .addComponent(guardarAct)
                        .addGap(99, 99, 99))))
        );
        lateralAzLayout.setVerticalGroup(
            lateralAzLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, lateralAzLayout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addComponent(icono)
                .addGap(47, 47, 47)
                .addGroup(lateralAzLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nombreProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(37, 37, 37)
                .addGroup(lateralAzLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(descripcionProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addGroup(lateralAzLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(categoriaCB1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(lateralAzLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(unidadMedidaCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addGap(21, 21, 21)
                .addGroup(lateralAzLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(stockInicial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33)
                .addComponent(guardarAct)
                .addGap(18, 18, 18))
        );

        lblEditarProductos.setFont(new java.awt.Font("Ebrima", 0, 24)); // NOI18N
        lblEditarProductos.setText("MODIFICAR PRODUCTOS");

        jLabel1.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N
        jLabel1.setText("Nombre");

        nombreProductoCB.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        nombreProductoCB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nombreProductoCBActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N
        jLabel2.setText("Selección Producto");

        jSeparator1.setForeground(new java.awt.Color(0, 102, 102));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null},
                {null},
                {null},
                {null}
            },
            new String [] {
                "Productos Actualizados"
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addComponent(lblEditarProductos))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(nombreProductoCB, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(34, 34, 34))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                .addGap(18, 18, 18)
                .addComponent(lateralAz, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(layout.createSequentialGroup()
                .addGap(55, 55, 55)
                .addComponent(lblEditarProductos)
                .addGap(48, 48, 48)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(nombreProductoCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(77, 77, 77)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(8, Short.MAX_VALUE))
            .addComponent(lateralAz, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 490, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void nombreProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nombreProductoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nombreProductoActionPerformed

    private void nombreProductoCBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nombreProductoCBActionPerformed
        cargarDatosProducto();
    }//GEN-LAST:event_nombreProductoCBActionPerformed

    private void guardarActActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_guardarActActionPerformed
        actualizarProducto();
    }//GEN-LAST:event_guardarActActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> categoriaCB1;
    private javax.swing.JTextField descripcionProducto;
    private javax.swing.JButton guardarAct;
    private javax.swing.JLabel icono;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable jTable1;
    private javax.swing.JPanel lateralAz;
    private javax.swing.JLabel lblEditarProductos;
    private javax.swing.JTextField nombreProducto;
    private javax.swing.JComboBox<String> nombreProductoCB;
    private javax.swing.JTextField stockInicial;
    private javax.swing.JComboBox<String> unidadMedidaCB;
    // End of variables declaration//GEN-END:variables
}
