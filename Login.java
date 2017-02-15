import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class Login extends javax.swing.JFrame {
    
    private void registruotiActionPerformed(java.awt.event.ActionEvent evt){
        dispose();
        new Reg().setVisible(true);
    }
    
    private void prisijungtiActionPerformed(java.awt.event.ActionEvent evt){
        String email=jTextField1_email1.getText(); 
        String pass=jPasswordField1.getText();
        if("".equals(jTextField1_email1.getText())){
            JOptionPane.showMessageDialog(null, "Įveskite el. paštą");
        }
        else if ("".equals(pass)){
            JOptionPane.showMessageDialog(null, "Įveskite slaptažodį");
        }
        else if(("admin".equals(email))&&(("admin".equals(pass)))){
            new Admin().setVisible(true); 
            dispose();
            return;
        }
        
        try{
            Connection con=NewCon.connect();
            Statement st=con.createStatement();
            String sql="select * from klientai where email='"+email+"' "
                    + "and password='"+pass+"'"; 
            ResultSet rs=st.executeQuery(sql);
            if(rs.next()){
                new Purchase().setVisible(true); 
                Purchase.User_name.setText(this.jTextField1_email1.getText()); 
                dispose();
            }
            else{
                JOptionPane.showMessageDialog(null,"Neteisingas prisijungimo " + "vardas arba slaptažodis");
            }
        }
        catch(SQLException ex){
            JOptionPane.showMessageDialog(null, ex);
        }
        catch(HeadlessException ex){
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    
    private void slaptazodisMouseClicked(java.awt.event.MouseEvent evt) {
        new Changepass().setVisible(true);
    }
    
    public static void main(String args[]) { 
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Login().setVisible(true);
            } 
        });
    }
    
    private javax.swing.JLabel jLabel2_foto;
    private javax.swing.JLabel jLabel3_email;
    private javax.swing.JLabel jLabel3_login;
    private javax.swing.JLabel jLabel3_pass;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPasswordField jPasswordField1;
    public static javax.swing.JTextField jTextField1_email1;
    private javax.swing.JButton prisijungti;
    private javax.swing.JButton registruoti;
    private javax.swing.JLabel slaptazodis;
    
}