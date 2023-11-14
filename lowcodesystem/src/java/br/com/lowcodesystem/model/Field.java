package br.com.lowcodesystem.model;

import com.google.gson.annotations.Expose;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Maykon
 */
public class Field {

    public static final String SCOPE_APPLICATION = "SCOPE_APPLICATION";
    public static final String SCOPE_SESSION = "SCOPE_SESSION";
    public static final String SCOPE_REQUEST = "SCOPE_REQUEST";

    @Expose
    private String id;
    @Expose
    private String label = "Rotulo";
    @Expose
    private String type = "String";
    @Expose
    private String title;
    private String placeholder;
    private String component = "text";
    private String format;
    private String mask;
    private String defaultValue;
    @Expose
    private boolean required;
    @Expose
    private int size;
    private int width = 3;
    private List<String> options = new ArrayList();
    private String sqlOptions;
    @Expose
    private boolean hasUpdate = true;
    @Expose
    private boolean hasSearch = true;
    @Expose
    private boolean hasNew = true;
    @Expose
    private boolean hasView = true;
    private boolean submitOnchange = false;
    private String style;
    private String classStyle;
    private String scope;
    private String datasource;
    private boolean tbPaging = true;
    private boolean tbOrdering = true;
    private boolean tbInfo = true;
    private boolean tbCheck;
    private boolean tbSearching = true;

    public Field() {
    }

    public Field(String id) {
        this.id = id;
    }

    public String getId() {
        return id == null ? "" : id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabel() {
        return label == null ? "" : label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getType() {
        return type == null ? "" : type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title == null ? "" : title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPlaceholder() {
        return placeholder == null ? "" : placeholder;
    }

    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
    }

    public String getComponent() {
        return component == null ? "" : component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public String getDefaultValue() {
        return defaultValue == null ? "" : defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public String getSqlOptions() {
        return sqlOptions;
    }

    public void setSqlOptions(String sqlOptions) {
        this.sqlOptions = sqlOptions;
    }

    public boolean isHasUpdate() {
        return hasUpdate;
    }

    public void setHasUpdate(boolean hasUpdate) {
        this.hasUpdate = hasUpdate;
    }

    public boolean isHasSearch() {
        return hasSearch;
    }

    public void setHasSearch(boolean hasSearch) {
        this.hasSearch = hasSearch;
    }

    public boolean isHasNew() {
        return hasNew;
    }

    public void setHasNew(boolean hasNew) {
        this.hasNew = hasNew;
    }

    public boolean isHasView() {
        return hasView;
    }

    public void setHasView(boolean hasView) {
        this.hasView = hasView;
    }

    public boolean isSubmitOnchange() {
        return submitOnchange;
    }

    public void setSubmitOnchange(boolean submitOnchange) {
        this.submitOnchange = submitOnchange;
    }

    public String getStyle() {
        return style == null ? "" : style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getClassStyle() {
        return classStyle == null ? "" : classStyle;
    }

    public void setClassStyle(String classStyle) {
        this.classStyle = classStyle;
    }

    public String getScope() {
        if (scope == null) {
            scope = SCOPE_REQUEST;
        }
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getDatasource() {
        return datasource;
    }

    public void setDatasource(String datasource) {
        this.datasource = datasource;
    }

    public boolean isTbPaging() {
        return tbPaging;
    }

    public void setTbPaging(boolean tbPaging) {
        this.tbPaging = tbPaging;
    }

    public boolean isTbOrdering() {
        return tbOrdering;
    }

    public void setTbOrdering(boolean tbOrdering) {
        this.tbOrdering = tbOrdering;
    }

    public boolean isTbInfo() {
        return tbInfo;
    }

    public void setTbInfo(boolean tbInfo) {
        this.tbInfo = tbInfo;
    }

    public boolean isTbCheck() {
        return tbCheck;
    }

    public void setTbCheck(boolean tbCheck) {
        this.tbCheck = tbCheck;
    }

    public boolean isTbSearching() {
        return tbSearching;
    }

    public void setTbSearching(boolean tbSearching) {
        this.tbSearching = tbSearching;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getMask() {
        return mask;
    }

    public void setMask(String mask) {
        this.mask = mask;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Field other = (Field) obj;
        if ((this.id == null) ? (other.id != null) : !this.id.equals(other.id)) {
            return false;
        }
        return true;
    }

}
