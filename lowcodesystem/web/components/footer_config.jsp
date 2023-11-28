<%@page import="br.com.lowcodesystem.model.Page"%>
<%if (session.getAttribute("dev") != null && (Boolean) session.getAttribute("dev")) {%>
<script type="text/javascript">
    <%= request.getAttribute("p") != null ? ((Page) request.getAttribute("p")).getJavascript() : ""%>
</script>
<div>
    <%@include file="modal_login.jsp" %>
    <%@include file="modal_project.jsp" %>
    <%@include file="modal_datasource.jsp" %>
    <%@include file="modal_javascript_global.jsp" %>
    <%@include file="modal_css_global.jsp" %>
    <%@include file="modal_menu.jsp" %>
    <% Page p = (Page) request.getAttribute("p");%>
    <% if (p != null) {
    %>
    <%@include file="modal_page.jsp" %>
    <%@include file="modal_javascript_local.jsp" %>
    <%@include file="modal_javascript_server.jsp" %>
    <%@include file="modal_field.jsp" %>
    <%@include file="modal_sql_activate.jsp" %>
    <%@include file="modal_sql_delete.jsp" %>
    <%@include file="modal_sql_edit.jsp" %>
    <%@include file="modal_sql_export.jsp" %>
    <%@include file="modal_sql_save.jsp" %>
    <%@include file="modal_sql_search.jsp" %>
    <%@include file="modal_sql_view.jsp" %>
    <%@include file="modal_backend_action.jsp" %>
    <%}%>
</div>
<%}%>