<%@page import="br.com.lowcodesystem.ctrl.Render"%>
<%@page import="br.com.lowcodesystem.dao.DataSource"%>
<%if (session.getAttribute("dev") != null && (Boolean) session.getAttribute("dev")) {%>
<form method="post" action="builder" id="formLogin">
    <div class="modal fade" id="modalLogin" data-coreui-backdrop="static" data-coreui-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
        <div class="modal-dialog modal-xl">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="staticBackdropLabel">Configurações de autenticação</h5>
                    <button type="button" class="btn-close" data-coreui-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <div class="container-fluid"> 
                        <input type="hidden" name="session" value="login"/>
                        <div class="form-row">
                            <div class="alert alert-info alert-dismissible fade show" role="alert">
                                Informe um SELECT para autenticar o usuário. O valor da senha será encriptado (MD5) antes de fazer a consulta. Se a consulta não retornar nenhum resultado, a autenticação será inválida. Caso retorne alguma informação, será autenticado e os dados retornados serão inseridos na sessão do usuário. <br>Ex. <b>SELECT 'canal'</b> (loga com perfil 'canal' independente do usuário e senha utilizado)
                                <button type="button" class="btn-close" data-coreui-dismiss="alert" aria-label="Close"></button>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-3">
                                <label class="form-label">Opções</label>
                                <select multiple class="form-control" style="height: 135px;" ondblclick="$('#sqlLogin').val($('#sqlLogin').val() + '\#{' + $(this).val() + '}')">
                                    <option title="user">user</option> 
                                    <option title="pass">pass</option> 
                                </select>
                            </div>
                            <div class="col-md-5">
                                <label for="sqlLogin" class="form-label">SELECT de autenticação</label>
                                <textarea id="sqlLogin" name="sqlLogin" class="form-control" placeholder="Informe o SQL" rows="5"
                                          title="SELECT id from usuario where login = '\#{user}' and senha = '\#{pass}'"><%=Render.project.getSqlLogin()%></textarea>
                            </div>
                            <div class="col-md-4">
                                <label for="opcoesLogin" class="form-label">Usuários fixos (perfil:usuario:senha)</label>
                                <textarea id="opcoesLogin" name="opcoesLogin" class="form-control" placeholder="perfil:usuario:senha" rows="5"
                                          title="perfil:usuario:senha"><%=Render.project.getOpcoesLogin()%></textarea>
                            </div>
                            <div class="col-md-4">
                                <label for="sqlLoginDS" class="form-label">Datasource</label>
                                <select id="sqlLoginDS" name="sqlLoginDS" class="form-control" size="1" style="height: 34px">	
                                    <% for (String key : DataSource.database.keySet()) {
                                            out.println("<option value=\"" + key + "\" " + (Render.project.getDatasource().equals(key) ? "selected" : "") + ">" + key + "</option>");
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
    </div>
</form>
<%}%>