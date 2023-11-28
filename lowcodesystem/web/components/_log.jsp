
<%@page import="br.com.lowcodesystem.ctrl.Render"%>
<%@page import="br.com.lowcodesystem.util.ManterXML"%>
<%@page import="java.io.File"%>
<%@page import="br.com.lowcodesystem.util.Log"%>
<%
    if (!Render.project.isLogEnable() && !session.getAttribute("perfil").equals(Render.ADMINISTRADOR)
            && !(session.getAttribute("dev") != null && (Boolean) session.getAttribute("dev"))) {
        response.sendRedirect("render");
        return;
    }
%>

<div>
    <div class="card">
        <h5 class="card-header">Gerenciamento de Logs</h5>
        <div class="card-body">
            <form role="form" method="POST" action="render" id="form">
                <input type="hidden" name="page" id="page" value="page_log"/>
                <input type="hidden" name="action" id="action" value="action_log"/>
                <div class="row">
                    <div class="col-md-12 mb-3">
                        <label class="form-label" >Gravar:</label>
                        <input type="checkbox" class="form-check-input" id="hasErrorRegister" name="hasErrorRegister" style="margin-left: 0px;" checked="checked" disabled="true"/>
                        <label for="hasErrorRegister" class="form-label">ERROR</label>
                        <input type="checkbox" class="form-check-input" id="hasWarningRegister" name="hasWarningRegister" style="margin-left: 0px;" <%=request.getAttribute("hasWarningRegister") != null && request.getAttribute("hasWarningRegister").equals("on") ? "checked" : ""%>  onchange="document.getElementById('form').submit();"/>
                        <label for="hasWarningRegister" class="form-label">WARNING</label>
                        <input type="checkbox" class="form-check-input" id="hasConfigRegister" name="hasConfigRegister" style="margin-left: 0px;" <%=request.getAttribute("hasConfigRegister") != null && request.getAttribute("hasConfigRegister").equals("on") ? "checked" : ""%>  onchange="document.getElementById('form').submit();"/>
                        <label for="hasConfigRegister" class="form-label">CONFIG</label>
                        <input type="checkbox" class="form-check-input" id="hasInfoRegister" name="hasInfoRegister" style="margin-left: 0px;" <%=request.getAttribute("hasInfoRegister") != null && request.getAttribute("hasInfoRegister").equals("on") ? "checked" : ""%>  onchange="document.getElementById('form').submit();"/>
                        <label for="hasInfoRegister" class="form-label">INFO</label>
                        <input type="checkbox" class="form-check-input" id="hasTimeRegister" name="hasTimeRegister" style="margin-left: 0px;" <%=request.getAttribute("hasTimeRegister") != null && request.getAttribute("hasTimeRegister").equals("on") ? "checked" : ""%>  onchange="document.getElementById('form').submit();"/>
                        <label for="hasTimeRegister" class="form-label">TIME</label>
                        <input type="checkbox" class="form-check-input" id="hasDebugRegister" name="hasDebugRegister" style="margin-left: 0px;" <%=request.getAttribute("hasDebugRegister") != null && request.getAttribute("hasDebugRegister").equals("on") ? "checked" : ""%>  onchange="document.getElementById('form').submit();"/>
                        <label for="hasDebugRegister" class="form-label">DEBUG</label>
                        <input type="checkbox" class="form-check-input" id="hasRequestRegister" name="hasRequestRegister" style="margin-left: 0px;" <%=request.getAttribute("hasRequestRegister") != null && request.getAttribute("hasRequestRegister").equals("on") ? "checked" : ""%>  onchange="document.getElementById('form').submit();"/>
                        <label for="hasRequestRegister" class="form-label">REQUEST</label>
                        <input type="checkbox" class="form-check-input" id="hasAllRegister" name="hasAllRegister" style="margin-left: 0px;" onchange="document.getElementById('form').submit();"/>
                        <label for="hasAllRegister" class="form-label" >ALL</label>
                    </div>
                    <div class="col-md-12 mb-3">
                        <label class="form-label" >Listar:</label>
                        <input type="checkbox" class="form-check-input" id="hasError" name="hasError" style="margin-left: 0px;" <%=request.getAttribute("hasError") != null && request.getAttribute("hasError").equals("on") ? "checked" : ""%>  onchange="document.getElementById('form').submit();"/>
                        <label for="hasError" class="form-label">ERROR</label>
                        <input type="checkbox" class="form-check-input" id="hasWarning" name="hasWarning" style="margin-left: 0px;" <%=request.getAttribute("hasWarning") != null && request.getAttribute("hasWarning").equals("on") ? "checked" : ""%>  onchange="document.getElementById('form').submit();"/>
                        <label for="hasWarning" class="form-label">WARNING</label>
                        <input type="checkbox" class="form-check-input" id="hasConfig" name="hasConfig" style="margin-left: 0px;" <%=request.getAttribute("hasConfig") != null && request.getAttribute("hasConfig").equals("on") ? "checked" : ""%>  onchange="document.getElementById('form').submit();"/>
                        <label for="hasConfig" class="form-label">CONFIG</label>
                        <input type="checkbox" class="form-check-input" id="hasInfo" name="hasInfo" style="margin-left: 0px;" <%=request.getAttribute("hasInfo") != null && request.getAttribute("hasInfo").equals("on") ? "checked" : ""%>  onchange="document.getElementById('form').submit();"/>
                        <label for="hasInfo" class="form-label">INFO</label>
                        <input type="checkbox" class="form-check-input" id="hasTime" name="hasTime" style="margin-left: 0px;" <%=request.getAttribute("hasTime") != null && request.getAttribute("hasTime").equals("on") ? "checked" : ""%>  onchange="document.getElementById('form').submit();"/>
                        <label for="hasTime" class="form-label">TIME</label>
                        <input type="checkbox" class="form-check-input" id="hasDebug" name="hasDebug" style="margin-left: 0px;" <%=request.getAttribute("hasDebug") != null && request.getAttribute("hasDebug").equals("on") ? "checked" : ""%>  onchange="document.getElementById('form').submit();"/>
                        <label for="hasDebug" class="form-label">DEBUG</label>
                        <input type="checkbox" class="form-check-input" id="hasRequest" name="hasRequest" style="margin-left: 0px;" <%=request.getAttribute("hasRequest") != null && request.getAttribute("hasRequest").equals("on") ? "checked" : ""%>  onchange="document.getElementById('form').submit();"/>
                        <label for="hasRequest" class="form-label">REQUEST</label>
                        <input type="checkbox" class="form-check-input" id="hasAll" name="hasAll" style="margin-left: 0px;" onchange="document.getElementById('form').submit();"/>
                        <label for="hasAll" class="form-label" >ALL</label>
                    </div>
                    <div class="col-md-5 mb-3">
                        <%
                            File logs = new File(Render.project.getLogPath());
                            if (logs.exists()) {
                                int lastLog = 0, i = 0;
                                String filePath = "";
                                if (request.getAttribute("logFile") == null || request.getAttribute("logFile").toString().isEmpty()) {
                                    lastLog = logs.list().length;
                                } else {
                                    filePath = request.getAttribute("logFile").toString();
                                }
                                out.print("<select id=\"logFile\" name=\"logFile\" class=\"form-select\" size=\"1\" onchange=\"document.getElementById('form').submit();\">");
                                for (File log : logs.listFiles()) {
                                    i++;
                                    if (request.getParameter("inputDate") == null || log.getName().contains(request.getParameter("inputDate"))) {
                                        if (lastLog == i || filePath.equals(log.getAbsolutePath())) {
                                            out.print("<option value=\"" + log.getAbsolutePath() + "\" selected=\"true\">" + log.getName() + "</option>");
                                        } else {
                                            out.print("<option value=\"" + log.getAbsolutePath() + "\">" + log.getName() + "</option>");
                                        }
                                    }
                                }
                                out.print("</select>");
                            }
                        %>
                    </div>


                    <div class="col-md-5 mb-3">
                        <button type="submit" class="btn btn-outline-danger fa fa-trash" id="btnDelete" name="btnDelete" title="Excluir arquivo de log selecionado."></button>
                        <button type="submit" class="btn btn-outline-info fa fa-file-o" id="btnBackup" name="btnBackup" style="margin-left: 10px;" title="Criar um novo arquivo de log."></button>
                        <input type="text" class="form-control" id="inputDate" name="inputDate" style="margin-left: 10px;width: 55%;display: inline;" placeholder="Filtrar arquivos" value="<%=(request.getParameter("inputDate") != null) ? request.getParameter("inputDate") : ""%>" title="Filtrar arquivos por essa valor." onchange="document.getElementById('form').submit();"/>
                    </div>

                    <div class="col-md-2 mb-3">
                        <input type="submit" class="btn btn-primary" id="btnRefresh" name="btnRefresh" value="ATUALIZAR">
                    </div>
                </div>

                <%
                    if (request.getAttribute("logFile") != null && !request.getAttribute("logFile").toString().isEmpty()) {
                        Log.print(out, new File(request.getAttribute("logFile").toString()), request.getAttribute("hasError") != null && request.getAttribute("hasError").equals("on"), request.getAttribute("hasWarning") != null && request.getAttribute("hasWarning").equals("on"), request.getAttribute("hasInfo") != null && request.getAttribute("hasInfo").equals("on"), request.getAttribute("hasConfig") != null && request.getAttribute("hasConfig").equals("on"), request.getAttribute("hasDebug") != null && request.getAttribute("hasDebug").equals("on"), request.getAttribute("hasTime") != null && request.getAttribute("hasTime").equals("on"), request.getAttribute("hasRequest") != null && request.getAttribute("hasRequest").equals("on"));
                    }
                %>
            </form>
        </div>
    </div>
</div>
