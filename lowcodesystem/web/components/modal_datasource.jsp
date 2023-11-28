<%@page import="br.com.lowcodesystem.dao.DataSource"%>
<%@page import="br.com.lowcodesystem.model.Page"%>
<%@page import="br.com.lowcodesystem.util.ManterXML"%>
<%@page import="br.com.lowcodesystem.util.FormataTexto"%>
<%@page import="br.com.lowcodesystem.ctrl.Render"%>
<%if (session.getAttribute("dev") != null && (Boolean) session.getAttribute("dev")) {%>
<div class="modal fade" id="modalDatasource" data-coreui-backdrop="static" data-coreui-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
    <div class="modal-dialog modal-xl">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="staticBackdropLabel">Configuração de Datasource</h5>
                <button type="button" class="btn-close" data-coreui-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <header class="header">
                    <form id="modalDatasourceForm">
                        <input type="hidden" name="session" value="addDS"/>
                        <div class="row">
                            <div class="mb-3 col-md-10">
                                <span class="header-brand mb-0 h1">
                                    Inclusão de novo Datasource
                                </span>
                                <% if (request.getAttribute("msg") != null) {%>
                                <div class="alert alert-success alert-dismissible fade show">
                                    <button type="button" class="btn-close" data-coreui-dismiss="alert" aria-label="Close"></button>
                                    <%=request.getAttribute("msg")%>
                                </div>
                                <%}%>
                            </div>
                            <div class="mb-3 col-md-6">
                                <label for="input_datasource" class="form-label">Nome do datasource</label>
                                <input type="text" id="input_datasource" name="input_datasource" class="form-control" required="true"/>	
                            </div>
                            <div class="mb-3 col-md-6">
                                <button type="submit" id="btn-loading" class="btn btn-primary" value="Adicionar" style="margin-top: 30px">
                                    <span class="spinner-border spinner-border-sm" style="display: none;" aria-hidden="true"></span>
                                    Adicionar
                                </button>
                            </div>
                        </div>
                    </form>
                </header>
                <br>
                <form id="formAdminDS">
                    <input type="hidden" name="session" value="adminB"/>
                    <div class="row">
                        <div class="mb-3 col-md-10">
                            <span class="header-brand mb-0 h1">
                                Configuração de banco de dados
                            </span>
                            <% if (request.getAttribute("msg") != null) {%>
                            <div class="alert alert-success">
                                <button type="button" class="close" data-dismiss="alert">×</button>
                                <%=request.getAttribute("msg")%>
                            </div>
                            <%}%>
                        </div>
                        <br/> 
                        <br/> 
                        <div class="mb-3 col-md-3">
                            <label for="datasource" class="form-label">Datasource</label>
                            <select id="datasource" name="datasource" class="form-select" size="1" autofocus required="true">	
                                <option value="null">SELECIONE</option>
                                <%for (String key : DataSource.database.keySet()) {
                                        out.println("<option value=\"" + key + "\" " + (request.getParameter("datasource") != null && request.getParameter("datasource").equals(key) ? "selected" : "") + ">" + key + "</option>");
                                    }%>

                            </select>
                        </div>

                        <div class="mb-3 col-md-3">
                            <label for="database" class="form-label">Banco</label>
                            <select id="database" name="database" class="form-select" size="1" onchange="updateDriver();">	
                                <option value="null">SELECIONE</option>
                                <option value="Postgres"<%=request.getParameter("datasource") != null && DataSource.database.containsKey(request.getParameter("datasource")) && DataSource.database.get(request.getParameter("datasource")).getDatabase() != null && DataSource.database.get(request.getParameter("datasource")).getDatabase().equals("Postgres") ? " selected" : ""%>>Postgres</option>
                                <option value="MSSQL"<%=request.getParameter("datasource") != null && DataSource.database.containsKey(request.getParameter("datasource")) && DataSource.database.get(request.getParameter("datasource")).getDatabase() != null && DataSource.database.get(request.getParameter("datasource")).getDatabase().equals("MSSQL") ? " selected" : ""%>>MSSQL</option>
                                <option value="Oracle"<%=request.getParameter("datasource") != null && DataSource.database.containsKey(request.getParameter("datasource")) && DataSource.database.get(request.getParameter("datasource")).getDatabase() != null && DataSource.database.get(request.getParameter("datasource")).getDatabase().equals("Oracle") ? " selected" : ""%>>Oracle</option>
                                <option value="MySQL"<%=request.getParameter("datasource") != null && DataSource.database.containsKey(request.getParameter("datasource")) && DataSource.database.get(request.getParameter("datasource")).getDatabase() != null && DataSource.database.get(request.getParameter("datasource")).getDatabase().equals("MySQL") ? " selected" : ""%>>MySQL</option>
                                <option value="Sqlite"<%=request.getParameter("datasource") != null && DataSource.database.containsKey(request.getParameter("datasource")) && DataSource.database.get(request.getParameter("datasource")).getDatabase() != null && DataSource.database.get(request.getParameter("datasource")).getDatabase().equals("Sqlite") ? " selected" : ""%>>Sqlite</option>
                            </select>
                        </div>

                        <div class="mb-3 col-md-6">
                            <label for="driverClass" class="form-label">Driver Class</label>
                            <input type="text" required="true" id="driverClass" name="driverClass" class="form-control" value="<%=request.getParameter("datasource") != null && DataSource.database.get(request.getParameter("datasource")) != null ? DataSource.database.get(request.getParameter("datasource")).getDriverClass() : ""%>" data-toggle="tooltip" title="org.postgresql.Driver"/>
                        </div>

                        <div class="mb-3 col-md-6">
                            <label for="jdbcUrl" class="form-label">JDBC URL</label>
                            <input type="text" required="true" id="jdbcUrl" name="jdbcUrl" class="form-control" value="<%=request.getParameter("datasource") != null && DataSource.database.get(request.getParameter("datasource")) != null ? DataSource.database.get(request.getParameter("datasource")).getJdbcUrl() : ""%>" data-toggle="tooltip" title="jdbc:postgresql://localhost:5432/projectx"/>
                        </div>

                        <div class="mb-3 col-md-3">
                            <label for="userDB" class="form-label">Usuário</label>
                            <input type="text" required="true" id="userDB" name="userDB" class="form-control" value="<%=request.getParameter("datasource") != null && DataSource.database.get(request.getParameter("datasource")) != null ? DataSource.database.get(request.getParameter("datasource")).getUser() : ""%>" data-toggle="tooltip" title="root"/>
                        </div>

                        <div class="mb-3 col-md-3">
                            <label for="passDB" class="form-label">Senha</label>
                            <input type="password" id="passDB" name="passDB" class="form-control" value="<%=request.getParameter("datasource") != null && DataSource.database.get(request.getParameter("datasource")) != null ? DataSource.database.get(request.getParameter("datasource")).getPass() : ""%>" data-toggle="tooltip" title="*******"/>
                        </div>

                        <div class="mb-3 col-md-2">
                            <label for="minPoolSize" class="form-label">Min Pool Size</label>
                            <input type="number" required="true" id="minPoolSize" name="minPoolSize" class="form-control" value="<%=request.getParameter("datasource") != null && DataSource.database.get(request.getParameter("datasource")) != null ? DataSource.database.get(request.getParameter("datasource")).getMinPoolSize() : ""%>" data-toggle="tooltip" title="3"/>
                        </div>

                        <div class="mb-3 col-md-2">
                            <label for="maxPoolSize" class="form-label">Max Pool Size</label>
                            <input type="number" required="true" id="maxPoolSize" name="maxPoolSize"  class="form-control" value="<%=request.getParameter("datasource") != null && DataSource.database.get(request.getParameter("datasource")) != null ? DataSource.database.get(request.getParameter("datasource")).getMaxPoolSize() : ""%>" data-toggle="tooltip" title="20"/>
                        </div>

                        <div class="mb-3 col-md-2">
                            <label for="maxStatements" class="form-label">Max Statements</label>
                            <input type="number" required="true" id="maxStatements" name="maxStatements" class="form-control" value="<%=request.getParameter("datasource") != null && DataSource.database.get(request.getParameter("datasource")) != null ? DataSource.database.get(request.getParameter("datasource")).getMaxStatements() : ""%>" data-toggle="tooltip" title="50"/>
                        </div>

                        <div class="mb-3 col-md-2">
                            <label for="timeout" class="form-label">Max Idle Time</label>
                            <input type="number" required="true" id="timeout" name="timeout"   class="form-control" value="<%=request.getParameter("datasource") != null && DataSource.database.get(request.getParameter("datasource")) != null ? DataSource.database.get(request.getParameter("datasource")).getTimeout() : ""%>" data-toggle="tooltip" title="1800"/>
                        </div>

                        <div class="mb-3 col-md-2">
                            <label for="idleTestPeriod" class="form-label">Idle test period</label>
                            <input type="number" required="true" id="idleTestPeriod" name="idleTestPeriod"  class="form-control" value="<%=request.getParameter("datasource") != null && DataSource.database.get(request.getParameter("datasource")) != null ? DataSource.database.get(request.getParameter("datasource")).getIdleTestPeriod() : ""%>" data-toggle="tooltip" title="3000"/>
                        </div>

                        <div class="mb-3 col-md-5">
                            <label for="testSQL" class="form-label">SQL para teste da conexão (gravar antes de testar)</label>
                            <input type="text" id="testSQL" name="testSQL" placeholder="postgresql: SELECT 1; oracle: SELECT 1 FROM DUAL;" class="form-control" value="" data-toggle="tooltip" title="Informe um select para testar a conexão."/>
                        </div>
                        <div class="mb-3 col-md-1">
                            <button type="button" class="btn btn-primary" style="margin-top: 30px;" id="btn_testar" name="btn_testar">
                                <span class="spinner-border spinner-border-sm" style="display: none;" aria-hidden="true"></span>
                                Testar
                            </button>
                        </div>
                    </div>
                    <span id="sqlResult"></span>
                    <br>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-danger" id="btn_excluir" name="btn_excluir" >
                            <span class="spinner-border spinner-border-sm" style="display: none;" aria-hidden="true"></span>
                            Excluir
                        </button>
                        <div style="flex:1"></div>
                        <button type="button" class="btn btn-light" data-coreui-dismiss="modal">Fechar</button>
                        <button type="submit" class="btn btn-primary">
                            <span class="spinner-border spinner-border-sm" style="display: none;" aria-hidden="true"></span>
                            Gravar
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<%}%>