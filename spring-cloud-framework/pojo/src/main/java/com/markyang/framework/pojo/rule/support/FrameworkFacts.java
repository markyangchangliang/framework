package com.markyang.framework.pojo.rule.support;

import com.google.common.collect.Maps;
import org.jeasy.rules.api.Facts;
import org.springframework.lang.NonNull;

import java.util.*;

import static java.lang.String.format;

/**
 * 框架事实
 *
 * @author yangchangliang
 * @version 1
 */
public class FrameworkFacts extends Facts {

    private final Facts transientFacts;
    private final Facts beanFacts;

    public FrameworkFacts(Facts beanFacts) {
        this(beanFacts, new Facts());
    }

    public FrameworkFacts(Facts beanFacts, Facts transientFacts) {
        this.beanFacts = beanFacts;
        this.transientFacts = transientFacts;
    }

    /**
     * Put a fact in the working memory.
     * This will replace any fact having the same name.
     *
     * @param name fact name
     * @param fact object to put in the working memory
     * @return the previous value associated with <tt>name</tt>, or
     * <tt>null</tt> if there was no mapping for <tt>name</tt>.
     * (A <tt>null</tt> return can also indicate that the map
     * previously associated <tt>null</tt> with <tt>name</tt>.)
     */
    @Override
    public Object put(String name, Object fact) {
        return this.transientFacts.put(name, fact);
    }

    /**
     * Remove fact.
     *
     * @param name of fact to remove
     * @return the previous value associated with <tt>name</tt>, or
     * <tt>null</tt> if there was no mapping for <tt>name</tt>.
     * (A <tt>null</tt> return can also indicate that the map
     * previously associated <tt>null</tt> with <tt>name</tt>.)
     */
    @Override
    public Object remove(String name) {
        return this.transientFacts.remove(name);
    }

    /**
     * Get a fact by name.
     *
     * @param name of the fact
     * @return the fact having the given name, or null if there is no fact with the given name
     */
    @SuppressWarnings("unchecked")
    @Override
    public <T> T get(String name) {
        Object value = this.transientFacts.get(name);
        if (Objects.isNull(value)) {
            // try to fetch from beanFacts
            value = this.beanFacts.get(name);
        }
        return (T) value;
    }

    /**
     * Return facts as a map.
     *
     * @return the current facts as a {@link HashMap}
     */
    @Override
    public Map<String, Object> asMap() {
        HashMap<String, Object> map = Maps.newHashMapWithExpectedSize((this.beanFacts.asMap().size() + this.transientFacts.asMap().size()) * 2);
        map.putAll(this.beanFacts.asMap());
        map.putAll(this.transientFacts.asMap());
        return map;
    }

    @NonNull
    @Override
    public Iterator<Map.Entry<String, Object>> iterator() {
        return this.asMap().entrySet().iterator();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("[");
        List<Map.Entry<String, Object>> entries = new ArrayList<>(this.asMap().entrySet());
        for (int i = 0; i < entries.size(); i++) {
            Map.Entry<String, Object> entry = entries.get(i);
            stringBuilder.append(format(" { %s : %s } ", entry.getKey(), entry.getValue()));
            if (i < entries.size() - 1) {
                stringBuilder.append(",");
            }
        }
        stringBuilder.append("]");
        return  stringBuilder.toString();
    }
}
