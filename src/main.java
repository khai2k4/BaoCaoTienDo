import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.nio.Buffer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import javax.print.PrintException;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author khain
 */
public class main extends javax.swing.JFrame {

    List<NhanVien> qlnv = new ArrayList();
    DefaultTableModel modelTable;
    long startTime = System.currentTimeMillis();
    public main() {
        initComponents();
        setTitle("Quản Lí Nhân Viên");
        setLocationRelativeTo(null);
        loadNhanVien();
        filteToTable();
        addItemThamNien();
        tilteColumn();
        getLastNhanVien();
        displayClock();
    }

    //Tạo đồng hồ đếm thời gian sử dụng phần mềm:
    void displayClock() {
        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateClock();
            }

        });
        timer.start();
    }

    void updateClock() {
        
        long countTime = System.currentTimeMillis() - startTime;
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        timeFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        String time = timeFormat.format(new Date(countTime));
        lbl_DongHo.setText(time);
    }

    void loadNhanVien() {
        qlnv.add(new NhanVien("KHv@1", "Nguyễn Thúy Hằng", false, 2));
        qlnv.add(new NhanVien("KHv@2", "Trần Tuấn Phong", true, 14));
        qlnv.add(new NhanVien("KHv@3", "Vũ Văn Nguyên", true, 13));
        qlnv.add(new NhanVien("KHv@4", "Nguyễn Hoàng Tiến", true, 1));
        qlnv.add(new NhanVien("KHv@5", "Chu Thị Ngân", false, 5));
    }

    // add dữ liệu list vào table
    void filteToTable() {
        modelTable = (DefaultTableModel) tbl_NhanVien.getModel();
        modelTable.setRowCount(0);
        for (NhanVien nv : qlnv) {
            modelTable.addRow(new Object[]{nv.getMaNV(), nv.getTenNV(), nv.isGioiTinh() ? "Nam" : "Nữ", nv.getThamNien(), nv.getThuong()});
        }
    }

    // thêm  thâm niên 100 tháng
    void addItemThamNien() {
        cbo_SoThang.removeAllItems();
        for (int i = 1; i <= 100; i++) {
            cbo_SoThang.addItem(String.valueOf(i));
        }
    }

    // đặt tên cột cho bảng
    void tilteColumn() {
        TableColumnModel columnModel = tbl_NhanVien.getColumnModel();
        String[] tilteColumn = {"Mã Nhân Viên", "Họ Và Tên", "Giới Tính", "Thâm Niên", "Thưởng"};
        for (int i = 0; i < tilteColumn.length; i++) {
            TableColumn tableColumn = columnModel.getColumn(i);
            tableColumn.setHeaderValue(tilteColumn[i]);
        }
    }

    // check mã nhân viên trùng
    boolean checkMaNv(String ID) {
        for (NhanVien nv : qlnv) {
            if (txt_MaNv.getText().contains(nv.getMaNV())) {
                return false;
            }
        }
        return true;
    }

    // hiển thị nhân viên cuối cùng trong bảng
    void getLastNhanVien() {
        if (!qlnv.isEmpty()) {
            NhanVien nv = qlnv.get(tbl_NhanVien.getRowCount() - 1);
            txt_MaNv.setText(nv.getMaNV());
            txt_Ten_Nv.setText(nv.getTenNV());
            if (nv.isGioiTinh()) {
                rdo_Nam.setSelected(true);
            }
            if (!nv.isGioiTinh()) {
                rdo_Nu.setSelected(true);
            }
            cbo_SoThang.setSelectedItem(String.valueOf(nv.getThamNien()));

        }
    }

    // Hiển thị dữ liệu khi click
    void getdDataNhanVien() {
        int i = tbl_NhanVien.getSelectedRow();
        if (i > -1 && i < qlnv.size()) {
            NhanVien nv = qlnv.get(i);
            txt_MaNv.setText(nv.getMaNV());
            txt_Ten_Nv.setText(nv.getTenNV());
            if (nv.isGioiTinh()) {
                rdo_Nam.setSelected(true);
            }
            if (!nv.isGioiTinh()) {
                rdo_Nu.setSelected(true);
            }
            cbo_SoThang.setSelectedItem(String.valueOf(nv.getThamNien()));

        }

    }

    boolean gioiTinh;

    NhanVien addNhanVien() {
        return new NhanVien(txt_MaNv.getText(), txt_Ten_Nv.getText(), gioiTinh, Integer.parseInt(cbo_SoThang.getSelectedItem().toString()));
    }

    // xóa nhân viên
    void removeNhanVien() {
        int remove = tbl_NhanVien.getSelectedRow();
        if (remove < 0) {
            JOptionPane.showMessageDialog(null, "Chọn nhân viên Cần Xóa");
        } else {
            qlnv.remove(remove);
            filteToTable();
            JOptionPane.showMessageDialog(null, "Nhân viên " + qlnv.get(remove).getTenNV()
                    + " đã được xóa");
        }
    }

    // update nhân viên
    void updateDataNhanVien() {
        int update = tbl_NhanVien.getSelectedRow();
        if (update < 0) {
            JOptionPane.showMessageDialog(null, "Chọn nhân viên cần sửa");
        } else {
            NhanVien nv = qlnv.get(update);
            nv.setThamNien(Integer.parseInt(cbo_SoThang.getSelectedItem().toString()));
            filteToTable();
            JOptionPane.showMessageDialog(null, "Nhân viên " + qlnv.get(update).getTenNV() + " đã được sửa");
        }
    }

    //Ghi file
    void WriteFile() {

        try {
            FileWriter fw = new FileWriter("C:/Users/HUNGPYN/Desktop/FileWordSQL/NhanVien.txt");

            BufferedWriter bw = new BufferedWriter(fw);
            for (NhanVien nv : qlnv) {
                bw.write(nv.getMaNV() + "," + nv.getTenNV() + "," + (nv.isGioiTinh() ? "Nam" : "Nữ") + "," + nv.getThamNien() + "," + nv.getThuong());
                bw.newLine();

            }
            bw.close();
            fw.close();
            JOptionPane.showMessageDialog(null, "Ghi file thành công");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Ghi file thất bại");
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        rdo_Group_Sex = new javax.swing.ButtonGroup();
        lbl_QLNV = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        lbl_Ma_Nv = new javax.swing.JLabel();
        lbl_Ten_Nv = new javax.swing.JLabel();
        lbl_GioiTinh = new javax.swing.JLabel();
        txt_MaNv = new javax.swing.JTextField();
        txt_Ten_Nv = new javax.swing.JTextField();
        rdo_Nam = new javax.swing.JRadioButton();
        rdo_Nu = new javax.swing.JRadioButton();
        btn_Them = new javax.swing.JButton();
        btn_Sua = new javax.swing.JButton();
        btn_Xoa = new javax.swing.JButton();
        btn_GhiFile = new javax.swing.JButton();
        lbl_ThamNien = new javax.swing.JLabel();
        cbo_SoThang = new javax.swing.JComboBox<>();
        lbl_Thang = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_NhanVien = new javax.swing.JTable();
        lbl_DongHo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lbl_QLNV.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lbl_QLNV.setForeground(new java.awt.Color(0, 0, 204));
        lbl_QLNV.setText("QUẢN LÍ NHÂN VIÊN");

        lbl_Ma_Nv.setText("Mã Nhân Viên");

        lbl_Ten_Nv.setText("Tên Nhân Viên");

        lbl_GioiTinh.setText("Giới Tính");

        txt_MaNv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_MaNvActionPerformed(evt);
            }
        });

        rdo_Group_Sex.add(rdo_Nam);
        rdo_Nam.setText("Nam");
        rdo_Nam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdo_NamActionPerformed(evt);
            }
        });

        rdo_Group_Sex.add(rdo_Nu);
        rdo_Nu.setText("Nữ");
        rdo_Nu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdo_NuActionPerformed(evt);
            }
        });

        btn_Them.setText("Thêm");
        btn_Them.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ThemActionPerformed(evt);
            }
        });

        btn_Sua.setText("Sửa");
        btn_Sua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_SuaActionPerformed(evt);
            }
        });

        btn_Xoa.setText("Xóa");
        btn_Xoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_XoaActionPerformed(evt);
            }
        });

        btn_GhiFile.setText("Ghi File");
        btn_GhiFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_GhiFileActionPerformed(evt);
            }
        });

        lbl_ThamNien.setText("Thâm Niên");

        cbo_SoThang.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        lbl_Thang.setText("Tháng");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btn_Them)
                        .addGap(28, 28, 28)
                        .addComponent(btn_Sua)
                        .addGap(29, 29, 29)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btn_Xoa)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                                .addComponent(btn_GhiFile))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(rdo_Nu)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(lbl_Ma_Nv)
                                    .addGap(20, 20, 20))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                    .addComponent(lbl_Ten_Nv)
                                    .addGap(18, 18, 18)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lbl_GioiTinh)
                                    .addComponent(lbl_ThamNien))
                                .addGap(37, 37, 37)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_MaNv, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txt_Ten_Nv)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(rdo_Nam)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(cbo_SoThang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(lbl_Thang)))
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_Ma_Nv)
                    .addComponent(txt_MaNv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txt_Ten_Nv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_Ten_Nv))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_GioiTinh)
                    .addComponent(rdo_Nam)
                    .addComponent(rdo_Nu))
                .addGap(8, 8, 8)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_ThamNien)
                    .addComponent(cbo_SoThang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_Thang))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_Them)
                    .addComponent(btn_Sua)
                    .addComponent(btn_Xoa)
                    .addComponent(btn_GhiFile))
                .addContainerGap())
        );

        tbl_NhanVien.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5"
            }
        ));
        tbl_NhanVien.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                tbl_NhanVienAncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        tbl_NhanVien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_NhanVienMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbl_NhanVien);

        lbl_DongHo.setFont(new java.awt.Font("Sitka Text", 1, 14)); // NOI18N
        lbl_DongHo.setForeground(new java.awt.Color(255, 0, 51));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 470, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(19, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(101, 101, 101)
                .addComponent(lbl_QLNV)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbl_DongHo)
                .addGap(51, 51, 51))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_QLNV)
                    .addComponent(lbl_DongHo))
                .addGap(28, 28, 28)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txt_MaNvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_MaNvActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_MaNvActionPerformed

    private void rdo_NamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdo_NamActionPerformed
        // TODO add your handling code here:
        gioiTinh = rdo_Nam.isSelected();
    }//GEN-LAST:event_rdo_NamActionPerformed

    private void tbl_NhanVienAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_tbl_NhanVienAncestorAdded
        // TODO add your handling code here:

    }//GEN-LAST:event_tbl_NhanVienAncestorAdded

    private void btn_ThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ThemActionPerformed
        // TODO add your handling code here:
        if (!checkMaNv(txt_MaNv.getText())) {
            JOptionPane.showMessageDialog(null, "Mã Nhân Viên Đã Tồn Tại");
        } else if (txt_MaNv.getText().isEmpty() || txt_Ten_Nv.getText().isEmpty() || (!rdo_Nam.isSelected() && !rdo_Nu.isSelected())) {
            JOptionPane.showMessageDialog(null, "Nhập Thiếu Thông Tin");
        } else {
            qlnv.add(addNhanVien());
            filteToTable();
            JOptionPane.showMessageDialog(null, "Nhân Viên" + txt_Ten_Nv.getText() + " đã được thêm");
        }
    }//GEN-LAST:event_btn_ThemActionPerformed

    private void rdo_NuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdo_NuActionPerformed
        // TODO add your handling code here:
        gioiTinh = !rdo_Nu.isSelected();
    }//GEN-LAST:event_rdo_NuActionPerformed

    private void tbl_NhanVienMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_NhanVienMouseClicked
        // TODO add your handling code here:
        getdDataNhanVien();
    }//GEN-LAST:event_tbl_NhanVienMouseClicked

    private void btn_XoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_XoaActionPerformed
        // TODO add your handling code here:
        removeNhanVien();
    }//GEN-LAST:event_btn_XoaActionPerformed

    private void btn_SuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_SuaActionPerformed
        // TODO add your handling code here:
        updateDataNhanVien();
    }//GEN-LAST:event_btn_SuaActionPerformed

    private void btn_GhiFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_GhiFileActionPerformed
        // TODO add your handling code here:
        WriteFile();
    }//GEN-LAST:event_btn_GhiFileActionPerformed

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
            java.util.logging.Logger.getLogger(main.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(main.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(main.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(main.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new main().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_GhiFile;
    private javax.swing.JButton btn_Sua;
    private javax.swing.JButton btn_Them;
    private javax.swing.JButton btn_Xoa;
    private javax.swing.JComboBox<String> cbo_SoThang;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbl_DongHo;
    private javax.swing.JLabel lbl_GioiTinh;
    private javax.swing.JLabel lbl_Ma_Nv;
    private javax.swing.JLabel lbl_QLNV;
    private javax.swing.JLabel lbl_Ten_Nv;
    private javax.swing.JLabel lbl_ThamNien;
    private javax.swing.JLabel lbl_Thang;
    private javax.swing.ButtonGroup rdo_Group_Sex;
    private javax.swing.JRadioButton rdo_Nam;
    private javax.swing.JRadioButton rdo_Nu;
    private javax.swing.JTable tbl_NhanVien;
    private javax.swing.JTextField txt_MaNv;
    private javax.swing.JTextField txt_Ten_Nv;
    // End of variables declaration//GEN-END:variables
}
  


