<%@page import="br.com.lowcodesystem.ctrl.Render"%>
<%@page import="br.com.lowcodesystem.model.Page"%>
<%if (session.getAttribute("dev") != null && (Boolean) session.getAttribute("dev")) {%>
<button class="btn btn-primary lcs-settings mx-3" title="Esconder configurações DEV" onclick="$('.lcs-settings').hide(); $('.lcs-show-settings').show();">
    <i class="fa fa-eye-slash"></i>
</button>
<button class="btn btn-primary lcs-show-settings mx-3" title="Mostrar configurações DEV" onclick="$('.lcs-settings').show(); $('.lcs-show-settings').hide();" style="display: none;">
    <i class="fa fa-eye"></i>
</button>
<a href="#" class="btn btn-primary lcs-settings" title="Configurações de datasource" data-coreui-toggle="modal" data-coreui-target="#modalDatasource">
    <i class="fa fa-database"></i>
</a>                        
<a href="#" class="btn btn-primary lcs-settings mx-3" title="Configurações do projeto" data-coreui-toggle="modal" data-coreui-target="#modalProject">
    <i class="fa fa-cog"></i>
</a>                        
<a href="#" class="btn btn-primary lcs-settings" title="Configurações de login" data-coreui-toggle="modal" data-coreui-target="#modalLogin">
    <i class="fa fa-user"></i>
</a>
<a href="#" class="btn btn-primary lcs-settings mx-3" title="Configurações de CSS" data-coreui-toggle="modal" data-coreui-target="#modalCssGlobal">
    <i class="fa fa-css3"></i>
</a> 
<a href="#" class="btn btn-primary lcs-settings" title="Configurações de javascript" data-coreui-toggle="modal" data-coreui-target="#modalJsGlobal">
    <i class="fa fa-code"></i>
</a> 
<a href="render?page=all" class="btn btn-primary lcs-settings mx-3" title="Listar todas as páginas">
    <i class="fa fa-list"></i>
</a>
<a href="builder?session=menu-add" class="btn btn-primary lcs-settings" title="Adicionar novo menu">
    <i class="fa fa-plus"></i>
</a>
<%}%>