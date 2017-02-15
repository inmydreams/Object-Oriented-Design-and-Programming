import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class Changepass extends javax.swing.JFrame{
    
    private void keistiActionPerformed(java.awt.event.ActionEvent evt){
        String email = jTextField1_email.getText();
        String newpass = jPasswordField1.getText();
        String repass = jPasswordField2.getText();
        {
            if(newpass == null ? (repass) != null: !newpass.equals(repass)){
                JOptionPane.showMessageDialog(null, "Nesutampa slaptažodžiai");
                return;
            }
            
            try{
                Connection con = NewCon.connect();
                Statement st = con.createStatement();
                String sql ="select email from klientai where email='"+email+"'";
                ResultSet rs = st.executeQuery(sql);
                if(rs.next()){
                    String check = rs.getString("email");
                    if(check.equals(email)){
                        Statement st1 = con.createStatement();
                        String sql1 ="Update klientai set password='"+newpass+"' "
                                + "where email='"+email+"'";
                        st1.executeUpdate(sql1);
                        JOptionPane.showMessageDialog(null, "Slaptažodis pakeistas");
                    }
                }
                else{
                    JOptionPane.showMessageDialog(null, "Tokio vartotojo nėra");
                }
            }catch(SQLException e){
                JOptionPane.showMessageDialog(null, e);
            }catch(HeadlessException e){
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }
    
    public static void main(String args[]){
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Changepass().setVisible(true);
            }
        });
    }
    
    private javax.swing.JLabel jLabel1_email;
    private javax.swing.JLabel jLabel2_pakartoti;
    private javax.swing.JLabel jLabel3_slaptazodis;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JPasswordField jPasswordField2;
    private javax.swing.JTextField jTextField1_email;
    private javax.swing.JButton keisti;
}

