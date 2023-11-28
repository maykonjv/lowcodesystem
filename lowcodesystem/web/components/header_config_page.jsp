<%@page import="br.com.lowcodesystem.ctrl.Render"%>
<%@page import="br.com.lowcodesystem.model.Page"%>
<%if (session.getAttribute("dev") != null && (Boolean) session.getAttribute("dev")) {%>
<% Page p = (Page) request.getAttribute("p");%>
<%  if (p != null) {%>
<div style="display: flex; flex: 1; justify-content: end">
    <a href="builder?page=<%=request.getParameter("page")%>&action=<%=request.getAttribute("action")%>&session=field-add" class="btn btn-primary lcs-settings mx-3" title="Adicionar novo campo">
        <i class="fa fa-plus-square-o"></i>
    </a>
    <a href="#" class="btn btn-primary lcs-settings" title="Configuração da página"  data-coreui-toggle="modal" data-coreui-target="#modalPage">
        <i class="fa fa-cog"></i>
    </a>
    <a href="#" class="btn btn-primary lcs-settings mx-3" title="Javascript local" data-coreui-toggle="modal" data-coreui-target="#modalJsLocal">
        <i class="fa fa-code"></i>
    </a>
    <a href="#" class="btn btn-primary lcs-settings" title="Javascript servidor" data-coreui-toggle="modal" data-coreui-target="#modalJsServer">
        <i class="fa fa-code"></i>
    </a>
    <a href="render?page=<%= p.getId()%>&action=<%=Render.ACTION_ALL%>" class="btn btn-primary lcs-settings mx-3" title="Listar todos os campos">
        <i class="fa fa-th"></i>
    </a>
    <a href="#" class="btn btn-primary lcs-settings btn-form-remove" title="Excluir página">
        <i class="fa fa-trash"></i>
    </a>
</div>
<%  }%>
<%}%>