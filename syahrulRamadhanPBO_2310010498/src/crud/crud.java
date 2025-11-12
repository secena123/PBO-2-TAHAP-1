/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package crud;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import java.sql.ResultSetMetaData;
/**
 *
 * @author User
 */
    public class crud {
    private Connection Koneksidb;
    private String username="root";
    private String password="";
    private String dbname="db_perkantoran"; 
    private String urlKoneksi="jdbc:mysql://localhost/"+dbname;
    public boolean duplikasi=false;

    public String CEK_KODE_SATUAN, CEK_NAMA_SATUAN = null;
    public String CEK_KODE_BRG, CEK_NAMA_BRG, CEK_KETERANGAN_BRG, CEK_STOK_AWAL, CEK_STOK, CEK_SATUAN_ID, CEK_REKOMENDASI_BRG, CEK_STATUS_BRG, CEK_ADMIN_VIEW, CEK_STAF_VIEW, CEK_TANGGAL_UPDATE = null;
    public String CEK_USER_ID_RW, CEK_BARANG_ID_RW, CEK_JENIS_RW, CEK_QTY_RW, CEK_KETERANGAN_RW, CEK_TANGGAL_RW = null;
    public String CEK_MARKETING_ID, CEK_STAF_ID, CEK_BARANG_ID_PSN, CEK_PERMINTAAN, CEK_PENGIRIMAN, CEK_TANGGAL_MINTA, CEK_TANGGAL_KIRIM, CEK_STATUS_PSN, CEK_REKOMENDASI_PSN, CEK_STATUS_REKOMENDASI = null;

    
    public crud(){
        try {
            Driver dbdriver = new com.mysql.jdbc.Driver();
            DriverManager.registerDriver(dbdriver);
            Koneksidb=DriverManager.getConnection(urlKoneksi,username,password);
            System.out.print("Database Terkoneksi");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,e.toString());
        }
    }

    public void simpanSatuan01(String id, String kode, String nama){
        try {
            String sqlsimpan="insert into Satuan(id, kode, nama) value"
                    + " ('"+id+"', '"+kode+"', '"+nama+"')";
            String sqlcari="select*from Satuan where id='"+id+"'";
            
            Statement cari=Koneksidb.createStatement();
            ResultSet data=cari.executeQuery(sqlcari);
            
            if (data.next()){
                JOptionPane.showMessageDialog(null, "ID Satuan sudah terdaftar");
            } else {
                Statement perintah=Koneksidb.createStatement();
                perintah.execute(sqlsimpan);
                JOptionPane.showMessageDialog(null, "Data Satuan berhasil disimpan");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public void simpanSatuan02(String id, String kode, String nama){
        try {
            String sqlsimpan="INSERT INTO Satuan (id, kode, nama) VALUES (?, ?, ?)";
            String sqlcari= "SELECT*FROM Satuan WHERE id = ?";
            
            PreparedStatement cari = Koneksidb.prepareStatement(sqlcari);
            cari.setString(1, id);
            ResultSet data = cari.executeQuery();
            
            if (data.next()){
                JOptionPane.showMessageDialog(null, "ID Satuan sudah terdaftar");
                this.duplikasi = true;
                this.CEK_KODE_SATUAN = data.getString("kode");
                this.CEK_NAMA_SATUAN = data.getString("nama");
            } else {
                this.duplikasi = false;
                this.CEK_KODE_SATUAN = null;
                this.CEK_NAMA_SATUAN = null;
                
                PreparedStatement perintah = Koneksidb.prepareStatement(sqlsimpan);
                perintah.setString(1, id);
                perintah.setString(2, kode);
                perintah.setString(3, nama);
                perintah.executeUpdate();
                JOptionPane.showMessageDialog(null, "Data Satuan berhasil disimpan");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    public void ubahSatuan(String id, String kode, String nama){
        try {
            String sqlubah="UPDATE Satuan SET kode = ?, nama = ? WHERE id = ?";
            PreparedStatement perintah = Koneksidb.prepareStatement(sqlubah);
            perintah.setString(1, kode);
            perintah.setString(2, nama);
            perintah.setString(3, id); 
            
            perintah.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data Satuan berhasil diubah");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    public void hapusSatuan(String id){
        try {
            String sqlhapus="DELETE FROM Satuan WHERE id = ?";
            PreparedStatement perintah = Koneksidb.prepareStatement(sqlhapus);
            perintah.setString(1, id);
            perintah.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data Satuan berhasil dihapus");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    public void tampilDataSatuan(JTable komponentabel, String SQL){
        try {
            PreparedStatement perintah = Koneksidb.prepareStatement(SQL);
            ResultSet data = perintah.executeQuery();
            ResultSetMetaData meta = data.getMetaData();
            int jumlahkolom = meta.getColumnCount();
            DefaultTableModel modeltabel = new DefaultTableModel();
            
            modeltabel.addColumn("ID");
            modeltabel.addColumn("Kode");
            modeltabel.addColumn("Nama");
            
            while(data.next()){
                Object[] row = new Object[jumlahkolom];
                for(int i=1; i<=jumlahkolom; i++){
                    row[i-1]=data.getObject(i);
                }
                modeltabel.addRow(row);
            }
            komponentabel.setModel(modeltabel);
        } catch (Exception e) {
        }
    }

    public void simpanBarang01(String id, String kode, String nama, String keterangan, String stok_awal, String stok, String satuan_id, String rekomendasi, String status, String admin_view, String staf_view, String tanggal_update){
        try {
            String sqlsimpan="insert into barang(id, kode, nama, keterangan, stok_awal, stok, satuan_id, rekomendasi, status, admin_view, staf_view, tanggal_update) value"
                    + " ('"+id+"', '"+kode+"', '"+nama+"', '"+keterangan+"', '"+stok_awal+"', '"+stok+"', '"+satuan_id+"', '"+rekomendasi+"', '"+status+"', '"+admin_view+"', '"+staf_view+"', '"+tanggal_update+"')";
            String sqlcari="select*from barang where id='"+id+"'";
            
            Statement cari=Koneksidb.createStatement();
            ResultSet data=cari.executeQuery(sqlcari);
            
            if (data.next()){
                JOptionPane.showMessageDialog(null, "ID Barang sudah terdaftar");
            } else {
                Statement perintah=Koneksidb.createStatement();
                perintah.execute(sqlsimpan);
                JOptionPane.showMessageDialog(null, "Data Barang berhasil disimpan");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public void simpanBarang02(String id, String kode, String nama, String keterangan, String stok_awal, String stok, String satuan_id, String rekomendasi, String status, String admin_view, String staf_view, String tanggal_update){
        try {
            String sqlsimpan="INSERT INTO barang (id, kode, nama, keterangan, stok_awal, stok, satuan_id, rekomendasi, status, admin_view, staf_view, tanggal_update) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            String sqlcari= "SELECT*FROM barang WHERE id = ?";
            
            PreparedStatement cari = Koneksidb.prepareStatement(sqlcari);
            cari.setString(1, id);
            ResultSet data = cari.executeQuery();
            
            if (data.next()){
                JOptionPane.showMessageDialog(null, "ID Barang sudah terdaftar");
                this.duplikasi = true;
                this.CEK_KODE_BRG = data.getString("kode");
                this.CEK_NAMA_BRG = data.getString("nama");
                this.CEK_KETERANGAN_BRG = data.getString("keterangan");
                this.CEK_STOK_AWAL = data.getString("stok_awal");
                this.CEK_STOK = data.getString("stok");
                this.CEK_SATUAN_ID = data.getString("satuan_id");
                this.CEK_REKOMENDASI_BRG = data.getString("rekomendasi");
                this.CEK_STATUS_BRG = data.getString("status");
                this.CEK_ADMIN_VIEW = data.getString("admin_view");
                this.CEK_STAF_VIEW = data.getString("staf_view");
                this.CEK_TANGGAL_UPDATE = data.getString("tanggal_update");
            } else {
                this.duplikasi = false;
                this.CEK_KODE_BRG = null;
                this.CEK_NAMA_BRG = null;
                this.CEK_KETERANGAN_BRG = null;
                this.CEK_STOK_AWAL = null;
                this.CEK_STOK = null;
                this.CEK_SATUAN_ID = null;
                this.CEK_REKOMENDASI_BRG = null;
                this.CEK_STATUS_BRG = null;
                this.CEK_ADMIN_VIEW = null;
                this.CEK_STAF_VIEW = null;
                this.CEK_TANGGAL_UPDATE = null;
                
                PreparedStatement perintah = Koneksidb.prepareStatement(sqlsimpan);
                perintah.setString(1, id);
                perintah.setString(2, kode);
                perintah.setString(3, nama);
                perintah.setString(4, keterangan);
                perintah.setString(5, stok_awal);
                perintah.setString(6, stok);
                perintah.setString(7, satuan_id);
                perintah.setString(8, rekomendasi);
                perintah.setString(9, status);
                perintah.setString(10, admin_view);
                perintah.setString(11, staf_view);
                perintah.setString(12, tanggal_update);
                perintah.executeUpdate();
                JOptionPane.showMessageDialog(null, "Data Barang berhasil disimpan");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    public void ubahBarang(String id, String kode, String nama, String keterangan, String stok_awal, String stok, String satuan_id, String rekomendasi, String status, String admin_view, String staf_view, String tanggal_update){
        try {
            String sqlubah="UPDATE barang SET kode = ?, nama = ?, keterangan = ?, stok_awal = ?, stok = ?, satuan_id = ?, rekomendasi = ?, status = ?, admin_view = ?, staf_view = ?, tanggal_update = ? WHERE id = ?";
            PreparedStatement perintah = Koneksidb.prepareStatement(sqlubah);
            perintah.setString(1, kode);
            perintah.setString(2, nama);
            perintah.setString(3, keterangan);
            perintah.setString(4, stok_awal);
            perintah.setString(5, stok);
            perintah.setString(6, satuan_id);
            perintah.setString(7, rekomendasi);
            perintah.setString(8, status);
            perintah.setString(9, admin_view);
            perintah.setString(10, staf_view);
            perintah.setString(11, tanggal_update);
            perintah.setString(12, id); 
            perintah.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data Barang berhasil diubah");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    public void hapusBarang(String id){
        try {
            String sqlhapus="DELETE FROM barang WHERE id = ?";
            PreparedStatement perintah = Koneksidb.prepareStatement(sqlhapus);
            perintah.setString(1, id);
            perintah.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data Barang berhasil dihapus");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    public void tampilDataBarang(JTable komponentabel, String SQL){
        try {
            PreparedStatement perintah = Koneksidb.prepareStatement(SQL);
            ResultSet data = perintah.executeQuery();
            ResultSetMetaData meta = data.getMetaData();
            int jumlahkolom = meta.getColumnCount();
            DefaultTableModel modeltabel = new DefaultTableModel();
            
            modeltabel.addColumn("ID");
            modeltabel.addColumn("Kode");
            modeltabel.addColumn("Nama");
            modeltabel.addColumn("Keterangan");
            modeltabel.addColumn("Stok Awal");
            modeltabel.addColumn("Stok");
            modeltabel.addColumn("Satuan ID");
            modeltabel.addColumn("Rekomendasi");
            modeltabel.addColumn("Status");
            modeltabel.addColumn("Admin View");
            modeltabel.addColumn("Staf View");
            modeltabel.addColumn("Tanggal Update");
            
            while(data.next()){
                Object[] row = new Object[jumlahkolom];
                for(int i=1; i<=jumlahkolom; i++){
                    row[i-1]=data.getObject(i);
                }
                modeltabel.addRow(row);
            }
            komponentabel.setModel(modeltabel);
        } catch (Exception e) {
        }
    }

    public void simpanRiwayat01(String id, String user_id, String barang_id, String jenis, String qty, String keterangan, String tanggal){
        try {
            String sqlsimpan="insert into Riwayat(id, user_id, barang_id, jenis, qty, keterangan, tanggal) value"
                    + " ('"+id+"', '"+user_id+"', '"+barang_id+"', '"+jenis+"', '"+qty+"', '"+keterangan+"', '"+tanggal+"')";
            String sqlcari="select*from Riwayat where id='"+id+"'";
            
            Statement cari=Koneksidb.createStatement();
            ResultSet data=cari.executeQuery(sqlcari);
            
            if (data.next()){
                JOptionPane.showMessageDialog(null, "ID Riwayat sudah terdaftar");
            } else {
                Statement perintah=Koneksidb.createStatement();
                perintah.execute(sqlsimpan);
                JOptionPane.showMessageDialog(null, "Data Riwayat berhasil disimpan");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public void simpanRiwayat02(String id, String user_id, String barang_id, String jenis, String qty, String keterangan, String tanggal){
        try {
            String sqlsimpan="INSERT INTO Riwayat (id, user_id, barang_id, jenis, qty, keterangan, tanggal) VALUES (?, ?, ?, ?, ?, ?, ?)";
            String sqlcari= "SELECT*FROM Riwayat WHERE id = ?";
            
            PreparedStatement cari = Koneksidb.prepareStatement(sqlcari);
            cari.setString(1, id);
            ResultSet data = cari.executeQuery();
            
            if (data.next()){
                JOptionPane.showMessageDialog(null, "ID Riwayat sudah terdaftar");
                this.duplikasi = true;
                this.CEK_USER_ID_RW = data.getString("user_id");
                this.CEK_BARANG_ID_RW = data.getString("barang_id");
                this.CEK_JENIS_RW = data.getString("jenis");
                this.CEK_QTY_RW = data.getString("qty");
                this.CEK_KETERANGAN_RW = data.getString("keterangan");
                this.CEK_TANGGAL_RW = data.getString("tanggal");
            } else {
                this.duplikasi = false;
                this.CEK_USER_ID_RW = null;
                this.CEK_BARANG_ID_RW = null;
                this.CEK_JENIS_RW = null;
                this.CEK_QTY_RW = null;
                this.CEK_KETERANGAN_RW = null;
                this.CEK_TANGGAL_RW = null;
                
                PreparedStatement perintah = Koneksidb.prepareStatement(sqlsimpan);
                perintah.setString(1, id);
                perintah.setString(2, user_id);
                perintah.setString(3, barang_id);
                perintah.setString(4, jenis);
                perintah.setString(5, qty);
                perintah.setString(6, keterangan);
                perintah.setString(7, tanggal);
                perintah.executeUpdate();
                JOptionPane.showMessageDialog(null, "Data Riwayat berhasil disimpan");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    public void ubahRiwayat(String id, String user_id, String barang_id, String jenis, String qty, String keterangan, String tanggal){
        try {
            String sqlubah="UPDATE Riwayat SET user_id = ?, barang_id = ?, jenis = ?, qty = ?, keterangan = ?, tanggal = ? WHERE id = ?";
            PreparedStatement perintah = Koneksidb.prepareStatement(sqlubah);
            perintah.setString(1, user_id);
            perintah.setString(2, barang_id);
            perintah.setString(3, jenis);
            perintah.setString(4, qty);
            perintah.setString(5, keterangan);
            perintah.setString(6, tanggal);
            perintah.setString(7, id); 
            perintah.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data Riwayat berhasil diubah");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    public void hapusRiwayat(String id){
        try {
            String sqlhapus="DELETE FROM Riwayat WHERE id = ?";
            PreparedStatement perintah = Koneksidb.prepareStatement(sqlhapus);
            perintah.setString(1, id);
            perintah.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data Riwayat berhasil dihapus");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    public void tampilDataRiwayat(JTable komponentabel, String SQL){
        try {
            PreparedStatement perintah = Koneksidb.prepareStatement(SQL);
            ResultSet data = perintah.executeQuery();
            ResultSetMetaData meta = data.getMetaData();
            int jumlahkolom = meta.getColumnCount();
            DefaultTableModel modeltabel = new DefaultTableModel();
            
            modeltabel.addColumn("ID");
            modeltabel.addColumn("User ID");
            modeltabel.addColumn("Barang ID");
            modeltabel.addColumn("Jenis");
            modeltabel.addColumn("Qty");
            modeltabel.addColumn("Keterangan");
            modeltabel.addColumn("Tanggal");
            
            while(data.next()){
                Object[] row = new Object[jumlahkolom];
                for(int i=1; i<=jumlahkolom; i++){
                    row[i-1]=data.getObject(i);
                }
                modeltabel.addRow(row);
            }
            komponentabel.setModel(modeltabel);
        } catch (Exception e) {
        }
    }

    public void simpanPesanan01(String id, String marketing_id, String staf_id, String barang_id, String permintaan, String pengiriman, String tanggal_minta, String tanggal_kirim, String status, String rekomendasi, String status_rekomendasi){
        try {
            String sqlsimpan="insert into Pesanan(id, marketing_id, staf_id, barang_id, permintaan, pengiriman, tanggal_minta, tanggal_kirim, status, rekomendasi, status_rekomendasi) value"
                    + " ('"+id+"', '"+marketing_id+"', '"+staf_id+"', '"+barang_id+"', '"+permintaan+"', '"+pengiriman+"', '"+tanggal_minta+"', '"+tanggal_kirim+"', '"+status+"', '"+rekomendasi+"', '"+status_rekomendasi+"')";
            String sqlcari="select*from Pesanan where id='"+id+"'";
            
            Statement cari=Koneksidb.createStatement();
            ResultSet data=cari.executeQuery(sqlcari);
            
            if (data.next()){
                JOptionPane.showMessageDialog(null, "ID Pesanan sudah terdaftar");
            } else {
                Statement perintah=Koneksidb.createStatement();
                perintah.execute(sqlsimpan);
                JOptionPane.showMessageDialog(null, "Data Pesanan berhasil disimpan");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    public void simpanPesanan02(String id, String marketing_id, String staf_id, String barang_id, String permintaan, String pengiriman, String tanggal_minta, String tanggal_kirim, String status, String rekomendasi, String status_rekomendasi){
        try {
            String sqlsimpan="INSERT INTO Pesanan (id, marketing_id, staf_id, barang_id, permintaan, pengiriman, tanggal_minta, tanggal_kirim, status, rekomendasi, status_rekomendasi) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            String sqlcari= "SELECT*FROM Pesanan WHERE id = ?";
            
            PreparedStatement cari = Koneksidb.prepareStatement(sqlcari);
            cari.setString(1, id);
            ResultSet data = cari.executeQuery();
            
            if (data.next()){
                JOptionPane.showMessageDialog(null, "ID Pesanan sudah terdaftar");
                this.duplikasi = true;
                this.CEK_MARKETING_ID = data.getString("marketing_id");
                this.CEK_STAF_ID = data.getString("staf_id");
                this.CEK_BARANG_ID_PSN = data.getString("barang_id");
                this.CEK_PERMINTAAN = data.getString("permintaan");
                this.CEK_PENGIRIMAN = data.getString("pengiriman");
                this.CEK_TANGGAL_MINTA = data.getString("tanggal_minta");
                this.CEK_TANGGAL_KIRIM = data.getString("tanggal_kirim");
                this.CEK_STATUS_PSN = data.getString("status");
                this.CEK_REKOMENDASI_PSN = data.getString("rekomendasi");
                this.CEK_STATUS_REKOMENDASI = data.getString("status_rekomendasi");
            } else {
                this.duplikasi = false;
                this.CEK_MARKETING_ID = null;
                this.CEK_STAF_ID = null;
                this.CEK_BARANG_ID_PSN = null;
                this.CEK_PERMINTAAN = null;
                this.CEK_PENGIRIMAN = null;
                this.CEK_TANGGAL_MINTA = null;
                this.CEK_TANGGAL_KIRIM = null;
                this.CEK_STATUS_PSN = null;
                this.CEK_REKOMENDASI_PSN = null;
                this.CEK_STATUS_REKOMENDASI = null;
                
                PreparedStatement perintah = Koneksidb.prepareStatement(sqlsimpan);
                perintah.setString(1, id);
                perintah.setString(2, marketing_id);
                perintah.setString(3, staf_id);
                perintah.setString(4, barang_id);
                perintah.setString(5, permintaan);
                perintah.setString(6, pengiriman);
                perintah.setString(7, tanggal_minta);
                perintah.setString(8, tanggal_kirim);
                perintah.setString(9, status);
                perintah.setString(10, rekomendasi);
                perintah.setString(11, status_rekomendasi);
                perintah.executeUpdate();
                JOptionPane.showMessageDialog(null, "Data Pesanan berhasil disimpan");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    public void ubahPesanan(String id, String marketing_id, String staf_id, String barang_id, String permintaan, String pengiriman, String tanggal_minta, String tanggal_kirim, String status, String rekomendasi, String status_rekomendasi){
        try {
            String sqlubah="UPDATE Pesanan SET marketing_id = ?, staf_id = ?, barang_id = ?, permintaan = ?, pengiriman = ?, tanggal_minta = ?, tanggal_kirim = ?, status = ?, rekomendasi = ?, status_rekomendasi = ? WHERE id = ?";
            PreparedStatement perintah = Koneksidb.prepareStatement(sqlubah);
            perintah.setString(1, marketing_id);
            perintah.setString(2, staf_id);
            perintah.setString(3, barang_id);
            perintah.setString(4, permintaan);
            perintah.setString(5, pengiriman);
            perintah.setString(6, tanggal_minta);
            perintah.setString(7, tanggal_kirim);
            perintah.setString(8, status);
            perintah.setString(9, rekomendasi);
            perintah.setString(10, status_rekomendasi);
            perintah.setString(11, id); 
            perintah.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data Pesanan berhasil diubah");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    public void hapusPesanan(String id){
        try {
            String sqlhapus="DELETE FROM Pesanan WHERE id = ?";
            PreparedStatement perintah = Koneksidb.prepareStatement(sqlhapus);
            perintah.setString(1, id);
            perintah.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data Pesanan berhasil dihapus");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    public void tampilDataPesanan(JTable komponentabel, String SQL){
        try {
            PreparedStatement perintah = Koneksidb.prepareStatement(SQL);
            ResultSet data = perintah.executeQuery();
            ResultSetMetaData meta = data.getMetaData();
            int jumlahkolom = meta.getColumnCount();
            DefaultTableModel modeltabel = new DefaultTableModel();
            
            modeltabel.addColumn("ID");
            modeltabel.addColumn("Marketing ID");
            modeltabel.addColumn("Staf ID");
            modeltabel.addColumn("Barang ID");
            modeltabel.addColumn("Permintaan");
            modeltabel.addColumn("Pengiriman");
            modeltabel.addColumn("Tanggal Minta");
            modeltabel.addColumn("Tanggal Kirim");
            modeltabel.addColumn("Status");
            modeltabel.addColumn("Rekomendasi");
            modeltabel.addColumn("Status Rekomendasi");
            
            while(data.next()){
                Object[] row = new Object[jumlahkolom];
                for(int i=1; i<=jumlahkolom; i++){
                    row[i-1]=data.getObject(i);
                }
                modeltabel.addRow(row);
            }
            komponentabel.setModel(modeltabel);
        } catch (Exception e) {
        }
    }   
}
    
