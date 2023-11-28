<%@page import="br.com.lowcodesystem.model.Field"%>
<%@page import="java.util.Enumeration"%>
<%@page import="br.com.lowcodesystem.model.Page"%>
<%@page import="br.com.lowcodesystem.util.ManterXML"%>
<%@page import="br.com.lowcodesystem.util.FormataTexto"%>
<%@page import="br.com.lowcodesystem.ctrl.Render"%>
<%if (session.getAttribute("dev") != null && (Boolean) session.getAttribute("dev")) {%>
<form   action="builder" method="post" id="modalJsServerForm">
    <input type="hidden" name="session" value="form-script-back"/>
    <input type="hidden" name="page" value="<%=((Page) request.getAttribute("p")).getId()%>"/>
    <input type="hidden" name="action" value="<%=request.getAttribute("action")%>"/>
    <div class="modal fade" id="modalJsServer" data-coreui-backdrop="static" data-coreui-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="staticBackdropLabel">Configuração de JavaScript</h5>
                    <button type="button" class="btn-close" data-coreui-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <div class="row">
                        <div class="col-md-4">
                            <label class="form-label">Opções</label>
                            <select multiple class="form-control" style="height: 375px;" ondblclick="$('#javascriptBack').val($('#javascriptBack').val() + $(this).val())">
                                <%
                                    for (Field f : ((Page) request.getAttribute("p")).getFields()) {
                                        out.println("<option title=\"" + f.getId() + "\"> " + f.getId() + "</option>");
                                    }
                                %>
                                <%
                                    Enumeration keys = session.getAttributeNames();
                                    while (keys.hasMoreElements()) {
                                        String key = (String) keys.nextElement();
                                        if (!key.contains("javamelody")) {
                                            out.println("<option title=\"" + key + "\"> session_" + key + "</option>");
                                        }
                                    }
                                    Enumeration keys2 = application.getAttributeNames();
                                    while (keys2.hasMoreElements()) {
                                        String key = (String) keys2.nextElement();
                                        if (!key.contains(".")) {
                                            out.println("<option title=\"" + key + "\"> #{application_" + key + "}</option>");
                                        }
                                    }
                                %>
                            </select>
                        </div>
                        <div class="col-md-8">
                            <label for="javascriptBack" class="form-label">SCRIPT da página (Back-End)</label>
                            <textarea id="javascriptBack" name="javascriptBack" class="form-control"  placeholder="Informe o javascript da página" rows="15"><%= ((Page) request.getAttribute("p")).getJavascriptBack()%></textarea>
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