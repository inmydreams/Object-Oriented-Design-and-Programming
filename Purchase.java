import java.awt.HeadlessException;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

public class Purchase extends javax.swing.JFrame{
    
    public Purchase() {
        fill();
        model();
        getCurrentTimeStamp(); 
        DatePicker();
    }
    
    Locale localeFromBuilder = new Locale.Builder().setLanguage("lt").setRegion("LT").build();
    
    private void DatePicker(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        jXDatePicker1.setFormats(dateFormat); 
        jXDatePicker1.setDate(getCurrentTimeStamp());
    }
    
    DefaultTableModel model = new DefaultTableModel(){
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    
    private static java.sql.Timestamp getCurrentTimeStamp() {
        java.util.Date today = new java.util.Date(); 
        return new java.sql.Timestamp(today.getTime());
    }
    
    private void model(){
        model.addColumn("id"); 
        model.addColumn("miestas"); 
        model.addColumn("adresas"); 
        model.addColumn("pavadinimas"); 
        model.addColumn("kaina"); 
        model.addColumn("kiekis"); 
        model.addColumn("data");
    }
    
    private void fill(){
        try{
            Connection con = NewCon.connect();
            Statement st= con.createStatement();
            String sql ="select distinct miestas from filmai"; 
            ResultSet rs =st.executeQuery(sql);
            while(rs.next()){
                miestas_combo.addItem(rs.getString("miestas"));
            }
        }
        catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex); 
            return;
        }
        
