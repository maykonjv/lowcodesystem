<%@page import="br.com.lowcodesystem.dao.DataSource"%>
<%@page import="br.com.lowcodesystem.util.ManterXML"%>
<%@page import="br.com.lowcodesystem.util.FormataTexto"%>
<%@page import="br.com.lowcodesystem.ctrl.Render"%>
<%if (session.getAttribute("dev") != null && (Boolean) session.getAttribute("dev")) {%>
<form   action="builder" method="post" id="formField">
    <input type="hidden" name="session" value="field"/>
    <input type="hidden" name="page" value="<%=request.getAttribute("page")%>"/>
    <input type="hidden" name="action" value="<%=request.getAttribute("action")%>"/>
    <input type="hidden" name="idFieldOld" value=""/>
    <div class="modal fade" id="modalField" data-coreui-backdrop="static" data-coreui-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
        <div class="modal-dialog modal-xl">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="staticBackdropLabel">Configuração de campos da página</h5>
                    <button type="button" class="btn-close" data-coreui-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <div class="row">
                        <div class="mb-3 col-md-4" id="div_id_field">
                            <label for="idField" class="form-label">ID:Name do campo</label>
                            <span style="color:Red;font-style: normal;font-weight:bold;" data-toggle="tooltip" title="Campo Obrigatório.">*</span>
                            <input type="text" id="idField" name="idField" class="form-control" value="" autofocus required placeholder="Informe ID:Name do campo." data-toggle="tooltip" title=" Caso informe somente um valor, esse será repetido para os dois parametros."/>
                        </div>
                        <div class="mb-3 col-md-2" id="div_component">
                            <label for="componentField" class="form-label">Componente</label>
                            <select id="componentField" name="componentField" class="form-select" size="1">	
                                <option value="text">Texto Simples</option>
                                <option value="area">Area de Texto</option>
                                <option value="number">Número</option>
                                <option value="password">Senha</option>
                                <option value="email">Email</option>
                                <option value="message">Mensagem</option>
                                <option value="label">Rótulo (apenas)</option>
                                <option value="select">Combo</option>
                                <option value="checkbox">Check</option>
                                <option value="radio">Radio</option>
                                <option value="color">Cor</option>
                                <option value="range">Variação (Range)</option>
                                <option value="url">URL</option>
                                <option value="file">Upload</option>
                                <option value="hidden">Oculto</option>
                                <option value="empty">Espaço vazio</option>
                                <option value="button">Botão</option>
                                <option value="submit">Botão Submit</option>
                                <option value="script">Botão Script</option>
                                <option value="reset">Botão Limpar</option>
                                <option value="free">Livre</option>
                                <option value="chart">Gráfico</option>
                                <option value="table">Tabela</option>
                            </select>
                        </div>
                        <div class="mb-3 col-md-4" id="div_label">
                            <label for="labelField" class="form-label">Rótulo do campo</label>
                            <input type="text" id="labelField" name="labelField" class="form-control" value="" placeholder="Informe Rótulo do campo"/>
                        </div>
                        <div class="mb-3 col-md-2" id="div_type">
                            <label for="typeField" class="form-label">Tipo de dados</label>
                            <select id="typeField" name="typeField" class="form-select" size="1">	
                                <option value="numeric">Numérico</option>
                                <option value="boolean">Boolean</option>
                                <option value="string" selected="true">String</option>
                                <option value="date">Date</option>
                            </select>
                        </div>
                        <div class="mb-3 col-md-4" id="div_defaultvalue">
                            <label for="defaultValueField" class="form-label">Valor padrão</label>
                            <input type="text" id="defaultValueField" name="defaultValueField" class="form-control" value="" placeholder="Informe Valor padrão"/>
                        </div>
                        <div class="mb-3 col-md-2" id="div_format">
                            <label for="formatField" class="form-label">Formato/Replace</label>
                            <input type="text" id="formatField" name="formatField" class="form-control" value="" data-toggle="tooltip" title="Caso o tipo de dados seja Data (informar formato da data - ex. dd/MM/yyyy), caso contrario (informar regex para replace de mascara - ex. [.-])."/>
                        </div>
                        <div class="mb-3 col-md-2" id="div_mask">
                            <label for="maskField" class="form-label">Máscara <a target="_blank" style="text-decoration: none;font-size: smaller;" href="https://igorescobar.github.io/jQuery-Mask-Plugin/">(Opções)</a></label>
                            <input type="text" id="maskField" name="maskField" class="form-control" value="" placeholder="00/00 00:00"/>
                        </div>
                        <div class="mb-3 col-md-4" id="div_title">
                            <label for="titleField" class="form-label">Title</label>
                            <input type="text" id="titleField" name="titleField" class="form-control" value="" placeholder="Informe Hint"/>
                        </div>
                        <div class="mb-3 col-md-4" id="div_placeholder">
                            <label for="placeholderField" class="form-label">Placeholder</label>
                            <input type="text" id="placeholderField" name="placeholderField" class="form-control" value="" placeholder="Informe Placeholder"/>
                        </div>
                        <div class="mb-3 col-md-2" id="div_size">
                            <label for="sizeField" class="form-label">Tamanho max.</label>
                            <input type="number" min="0" id="sizeField" name="sizeField" class="form-control" value="0" placeholder="Informe Tamanho"/>
                        </div>
                        <div class="mb-3 col-md-2" id="div_width">
                            <label for="widthField" class="form-label">Largura (width)</label>
                            <input type="number" min="1" max="12" size="2" maxlength="2" id="widthField" name="widthField" class="form-control" value="" placeholder="Informe Largura"/>
                        </div>
                        <div class="mb-3 col-md-2" id="div_datasource">
                            <label for="datasourceField" class="form-label">Datasource</label>
                            <select id="datasourceField" name="datasourceField" class="form-select" size="1">	
                                <%for (String key : DataSource.database.keySet()) {
                                        out.println("<option value=\"" + key + "\" " + (request.getParameter("datasourceField") != null && request.getParameter("datasourceField").equals(key) ? "selected" : "") + ">" + key + "</option>");
                                    }%>
                            </select>
                        </div>
                        <div class="mb-3 col-md-6" id="div_optionsField">
                            <label for="optionsField" class="form-label">Opções para escolha</label>
                            <textarea id="optionsField" name="optionsField" class="form-control" wrap='on' data-toggle="tooltip" title="Ex: valor=label" placeholder="Opções para escolha" rows="5" style="overflow: scroll"></textarea>
                        </div>
                        <div class="mb-3 col-md-6" id="div_sqlOptionsField">
                            <label for="sqlOptionsField" class="form-label">SQL</label>
                            <textarea id="sqlOptionsField" name="sqlOptionsField" class="form-control" data-toggle="tooltip" title="Ex: SELECT id, nome FROM table" rows="5" style="overflow: scroll"></textarea>
                        </div>
                        <div class="mb-3 col-md-4" id="div_style">
                            <label for="styleField" class="form-label">Style</label>
                            <input type="text" id="styleField" name="styleField" class="form-control" value="" placeholder="Informe Style css"/>
                        </div>
                        <div class="mb-3 col-md-2" id="div_classStyle">
                            <label for="classStyleField" class="form-label">Class</label>
                            <input type="text" id="classStyleField" name="classStyleField" class="form-control" value="" placeholder="Informe Classe css"/>
                        </div>
                        <div class="mb-3 col-md-2" id="div_scope">
                            <label for="scopeField" class="form-label">Escopo</label>
                            <select id="scopeField" name="scopeField" class="form-select" size="1">	
                                <option value="SCOPE_REQUEST">Request</option>
                                <option value="SCOPE_SESSION">Sessão</option>
                                <option value="SCOPE_APPLICATION">Aplicação</option>
                            </select>
                        </div>
                        <div class="mb-3 col-md-2" id="div_submit">
                            <div class="checkbox" style="margin-top: 45px;">
                                <input type="checkbox" id="submitOnchange" name="submitOnchange" class="form-check-input"/>
                                <label for="submitOnchange" class="form-check-label" data-toggle="tooltip" title="Evento onchage dispara o submit do formulário.">Evento Submit</label>
                            </div>
                        </div>
                        <div class="mb-3 col-md-2" id="div_required">
                            <div class="checkbox" style="margin-top: 45px;">
                                <input type="checkbox" id="requiredField" name="requiredField" class="form-check-input"/>
                                <label for="requiredField" class="form-check-label">Campo Obrigatório</label>
                            </div>
                        </div>
                        <div class="mb-3 col-md-6" id="div_type_action" style="margin-top: 45px">

                            <input type="checkbox" id="hasNewField" name="hasNewField" class="form-check-input"/>
                            <label for="hasNewField" class="form-check-label" style="margin-right: 10px">Novo</label>
                            <input type="checkbox" id="hasUpdateField" name="hasUpdateField" class="form-check-input"/>
                            <label for="hasUpdateField" class="form-check-label" style="margin-right: 10px">Alterar</label>
                            <input type="checkbox" id="hasViewField" name="hasViewField" class="form-check-input"/>
                            <label for="hasViewField" class="form-check-label" style="margin-right: 10px">Visualizar</label>
                            <input type="checkbox" id="hasSearchField" name="hasSearchField" class="form-check-input"/>
                            <label for="hasSearchField" class="form-check-label" style="margin-right: 10px">Pesquisar</label>
                        </div>
                        <div class="mb-3 col-md-6" id="div_table_options" style="margin-top: 25px">
                            <input type="checkbox" id="hasCheck" name="hasCheck" class="form-check-input"/>
                            <label for="hasCheck" class="form-check-label" style="margin-right: 10px">Checkbox</label>
                            <input type="checkbox" id="hasPaging" name="hasPaging" class="form-check-input"/>
                            <label for="hasPaging" class="form-check-label" style="margin-right: 10px">Paginação</label>
                            <input type="checkbox" id="hasOrdering" name="hasOrdering" class="form-check-input"/>
                            <label for="hasOrdering" class="form-check-label" style="margin-right: 10px">Ordenação</label>
                            <input type="checkbox" id="hasSearching" name="hasSearching" class="form-check-input"/>
                            <label for="hasSearching" class="form-check-label" style="margin-right: 10px">Filtro</label>
                            <input type="checkbox" id="hasInfo" name="hasInfo" class="form-check-input"/>
                            <label for="hasInfo" class="form-check-label" style="margin-right: 10px">Info</label>
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