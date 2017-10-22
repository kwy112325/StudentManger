package com.kwy.stumanager1;

import javax.swing.table.AbstractTableModel;
import java.sql.*;
import java.util.Vector;

/**
 * Created by kong on 2017/10/21.
 * 这是我的一个stu表的模型
 */
public class StuModel extends AbstractTableModel{
    Vector rowData,columnNames;
    Connection con=null;
    PreparedStatement pst=null;
    ResultSet rs=null;

    public void init(String sql){
        if(sql.equals("")){
            sql="SELECT * FROM stu";
        }
        rowData = new Vector();
        columnNames=new Vector();
        //设置列名
        columnNames.add("学号");
        columnNames.add("名字");
        columnNames.add("性别");
        columnNames.add("年龄");
        columnNames.add("籍贯");
        columnNames.add("系别");


        try {
            Class.forName("com.mysql.jdbc.Driver");
            con= DriverManager.getConnection("jdbc:mysql://localhost:3306/school?useUnicode=true&characterEncoding=utf-8", "root", "kwy112325");
            pst=con.prepareStatement(sql);
            rs=pst.executeQuery();
            while(rs.next()){
                Vector hang=new Vector();
                hang.add(rs.getString(1));
                hang.add(rs.getString(2));
                hang.add(rs.getString(3));
                hang.add(rs.getInt(4));
                hang.add(rs.getString(5));
                hang.add(rs.getString(6));

                //加入到rowData
                rowData.add(hang);

            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                if(rs!=null) rs.close();
                if(pst!=null) pst.close();
                if(con!=null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
    //
    public StuModel(String sql){
        this.init(sql);
    }
    //构造函数
    public StuModel(){
        this.init("");
    }
    @Override//得到共有多少列
    public int getRowCount() {
        return this.rowData.size();
    }

    @Override//得到共有多少行
    public int getColumnCount() {
        return this.columnNames.size();
    }

    @Override//得到某行某列的数据
    public Object getValueAt(int rowIndex, int columnIndex) {
        return ((Vector)this.rowData.get(rowIndex)).get(columnIndex);
    }
    public String getColumnName(int columnIndex){
        return (String)this.columnNames.get(columnIndex);
    }
}
