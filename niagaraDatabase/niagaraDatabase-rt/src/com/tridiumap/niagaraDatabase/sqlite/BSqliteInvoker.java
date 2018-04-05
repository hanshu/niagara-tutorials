package com.tridiumap.niagaraDatabase.sqlite;

import com.tridiumap.niagaraDatabase.BBaseComponent;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Logger;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import org.sqlite.JDBC;

@NiagaraType
public class BSqliteInvoker extends BBaseComponent {

  /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
  /*@ $com.tridiumap.niagaraDatabase.sqlite.BSqliteInvoker(2979906276)1.0$ @*/
  /* Generated Thu Apr 05 17:28:08 CST 2018 by Slot-o-Matic (c) Tridium, Inc. 2012 */

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

  protected void onExecute() {

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
