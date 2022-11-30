import net.proteanit.sql.DbUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Employee {
    private JPanel main;
    private JTextField txtname;
    private JTextField txtdept;
    private JTextField txtpost;
    private JButton saveButton;
    private JTable table1;
    private JButton updateButton;
    private JButton deleteButton;
    private JButton searchButton;
    private JTextField txtid;
    private JScrollPane table_1;
    private JTextField txtemail;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Employee");
        frame.setContentPane(new Employee().main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    Connection con;
    PreparedStatement pst;
    public void connect()
    {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/AK_MNCS", "root","Admin@123");
            System.out.println("Successs");
        }
        catch (ClassNotFoundException ex)
        {
            ex.printStackTrace();

        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }

    }

    void tabel_load()
    {
        try
        {
            pst = con.prepareStatement("select * from employee");
            ResultSet rs = pst.executeQuery();
            table1.setModel(DbUtils.resultSetToTableModel(rs));
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }


    public Employee() {
        connect();
        tabel_load();
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            String empname,email,dept,post;

            empname=txtname.getText();
            email=txtemail.getText();
            dept= txtdept.getText();
            post= txtpost.getText();


                try {
                    pst = con.prepareStatement("insert into employee(empname,email,dept,post)values(?,?,?,?)");
                    pst.setString(1, empname);
                    pst.setString(2, email);
                    pst.setString(3, dept);
                    pst.setString(4, post);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Record Addedddd!!!!!");
                    tabel_load();
                    txtname.setText("");
                    txtemail.setText("");
                    txtdept.setText("");
                    txtpost.setText("");
                    txtname.requestFocus();
                }

                catch (SQLException e1)
                {

                    e1.printStackTrace();
                }

            }
        });
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    String empid = txtid.getText();

                    pst = con.prepareStatement("select empname,email, dept,post from employee where id = ?");
                    pst.setString(1, empid);
                    ResultSet rs = pst.executeQuery();

                    if(rs.next()==true)
                    {
                        String empname = rs.getString(1);
                        String ememail = rs.getString(2);
                        String emdept = rs.getString(3);
                        String empost = rs.getString(4);

                        txtname.setText(empname);
                        txtemail.setText(ememail);
                        txtdept.setText(emdept);
                        txtpost.setText(empost);

                    }
                    else
                    {
                        txtname.setText("");
                        txtemail.setText("");
                        txtdept.setText("");
                        txtpost.setText("");
                        JOptionPane.showMessageDialog(null,"Invalid Employee No");

                    }
                }
                catch (SQLException ex)
                {
                    ex.printStackTrace();
                }

            }
        });
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String empid,empname,email,dept,post;
                empname = txtname.getText();
                email = txtemail.getText();
                dept = txtdept.getText();
                post = txtpost.getText();
                empid = txtid.getText();

                try {
                    pst = con.prepareStatement("update employee set empname = ?,email = ?,dept = ?,post = ? where id = ?");
                    pst.setString(1, empname);
                    pst.setString(2, email);
                    pst.setString(3, dept);
                    pst.setString(4, post);
                    pst.setString(5, empid);

                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Record Updateee!!!!!");
                    tabel_load();
                    txtname.setText("");
                    txtemail.setText("");
                    txtdept.setText("");
                    txtpost.setText("");
                    txtname.requestFocus();
                }

                catch (SQLException e1)
                {
                    e1.printStackTrace();
                }
            }

        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String empid;
                empid = txtid.getText();

                try {
                    pst = con.prepareStatement("delete from employee  where id = ?");

                    pst.setString(1, empid);

                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Record Deleteeeeee!!!!!");
                    tabel_load();
                    txtname.setText("");
                    txtemail.setText("");
                    txtdept.setText("");
                    txtpost.setText("");
                    txtname.requestFocus();
                }

                catch (SQLException e1)
                {
                    e1.printStackTrace();
                }
            }

        });
    }
}
