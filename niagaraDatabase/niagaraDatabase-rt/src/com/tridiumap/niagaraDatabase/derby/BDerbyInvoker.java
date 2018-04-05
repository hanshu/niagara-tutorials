package com.tridiumap.niagaraDatabase.derby;

import java.io.File;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;
import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Action;
import javax.baja.sys.BComponent;
import javax.baja.sys.BFacets;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import org.apache.derby.jdbc.AutoloadedDriver;
import org.apache.derby.jdbc.EmbeddedDriver;

@NiagaraType
@NiagaraProperty(name = "result", type = "String", defaultValue = "",
    facets = {@Facet("BFacets.make(BFacets.MULTI_LINE,true)")}, flags = Flags.TRANSIENT
)
@NiagaraProperty(name = "faultCause", type = "String", defaultValue = "", flags = Flags.TRANSIENT
    | Flags.READONLY)
@NiagaraProperty(name = "stationShareHome", type = "String", defaultValue = "", flags = Flags.READONLY)
@NiagaraAction(name = "execute")
public class BDerbyInvoker extends BComponent {

  /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
  /*@ $com.tridiumap.niagaraDerby.BDerbyInvoker(1637556178)1.0$ @*/
  /* Generated Thu Apr 05 09:25:32 CST 2018 by Slot-o-Matic (c) Tridium, Inc. 2012 */

////////////////////////////////////////////////////////////////
// Property "result"
////////////////////////////////////////////////////////////////

  /**
   * Slot for the {@code result} property.
   *
   * @see #getResult
   * @see #setResult
   */
  public static final Property result = newProperty(Flags.TRANSIENT, "",
      BFacets.make(BFacets.MULTI_LINE, true));

  /**
   * Get the {@code result} property.
   *
   * @see #result
   */
  public String getResult() {
    return getString(result);
  }

  /**
   * Set the {@code result} property.
   *
   * @see #result
   */
  public void setResult(String v) {
    setString(result, v, null);
  }

////////////////////////////////////////////////////////////////
// Property "faultCause"
////////////////////////////////////////////////////////////////

  /**
   * Slot for the {@code faultCause} property.
   *
   * @see #getFaultCause
   * @see #setFaultCause
   */
  public static final Property faultCause = newProperty(Flags.TRANSIENT | Flags.READONLY, "", null);

  /**
   * Get the {@code faultCause} property.
   *
   * @see #faultCause
   */
  public String getFaultCause() {
    return getString(faultCause);
  }

  /**
   * Set the {@code faultCause} property.
   *
   * @see #faultCause
   */
  public void setFaultCause(String v) {
    setString(faultCause, v, null);
  }

////////////////////////////////////////////////////////////////
// Property "stationShareHome"
////////////////////////////////////////////////////////////////

  /**
   * Slot for the {@code stationShareHome} property.
   *
   * @see #getStationShareHome
   * @see #setStationShareHome
   */
  public static final Property stationShareHome = newProperty(Flags.READONLY, "", null);

  /**
   * Get the {@code stationShareHome} property.
   *
   * @see #stationShareHome
   */
  public String getStationShareHome() {
    return getString(stationShareHome);
  }

  /**
   * Set the {@code stationShareHome} property.
   *
   * @see #stationShareHome
   */
  public void setStationShareHome(String v) {
    setString(stationShareHome, v, null);
  }

////////////////////////////////////////////////////////////////
// Action "execute"
////////////////////////////////////////////////////////////////

  /**
   * Slot for the {@code execute} action.
   *
   * @see #execute()
   */
  public static final Action execute = newAction(0, null);

  /**
   * Invoke the {@code execute} action.
   *
   * @see #execute
   */
  public void execute() {
    invoke(execute, null, null);
  }

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

  @Override
  public void started() throws Exception {
    super.started();

    File shareHome = Sys.getStationHome();
    setStationShareHome(shareHome.getAbsolutePath());
  }

  public void doExecute() {
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
