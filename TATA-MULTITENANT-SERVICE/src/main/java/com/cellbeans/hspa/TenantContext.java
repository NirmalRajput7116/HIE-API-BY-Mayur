package com.cellbeans.hspa;

public class TenantContext {
    private static ThreadLocal<Object> currentTenant = new ThreadLocal<>();

    public static void setCurrentTenant(Object tenant) {
        synchronized (TenantContext.class) {
            currentTenant.set(tenant);
        }
    }

    public static Object getCurrentTenant() {
        return currentTenant.get();
    }

}
