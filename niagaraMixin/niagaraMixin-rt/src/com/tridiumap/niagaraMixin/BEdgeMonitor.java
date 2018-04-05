package com.tridiumap.niagaraMixin;

import javax.baja.naming.BOrd;
import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BAbstractService;
import javax.baja.sys.BFacets;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

@NiagaraType
@NiagaraProperty(
    name = "statisticsFile",
    type = "baja:Ord",
    defaultValue = "BOrd.make(\"file:^traffic/trafficStats.properties\")",
    facets = {@Facet(name = "BFacets.TARGET_TYPE", value = "\"baja:IFile\"")}
)
public class BEdgeMonitor extends BAbstractService {
  /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
  /*@ $com.tridiumap.niagaraMixin.BEdgeMonitor(3753523009)1.0$ @*/
  /* Generated Thu Apr 05 22:19:45 CST 2018 by Slot-o-Matic (c) Tridium, Inc. 2012 */

////////////////////////////////////////////////////////////////
// Property "statisticsFile"
////////////////////////////////////////////////////////////////

  /**
   * Slot for the {@code statisticsFile} property.
   *
   * @see #getStatisticsFile
   * @see #setStatisticsFile
   */
  public static final Property statisticsFile = newProperty(0,
      BOrd.make("file:^traffic/trafficStats.properties"),
      BFacets.make(BFacets.TARGET_TYPE, "baja:IFile"));

  /**
   * Get the {@code statisticsFile} property.
   *
   * @see #statisticsFile
   */
  public BOrd getStatisticsFile() {
    return (BOrd) get(statisticsFile);
  }

  /**
   * Set the {@code statisticsFile} property.
   *
   * @see #statisticsFile
   */
  public void setStatisticsFile(BOrd v) {
    set(statisticsFile, v, null);
  }

////////////////////////////////////////////////////////////////
// Type
////////////////////////////////////////////////////////////////

  @Override
  public Type getType() {
    return TYPE;
  }

  public static final Type TYPE = Sys.loadType(BEdgeMonitor.class);

  /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
  private static Type[] serviceTypes = new Type[]{TYPE};

  @Override
  public Type[] getServiceTypes() {
    return serviceTypes;
  }

  @Override
  public void serviceStarted() throws Exception {
    super.serviceStarted();
    getComponentSpace().enableMixIn(BHeartbeatMixin.TYPE);
  }

  @Override
  public void serviceStopped() throws Exception {
    super.serviceStopped();
    getComponentSpace().disableMixIn(BHeartbeatMixin.TYPE);
  }
}
