import java.awt.Color;
import java.awt.HeadlessException;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;

public class Edit_profile extends javax.swing.JFrame{
    
    public Edit_profile(){
        radio();
        combobox();
        fill();
    }
    
    private void radio(){
        buttonGroup1 = new ButtonGroup(); 
        buttonGroup1.add(jRadioButton1); 
        buttonGroup1.add(jRadioButton2);
    }
    
    private void combobox(){
        Calendar today=Calendar.getInstance(); 
        int year=today.get(Calendar.YEAR)-100; 
        int year1=today.get(Calendar.YEAR); 
        jComboBox4_metai.addItem("yyyy");
        for (int i = year; i <=year1; i++){ 
            String year2=String.valueOf(i); 
            jComboBox4_metai.addItem(year2);
        }
    }
    
    private void fill(){
        int mobile=0;
        String vardas="";
        String pavarde="";
        String lytis="";
        String adresas="";
        String miestas="";
        String salis="";
        String zip="";
        String email=Purchase.User_name.getText(); 
        jTextField1_email.setText(email);
    
        try{
            Connection con = NewCon.connect();
            Statement st = con.createStatement();
            String sql ="select * from klientai where email='"+email+"'";
            ResultSet rs = st.executeQuery(sql);
            while(rs.next() ){
                mobile=rs.getInt("mobile");
                vardas= rs.getString("vardas");
                pavarde= rs.getString("pavarde");
                adresas= rs.getString("adresas");
                miestas= rs.getString("miestas"); 
                salis=rs.getString("salis"); 
                zip=rs.getString("zip");
                Date data = rs.getDate("gimimo_data"); 
                String date1=data.toString();
                String[] dStr = date1.split("-"); 
                jComboBox4_metai.setSelectedItem(dStr[0]); 
                jComboBox2_menuo.setSelectedItem(dStr[1]); 
                jComboBox3_diena.setSelectedItem(dStr[2]); 
                lytis=rs.getString("lytis"); 
                if(lytis.equals("Vyras")){
                    jRadioButton1.setSelected(true);
                }
                else{
                    jRadioButton2.setSelected(true);
                }
            }
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex);
            return;
        }
        try{
            String mobile1=Integer.toString(mobile); 
            telefonas.setText(mobile1); 
            jTextField4_vardas.setText(vardas); 
            jTextField5_pavarde.setText(pavarde); 
            jTextField7_adresas.setText(adresas); 
            jTextField8_miestas.setText(miestas); 
            jTextField10_zip.setText(zip); 
            jComboBox5_salis.setSelectedItem(salis);
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    private void issaugotiActionPerformed(java.awt.event.ActionEvent evt){
        String email=jTextField1_email.getText();
        String oldpass=jPasswordField2.getText();
        String password=jPasswordField1.getText();
        String repass=jPasswordField2_confirm.getText();
        String vardas=jTextField4_vardas.getText();
        String pavarde=jTextField5_pavarde.getText();
        String diena=(String)jComboBox3_diena.getSelectedItem(); 
        String menuo=(String)jComboBox2_menuo.getSelectedItem(); 
        String metai=(String)jComboBox4_metai.getSelectedItem(); 
        String data=metai+"-"+menuo+"-"+diena;
        String adresas=jTextField7_adresas.getText();
        String miestas=jTextField8_miestas.getText();
        String salis=(String)jComboBox5_salis.getSelectedItem(); 
        String lytis="";
        String zip=(String)jTextField10_zip.getText();
        String mobile=(String)telefonas.getText();
        
        try{
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
            java.util.Date date = dateFormat.parse(data);
            Calendar dob = Calendar.getInstance();
            dob.setTime(date);
            Calendar today = Calendar.getInstance();
            int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
            if(age<18){
                JOptionPane.showMessageDialog(null, "Esate per jaunas");
                return;
            }
        }
        catch(ParseException e){
            JOptionPane.showMessageDialog(null, e);
            return;
        }catch(HeadlessException e){
            JOptionPane.showMessageDialog(null, e);
            return;
        }
        
        if("".equals(password)) {
            JOptionPane.showMessageDialog(null, "Įveskite slaptažodį");
            return; 
        }
        else if(password == null ? (repass) != null : !password.equals(repass)){
            JOptionPane.showMessageDialog(null,"Slaptažodžiai nesutampa"); 
            jPasswordField2_confirm.setForeground(Color.red);
            return;
        }
        
        try{
            "".equals(vardas); 
            "".equals(pavarde); 
            "".equals(adresas); 
            "".equals(miestas); 
            "".equals(zip); 
            "".equals(mobile); 
            metai.equals("yyyy"); 
            menuo.equals("mm"); 
            diena.equals("dd");
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null,"Užpildykite laukus");
            return;
        }
        
        try{
            if(jRadioButton1.isSelected()){
                lytis="Vyras";
            }
            else if(jRadioButton2.isSelected()){
                lytis="Moteris";
            }
        }
        catch(Exception ex){
            JOptionPane.showMessageDialog(null,ex);
            return;
        }
        try{
            Connection con = NewCon.connect();
            Statement st= con.createStatement();
            String sql ="update klientai set password='"+password+"',"
                    + "vardas='"+vardas+"',pavarde='"+pavarde+"',"
                    + "gimimo_data='"+data+"',adresas='"+adresas+"',"
                    + "miestas='"+miestas+"',salis='"+salis+"',"
                    + "zip='"+zip+"',mobile='"+mobile+"',lytis='"+lytis+"' " 
                    + "where email='"+email+"' AND password='"+oldpass+"'";
            st.executeUpdate(sql);
            JOptionPane.showMessageDialog(null, "Pakeista");
        }catch(SQLException er){
            JOptionPane.showMessageDialog(null, er);
        }catch(HeadlessException er){
            JOptionPane.showMessageDialog(null, er);
        }
    }
    
