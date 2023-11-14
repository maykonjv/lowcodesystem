<%-- 
    Document   : modal_login
    Created on : 03/04/2016, 17:57:49
    Author     : Maykon
--%>

<%@page import="br.com.lowcodesystem.ctrl.Render"%>
<%@page import="br.com.lowcodesystem.dao.DataSource"%>
<form method="post" action="builder" id="formLogin">
    <div class="modal fade" id="modalLogin" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog"  style="width: 100%; height: 100%;"> 
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">×</button>
                    <h3>Configurações de autenticação</h3>
                </div>
                <div class="modal-body">
                    <div class="row-fluid">
                        <div class="well span10">
                            <br/>
                            <input type="hidden" name="session" value="login"/>
                            <div class="alert alert-info">
                                <button type="button" class="close" data-dismiss="alert">×</button>
                                Informe um SELECT para autenticar o usuário. O valor da senha será encriptado (MD5) antes de fazer a consulta. Se a consulta não retornar nenhum resultado, a autenticação será inválida. Caso retorne alguma informação, será autenticado e os dados retornados serão inseridos na sessão do usuário. <br>Ex. <b>SELECT 'canal'</b> (loga com perfil 'canal' independente do usuário e senha utilizado)
                            </div>
                            <div>
                                <div class="form-row">
                                    <div class="col-xs-4 form-group">
                                        <label class="control-label">Opções</label>
                                        <select multiple class="form-control" style="height: 115px;" ondblclick="$('#sqlLogin').val($('#sqlLogin').val() + '\#{' + $(this).val() + '}')">
                                            <option title="user">user</option> 
                                            <option title="pass">pass</option> 
                                        </select>
                                    </div>
                                </div>
                                <div class="form-row">
                                    <div class="col-xs-4 form-group">
                                        <label for="sqlLogin" class="control-label">SELECT de autenticação</label>
                                        <textarea id="sqlLogin" name="sqlLogin" class="form-control" placeholder="Informe o SQL" rows="5"
                                                  title="SELECT id from usuario where login = '\#{user}' and senha = '\#{pass}'"><%=request.getAttribute("sqlLogin")%></textarea>
                                    </div>
                                </div>  
                                <div class="form-row">
                                    <div class="col-xs-4 form-group">
                                        <label for="opcoesLogin" class="control-label">Usuários fixos (perfil:usuario:senha)</label>
                                        <textarea id="opcoesLogin" name="opcoesLogin" class="form-control" placeholder="perfil:usuario:senha" rows="5"
                                                  title="perfil:usuario:senha"><%=request.getAttribute("opcoesLogin")%></textarea>
                                    </div>
                                </div>  
                                <div class="form-row" id="div_datasource">
                                    <div class="col-xs-4 form-group">
                                        <label for="sqlLoginDS" class="control-label">Datasource</label>
                                        <select id="sqlLoginDS" name="sqlLoginDS" class="form-control" size="1" style="height: 34px">	
                                            <% for (String key : DataSource.database.keySet()) {
                                                    out.println("<option value=\"" + key + "\" " + (Render.project.getDatasource().equals(key) ? "selected" : "") + ">" + key + "</option>");
                                                }%>
                                        </select>
                                    </div>
                                </div>
                            </div>
                            <br clear="all"/>
                            <input type="submit" class="hidden"/>
                        </div><!--/span-->
                    </div>
                </div>

                <div class="modal-footer">
                    <a href="#" class="btn btn-default" data-dismiss="modal">Fechar</a>
                    <input type="submit" class="btn btn-primary" value="Gravar">
                </div>
            </div>
        </div>
    </div>
</form>