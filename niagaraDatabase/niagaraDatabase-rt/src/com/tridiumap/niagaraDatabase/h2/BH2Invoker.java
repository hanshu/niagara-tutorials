package com.tridiumap.niagaraDatabase.h2;

import com.tridiumap.niagaraDatabase.BBaseComponent;
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
public class BH2Invoker extends BBaseComponent {


  /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
  /*@ $com.tridiumap.niagaraDatabase.h2.BH2Invoker(2979906276)1.0$ @*/
  /* Generated Thu Apr 05 17:28:08 CST 2018 by Slot-o-Matic (c) Tridium, Inc. 2012 */

////////////////////////////////////////////////////////////////
// Type
////////////////////////////////////////////////////////////////

  @Override
  public Type getType() {
    return TYPE;
  }

  public static final Type TYPE = Sys.loadType(BH2Invoker.class);

  /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
  private static final Logger LOG = Logger.getLogger("tutorial.h2db");

  protected void onExecute() {
    String result = "";
//    String dbUrl = "file:^h2db";
//    BOrd h2Db = BOrd.make(dbUrl);
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
