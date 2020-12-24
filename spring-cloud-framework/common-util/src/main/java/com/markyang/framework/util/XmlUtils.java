package com.markyang.framework.util;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.thoughtworks.xstream.XStream;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.collections.CollectionUtils;
import org.dom4j.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
public final class XmlUtils {

    /**
     * 对象转XML
     * @param object 对象
     * @return XML字符串
     */
    public static String toXmlFromBean(Object object) {
        XStream xStream = new XStream();
        xStream.processAnnotations(object.getClass());
        xStream.autodetectAnnotations(true);
        return xStream.toXML(object);
    }

    /**
     * XML转对象
     * @param xml XML字符串
     * @param clazz 类
     * @param <T> 泛型
     * @return 对象
     */
    @SuppressWarnings("unchecked")
    public static <T> T toBeanFromXml(String xml, Class<T> clazz) {
        XStream xStream = new XStream();
        xStream.processAnnotations(clazz);
        xStream.autodetectAnnotations(true);
        T object;
        try {
            object = clazz.newInstance();
        } catch (IllegalAccessException | InstantiationException e) {
            log.error("XML【{}】转对象【{}】出现错误：", xml, clazz.getSimpleName(), e);
            return null;
        }
        return (T) xStream.fromXML(xml, object);
    }

    /**
     * 将XML转为Map对象
     * @param xml xml字符串
     * @return Map对象
     */
    public static Map<String, Object> xmlToMap(String xml) {
        Map<String, Object> data = Maps.newHashMap();
        Document document;
        try {
            // 解析xml
            document = DocumentHelper.parseText(xml);
        } catch (DocumentException e) {
            e.printStackTrace();
            return null;
        }
        Element rootElement = document.getRootElement();
        if (rootElement.isTextOnly()) {
            // 只有文本，直接设值
            data.put(rootElement.getName(), rootElement.getTextTrim());
            return data;
        }
        Map<String, Object> child = Maps.newHashMap();
        // 递归调用
        extractKv(rootElement, child);
        data.put(rootElement.getName(), child);
        return data;
    }

    /**
     * 将Map对象转为XML
     * @param map map对象
     * @return xml字符串
     */
    public static String mapToXml(Map<String, Object> map) {
        Document document = DocumentHelper.createDocument();
        handleElements(document, map);
        return document.asXML();
    }

    /**
     * 处理XML节点
     * @param branch 节点对象
     * @param map Map对象
     */
    @SuppressWarnings("unchecked")
    private static void handleElements(Branch branch, Map<String, Object> map) {
        map.forEach((key, val) -> {
            if (Objects.isNull(val)) {
                return;
            }
            if (List.class.isAssignableFrom(val.getClass())) {
                // List类型
                ((List<Object>) val).parallelStream().forEach(item -> {
                    if (Map.class.isAssignableFrom(item.getClass())) {
                        branch.add(createElement(key, (Map<String, Object>) item));
                    } else {
                        branch.addElement(key).addText(ConvertUtils.convert(val));
                    }
                });
            } else if (Map.class.isAssignableFrom(val.getClass())) {
                branch.add(createElement(key, (Map<String, Object>) val));
            } else {
                // 其他类型
                branch.addElement(key).addText(ConvertUtils.convert(val));
            }
        });
    }

    /**
     * 创建XML节点
     * @param name 节点名称
     * @param map map对象
     * @return 节点对象
     */
    private static Element createElement(String name, Map<String, Object> map) {
        Element element = DocumentHelper.createElement(name);
        handleElements(element, map);
        return element;
    }

    /**
     * 抽取一个节点的信息 键值对
     * @param element 节点对象
     * @param map Map对象
     */
    private static void extractKv(Element element, Map<String, Object> map) {
        List<Element> elements = element.elements();
        if (CollectionUtils.isEmpty(elements)) {
            // 直接写入文本信息
            map.put(element.getName(), element.getTextTrim());
            return;
        }
        // 多节点，循环处理
        for (Element elem : elements) {
            if (elem.isTextOnly()) {
                putKv(map, elem.getName(), elem.getTextTrim());
                continue;
            }
            Map<String, Object> child = Maps.newHashMap();
            extractKv(elem, child);
            putKv(map, elem.getName(), child);
        }
    }

    /**
     * 将值放到Map中
     * @param map Map对象
     * @param key 键
     * @param value 值
     */
    @SuppressWarnings("unchecked")
    private static void putKv(Map<String, Object> map, String key, Object value) {
        if (map.containsKey(key)) {
            Object val = map.get(key);
            // 如果是List则追加
            if (val instanceof List) {
                ((List<Object>) val).add(value);
                return;
            }
            // 否则创建新的List
            map.put(key, Lists.newArrayList(
                val,
                value
            ));
            return;
        }
        map.put(key, value);
    }
}
