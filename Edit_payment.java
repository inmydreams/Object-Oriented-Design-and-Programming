import java.awt.HeadlessException;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;

public class Edit_payment extends javax.swing.JFrame{
    
    public Edit_payment(){
        radio();
        fill();
    }
    
    private void radio(){
        button_group=new ButtonGroup(); 
        button_group.add(jRadioButton2); 
        button_group.add(jRadioButton3);
    }
    
    private void fill(){
        String number="";
        String email=Purchase.User_name.getText(); 
        jTextField1_email.setText(email);
        String data_card="";
        String tipas="";
        
        try{
            Connection con = NewCon.connect();
            Statement st = con.createStatement();
            String sql ="select * from klientai where email='"+email+"'";
            ResultSet rs = st.executeQuery(sql);
            while(rs.next() ){
                number=rs.getString("numeris");
                card.setText(number); 
                data_card=rs.getString("data"); 
                String[] dStr = data_card.split("/"); 
                jComboBox1.setSelectedItem(dStr[0]); 
                jComboBox2.setSelectedItem(dStr[1]); 
                tipas=rs.getString("tipas"); 
                if(tipas.equals("master")){ 
                    jRadioButton2.setSelected(true);
                }
                else{
                    jRadioButton3.setSelected(true);
                }
            }
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    
    private void issaugotiActionPerformed(java.awt.event.ActionEvent evt){
        
        String tipas = "";
        
        if(jRadioButton2.isSelected()) {
            tipas="master";
        }
        else if(jRadioButton3.isSelected()){
            tipas="visa";
        }
        else{
            JOptionPane.showMessageDialog(null, "Pasirinkite kortelę");
            return;
        }
        
        String email=jTextField1_email.getText();
        String number=card.getText();
        
        try{
            "".equals(number);
        }
        catch(Exception ex){
            JOptionPane.showMessageDialog(null, "Neteisingas kortelės numeris");
            return;
        }
        
        int length = number.length();
        if(length!=16){
            JOptionPane.showMessageDialog(null, "Neteisingas kortelės numeris");
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
            JOptionPane.showMessageDialog(null, "Netinkama data");
            return;
        }
    
        try{
            Connection con = NewCon.connect();
            Statement st = con.createStatement();
            String sql ="update klientai set numeris='"+number+"',"
                + "data='"+data_card+"',tipas='"+tipas+"' "
                + "where email='"+email+"'";
            st.executeUpdate(sql);
            JOptionPane.showMessageDialog(null,"Išsaugota"); 
            new Purchase().setVisible(true);
        
        }catch(SQLException er){
            JOptionPane.showMessageDialog(null, er);
        }catch(HeadlessException er){
            JOptionPane.showMessageDialog(null, er);
        }
    }
    
    private void cardKeyTyped(java.awt.event.KeyEvent evt){
        char c=evt.getKeyChar();
        if(!(Character.isDigit(c)||c==KeyEvent.VK_BACK_SPACE || c==KeyEvent.VK_DELETE)){
            evt.consume();
        }
    }
    
    public static void main (String args[]){
        java.awt.EventQueue.invokeLater(new Runnable(){
            
            public void run(){
                new Payment().setVisible(true);
            }
        });
    }
    private javax.swing.ButtonGroup button_group;
    private javax.swing.JTextField card;
    private javax.swing.JButton issaugoti;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JComboBox jComboBox2;
    private javax.swing.JLabel jLabel12_data;
    private javax.swing.JLabel jLabel5_kortele;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JRadioButton jRadioButton3;
    private javax.swing.JTextField jTextField1_email;
}

