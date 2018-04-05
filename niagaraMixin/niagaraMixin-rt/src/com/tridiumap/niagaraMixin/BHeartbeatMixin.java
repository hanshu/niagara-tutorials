package com.tridiumap.niagaraMixin;

import javax.baja.nre.annotations.AgentOn;
import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Action;
import javax.baja.sys.BComponent;
import javax.baja.sys.BIMixIn;
import javax.baja.sys.Context;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

@NiagaraType(agent = {@AgentOn(types = {"niagaraDriver:NiagaraStation"})})
@NiagaraProperty(
    name = "timesIncremented",
    type = "int",
    flags = Flags.READONLY,
    defaultValue = "0"
)
@NiagaraAction(
    name = "increment"
)
public class BHeartbeatMixin extends BComponent implements BIMixIn {

  /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
  /*@ $com.tridiumap.niagaraMixin.BHeartbeatMixin(386003648)1.0$ @*/
  /* Generated Thu Apr 05 22:14:41 CST 2018 by Slot-o-Matic (c) Tridium, Inc. 2012 */

////////////////////////////////////////////////////////////////
// Property "timesIncremented"
////////////////////////////////////////////////////////////////

  /**
   * Slot for the {@code timesIncremented} property.
   *
   * @see #getTimesIncremented
   * @see #setTimesIncremented
   */
  public static final Property timesIncremented = newProperty(Flags.READONLY, 0, null);

  /**
   * Get the {@code timesIncremented} property.
   *
   * @see #timesIncremented
   */
  public int getTimesIncremented() {
    return getInt(timesIncremented);
  }

  /**
   * Set the {@code timesIncremented} property.
   *
   * @see #timesIncremented
   */
  public void setTimesIncremented(int v) {
    setInt(timesIncremented, v, null);
  }

////////////////////////////////////////////////////////////////
// Action "increment"
////////////////////////////////////////////////////////////////

  /**
   * Slot for the {@code increment} action.
   *
   * @see #increment()
   */
  public static final Action increment = newAction(0, null);

  /**
   * Invoke the {@code increment} action.
   *
   * @see #increment
   */
  public void increment() {
    invoke(increment, null, null);
  }

////////////////////////////////////////////////////////////////
// Type
////////////////////////////////////////////////////////////////

  @Override
  public Type getType() {
    return TYPE;
  }

  public static final Type TYPE = Sys.loadType(BHeartbeatMixin.class);

  /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  @Override
  public String getDisplayNameInParent(Context context) {
    return "Heartbeat";
  }

  public void doIncrement() {
    setTimesIncremented(getTimesIncremented() + 1);
  }
}