        try{
            String miestas=(String)miestas_combo.getSelectedItem();
            Connection con = NewCon.connect();
            Statement st= con.createStatement();
            String sql ="select distinct adresas from filmai where miestas='"+miestas+"'"; 
            ResultSet rs =st.executeQuery(sql);
            while(rs.next()){
                String adresas=rs.getString("adresas"); 
                adresas_combo.addItem(adresas);
            }
        }
        catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex); 
            return;
        }
        
        miestas_combo.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent ie){
                String miestas=(String)miestas_combo.getSelectedItem(); 
                DefaultComboBoxModel model = new DefaultComboBoxModel();
                try{
                    String sql="select distinct adresas from filmai where miestas='"+miestas+"'"; 
                    Connection con = NewCon.connect();
                    Statement st= con.createStatement();
                    ResultSet rs = st.executeQuery(sql);
                    while(rs.next()){
                        model.addElement(rs.getString("adresas"));
                    }
                    adresas_combo.setModel(model);
                }
                catch(Exception e){
                    JOptionPane.showMessageDialog(null, e);
                }
            }
        });
    }
    
    private void combobox(){
        try{
            String adresas=(String)adresas_combo.getSelectedItem();
            String miestas=(String)miestas_combo.getSelectedItem(); 
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
            jXDatePicker1.setFormats(dateFormat);
            String data = dateFormat.format(jXDatePicker1.getDate()).toString();
            try{
                Connection con = NewCon.connect();
                Statement st= con.createStatement();
                String sql ="select * from filmai where miestas='"+miestas
                        +"' and adresas='"+adresas+"' and '"
                        +data+"' between data_nuo and data_iki"; 
                ResultSet rs =st.executeQuery(sql);
                ResultSetMetaData rsmetadata=rs.getMetaData(); 
                int columns = rsmetadata.getColumnCount();
                
                DefaultTableModel dtm = new DefaultTableModel(){
                    public boolean isCellEditable(int row, int column){
                        return false; 
                    }
                };
                Vector columns_name = new Vector(); 
                Vector data_rows = new Vector();
                
                for (int i=1; i<=columns;i++){ 
                    columns_name.addElement(rsmetadata.getColumnName(i));
                } 
                dtm.setColumnIdentifiers(columns_name);
                
                while(rs.next()){
                    data_rows = new Vector();
                    for (int j=1; j<=columns; j++){
                        data_rows.addElement(rs.getString(j)); 
                    }
                    dtm.addRow(data_rows);
                }
                jTable1.setModel(dtm); 
                jPanel2.setVisible(true);
            }
            catch(Exception ex){
                JOptionPane.showMessageDialog(null, ex);
            }
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    private void atsijungtiActionPerformed(java.awt.event.ActionEvent evt) { 
        new Login().setVisible(true);
        dispose();
    }
    
    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {
        id.setText(null);
        jTextField1_kiekis.setText(null);
        jTextField1_kiekis.getDocument().addDocumentListener(new DocumentListener(){
            public void changedUpdate(DocumentEvent de){ 
                warn();
            }
            public void removeUpdate(DocumentEvent de){
                kaina1.setText(null);
                id.setText(null); 
            }
            public void insertUpdate(DocumentEvent de){
                warn();
            }
            
            public void warn(){
                try{
                    int i=jTable1.getSelectedRow();
                    String s1=jTable1.getValueAt(i, 0).toString();
                    String s2= jTable1.getValueAt (i, 4).toString();
                    double kaina=Double.parseDouble(s2);
                    int quantity = Integer.parseInt(jTextField1_kiekis.getText()); 
                    double s3=kaina*quantity;
                    String s4 = Double.toString(s3);
                    id.setText(s1);
                    kaina1.setText(s4);
                }
                catch(Exception e){
                    JOptionPane.showMessageDialog(null, "Įveskite kiekį");
                }
            }
        });
    }
    
    private void keisti_pActionPerformed(java.awt.event.ActionEvent evt){
        new Edit_profile().setVisible(true);
    }

    private void keisti_mActionPerformed(java.awt.event.AWTEventListener evt){
        new Edit_payment().setVisible(true);
    }
    
    private void pridetiActionPerformed(java.awt.event.ActionEvent evt) {
        try{
            String labelid=id.getText();
            if("".equals(labelid)){
                JOptionPane.showMessageDialog(null, "Pasirinkite");
                return;
            }
            
            String kaina=kaina1.getText();
            if("0".equals(kaina)){
                JOptionPane.showMessageDialog(null, "Įveskite kiekį");
                return; 
            }
            
            if("".equals(jTextField1_kiekis.getText())){ 
                JOptionPane.showMessageDialog(null, "Įveskite kiekį");
                return;
            }
            jPanel3.setVisible(true);
            int i=jTable1.getSelectedRow();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
            jXDatePicker1.setFormats(dateFormat);
            String date = dateFormat.format(jXDatePicker1.getDate()).toString(); 
            String c1=jTable1.getValueAt(i, 0).toString();
            String c2=jTable1.getValueAt(i, 1).toString();
            String c3=jTable1.getValueAt(i, 2).toString();
            String c4=jTable1.getValueAt(i, 3).toString();
            String c5=kaina1.getText().toString();
            String c6=jTextField1_kiekis.getText().toString();
            String c7=date;
            model.addRow(new Object[]{c1, c2, c3, c4, c5, c6, c7}); 
            jTable2.setModel(model);
            
            ArrayList<String> list = new ArrayList();
            int row = jTable2.getRowCount();
            for (int m=0; m<row; m++){
                String ids = jTable2.getValueAt(m, 0).toString(); 
                String data = jTable2.getValueAt(m, 6).toString(); 
                String check = ids+data;
                if (list.contains(check)) {
                    JOptionPane.showMessageDialog(null, "Įrašas jau yra");
                    model.removeRow(jTable2.getRowCount()-1); 
                }
                else{
                    list.add(check);
                }
            }
            
            double total=0;
            int rows = jTable2.getRowCount();
            for (int j = 0; j < rows; j++) {
                total += ( Double.parseDouble(jTable2.getValueAt(j, 4).toString())); 
            }
            String c=java.text.NumberFormat.getCurrencyInstance(localeFromBuilder).format(total);
            jLabel3_suma.setText(c);
        }
        catch(HeadlessException e){
            JOptionPane.showMessageDialog(null, "Pasirinkite");
        }
        catch (NumberFormatException e) { 
            JOptionPane.showMessageDialog(null, "Pasirinkite");
        }
    }
    
    private void istrintiActionPerformed(java.awt.event.ActionEvent evt) {
        try{
            model.removeRow(jTable2.getSelectedRow());
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null,"Pasirinkite, ką norite ištrinti");
            return; 
        }
        int total=0;
        int rows = jTable2.getRowCount();
        for (int j = 0; j < rows; j++) {
            total += ( Double.parseDouble(jTable2.getValueAt(j, 4).toString())) ;
        }
        String total1=String.valueOf(total);
        jLabel3_suma.setText(total1);
    }
    
    private void pirkiActionPerformed(java.awt.event.ActionEvent evt) {
        try{
            String number="";
            String tipas="";
            
            try{
                Connection con = NewCon.connect();
                Statement st= con.createStatement();
                String sql ="select * from klientai where email='"+
                        User_name.getText()+"'"; 
                ResultSet rs =st.executeQuery(sql);
                while(rs.next()){
                    number=rs.getString("numeris");
                    rs.getString("tipas");
                    if(number.equals("")){
                        JOptionPane.showMessageDialog(null, "Neįmanoma apmokėti");
                        return; 
                    }
                }
            }
            catch(SQLException ex){
                JOptionPane.showMessageDialog(null, ex); 
                return;
            }
            catch(HeadlessException ex){
                JOptionPane.showMessageDialog(null, ex); 
                return;
            }
            String status="nupirkta";
            int rows = jTable2.getRowCount();
            for(int row = 0; row<rows ; row++){
                String ids = jTable2.getValueAt(row, 0).toString(); 
                String kaina= jTable2.getValueAt(row, 4).toString(); 
                String kiekis=jTable2.getValueAt(row, 5).toString(); 
                String data=jTable2.getValueAt(row,6).toString();
                try{
                    Connection con = NewCon.connect();
                    PreparedStatement ps=con.prepareStatement("insert into uzsakymai (email,id,busena,kaina,kiekis,numeris,tipas,data,data_save) values (?,?,?,?,?,?,?,?,?)"); 
                    ps.setString(1, User_name.getText());
                    ps.setString(2, ids);
                    ps.setString(3, status);
                    ps.setString(4, kaina);
                    ps.setString(5, kiekis);
                    ps.setString(6, number);
                    ps.setString(7, tipas);
                    ps.setString(8, data);
                    ps.setTimestamp(9, getCurrentTimeStamp());
                    ps.addBatch();
                    ps.executeBatch();
                }
                catch(Exception e){
                    JOptionPane.showMessageDialog(null, e);
                    return;
                }
            }
            JOptionPane.showMessageDialog(null, "Apmokėta");
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Pasirinkite");
        }
    }
    
    private void jXDatePicker1PopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
        combobox();
    }
    
    private void adresas_comboPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
        combobox();
    }
    
    private void miestas_comboPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
        combobox();
    }
    
    public static void main(String args[]) { 
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Purchase().setVisible(true);
            } 
        });
    }
    
    private javax.swing.ButtonGroup button_group;
    public static javax.swing.JLabel User_name;
    private javax.swing.JComboBox adresas_combo;
    private javax.swing.JButton atsijungti;
    private javax.swing.JLabel id;
    private javax.swing.JLabel id1;
    private javax.swing.JButton istrinti;
    private javax.swing.JLabel jLabel13_miestas;
    private javax.swing.JLabel jLabel1_kiekis;
    private javax.swing.JLabel jLabel2_data;
    private javax.swing.JLabel jLabel3_suma;
    private javax.swing.JLabel jLabel4_suma;
    private javax.swing.JLabel jLabel9_adresas;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField jTextField1_kiekis; 
    private org.jdesktop.swingx.JXDatePicker jXDatePicker1; 
    private javax.swing.JLabel kaina1;
    private javax.swing.JButton keisti_m;
    private javax.swing.JButton keisti_p;
    private javax.swing.JLabel label3_kaina;
    private javax.swing.JComboBox miestas_combo;
    private javax.swing.JButton pirki;
    private javax.swing.JButton prideti;
    private javax.swing.JLabel prisijunges;
}

    