<%@page import="br.com.lowcodesystem.model.Page"%>
<%@page import="br.com.lowcodesystem.util.ManterXML"%>
<%@page import="br.com.lowcodesystem.util.FormataTexto"%>
<%@page import="br.com.lowcodesystem.ctrl.Render"%>
<%if (session.getAttribute("dev") != null && (Boolean) session.getAttribute("dev")) {%>
<form   action="builder" method="post" id="modalPageForm">
    <input type="hidden" name="session" value="form"/>
    <input type="hidden" name="page" value="<%=((Page) request.getAttribute("p")).getId()%>"/>
    <div class="modal fade" id="modalPage" data-coreui-backdrop="static" data-coreui-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="staticBackdropLabel">Configuração da página</h5>
                    <button type="button" class="btn-close" data-coreui-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <div class="row g-3">
                        <div class="col-md-6">
                            <label for="idForm" class="form-label">ID da página</label>
                            <span style="color:Red;font-style:normal;font-weight:bold;" data-toggle="tooltip" title="Campo Obrigatório.">*</span>
                            <input type="text" id="idForm" name="idForm" class="form-control" value="<%= ((Page) request.getAttribute("p")).getId()%>" placeholder="Informe ID da página"/>
                        </div>
                        <div class="col-md-6">
                            <label for="nameForm" class="form-label">Título da página</label>
                            <span style="color:Red;font-style:normal;font-weight:bold;" data-toggle="tooltip" title="Campo Obrigatório.">*</span>
                            <input type="text" id="nameForm" name="nameForm" class="form-control" value="<%= ((Page) request.getAttribute("p")).getName()%>" placeholder="Informe Título da página"/>
                        </div>
                        <div class="col-md-12">
                            <div class="checkbox" style="margin-top: 30px;">
                                <input type="checkbox" class="form-check-input" id="hasList" name="hasList" <%= ((Page) request.getAttribute("p")).isHasList() ? "checked=\"checked\"" : ""%> style="margin-left: 0px;"/>
                                <label for="hasList" class="form-check-label" style="margin-right: 10px">Listar</label>
                                <input type="checkbox" class="form-check-input" id="hasNew" name="hasNew" <%= ((Page) request.getAttribute("p")).isHasNew() ? "checked=\"checked\"" : ""%> style="margin-left: 0px;"/>
                                <label for="hasNew" class="form-check-label" style="margin-right: 10px">Novo</label>
                                <input type="checkbox" class="form-check-input" id="hasUpdate" name="hasUpdate" <%= ((Page) request.getAttribute("p")).isHasUpdate() ? "checked=\"checked\"" : ""%> style="margin-left: 0px;"/>
                                <label for="hasUpdate" class="form-check-label" style="margin-right: 10px">Alterar</label>
                                <input type="checkbox" class="form-check-input" id="hasDelete" name="hasDelete" <%= ((Page) request.getAttribute("p")).isHasDelete() ? "checked=\"checked\"" : ""%> style="margin-left: 0px;"/>
                                <label for="hasDelete" class="form-check-label" style="margin-right: 10px">Excluir</label>
                                <input type="checkbox" class="form-check-input" id="hasActive" name="hasActive" <%= ((Page) request.getAttribute("p")).isHasActive() ? "checked=\"checked\"" : ""%> style="margin-left: 0px;"/>
                                <label for="hasActive" class="form-check-label" style="margin-right: 10px">Inativar</label>
                                <input type="checkbox" class="form-check-input" id="hasView" name="hasView" <%= ((Page) request.getAttribute("p")).isHasView() ? "checked=\"checked\"" : ""%> style="margin-left: 0px;"/>
                                <label for="hasView" class="form-check-label" style="margin-right: 10px">Visualizar</label>
                                <input type="checkbox" class="form-check-input" id="hasSearch" name="hasSearch" <%= ((Page) request.getAttribute("p")).isHasSearch() ? "checked=\"checked\"" : ""%> style="margin-left: 0px;"/>
                                <label for="hasSearch" class="form-check-label" style="margin-right: 10px">Pesquisar</label>
                            </div>
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