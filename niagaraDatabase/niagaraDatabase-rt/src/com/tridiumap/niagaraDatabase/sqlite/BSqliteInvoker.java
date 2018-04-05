package com.tridiumap.niagaraDatabase.sqlite;

import java.io.File;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.atomic.AtomicReference;
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
import org.sqlite.JDBC;

@NiagaraType
@NiagaraProperty(name = "result", type = "String", defaultValue = "",
    facets = {@Facet("BFacets.make(BFacets.MULTI_LINE,true)")}, flags = Flags.TRANSIENT
)
@NiagaraProperty(name = "faultCause", type = "String", defaultValue = "", flags = Flags.TRANSIENT
    | Flags.READONLY)
@NiagaraProperty(name = "stationShareHome", type = "String", defaultValue = "", flags = Flags.READONLY)
@NiagaraAction(name = "execute")
public class BSqliteInvoker extends BComponent {
  /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
  /*@ $com.tridiumap.niagaraDatabase.sqlite.BSqliteInvoker(1637556178)1.0$ @*/
  /* Generated Thu Apr 05 15:18:26 CST 2018 by Slot-o-Matic (c) Tridium, Inc. 2012 */

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

  public static final Type TYPE = Sys.loadType(BSqliteInvoker.class);

  /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
  private static final Logger LOG = Logger.getLogger("tutorial.sqlite");

  @Override
  public void started() throws Exception {
    super.started();

    File shareHome = Sys.getStationHome();
    setStationShareHome(shareHome.getAbsolutePath());
    LOG.fine("niagara home: " + Sys.getNiagaraHome().getAbsolutePath());
  }

  public void doExecute() {

    AccessController.doPrivileged((PrivilegedAction<Void>) () -> {
      System.setProperty("org.sqlite.tmpdir", getStationShareHome());

      System.setProperty("org.sqlite.lib.path", getStationShareHome());
      System.setProperty("org.sqlite.lib.name", "sqlitejdbc.dll");

      try {
        LOG.fine("begin to register sqlite jdbc driver...");
        DriverManager.registerDriver(new JDBC());
      } catch (SQLException e) {
        setFaultCause(e.getMessage());
      }
      return null; // nothing to return
    });

    AtomicReference<Connection> connection = new AtomicReference<>();
    AccessController.doPrivileged((PrivilegedAction<Void>) () -> {
      try {
        // create a database connection
        connection.set(
            DriverManager.getConnection("jdbc:sqlite:" + getStationShareHome() + "/sample.db"));

        try (Statement statement = connection.get().createStatement()) {
          statement.setQueryTimeout(30);  // set timeout to 30 sec.

          statement.executeUpdate("drop table if exists person");
          statement.executeUpdate("create table person (id integer, name string)");
          statement.executeUpdate("insert into person values(1, 'tridium')");
          statement.executeUpdate("insert into person values(2, 'niagara')");

          StringBuilder sb = new StringBuilder();
          try (ResultSet rs = statement.executeQuery("select * from person")) {
            while (rs.next()) {
              // read the result set
              sb.append("id = " + rs.getInt("id"))
                  .append(", name = " + rs.getString("name"))
                  .append("\n");
            }
          }

          setResult(sb.toString());
        }

        return null;
      } catch (SQLException e) {
        // if the error message is "out of memory",
        // it probably means no database file is found
        LOG.severe(e.getMessage());
        setFaultCause(e.getMessage());
      } finally {
        try {

          if (connection.get() != null) {
            connection.get().close();
          }
        } catch (SQLException e) {
          // connection close failed.
          setFaultCause(e.getMessage());
          LOG.severe(e.getMessage());
        }
      }

      return null;
    });
  }
}
