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
import javax.swing.table.DefaultTableModel;
public class BuscarPanel extends javax.swing.JPanel {

    /**
     * Creates new form BuscarPanel
     */
    public BuscarPanel() {
        initComponents();
        inicializarTabla(); // Configurar el modelo de la tabla
        cargarDatosEnComboBox();
    }

     private void inicializarTabla() {
        DefaultTableModel modeloTabla = new DefaultTableModel(
            new Object[][]{}, // Datos iniciales vacíos
            new String[]{"Nombre", "Categoría", "Medida", "Stock"} // Nombres de las columnas
        );
        jTable1.setModel(modeloTabla); // Asignar el modelo a la tabla
    }

    // Método para cargar datos iniciales en los ComboBox
    private void cargarDatosEnComboBox() {
        String url = "jdbc:postgresql://localhost:5432/Bodegas";
        String user = "postgres";
        String password = "20213123Juan";

        String sql = "SELECT DISTINCT categoria, nombre, medida FROM productos";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            // Limpiar los ComboBox antes de cargar datos
            categoriaCB.removeAllItems();
            nombreProductoCB.removeAllItems();
            jComboBox1.removeAllItems();

            // Llenar los ComboBox con valores únicos
            while (rs.next()) {
                String categoria = rs.getString("categoria");
                String nombre = rs.getString("nombre");
                String medida = rs.getString("medida");

                if (categoria != null && !categoria.isEmpty()) {
                    categoriaCB.addItem(categoria);
                }
                if (nombre != null && !nombre.isEmpty()) {
                    nombreProductoCB.addItem(nombre);
                }
                if (medida != null && !medida.isEmpty()) {
                    jComboBox1.addItem(medida);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar los datos en los filtros.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método para buscar productos según los filtros
    private void buscarProductos() {
        String categoria = (String) categoriaCB.getSelectedItem();
        String nombre = (String) nombreProductoCB.getSelectedItem();
        String medida = (String) jComboBox1.getSelectedItem();

        // Construir consulta SQL dinámica
        String sql = "SELECT nombre, categoria, medida, stock FROM productos WHERE 1=1";
        if (categoria != null && !categoria.isEmpty()) {
            sql += " AND categoria = ?";
        }
        if (nombre != null && !nombre.isEmpty()) {
            sql += " AND nombre = ?";
        }
        if (medida != null && !medida.isEmpty()) {
            sql += " AND medida = ?";
        }

        String url = "jdbc:postgresql://localhost:5432/Bodegas";
        String user = "postgres";
        String password = "20213123Juan";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            int paramIndex = 1;
            if (categoria != null && !categoria.isEmpty()) {
                ps.setString(paramIndex++, categoria);
            }
            if (nombre != null && !nombre.isEmpty()) {
                ps.setString(paramIndex++, nombre);
            }
            if (medida != null && !medida.isEmpty()) {
                ps.setString(paramIndex++, medida);
            }

            ResultSet rs = ps.executeQuery();

            // Obtener el modelo de la tabla
            DefaultTableModel modeloTabla = (DefaultTableModel) jTable1.getModel();
            modeloTabla.setRowCount(0); // Limpiar la tabla antes de agregar nuevos datos

            // Llenar la tabla con los resultados
            while (rs.next()) {
                modeloTabla.addRow(new Object[]{
                    rs.getString("nombre"),
                    rs.getString("categoria"),
                    rs.getString("medida"),
                    rs.getInt("stock")
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al buscar productos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lateralAz = new javax.swing.JPanel();
        icono = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        lblConsultarProductos = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        categoriaCB = new javax.swing.JComboBox<>();
        nombreProductoCB = new javax.swing.JComboBox<>();
        buscar = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();

        setBackground(new java.awt.Color(236, 236, 236));
        setMinimumSize(new java.awt.Dimension(760, 490));
        setPreferredSize(new java.awt.Dimension(740, 480));

        lateralAz.setBackground(new java.awt.Color(64, 119, 119));
        lateralAz.setPreferredSize(new java.awt.Dimension(356, 400));

        icono.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/bodegaManu.png"))); // NOI18N

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Productos Buscados", "Categoría", "Medida", "Stock"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setResizable(false);
        }

        jLabel5.setFont(new java.awt.Font("Ebrima", 0, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("BODEGA MANUELITA");

        javax.swing.GroupLayout lateralAzLayout = new javax.swing.GroupLayout(lateralAz);
        lateralAz.setLayout(lateralAzLayout);
        lateralAzLayout.setHorizontalGroup(
            lateralAzLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(lateralAzLayout.createSequentialGroup()
                .addGap(113, 113, 113)
                .addComponent(jLabel5)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, lateralAzLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(lateralAzLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, lateralAzLayout.createSequentialGroup()
                        .addComponent(icono)
                        .addGap(146, 146, 146))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, lateralAzLayout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 381, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(14, 14, 14))))
        );
        lateralAzLayout.setVerticalGroup(
            lateralAzLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(lateralAzLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(icono)
                .addGap(30, 30, 30)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(46, Short.MAX_VALUE))
        );

        lblConsultarProductos.setFont(new java.awt.Font("Ebrima", 0, 24)); // NOI18N
        lblConsultarProductos.setText("BUSCAR PRODUCTOS");

        jSeparator1.setForeground(new java.awt.Color(0, 102, 102));

        jLabel2.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N
        jLabel2.setText("Selección Producto");

        jLabel3.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N
        jLabel3.setText("Categoría");

        jLabel1.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N
        jLabel1.setText("Nombre");

        categoriaCB.setFont(new java.awt.Font("Ebrima", 0, 12)); // NOI18N
        categoriaCB.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        nombreProductoCB.setFont(new java.awt.Font("Ebrima", 0, 12)); // NOI18N
        nombreProductoCB.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        buscar.setBackground(new java.awt.Color(64, 119, 119));
        buscar.setFont(new java.awt.Font("Ebrima", 0, 12)); // NOI18N
        buscar.setForeground(new java.awt.Color(255, 255, 255));
        buscar.setText("BUSCAR");
        buscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N
        jLabel4.setText("Medida");

        jComboBox1.setFont(new java.awt.Font("Ebrima", 0, 12)); // NOI18N
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(lblConsultarProductos))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel2)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel3)
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel4)))
                        .addGap(52, 52, 52)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(nombreProductoCB, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(categoriaCB, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(131, 131, 131)
                        .addComponent(buscar)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lateralAz, javax.swing.GroupLayout.DEFAULT_SIZE, 401, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(lateralAz, javax.swing.GroupLayout.PREFERRED_SIZE, 490, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addComponent(lblConsultarProductos)
                .addGap(30, 30, 30)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addGap(34, 34, 34)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(categoriaCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(51, 51, 51)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(nombreProductoCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(42, 42, 42)
                .addComponent(buscar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void buscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarActionPerformed
         buscarProductos();
    }//GEN-LAST:event_buscarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buscar;
    private javax.swing.JComboBox<String> categoriaCB;
    private javax.swing.JLabel icono;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable jTable1;
    private javax.swing.JPanel lateralAz;
    private javax.swing.JLabel lblConsultarProductos;
    private javax.swing.JComboBox<String> nombreProductoCB;
    // End of variables declaration//GEN-END:variables
}
