package com.hongshi.intern;

import java.sql.*;
import java.util.Scanner;

public class MySqlConnector {
    public static void connectMySql() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("start connecting MySQL.");
        Connection conn;

        System.out.print("Plz enter host: ");
        String host = scanner.nextLine();
        System.out.print("Plz enter port: ");
        String port = scanner.nextLine();
        System.out.print("Plz enter database name: ");
        String database = scanner.nextLine();
        String url = "jdbc:mysql://" + host + ":" + port + "/" + database + "?autoReconnect=true&useSSL=false";
        String driver = "com.mysql.cj.jdbc.Driver";

        System.out.print("Plz enter user name: ");
        String userName = scanner.nextLine();
        System.out.print("Plz enter password: ");
        String password = scanner.nextLine();

        Statement stmt = null;
        ResultSet rs = null;

        try {
            //a.导入驱动，加载具体的驱动类
            Class.forName(driver);
            //b.与数据库建立连接
            conn = DriverManager.getConnection(url, userName, password);
            //c.发送sql语句，执行sql语句
            stmt = conn.createStatement();

            //task1
            System.out.println("task 1");
            String sql = "SELECT * FROM t_area WHERE area_code = '310114103035'";
            //查询的时候用executeQuery
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt("id");
                String area_code = rs.getString("area_code");
                String area_name = rs.getString("area_name");
                int level = rs.getInt("level");
                String parent_code = rs.getString("parent_code");
                String code2 = rs.getString("code2");
                String ctime = rs.getString("ctime");
                printRow(id, area_code, area_name, level, parent_code, code2, ctime);
            }

            //task2
            System.out.println("task 2");
            sql = "SELECT * FROM t_area WHERE area_name LIKE '%社区%'";
            rs = stmt.executeQuery(sql);
            int numCommunity = 0;
            while (rs.next()) {
                ++numCommunity;
            }
            System.out.println(numCommunity);

            //task3
            System.out.println("task 3");
            sql = "INSERT INTO t_area (area_code, area_name, level, parent_code, code2, ctime) VALUES ('55555', 'test', 0, '22222', '0', '2020-06-15')";
            //增删改时用executeUpdate
            int count = stmt.executeUpdate(sql);
            if (count > 0) {
                System.out.println("success！");
            }

            //task4
            System.out.println("task 4");
            sql = "UPDATE t_area SET area_name = '深圳虹识测试' WHERE area_name = 'test'";
            count = stmt.executeUpdate(sql);
            if (count > 0) {
                System.out.println("success！");
            }

            //task5
            System.out.println("task 5");
            sql = "DELETE FROM t_area WHERE area_name = '深圳虹识测试'";
            count = stmt.executeUpdate(sql);
            if (count > 0) {
                System.out.println("success！");
            }

            // close
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException sqlEx) {
                } // ignore
            }

            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException sqlEx) {
                } // ignore
            }
        }
    }

    public static void printRow(int id, String area_code, String area_name, int level, String parent_code, String code2, String ctime) {
        System.out.println("id = " + id + ", 地区码 = " + area_code + ", 地区名称 = " + area_name + ", 级别 = " + level + ", 上级码 = " + parent_code + ", 城乡区别 = " + code2 + ", 创建时间 = " + ctime);
    }
}
