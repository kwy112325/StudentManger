package com.kwy.stumanager1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

/**
 * Created by kong on 2017/10/22.
 */
public class StuAddDialog extends Dialog implements ActionListener{
    //JFrame �������࣬�����࣬����Ƕ��ü�������
    //JPanel �����࣬�൱��һ���󴰿�
    //JLabel ���������������JPanel�У����εĶ�������������ֽ
    //JButton ��ť
    JPanel jp1,jp2,jp3;
    JLabel jl1,jl2,jl3,jl4,jl5,jl6;
    JButton jb1,jb2;
    JTextField jtf1,jtf2,jtf3,jtf4,jtf5,jtf6;
    //owner ���ĸ�����
    //titile ������
    //model ָ����ģʽ���ڻ��Ƿ�ģʽ����
    public StuAddDialog(Frame owner,String title,boolean model){
        super(owner,title,model);
        //
        jl1=new JLabel("ѧ��");
        jl2=new JLabel("����");
        jl3=new JLabel("�Ա�");
        jl4=new JLabel("����");
        jl5=new JLabel("����");
        jl6=new JLabel("ϵ��");

        jtf1=new JTextField();
        jtf2=new JTextField();
        jtf3=new JTextField();
        jtf4=new JTextField();
        jtf5=new JTextField();
        jtf6=new JTextField();

        jb1=new JButton("���");
        jb1.addActionListener(this);
        jb2=new JButton("ȡ��");
        jb2.addActionListener(this);

        jp1=new JPanel();
        jp2=new JPanel();
        jp3=new JPanel();

        //���ò���
        jp1.setLayout(new GridLayout(6,1));
        jp2.setLayout(new GridLayout(6, 1));

        //������
        jp1.add(jl1);
        jp1.add(jl2);
        jp1.add(jl3);
        jp1.add(jl4);
        jp1.add(jl5);
        jp1.add(jl6);

        jp2.add(jtf1);
        jp2.add(jtf2);
        jp2.add(jtf3);
        jp2.add(jtf4);
        jp2.add(jtf5);
        jp2.add(jtf6);

        jp3.add(jb1);
        jp3.add(jb2);

        //����
        this.add(jp1, BorderLayout.WEST);
        this.add(jp2, BorderLayout.CENTER);
        this.add(jp3, BorderLayout.SOUTH);

        //չ��
        this.setSize(300,250);
        //this.setDefaultCloseOperation(owner.EXIT_ON_CLOSE);

        this.setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==jb1){
            Connection con=null;
            PreparedStatement pst=null;
            ResultSet rs=null;

            try {
                Class.forName("com.mysql.jdbc.Driver");
                con= DriverManager.getConnection("jdbc:mysql://localhost:3306/school?useUnicode=true&characterEncoding=utf-8", "root", "kwy112325");
                pst=con.prepareStatement("insert into stu values(?,?,?,?,?,?) ");
                //���������ֵ
                pst.setString(1,jtf1.getText());
                pst.setString(2,jtf2.getText());
                pst.setString(3,jtf3.getText());
                pst.setString(4,jtf4.getText());
                pst.setString(5,jtf5.getText());
                pst.setString(6,jtf6.getText());
                pst.executeUpdate();
                this.dispose();//�ر����ѧ��
            } catch (ClassNotFoundException e1) {
                e1.printStackTrace();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }finally {
                try {
                    if(rs!=null) rs.close();
                    if(pst!=null) pst.close();
                    if(con!=null) con.close();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        }
        else if(e.getSource()==jb2){
            this.dispose();
        }

    }
}
