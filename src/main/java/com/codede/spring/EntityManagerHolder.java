package com.codede.spring;

import javax.persistence.EntityManager;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class EntityManagerHolder {

    private final static EntityManager EM_PROXY = (EntityManager) Proxy.newProxyInstance(
            ExoEntityManagerInvocationHandler.class.getClassLoader(),
            new Class[] { EntityManager.class },
            new ExoEntityManagerInvocationHandler());

    public static EntityManager get() {
        return EM_PROXY;
    }

    private static class ExoEntityManagerInvocationHandler implements InvocationHandler {
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            boolean emStarted = false;
            EntityManager entityManager = null;

            EntityManagerService service = ExoContainerContext.getCurrentContainer().getComponentInstanceOfType(EntityManagerService.class);
            try {
                entityManager = service.getEntityManager();
                if (entityManager == null) {
                    LOG.debug("Injecting new EntityManager");
                    entityManager = service.createEntityManager();
                    emStarted = true;
                } else {
                    LOG.debug("Using existing EntityManager");
                }
                return method.invoke(entityManager, args);

            } finally {
                if (emStarted && (entityManager != null) && (!entityManager.getTransaction().isActive())) {
                    LOG.debug("Closing new EntityManager");
                    service.closeEntityManager();
                }
            }
        }
    }
}
