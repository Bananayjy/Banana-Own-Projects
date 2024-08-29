package com.example.core;

import cn.hutool.core.collection.CollectionUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 元素节点
 * @author banana
 * @create 2024-08-29 21:19
 */
public class ElementNode {

    // 元素名称
    private String value;

    // 元素命名空间前缀
    private String namespace;

    // 元素内容
    private String content;

    // 子元素
    private List<ElementNode> children;

    // 元素结点创建构造器
    public ElementNode(String value, String namespace) {
        this.value = value;
        this.namespace = namespace;
        this.content = null;
        children = new ArrayList<>();
    }

    // 元素结点创建构造器
    public ElementNode(String value, String namespace, String content) {
        this.value = value;
        this.namespace = namespace;
        this.content = content;
        children = new ArrayList<>();
    }

    public void addChildElementNode(ElementNode elementNode){
        this.children.add(elementNode);
    }

    public List<ElementNode> getChildren() {
        return children;
    }

    public String getValue() {
        return value;
    }

    public String getContent() {
        return content;
    }

    public String getNamespace() {
        return namespace;
    }

    public Boolean ifChildren(){
        return CollectionUtil.isNotEmpty(this.children);
    }

}
