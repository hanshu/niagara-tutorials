package com.tridiumap.niagaraDll;

import java.security.AccessController;
import java.security.PrivilegedAction;

public class DllLoader
{
    static
    {
        AccessController.doPrivileged((PrivilegedAction<Void>)() -> {
            System.loadLibrary("NiagaraLibraries");
            return null; // nothing to return
        });
    }

    public native String getMessage();
}