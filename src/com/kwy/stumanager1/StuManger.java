package com.kwy.stumanager1;

/**
 * Created by kong on 2017/10/21.
 * 完成一个mini版的学生管理系统
 * 1.查询操作
 */

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class StuManger extends JFrame implements ActionListener {
    //rowData用来存放数据
    //columnNames存放列名

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

    //构造函数
    public StuManger(){
        jp1=new JPanel();
        jtf=new JTextField(10);
        jb1=new JButton("查询");
        jb1.addActionListener(this);
        jl=new JLabel("请输入名字");

        //把各个控件加入到jp1
        jp1.add(jl);
        jp1.add(jtf);
        jp1.add(jb1);

        jb2=new JButton("添加");
        jb2.addActionListener(this);
        jb3=new JButton("修改");
        jb3.addActionListener(this);
        jb4=new JButton("删除");
        jb4.addActionListener(this);


        jp2=new JPanel();
        //把各个按钮加入到jp2中
        jp2.add(jb2);
        jp2.add(jb3);
        jp2.add(jb4);

        StuModel stuModel=new StuModel();



        //初始化jt
        jt=new JTable(stuModel);

        //初始化jsp
        jsp=new JScrollPane(jt);
        this.add(jsp);
        this.add(jp1,"North");
        this.add(jp2,"South");
        //设置大小
        this.setSize(400, 300);
        //关闭时自动退出

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setVisible(true);
    }

   @Override
    public void actionPerformed(ActionEvent e) {
        //判断是哪个按钮被点击
       if(e.getSource()==jb1){
           String name=this.jtf.getText().trim();
           //写一个sql语句
           String sql="select * from stu where stuname='"+name+"'";
           sm=new StuModel(sql);
           jt.setModel(sm);
       }
       else if(e.getSource()==jb2){
           StuAddDialog sa=new StuAddDialog(this,"添加学生",true);
           sm=new StuModel();
           jt.setModel(sm);
   }
       else if(e.getSource()==jb3){
           sm=new StuModel();
           int rownum=this.jt.getSelectedRow();
           if(rownum==-1){
               JOptionPane.showMessageDialog(this,"请选择一行数据进行修改操作");
               return;
           }
           StuUpdate su=new StuUpdate(this,"修改学生信息",true,sm,rownum);
           sm=new StuModel();
           jt.setModel(sm);
       }
       else if(e.getSource()==jb4){
           //说明用户希望删除记录
           //1.得到该学生的id号
           //如果一行都没有选择，就返回-1

           int rownum=this.jt.getSelectedRow();
           if(rownum==-1){
               JOptionPane.showMessageDialog(this,"请选择一行数据进行删除操作");
               return;
           }
           //得到学生编号
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
               //给多个数赋值
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
