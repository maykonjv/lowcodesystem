<%@page import="br.com.lowcodesystem.model.Field"%>
<%@page import="br.com.lowcodesystem.model.Page"%>
<%@page import="br.com.lowcodesystem.util.ManterXML"%>
<%@page import="br.com.lowcodesystem.util.FormataTexto"%>
<%@page import="br.com.lowcodesystem.ctrl.Render"%>
<%if (session.getAttribute("dev") != null && (Boolean) session.getAttribute("dev")) {%>
<form   action="builder" method="post" id="modalJsLocalForm">
    <input type="hidden" name="session" value="form-script"/>
    <input type="hidden" name="page" value="<%=((Page) request.getAttribute("p")).getId()%>"/>
    <input type="hidden" name="action" value="<%=request.getAttribute("action")%>"/>
    <div class="modal fade" id="modalJsLocal" data-coreui-backdrop="static" data-coreui-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="staticBackdropLabel">Configura��o de JavaScript</h5>
                    <button type="button" class="btn-close" data-coreui-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <div class="row">
                        <div class="col-md-4">
                            <label class="form-label">Op��es</label>
                            <select multiple class="form-control" style="height: 375px;" ondblclick="$('#javascript').val($('#javascript').val() + $(this).val())">
                                <option title="id">\$('#id')</option>
                                <%
                                    for (Field f : ((Page) request.getAttribute("p")).getFields()) {
                                        out.println("<option title=\"" + f.getId() + "\"> $('#" + f.getId() + "')</option>");
                                    }
                                %>
                            </select>
                        </div>

                        <div class="col-md-8">
                            <label for="javascript" class="form-label">SCRIPT da p�gina (Front-End)</label>
                            <textarea id="javascript" name="javascript" class="form-control"  placeholder="Informe o javascript da p�gina" rows="15"><%= ((Page) request.getAttribute("p")).getJavascript()%></textarea>
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