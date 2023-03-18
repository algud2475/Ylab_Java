package datedmap;

import java.util.Date;
import java.util.HashMap;
import java.util.Set;

public class DatedMapImpl implements DatedMap {
    private HashMap<String, String> keyValueMap;
    private HashMap<String, Date> keyDateMap;

    public DatedMapImpl() {
        keyValueMap = new HashMap<>();
        keyDateMap = new HashMap<>();
    }

    public void put(String key, String value) {
        keyValueMap.put(key,value);
        keyDateMap.put(key,new Date());
    }

    public String get(String key) {
        return keyValueMap.get(key);
    }

    public boolean containsKey(String key) {
        return keyValueMap.containsKey(key);
    }

    public void remove(String key) {
        keyValueMap.remove(key);
        keyDateMap.remove(key);
    }

    public Set<String> keySet() {
        return keyValueMap.keySet();
    }

    public Date getKeyLastInsertionDate(String key) {
        return keyDateMap.get(key);
    }
}
