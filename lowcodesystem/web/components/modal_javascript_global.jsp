<%@page import="br.com.lowcodesystem.util.ManterXML"%>
<%@page import="br.com.lowcodesystem.util.FormataTexto"%>
<%@page import="br.com.lowcodesystem.ctrl.Render"%>
<%if (session.getAttribute("dev") != null && (Boolean) session.getAttribute("dev")) {%>

<form   action="builder" method="post" id="modalJsGlobalForm">
    <input type="hidden" name="session" value="js-global"/>
    <input type="hidden" name="js_global" id="js_global" value="<%=Render.project.getJsGeral()%>"/>
    <div class="modal fade" id="modalJsGlobal" data-coreui-backdrop="static" data-coreui-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
        <div class="modal-dialog modal-xl">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="staticBackdropLabel">Configuração de JavaScript</h5>
                    <button type="button" class="btn-close" data-coreui-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
 
                    <div class="form-row">
                        <div class="col-xs-8 form-group">
                            <label for="javascriptBack" class="control-label">SCRIPT Global</label>
                            <textarea id="javascriptGlobalBack" name="javascriptGlobalBack" class="form-control"  placeholder="Informe o javascript global" rows="15"><%=Render.project.getJsGeral()%></textarea>
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