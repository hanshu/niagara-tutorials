package com.tridiumap.niagaraDatabase.derby;

import com.tridiumap.niagaraDatabase.BBaseComponent;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import org.apache.derby.jdbc.EmbeddedDriver;

@NiagaraType
public class BDerbyInvoker extends BBaseComponent {


  /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
  /*@ $com.tridiumap.niagaraDatabase.derby.BDerbyInvoker(2979906276)1.0$ @*/
  /* Generated Thu Apr 05 17:26:54 CST 2018 by Slot-o-Matic (c) Tridium, Inc. 2012 */

////////////////////////////////////////////////////////////////
// Type
////////////////////////////////////////////////////////////////

  @Override
  public Type getType() {
    return TYPE;
  }

  public static final Type TYPE = Sys.loadType(BDerbyInvoker.class);

  /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
  private static final Logger LOG = Logger.getLogger("tutorial.derby");

  protected void onExecute() {
    Connection connection = null;
    String url = "jdbc:derby:testdb;user=USER12";

    try {
      AccessController.doPrivileged((PrivilegedAction<Void>) () -> {
        System.setProperty("derby.system.home", getStationShareHome());

        try {
          DriverManager.registerDriver(new EmbeddedDriver());
        } catch (SQLException e) {
          setFaultCause(e.getMessage());
        }
//        DriverManager.setLogWriter(new java.io.PrintWriter(System.out));
        return null; // nothing to return
      });

      connection = DriverManager.getConnection(url);
      try (Statement st = connection.createStatement()) {

        int sum;
        try (ResultSet rs = st.executeQuery("SELECT count(*) FROM USER12.CARS")) {
          sum = 0;
          if (rs.next()) {
            sum = rs.getInt(1);
          }
        }

        setResult("Records: " + sum);
//        while (rs.next()) {
//            System.out.print(rs.getInt(1));
//            System.out.print(" ");
//            System.out.print(rs.getString(2));
//            System.out.print(" ");
//            System.out.println(rs.getString(3));
//        }
      }
      DriverManager.getConnection("jdbc:derby:;shutdown=true");
    } catch (SQLException ex) {
      if (((ex.getErrorCode() == 50000) && ("XJ015".equals(ex.getSQLState())))) {
        setFaultCause("Derby shut down normally");
      } else {
        setFaultCause(ex.getMessage());
      }
    } finally {
      try {
        if (connection != null) {
          connection.close();
        }
      } catch (SQLException ex) {
        setFaultCause(ex.getMessage());
      }
    }
  }
}
