<%@page import="java.util.Enumeration"%>
<%@page import="br.com.lowcodesystem.dao.DataSource"%>
<%@page import="br.com.lowcodesystem.model.Page"%>
<%@page import="br.com.lowcodesystem.util.ManterXML"%>
<%@page import="br.com.lowcodesystem.util.FormataTexto"%>
<%@page import="br.com.lowcodesystem.ctrl.Render"%>
<%if (session.getAttribute("dev") != null && (Boolean) session.getAttribute("dev")) {%>
<form   action="builder" method="post" id="modalEditFormForm">
    <input type="hidden" name="session" value="form-sql-edit"/>
    <input type="hidden" name="page" value="<%=((Page) request.getAttribute("p")).getId()%>"/>
    <input type="hidden" name="action" value="<%=request.getAttribute("action")%>"/>
    <div class="modal fade" id="modalSQLEdit" data-coreui-backdrop="static" data-coreui-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
        <div class="modal-dialog modal-xl">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="staticBackdropLabel">Configuração da página</h5>
                    <button type="button" class="btn-close" data-coreui-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <div class="row">
                        <div class="mb-3 col-md-3">
                            <label class="form-label">Opções</label>
                            <select multiple class="form-control" style="height: 375px;" ondblclick="$('#sqlEdit').val($('#sqlEdit').val() + $(this).val())">
                                <option title="id">\#{id}</option>
                                <%
                                    Enumeration keys = session.getAttributeNames();
                                    while (keys.hasMoreElements()) {
                                        String key = (String) keys.nextElement();
                                        if (!key.contains("javamelody")) {
                                            out.println("<option title=\"" + key + "\"> #{session_" + key + "}</option>");
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

                        <div class="mb-3 col-md-9">
                            <label for="sqlEdit" class="form-label">SQL de alteração do registro</label>
                            <span title="Informe um sql válido para atualizar o banco com os campos da tela.">
                                <svg style="width: 16px" xmlns="http://www.w3.org/2000/svg" class="bi bi-exclamation-triangle-fill flex-shrink-0 me-2" viewBox="0 0 16 16" role="img" aria-label="Warning:">
                                    <path d="M8.982 1.566a1.13 1.13 0 0 0-1.96 0L.165 13.233c-.457.778.091 1.767.98 1.767h13.713c.889 0 1.438-.99.98-1.767L8.982 1.566zM8 5c.535 0 .954.462.9.995l-.35 3.507a.552.552 0 0 1-1.1 0L7.1 5.995A.905.905 0 0 1 8 5zm.002 6a1 1 0 1 1 0 2 1 1 0 0 1 0-2z"/>
                                </svg>                            
                            </span>
                            <textarea id="sqlEdit" name="sqlEdit" class="form-control"  placeholder="Informe o sql para alteração do registro" rows="15"><%= ((Page) request.getAttribute("p")).getSqlEdit()%></textarea>
                        </div>

                        <div class="mb-3 col-md-4">
                            <label for="sqlEditDS" class="form-label">Datasource</label>
                            <select id="sqlEditDS" name="sqlEditDS" class="form-select" size="1">	
                                <% for (String key : DataSource.database.keySet()) {
                                        out.println("<option value=\"" + key + "\" " + (((Page) request.getAttribute("p")).getSqlEditDS().equals(key) ? "selected" : "") + ">" + key + "</option>");
                                    }%>
                            </select>
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
</form>
<%}%>