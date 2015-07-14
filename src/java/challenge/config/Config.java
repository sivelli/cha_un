package challenge.config;

import java.util.HashMap;
import java.util.Map;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class Config {
    private static Config instance;
    private final Map<String, Object> mapConfig;
    private final Map<String, Object> mapDefault;
    
    private Config(Map<String, Object> mapDefault) {
        this.mapConfig = new HashMap<>();
        this.mapDefault = mapDefault;
    }
    
    public static void createInstance(Map<String, Object> mapDefault) {
        instance = new Config(mapDefault);
    }

    public static Config getInstance() {
        return instance;
    }
    
    private Object getEnvironmentJNDI(String name) {
        Object val = null;
        Context initCtx;
        try {
                initCtx = new InitialContext();
        Context ctx = (Context) initCtx.lookup("java:/comp/env");
        val = ctx.lookup(name);
        } catch (NamingException e1) {
        }
        return val;
    }
    
    public Object getVar(String name) {
        Object val = mapConfig.get(name);
        if (val == null && !mapConfig.containsKey(name)) {
            val = getEnvironmentJNDI(name);
            mapConfig.put(name, val);
        }
        if (val == null && mapDefault != null && mapDefault.containsKey(name)) {
            val = mapDefault.get(name);
            
        }
        return val;
    }

    public String getVarToString(String name) {
        Object val = getVar(name);
        return (val == null? null : val.toString());
    }
}
