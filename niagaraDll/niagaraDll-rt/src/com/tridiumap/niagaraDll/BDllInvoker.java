package com.tridiumap.niagaraDll;


import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

@NiagaraType
@NiagaraProperty(name="message",type="String",defaultValue="")
@NiagaraAction(name="execute")
public class BDllInvoker extends BComponent {
/*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
/*@ $com.tridiumap.niagaraDll.BDllInvoker(969444608)1.0$ @*/
/* Generated Fri Mar 30 16:29:03 CST 2018 by Slot-o-Matic (c) Tridium, Inc. 2012 */

////////////////////////////////////////////////////////////////
// Property "message"
////////////////////////////////////////////////////////////////
  
  /**
   * Slot for the {@code message} property.
   * @see #getMessage
   * @see #setMessage
   */
  public static final Property message = newProperty(0, "", null);
  
  /**
   * Get the {@code message} property.
   * @see #message
   */
  public String getMessage() { return getString(message); }
  
  /**
   * Set the {@code message} property.
   * @see #message
   */
  public void setMessage(String v) { setString(message, v, null); }

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
  public static final Type TYPE = Sys.loadType(BDllInvoker.class);

/*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

    public void doExecute() {
        // load libraries
        DllLoader dll = new DllLoader();
        String message = dll.getMessage();

        setMessage(message);
    }
}
