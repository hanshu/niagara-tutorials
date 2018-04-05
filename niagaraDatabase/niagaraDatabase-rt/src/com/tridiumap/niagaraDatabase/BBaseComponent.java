package com.tridiumap.niagaraDatabase;

import java.io.File;
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

@NiagaraType
@NiagaraProperty(name = "result", type = "String", defaultValue = "",
    facets = {@Facet("BFacets.make(BFacets.MULTI_LINE,true)")}, flags = Flags.TRANSIENT
)
@NiagaraProperty(name = "faultCause", type = "String", defaultValue = "", flags = Flags.TRANSIENT
    | Flags.READONLY)
@NiagaraProperty(name = "stationShareHome", type = "String", defaultValue = "", flags = Flags.READONLY)
@NiagaraAction(name = "execute")
public class BBaseComponent extends BComponent {
/*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
/*@ $com.tridiumap.niagaraDatabase.BBaseComponent(1637556178)1.0$ @*/
/* Generated Thu Apr 05 17:24:54 CST 2018 by Slot-o-Matic (c) Tridium, Inc. 2012 */

////////////////////////////////////////////////////////////////
// Property "result"
////////////////////////////////////////////////////////////////
  
  /**
   * Slot for the {@code result} property.
   * @see #getResult
   * @see #setResult
   */
  public static final Property result = newProperty(Flags.TRANSIENT, "", BFacets
      .make(BFacets.MULTI_LINE,true));
  
  /**
   * Get the {@code result} property.
   * @see #result
   */
  public String getResult() { return getString(result); }
  
  /**
   * Set the {@code result} property.
   * @see #result
   */
  public void setResult(String v) { setString(result, v, null); }

////////////////////////////////////////////////////////////////
// Property "faultCause"
////////////////////////////////////////////////////////////////
  
  /**
   * Slot for the {@code faultCause} property.
   * @see #getFaultCause
   * @see #setFaultCause
   */
  public static final Property faultCause = newProperty(Flags.TRANSIENT | Flags.READONLY, "", null);
  
  /**
   * Get the {@code faultCause} property.
   * @see #faultCause
   */
  public String getFaultCause() { return getString(faultCause); }
  
  /**
   * Set the {@code faultCause} property.
   * @see #faultCause
   */
  public void setFaultCause(String v) { setString(faultCause, v, null); }

////////////////////////////////////////////////////////////////
// Property "stationShareHome"
////////////////////////////////////////////////////////////////
  
  /**
   * Slot for the {@code stationShareHome} property.
   * @see #getStationShareHome
   * @see #setStationShareHome
   */
  public static final Property stationShareHome = newProperty(Flags.READONLY, "", null);
  
  /**
   * Get the {@code stationShareHome} property.
   * @see #stationShareHome
   */
  public String getStationShareHome() { return getString(stationShareHome); }
  
  /**
   * Set the {@code stationShareHome} property.
   * @see #stationShareHome
   */
  public void setStationShareHome(String v) { setString(stationShareHome, v, null); }

////////////////////////////////////////////////////////////////
// Action "execute"
////////////////////////////////////////////////////////////////
  
  /**
   * Slot for the {@code execute} action.
   * @see #execute()
   */
  public static final Action execute = newAction(0, null);
  
  /**
   * Invoke the {@code execute} action.
   * @see #execute
   */
  public void execute() { invoke(execute, null, null); }

////////////////////////////////////////////////////////////////
// Type
////////////////////////////////////////////////////////////////
  
  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBaseComponent.class);

/*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  @Override
  public void started() throws Exception {
    super.started();

    File shareHome = Sys.getStationHome();
    setStationShareHome(shareHome.getAbsolutePath());
  }
}
