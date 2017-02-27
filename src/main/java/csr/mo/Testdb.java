package csr.mo;

import java.sql.*;

public class Testdb {

    Connection conn;

    public Testdb(String db_file_name_prefix) throws Exception {
        try {
            Class.forName("org.hsqldb.jdbcDriver");
        } catch (Exception e) {
            System.out.println("ERROR: failed to load HSQLDB JDBC driver.");
            e.printStackTrace();
            return;
        }
        conn = DriverManager.getConnection("jdbc:hsqldb:mem:" + db_file_name_prefix, "sa", "");
    }

    public static void dump(ResultSet rs) throws SQLException {
        ResultSetMetaData meta = rs.getMetaData();
        int colmax = meta.getColumnCount();
        int i;
        Object o = null;
        for (; rs.next(); ) {
            for (i = 0; i < colmax; ++i) {
                o = rs.getObject(i + 1);    // Is SQL the first column is indexed
                System.out.print(o.toString() + " ");
            }
            System.out.println(" ");
        }
    }

    public void shutdown() throws SQLException {

        Statement st = conn.createStatement();
        st.execute("SHUTDOWN");
        conn.close();
    }

    public synchronized void update(String expression) throws SQLException {

        Statement st = null;

        st = conn.createStatement();

        int i = st.executeUpdate(expression);

        if (i == -1) {
            System.out.println("db error : " + expression);
        }

        st.close();
    }

    public synchronized void query(String expression) throws SQLException {

        Statement st = null;
        ResultSet rs = null;

        st = conn.createStatement();
        rs = st.executeQuery(expression);
        dump(rs);
        st.close();
    }
}
