<%@page import="br.com.lowcodesystem.model.Menu"%>
<%@page import="br.com.lowcodesystem.util.ManterXML"%>
<%@page import="br.com.lowcodesystem.util.FormataTexto"%>
<%@page import="br.com.lowcodesystem.ctrl.Render"%>
<%if (session.getAttribute("dev") != null && (Boolean) session.getAttribute("dev")) {%>
<form method="post" action="builder" id="formMenu">
    <input type="hidden" name="session" value="menu"/>
    <input type="hidden" name="idMenuOld" value=""/>
    <div class="modal fade" id="modalMenu" data-coreui-backdrop="static" data-coreui-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="staticBackdropLabel">Configuração do Menu</h5>
                    <button type="button" class="btn-close" data-coreui-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <div class="row g-3">
                        <div class="col-md-6">
                            <label for="idMenu" class="form-label">ID do menu</label>
                            <input type="text" id="idMenu" name="idMenu" class="form-control" value="" autofocus placeholder="ID do menu"/>
                        </div>
                        <div class="col-md-6">
                            <label for="label" class="form-label">Nome do menu</label>
                            <input type="text" id="label" name="label" class="form-control" value="" placeholder="Nome do menu"/>
                        </div>
                        <div class="col-md-6">
                            <label for="pageReferencia" class="form-label">ID da página de referência</label>
                            <input type="text" id="pageReferencia" name="pageReferencia" class="form-control" value="" placeholder="ID da página de referência"/>
                        </div>
                        <div class="col-md-6">
                            <label for="pageParam" class="form-label">Parâmetros</label>
                            <input type="text" id="pageParam" name="pageParam" class="form-control" value="" placeholder=""/>
                        </div>
                        <div class="col-md-6">
                            <label for="icon" class="form-label">Ícone <a target="_blank" style="text-decoration: none;font-size: smaller;" href="https://fontawesome.com/v4/icons/">(Opções)</a></label>
                            <input type="text" id="icon" name="icon" class="form-control" value="" placeholder="fa fa-user"/>
                        </div>
                        <div class="col-md-6">
                            <label for="idparent" class="form-label">ID Menu pai</label>
                            <select id="idparent" name="idparent" class="form-control" size="1">	
                                <option value="" selected="selected">Selecione</option>
                                <%
                                    for (Menu m : Render.menus) {
                                        if (m.getParentId() == null || m.getParentId().isEmpty()) {
                                            out.println("<option value=\"" + m.getId() + "\">" + m.getLabel() + "</option>");
                                        }
                                    }
                                %>
                            </select>
                        </div>
                    </div>
                    <br>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-light" data-coreui-dismiss="modal">Fechar</button>
                        <button type="submit" class="btn btn-primary">Gravar</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</form>
<%}%>