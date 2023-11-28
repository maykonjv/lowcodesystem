<%@page import="br.com.lowcodesystem.model.ProfileAction"%>
<%@page import="br.com.lowcodesystem.model.Profile"%>
<%@page import="java.io.File"%>
<%@page import="br.com.lowcodesystem.model.Field"%>
<%@page import="br.com.lowcodesystem.model.Page"%>
<%@page import="br.com.lowcodesystem.ctrl.Render"%>
<%@page import="br.com.lowcodesystem.model.Menu"%>

<% Object error = request.getAttribute("error");
    if (error != null && !error.toString().isEmpty()) {
        out.println("<div class=\"alert alert-danger\">");
        out.println("<button type=\"button\" class=\"close\" data-dismiss=\"alert\">×</button>");
        out.println(error);
        out.println("</div>");
    }
    Object success = request.getAttribute("success");
    if (success != null && !success.toString().isEmpty()) {
        out.println("<div class=\"alert alert-success\">");
        out.println("<button type=\"button\" class=\"close\" data-dismiss=\"alert\">×</button>");
        out.println(success);
        out.println("</div>");
    }
%>
<script type="text/javascript">
    function check(e) {
        document.getElementById("perfil_custom_" + e.id).checked = true;
        document.getElementById("perfil_ativar_" + e.id).checked = true;
        document.getElementById("perfil_excluir_" + e.id).checked = true;
        document.getElementById("perfil_listar_" + e.id).checked = true;
        document.getElementById("perfil_cadastrar_" + e.id).checked = true;
        document.getElementById("perfil_pesquisar_" + e.id).checked = true;
        document.getElementById("perfil_alterar_" + e.id).checked = true;
        document.getElementById("perfil_visualizar_" + e.id).checked = true;
    }
