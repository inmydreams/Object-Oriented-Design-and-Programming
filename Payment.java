import java.awt.*;
import java.awt.event.KeyEvent;
import javax.swing.*;
import java.sql.*;
import java.util.Calendar;

public class Payment extends javax.swing.JFrame {
    
    public Payment() {
        radio(); 
    }
    
    private void radio() {
        button_group=new ButtonGroup(); 
        button_group.add(jRadioButton1); 
        button_group.add(jRadioButton2);
    }
    
    private void testiActionPerformed (java.awt.event.ActionEvent evt){
        String tipas="";
        if(jRadioButton1.isSelected()) {
            tipas="master"; 
        }
        else if(jRadioButton2.isSelected()) {
            tipas="visa"; 
        }
        else{
            JOptionPane.showMessageDialog(null,"Pasirinkite kortelę");
            return;
        }
        
        String email=jTextField1.getText();
        String number=card.getText();
        
        try {
            "".equals(number); 
        }
        catch(Exception ex){
            JOptionPane.showMessageDialog(null,"Neteisingas kortelės numeris"); 
            return;
        }
        
        int lenght=number.length();
        if(lenght!=16){
            JOptionPane.showMessageDialog(null,"Neteisingas kortelės " + "numeris");
            return;
        }
        
        Calendar today=Calendar.getInstance();
        String menuo=(String) jComboBox1.getSelectedItem(); 
        String metai=(String) jComboBox2.getSelectedItem(); 
        int today1=today.get(Calendar.MONTH)+1;
        int today2=today.get(Calendar.YEAR);
        String data_card=menuo+"/"+metai;
        int month1=Integer.parseInt(menuo);
        int year1=Integer.parseInt(metai);
        
        if(today1>month1 || today2>year1){
            JOptionPane.showMessageDialog(null,"Netinkama data"); 
            return;
        }
        
        try{
            Connection con = NewCon.connect();
            Statement st= con.createStatement();
            String sql ="update klientai set numeris='"+number+"',data='"+
                    data_card+"',tipas='"+tipas+"' where email='"+email+"'"; 
            st.executeUpdate(sql);
            JOptionPane.showMessageDialog(null,"Kortelė sėkmingai pridėta"); 
            JOptionPane.showMessageDialog(null,"Galite prisijungti"); 
            dispose();
            new Login().setVisible(true);
        }catch(SQLException er){
            JOptionPane.showMessageDialog(null, er);
        }catch (HeadlessException er) {
            JOptionPane.showMessageDialog(null, er);
        }
    }
    
    private void cardKeyTyped(java.awt.event.KeyEvent evt){
        char c=evt.getKeyChar();
        if(!(Character.isDigit(c)|| c==KeyEvent.VK_BACK_SPACE || c==KeyEvent.VK_DELETE)){
            evt.consume(); 
        }
    }
    
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Payment().setVisible(true);
            }
        });
    }
    
    private javax.swing.ButtonGroup button_group;
    private javax.swing.JTextField card;
    private javax.swing.JComboBox jComboBox1; 
    private javax.swing.JComboBox jComboBox2; 
    private javax.swing.JLabel jLabel12_data; 
    private javax.swing.JLabel jLabel5_kortele; 
    private javax.swing.JPanel jPanel1;
    private javax.swing.JRadioButton jRadioButton1; 
    private javax.swing.JRadioButton jRadioButton2; 
    public static javax.swing.JTextField jTextField1; 
    private javax.swing.JButton testi;
    
}