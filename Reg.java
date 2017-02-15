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

public class Reg extends javax.swing.JFrame {
    
    public Reg() { 
        radio(); 
        combobox();
    }
    
    private void radio() {
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
    
    private void testiActionPerformed (java.awt.event.ActionEvent evt) {
        String email=jTextField1_email.getText();
        String password=jPasswordField1.getText();
        String repass=jPasswordField2_confirm.getText();
        String vardas=jTextField4_vardas.getText();
        String pavarde=jTextField5_pavarde.getText();
        String diena = (String) jComboBox3_diena.getSelectedItem(); 
        String menuo = (String) jComboBox2_menuo.getSelectedItem(); 
        String metai = (String) jComboBox4_metai.getSelectedItem(); 
        String data=metai+"-"+menuo+"-"+diena;
        String adresas=jTextField7_adresas.getText();
        String miestas=jTextField8_miestas.getText();
        String salis=(String)jComboBox5_salis.getSelectedItem(); 
        String lytis="";
        String zip=(String)jTextField10_zip.getText();
        String mobile=(String)telefonas.getText();
        
        try{
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
            Date date = dateFormat.parse(data);
            Calendar dob = Calendar.getInstance();
            dob.setTime(date);
            Calendar today = Calendar.getInstance();
            int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
            if (age<18) {
                JOptionPane.showMessageDialog(null, "Esate per jaunas");
                return; 
            }
        }
        catch(ParseException e){
            JOptionPane.showMessageDialog(null,e);
            return;
        }
        catch (HeadlessException e) {
            JOptionPane.showMessageDialog(null,e);
            return; 
        }
        
        if("".equals(password)){
            JOptionPane.showMessageDialog(null, "Įveskite slaptažodį");
        }
        else if(password == null ? (repass) != null : !password.equals(repass)) {
            JOptionPane.showMessageDialog(null,"Slaptažodžiai nesutampa"); 
            jPasswordField2_confirm.setForeground(Color.red);
            return;
        }
        
        try{
            "".equals(email); 
            "".equals(password); 
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
        if(jRadioButton1.isSelected()){
            lytis="Vyras";
        }
        else if(jRadioButton2.isSelected()){
            lytis="Moteris";
        }
        else{
            JOptionPane.showMessageDialog(null,"Pasirinkite lytį"); 
            return;
        }
        if(jCheckBox1.getSelectedObjects()==null){
            jCheckBox1.setForeground(Color.red); 
            JOptionPane.showMessageDialog(null,"sutikite su sąlygomis"); 
            return;
        }
        
        try{
            Connection con = NewCon.connect();
            Statement st= con.createStatement();
            String sql ="select email from klientai where email='"+email
                    +"'"; ResultSet rs=st.executeQuery(sql);
            if(rs.next()){
                String check = rs.getString("email"); 
                if(check.equals(email)){
                    JOptionPane.showMessageDialog(null, "Esate prisiregistravę");
                }
                return;
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e);
            return;
        }catch(HeadlessException e){
            JOptionPane.showMessageDialog(null, e);
            return;
        }
        
        try{
            Connection con = NewCon.connect(); Statement st= con.createStatement(); String sql ="insert into klientai "
                    + "(password,vardas,pavarde,gimimo_data,adresas," + "miestas,salis,zip,mobile,lytis,email) values " + "('"+password+"','"+vardas+"','"+pavarde+"',"
                    + "'"+data+"','"+adresas+"','"+miestas+"',"
                    + "'"+salis+"','"+zip+"','"+mobile+"',"
                    + "'"+lytis+"' ,'"+email+"')";
            st.executeUpdate(sql);
            dispose();
            new Payment().setVisible(true); 
            Payment.jTextField1.setText(this.jTextField1_email.getText());
        }catch(Exception er){
            JOptionPane.showMessageDialog(null, er);
        }
    }
    
    private void telefonasKeyTyped(java.awt.event.KeyEvent evt) {
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
    private javax.swing.JCheckBox jCheckBox1; 
    private javax.swing.JComboBox jComboBox2_menuo; 
    private javax.swing.JComboBox jComboBox3_diena; 
    private javax.swing.JComboBox jComboBox4_metai; 
    private javax.swing.JComboBox jComboBox5_salis; 
    private javax.swing.JLabel jLabel10_data; 
    private javax.swing.JLabel jLabel11_adresas; 
    private javax.swing.JLabel jLabel12_miestas; 
    private javax.swing.JLabel jLabel13_telefonas; 
    private javax.swing.JLabel jLabel14_salis; 
    private javax.swing.JLabel jLabel15_confirm; 
    private javax.swing.JLabel jLabel3_email; 
    private javax.swing.JLabel jLabel4_slaptazodis; 
    private javax.swing.JLabel jLabel6_lytis; 
    private javax.swing.JLabel jLabel7_vardas; 
    private javax.swing.JLabel jLabel8_pavarde;
    private javax.swing.JLabel jLabel9_zip;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JPasswordField jPasswordField2_confirm;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JTextField jTextField10_zip;
    public static javax.swing.JTextField jTextField1_email;
    private javax.swing.JTextField jTextField4_vardas;
    private javax.swing.JTextField jTextField5_pavarde;
    private javax.swing.JTextField jTextField7_adresas;
    private javax.swing.JTextField jTextField8_miestas;
    private javax.swing.JTextField telefonas;
    private javax.swing.JButton testi;
}