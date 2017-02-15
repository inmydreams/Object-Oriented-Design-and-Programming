import java.awt.Color;
import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

public class Admin extends javax.swing.JFrame{
    public Admin(){
        combobox();
    }
    
    private void combobox(){
        DefaultComboBoxModel model = new DefaultComboBoxModel();
        try{
            Connection con = NewCon.connect();
            Statement st = con.createStatement();
            String sql = "select id from filmai";
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                model.addElement(rs.getString("id"));
            }
            id.setModel(model);
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex);
            return;
        }
        jPanel5.setVisible(false);
        jPanel4.setVisible(true);
        prideti.setForeground(Color.black);
    }
    private void issaugotiActionPerformed(java.awt.event.ActionEvent evt){
        try{
            String adresas = jTextField5_pavadinimas.getText();
            String pavadinimas = jTextField4_pavadinimas.getText();
            
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            jXDatePicker1.setFormats(dateFormat);
            String data = dateFormat.format(jXDatePicker1.getDate());
            jXDatePicker3.setFormats(dateFormat);
            String data1 = dateFormat.format(jXDatePicker3.getDate());
            
            String pradzia=jTextField3_pradzia.getText(); 
            String pabaiga=jTextField2_pabaiga.getText(); 
            String ids=jLabel1_ID.getText();
            String miestas=jTextField1_miestas.getText();
            
            int kaina = 0;
            
            try{
                kaina=Integer.parseInt(jTextField6_kaina.getText());
            }
            catch(Exception er){
                JOptionPane.showMessageDialog(null, "įveskite kainą");
                return; 
            }
            {
                try{
                    Connection con = NewCon.connect();
                    Statement st = con.createStatement();
                    String sql ="update filmai set data_nuo='"+data+"',"
                            + "data_iki='"+data1+"',pradzia='"+pradzia+"',"
                            + "pabaiga='"+pabaiga+"',adresas='"+adresas+"',"
                            + "pavadinimas='"+pavadinimas+"',miestas='"+miestas+"'," 
                            + "kaina='"+kaina+"' where id='"+ids+"'";
                    st.executeUpdate(sql);
                    JOptionPane.showMessageDialog(null,"ATnaujinta");
                }catch(SQLException er){
                    JOptionPane.showConfirmDialog(null,er);
                }catch(HeadlessException er){
                    JOptionPane.showConfirmDialog(null,er);
                }
            }
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    private void pridetiActionPerformed(java.awt.event.ActionEvent evt){
        jPanel5.setVisible(true);
        jPanel4.setVisible(false);
    }
    private void atsijungtiActionPerformed(java.awt.event.ActionEvent evt){
        new Login().setVisible(true);
        dispose();
    }
    private void prideti1ActionPerformed(java.awt.event.ActionEvent evt){
        try{
            String adresas=jTextField5_adresas.getText(); 
            String pavadinimas=jTextField4_pavadinimas1.getText();
            String miestas=jTextField5_miestas.getText();
            String pradzia=jTextField3_pradzia1.getText();
            String pabaiga=jTextField2_pabaiga1.getText(); 
            String ids=jTextField5_ID.getText();
            
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
            jXDatePicker2.setFormats(dateFormat);
            String data = dateFormat.format(jXDatePicker2.getDate()).toString(); 
            jXDatePicker4.setFormats(dateFormat);
            String data1 = dateFormat.format(jXDatePicker4.getDate()).toString();
            
            int kaina = 0;
            try{
                kaina=Integer.parseInt(jTextField6_kaina1.getText());
            }
            catch(Exception er){
                JOptionPane.showMessageDialog(null, "įveskite kainą");
                return;
            }
            try{
                Connection con = NewCon.connect();
                Statement st = con.createStatement();
                String sql ="insert into filmai(id,adresas,"
                        + "pavadinimas,kaina,miestas,data_nuo,pradzia,"
                        + "pabaiga,data_iki) values('"+ids+"','"+adresas+"',"
                        + "'"+pavadinimas+"','"+kaina+"','"+miestas+"',"
                        + "'"+data+"','"+pradzia+"','"+pabaiga+"','"+data1+"')";
                st.executeUpdate(sql);
                JOptionPane.showMessageDialog(null, "Atnaujinta");
            }catch(SQLException er){
                JOptionPane.showMessageDialog(null, er);
            }catch(HeadlessException er){
                JOptionPane.showMessageDialog(null, er);
            }
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    private void istrintiActionPerformed(java.awt.event.ActionEvent evt){
        String ids = jLabel1_ID.getText();
        try{
            Connection con = NewCon.connect();
            Statement st = con.createStatement();
            String sql ="delete from filmai where id='"+ids+"'";
            st.executeUpdate(sql);
            JOptionPane.showMessageDialog(null, "Ištrinta");
        }catch(SQLException er){
            JOptionPane.showMessageDialog(null, er);
        }catch(HeadlessException er){
            JOptionPane.showMessageDialog(null, er);
        }
    }
    
    private void redagotiActionPerformed(java.awt.event.ActionEvent evt){
        jPanel5.setVisible(false);
        jPanel4.setVisible(true);
    }
    
    private void idPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt){
        String combo_id=(String)id.getSelectedItem(); 
        String ids="";
        String miestas="";
        String pradzia="";
        String pabaiga="";
        String data="";
        String data1="";
        int kaina = 0;
        String k="";
        String adresas="";
        String pavadinimas="";
        
        try{
            Connection con = NewCon.connect();
            Statement st = con.createStatement();
            String sql ="select * from filmai where id='"+combo_id+"'";
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                ids = rs.getString("id");
                kaina = rs.getInt("kaina");
                k = new Integer(kaina).toString();
                pradzia= rs.getString("pradzia"); 
                pabaiga= rs.getString("pabaiga"); 
                data=rs.getString("data_nuo"); 
                data1=rs.getString("data_iki");
                adresas= rs.getString("adresas"); 
                pavadinimas= rs.getString("pavadinimas"); 
                miestas= rs.getString("miestas");
            }
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex);
            return;
        }
        try{
            jLabel1_ID.setText(ids); 
            jTextField1_miestas.setText(miestas); 
            jTextField5_pavadinimas.setText(adresas); 
            jTextField4_pavadinimas.setText(pavadinimas); 
            jTextField3_pradzia.setText(pradzia); 
            jTextField2_pabaiga.setText(pabaiga); 
            jTextField6_kaina.setText(k);
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, "Nerasta");
            return;
        }
        
        try{
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
            java.util.Date date = dateFormat.parse(data); 
            jXDatePicker1.setDate(date);
            java.util.Date date1 = dateFormat.parse(data1); 
            jXDatePicker3.setDate(date1);
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    public static void main(String args[]){
        java.awt.EventQueue.invokeLater(new Runnable() {
            
            public void run() {
                new Admin().setVisible(true);
            }
        });
    }
    
    private javax.swing.JButton atsijungti;
    private javax.swing.JComboBox id;
    private javax.swing.JButton issaugoti;
    private javax.swing.JButton istrinti;
    private javax.swing.JLabel jLabel10_id;
    private javax.swing.JLabel jLabel11_id;
    private javax.swing.JLabel jLabel12_pavadinimas;
    private javax.swing.JLabel jLabel13_miestas;
    private javax.swing.JLabel jLabel16_adresas;
    private javax.swing.JLabel jLabel17_pabaiga;
    private javax.swing.JLabel jLabel18_kaina;
    private javax.swing.JLabel jLabel1_ID;
    private javax.swing.JLabel jLabel1_nuo;
    private javax.swing.JLabel jLabel20_miestas;
    private javax.swing.JLabel jLabel21_pradzia;
    private javax.swing.JLabel jLabel23_id;
    private javax.swing.JLabel jLabel25_pavadinimas;
    private javax.swing.JLabel jLabel26_adresas;
    private javax.swing.JLabel jLabel27_pabaiga;
    private javax.swing.JLabel jLabel2_data;
    private javax.swing.JLabel jLabel3_iki;
    private javax.swing.JLabel jLabel4_nuo;
    private javax.swing.JLabel jLabel5_data;
    private javax.swing.JLabel jLabel6_iki;
    private javax.swing.JLabel jLabel7_pradzia;
    private javax.swing.JLabel jLabel_kaina;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JTextField jTextField1_miestas;
    private javax.swing.JTextField jTextField2_pabaiga;
    private javax.swing.JTextField jTextField2_pabaiga1;
    private javax.swing.JTextField jTextField3_pradzia;
    private javax.swing.JTextField jTextField3_pradzia1;
    private javax.swing.JTextField jTextField4_pavadinimas;
    private javax.swing.JTextField jTextField4_pavadinimas1;
    private javax.swing.JTextField jTextField5_ID;
    private javax.swing.JTextField jTextField5_adresas;
    private javax.swing.JTextField jTextField5_miestas;
    private javax.swing.JTextField jTextField5_pavadinimas;
    private javax.swing.JTextField jTextField6_kaina;
    private javax.swing.JTextField jTextField6_kaina1;
    private org.jdesktop.swingx.JXDatePicker jXDatePicker1;
    private org.jdesktop.swingx.JXDatePicker jXDatePicker2;
    private org.jdesktop.swingx.JXDatePicker jXDatePicker3;
    private org.jdesktop.swingx.JXDatePicker jXDatePicker4;
    private javax.swing.JButton prideti;
    private javax.swing.JButton prideti1;
    private javax.swing.JButton redaguoti;
}