</script>
<form role="form" method="POST" action="builder" id="form">
    <input type="hidden" name="page" id="page" value="page_perfil"/>
    <input type="hidden" name="session" id="session" value="profile"/>
    <div class="card">
        <h5 class="card-header">Perfil</h5>
        <div class="card-body">
            <div class="row">
                <div class="mb-3 col-md-4">
                    <label for="perfil_nome" class="form-label">Perfil</label>
                    <select id="perfil_nome" name="perfil_nome" class="form-select" size="1" onchange="document.getElementById('form').submit();">	
                        <option value="" selected="selected">Selecione</option>
                        <%
                            for (Profile p : Render.profiles) {
                                out.println("<option value=\"" + p.getName() + "\"" + (request.getAttribute("perfil_nome") != null && request.getAttribute("perfil_nome").equals(p.getName()) ? "selected" : "") + ">" + p.getName() + "</option>");
                            }
                        %>
                    </select>
                </div>

                <div class="mb-3 col-md-4">
                    <button type="submit" class="btn btn-danger fa-trash" id="remover_perfil" name="remover_perfil" title="Excluir o perfil selecionado." style="margin-top: 33px"></button>
                </div>

                <div class="mb-3 col-md-2">
                    <label for="perfil_add_nome" class="form-label">Novo Perfil</label>
                    <input type="text" name="perfil_add_nome" class="form-control" placeholder="Novo perfil"/>
                </div>

                <div class="mb-3 col-md-1">
                    <input type="submit" class="btn btn-primary" name="perfil_evento" value="Adicionar" style="margin-top: 33px">
                </div>

                <%
                    String perfil = "";
                    if (request.getAttribute("perfil_nome") != null) {
                        perfil = request.getAttribute("perfil_nome").toString();
                    }
                    int index = Render.profiles.indexOf(new Profile(perfil));
                    if (!perfil.isEmpty() && index > -1) {
                        Profile profile = Render.profiles.get(index);
                        for (Page p : Render.pages) {
                            ProfileAction action = profile.getActions().get(p.getId());
                            if (action == null) {
                                action = new ProfileAction();
                            }
                %>

                <div class="mb-3 col-md-3">
                    <%= p.getName()%>
                </div>

                <div class="mb-3 col-md-9">
                    <div style="float: left;">
                        <input type="checkbox" class="form-check-input" onclick="check(this)" id="<%=p.getId()%>" name="<%=p.getId()%>" <%=(action.isAll() || perfil.equals(Render.ADMINISTRADOR)) ? "checked=\"checked\"" : ""%>/>
                        <label for="<%=p.getId()%>" class="form-label" style="margin-right: 10px">Todos</label>
                    </div>
                    <div style="float: left;">
                        <input type="checkbox" class="form-check-input" id="perfil_custom_<%=p.getId()%>" name="perfil_custom_<%=p.getId()%>" <%=(action.isCustom() || perfil.equals(Render.ADMINISTRADOR)) ? "checked=\"checked\"" : ""%>/>
                        <label for="perfil_custom_<%=p.getId()%>" class="form-label" style="margin-right: 10px">Menu</label>
                    </div>
                    <div style="float: left;">
                        <input type="checkbox" class="form-check-input" id="perfil_ativar_<%=p.getId()%>" name="perfil_ativar_<%=p.getId()%>"<%=(!p.isHasActive()) ? "disabled=disabled" : ""%> <%=((action.isActive() || perfil.equals(Render.ADMINISTRADOR)) && p.isHasActive()) ? "checked=\"checked\"" : ""%>/>
                        <label for="perfil_ativar_<%=p.getId()%>" class="form-label" style="margin-right: 10px">Ativar</label>
                    </div>
                    <div style="float: left;">
                        <input type="checkbox" class="form-check-input" id="perfil_excluir_<%=p.getId()%>" name="perfil_excluir_<%=p.getId()%>"<%=(!p.isHasDelete()) ? "disabled=disabled" : ""%> <%=((action.isDelete() || perfil.equals(Render.ADMINISTRADOR)) && p.isHasDelete()) ? "checked=\"checked\"" : ""%>/>
                        <label for="perfil_excluir_<%=p.getId()%>" class="form-label" style="margin-right: 10px">Excluir</label>
                    </div>
                    <div style="float: left;">
                        <input type="checkbox" class="form-check-input" id="perfil_listar_<%=p.getId()%>" name="perfil_listar_<%=p.getId()%>"<%=(!p.isHasList()) ? "disabled=disabled" : ""%> <%=((action.isList() || perfil.equals(Render.ADMINISTRADOR)) && p.isHasList()) ? "checked=\"checked\"" : ""%>/>
                        <label for="perfil_listar_<%=p.getId()%>" class="form-label" style="margin-right: 10px">Listar</label>
                    </div>
                    <div style="float: left;">
                        <input type="checkbox" class="form-check-input" id="perfil_cadastrar_<%=p.getId()%>" name="perfil_cadastrar_<%=p.getId()%>"<%=(!p.isHasNew()) ? "disabled=disabled" : ""%> <%=((action.isAdd() || perfil.equals(Render.ADMINISTRADOR)) && p.isHasNew()) ? "checked=\"checked\"" : ""%>/>
                        <label for="perfil_cadastrar_<%=p.getId()%>" class="form-label" style="margin-right: 10px">Cadastrar</label>
                    </div>
                    <div style="float: left;">
                        <input type="checkbox" class="form-check-input" id="perfil_pesquisar_<%=p.getId()%>" name="perfil_pesquisar_<%=p.getId()%>"<%=(!p.isHasSearch()) ? "disabled=disabled" : ""%> <%=((action.isSearch() || perfil.equals(Render.ADMINISTRADOR)) && p.isHasSearch()) ? "checked=\"checked\"" : ""%>/>
                        <label for="perfil_pesquisar_<%=p.getId()%>" class="form-label" style="margin-right: 10px">Pesquisar</label>
                    </div>
                    <div style="float: left;">
                        <input type="checkbox" class="form-check-input" id="perfil_alterar_<%=p.getId()%>" name="perfil_alterar_<%=p.getId()%>"<%=(!p.isHasUpdate()) ? "disabled=disabled" : ""%> <%=((action.isUpdate() || perfil.equals(Render.ADMINISTRADOR)) && p.isHasUpdate()) ? "checked=\"checked\"" : ""%>/>
                        <label for="perfil_alterar_<%=p.getId()%>" class="form-label" style="margin-right: 10px">Editar</label>
                    </div>
                    <div style="float: left;">
                        <input type="checkbox" class="form-check-input" id="perfil_visualizar_<%=p.getId()%>" name="perfil_visualizar_<%=p.getId()%>"<%=(!p.isHasView()) ? "disabled=disabled" : ""%> <%=((action.isView() || perfil.equals(Render.ADMINISTRADOR)) && p.isHasView()) ? "checked=\"checked\"" : ""%>/>
                        <label for="perfil_visualizar_<%=p.getId()%>" class="form-label" style="margin-right: 10px">Visualizar</label>
                    </div> 
                </div>
                <% }
                    }%>
            </div>
        </div>
        <div class="card-footer text-body-secondary">
            <input type="submit" class="btn btn-primary float-end" name="gravar_perfil" id="gravar_perfil" value="Gravar">
        </div>
    </div>
</form>