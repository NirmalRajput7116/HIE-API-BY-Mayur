package com.cellbeans.hspa.mstaction;

import java.util.List;

public class treestructor {

    private String label;
    private String data;
    private String expandedIcon;
    private String collapsedIcon;
    private List<treedata> children;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getExpandedIcon() {
        return expandedIcon;
    }

    public void setExpandedIcon(String expandedIcon) {
        this.expandedIcon = expandedIcon;
    }

    public String getCollapsedIcon() {
        return collapsedIcon;
    }

    public void setCollapsedIcon(String collapsedIcon) {
        this.collapsedIcon = collapsedIcon;
    }

    public List<treedata> getChildren() {
        return children;
    }

    public void setChildren(List<treedata> children) {
        this.children = children;
    }

}