    private void telefonasKeyTyped(java.awt.event.KeyEvent evt){
        char c=evt.getKeyChar();
        if(!(Character.isDigit(c)|| c==KeyEvent.VK_BACK_SPACE || c==KeyEvent.VK_DELETE)){
            evt.consume(); 
        }
    }
    
    public static void main(String args[]) { 
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Reg().setVisible(true);
            }
        });
    }
    
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton issaugoti;
    private javax.swing.JComboBox jComboBox2_menuo;
    private javax.swing.JComboBox jComboBox3_diena;
    private javax.swing.JComboBox jComboBox4_metai;
    private javax.swing.JComboBox jComboBox5_salis;
    private javax.swing.JLabel jLabel10_data;
    private javax.swing.JLabel jLabel11_adresas;
    private javax.swing.JLabel jLabel12_miestas;
    private javax.swing.JLabel jLabel13_telefonas;
    private javax.swing.JLabel jLabel14_salis;
    private javax.swing.JLabel jLabel15_slaptazodis;
    private javax.swing.JLabel jLabel3_email;
    private javax.swing.JLabel jLabel4_old;
    private javax.swing.JLabel jLabel5_new;
    private javax.swing.JLabel jLabel6_lytis;
    private javax.swing.JLabel jLabel7_vardas;
    private javax.swing.JLabel jLabel8_pavarde;
    private javax.swing.JLabel jLabel9_zip;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JPasswordField jPasswordField2;
    private javax.swing.JPasswordField jPasswordField2_confirm; 
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JTextField jTextField10_zip;
    private javax.swing.JTextField jTextField1_email;
    private javax.swing.JTextField jTextField4_vardas;
    private javax.swing.JTextField jTextField5_pavarde;
    private javax.swing.JTextField jTextField7_adresas;
    private javax.swing.JTextField jTextField8_miestas;
    private javax.swing.JTextField telefonas;
}