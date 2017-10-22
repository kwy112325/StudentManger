package com.kwy.stumanager1;

/**
 * Created by kong on 2017/10/21.
 * ���һ��mini���ѧ������ϵͳ
 * 1.��ѯ����
 */

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class StuManger extends JFrame implements ActionListener {
    //rowData�����������
    //columnNames�������

    JPanel jp1,jp2;
    JLabel jl;
    JButton jb1,jb2,jb3,jb4;
    JTable jt=null;
    JScrollPane jsp=null;
    JTextField jtf;
    StuModel sm;
    public static void main(String[] args) {
        StuManger test=new StuManger();
    }

    //���캯��
    public StuManger(){
        jp1=new JPanel();
        jtf=new JTextField(10);
        jb1=new JButton("��ѯ");
        jb1.addActionListener(this);
        jl=new JLabel("����������");

        //�Ѹ����ؼ����뵽jp1
        jp1.add(jl);
        jp1.add(jtf);
        jp1.add(jb1);

        jb2=new JButton("���");
        jb2.addActionListener(this);
        jb3=new JButton("�޸�");
        jb3.addActionListener(this);
        jb4=new JButton("ɾ��");
        jb4.addActionListener(this);


        jp2=new JPanel();
        //�Ѹ�����ť���뵽jp2��
        jp2.add(jb2);
        jp2.add(jb3);
        jp2.add(jb4);

        StuModel stuModel=new StuModel();



        //��ʼ��jt
        jt=new JTable(stuModel);

        //��ʼ��jsp
        jsp=new JScrollPane(jt);
        this.add(jsp);
        this.add(jp1,"North");
        this.add(jp2,"South");
        //���ô�С
        this.setSize(400, 300);
        //�ر�ʱ�Զ��˳�

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setVisible(true);
    }

   @Override
    public void actionPerformed(ActionEvent e) {
        //�ж����ĸ���ť�����
       if(e.getSource()==jb1){
           String name=this.jtf.getText().trim();
           //дһ��sql���
           String sql="select * from stu where stuname='"+name+"'";
           sm=new StuModel(sql);
           jt.setModel(sm);
       }
       else if(e.getSource()==jb2){
           StuAddDialog sa=new StuAddDialog(this,"���ѧ��",true);
           sm=new StuModel();
           jt.setModel(sm);
   }
       else if(e.getSource()==jb3){
           sm=new StuModel();
           int rownum=this.jt.getSelectedRow();
           if(rownum==-1){
               JOptionPane.showMessageDialog(this,"��ѡ��һ�����ݽ����޸Ĳ���");
               return;
           }
           StuUpdate su=new StuUpdate(this,"�޸�ѧ����Ϣ",true,sm,rownum);
           sm=new StuModel();
           jt.setModel(sm);
       }
       else if(e.getSource()==jb4){
           //˵���û�ϣ��ɾ����¼
           //1.�õ���ѧ����id��
           //���һ�ж�û��ѡ�񣬾ͷ���-1

           int rownum=this.jt.getSelectedRow();
           if(rownum==-1){
               JOptionPane.showMessageDialog(this,"��ѡ��һ�����ݽ���ɾ������");
               return;
           }
           //�õ�ѧ�����
           sm=new StuModel();
           String stuid=(String)sm.getValueAt(rownum,0);
           Connection con=null;
           PreparedStatement pst=null;
           ResultSet rs=null;


           try {
               Class.forName("com.mysql.jdbc.Driver");
               con= DriverManager.getConnection("jdbc:mysql://localhost:3306/school?useUnicode=true&characterEncoding=utf-8", "root", "kwy112325");
               pst=con.prepareStatement("DELETE FROM  stu WHERE stuid=?");
               pst.setString(1, stuid);
               pst.executeUpdate();
               //���������ֵ
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
           sm=new StuModel();
           jt.setModel(sm);
       }
    }
}
