package com.tridiumap.niagaraDatabase.h2;

import java.util.logging.Logger;
import javax.baja.nre.annotations.Facet;
import org.h2.jdbcx.JdbcConnectionPool;

import javax.baja.naming.BOrd;
import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

@NiagaraType
@NiagaraProperty(name = "result", type = "String", defaultValue = "",
    facets = {@Facet("BFacets.make(BFacets.MULTI_LINE,true)")}, flags = Flags.TRANSIENT
)
@NiagaraAction(name = "execute")
public class BH2Invoker extends BComponent {

  /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
  /*@ $com.tridiumap.niagaraDatabase.h2.BH2Invoker(1693166137)1.0$ @*/
  /* Generated Thu Apr 05 15:37:02 CST 2018 by Slot-o-Matic (c) Tridium, Inc. 2012 */

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

  public static final Type TYPE = Sys.loadType(BH2Invoker.class);

  /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
  private static final Logger LOG = Logger.getLogger("tutorial.h2");

  public void doExecute() {
    String result = "";

    String dbUrl = "file:^h2db";
    BOrd h2Db = BOrd.make(dbUrl);

//        BIFile file = (BIFile)h2Db.get();
//        FilePath filePath = file.getFilePath();
//        File localFile = BFileSystem.INSTANCE.pathToLocalFile(filePath);
    File shareHome = Sys.getStationHome();
//        System.out.println(localFile);
    //setResult(localFile.getPath());
    Path url = Paths.get(shareHome.getPath(), "h2db");
    try {
      JdbcConnectionPool cp = JdbcConnectionPool
          .create("jdbc:h2:file:" + url.toString(), "hans", "huyaohui");
//            JdbcConnectionPool cp = JdbcConnectionPool.create("jdbc:h2:file:~/h2db","hans","huyaohui");
      Connection conn = cp.getConnection();

      try (Statement stmt = conn.createStatement(); ResultSet rs = stmt
          .executeQuery("select * from TEST");) {
        StringBuilder sb = new StringBuilder();
        while (rs.next()) {
          result = String.format("ID: %d, NAME: %s", rs.getInt("ID"), rs.getString("NAME"));
          LOG.info(result);
          sb.append(result).append("\n");
        }

        setResult(sb.toString());
      }

      conn.close();
      cp.dispose();
    } catch (Exception ex) {
      LOG.severe(ex.getMessage());
    }

  }
}
