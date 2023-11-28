<%@page import="br.com.lowcodesystem.util.ManterXML"%>
<%@page import="br.com.lowcodesystem.util.FormataTexto"%>
<%@page import="br.com.lowcodesystem.ctrl.Render"%>
<%@page import="br.com.lowcodesystem.dao.DataSource"%>
<%if (session.getAttribute("dev") != null && (Boolean) session.getAttribute("dev")) {%>
<form   action="builder" method="post" id="formAdmin">
    <input type="hidden" name="session" value="adminA"/>
    <div class="modal fade" id="modalProject" data-coreui-backdrop="static" data-coreui-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="staticBackdropLabel">Configurações do Projeto</h5>
                    <button type="button" class="btn-close" data-coreui-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <p class="text-medium-emphasis">Configuração gerais do sistema.</p>
                    <div class="mb-3">
                        <label for="projectName" class="form-label">Nome do Projeto</label>
                        <input type="text" class="form-control" id="projectName" name="projectName" placeholder="Sistema ABCD" value="<%=Render.project.getProjectName()%>">
                    </div>
                    <div class="mb-3">
                        <label for="projectID" class="form-label">ID do Projeto</label>
                        <input type="text" class="form-control" id="projectID" name="projectID" placeholder="sistema_abcd" value="<%=Render.project.getProjectID()%>">
                    </div>
                    <div class="mb-3">
                        <label for="projectVersion" class="form-label">Versão do Projeto</label>
                        <input type="text" class="form-control" id="projectVersion" name="projectVersion" placeholder="1.0.0" value="<%=Render.project.getProjectVersion()%>">
                    </div>
                    <div class="mb-3">
                        <label for="projectYear" class="form-label">Ano do Projeto</label>
                        <input type="text" class="form-control" id="projectYear" name="projectYear" placeholder="2020" value="<%=Render.project.getProjectYear()%>">
                    </div>
                    <div class="mb-3">
                        <label for="pathSchema" class="form-label">Pasta dos projetos</label>
                        <input type="text" class="form-control" id="pathSchema" name="pathSchema" placeholder="/home/lowcodesystem/" value="<%=FormataTexto.toString(ManterXML.pasta)%>">
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