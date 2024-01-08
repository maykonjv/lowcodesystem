<%@page import="br.com.lowcodesystem.model.Field"%>
<%@page import="java.util.Enumeration"%>
<%@page import="br.com.lowcodesystem.dao.DataSource"%>
<%@page import="br.com.lowcodesystem.model.Page"%>
<%@page import="br.com.lowcodesystem.util.ManterXML"%>
<%@page import="br.com.lowcodesystem.util.FormataTexto"%>
<%@page import="br.com.lowcodesystem.ctrl.Render"%>
<%if (session.getAttribute("dev") != null && (Boolean) session.getAttribute("dev")) {%>

<form action="builder" method="post" id="modalBackendActionForm">
    <input type="hidden" name="session" value="form-sql-updateview"/>
    <input type="hidden" name="page" value="<%=((Page) request.getAttribute("p")).getId()%>"/>
    <input type="hidden" name="action" value="<%=request.getAttribute("action")%>"/>
    <input type="hidden" name="sqlView"  value="<%=request.getAttribute("sqlView")%>"/>
    <div class="modal fade" id="modalBackendAction" data-coreui-backdrop="static" data-coreui-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
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
                            <select id="fieldsID" multiple class="form-control" style="height: 375px;" ondblclick="copyValue(this)">
                                <%
                                    for (Field f : ((Page) request.getAttribute("p")).getFields()) {
                                        if (f.isHasSearch()) {
                                            out.println("<option title=\"" + f.getId() + "\"> #{" + f.getId() + "}</option>");
                                        }
                                    }
                                %>
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
                            <div id="lbl_copy" style="color: green;font-weight: bold;text-align: end;display: none;">Copiado!</div>
                        </div>

                        <div class="mb-3 col-md-9">
                            <label for="sqlView" class="form-label">SQL de busca do registro para edição/visualização</label>
                            <span title="Informe um sql válido para preencher os campos da tela (Alteração/Visualização). Utilize alias com o mesmo id dos campos para fazer o vínculo. Utilize o parametro id na sua consulta, Ex. SELECT ... FROM ... WHERE id = \#{id}.">
                                <svg style="width: 16px" xmlns="http://www.w3.org/2000/svg" class="bi bi-exclamation-triangle-fill flex-shrink-0 me-2" viewBox="0 0 16 16" role="img" aria-label="Warning:">
                                    <path d="M8.982 1.566a1.13 1.13 0 0 0-1.96 0L.165 13.233c-.457.778.091 1.767.98 1.767h13.713c.889 0 1.438-.99.98-1.767L8.982 1.566zM8 5c.535 0 .954.462.9.995l-.35 3.507a.552.552 0 0 1-1.1 0L7.1 5.995A.905.905 0 0 1 8 5zm.002 6a1 1 0 1 1 0 2 1 1 0 0 1 0-2z"/>
                                </svg>                            
                            </span>
                            <div class="accordion" id="accordionExample">
                                <div class="accordion-item">
                                    <h2 class="accordion-header">
                                        <button class="accordion-button collapsed" type="button" data-coreui-toggle="collapse" data-coreui-target="#collapseEditorJSF" aria-expanded="false" aria-controls="collapseEditorJSF">
                                            Javascript Frontend
                                        </button>
                                    </h2>
                                    <div id="collapseEditorJSF" class="accordion-collapse collapse" data-coreui-parent="#accordionExample">
                                        <div class="accordion-body" style="padding: 0">
                                            <wc-monaco-editor 
                                                id="editorJSF" 
                                                language="javascript" 
                                                no-minimap 
                                                style="width: 100%; height:400px;">
                                            </wc-monaco-editor>
                                        </div>
                                    </div>
                                </div>
                                <div class="accordion-item">
                                    <h2 class="accordion-header">
                                        <button class="accordion-button" type="button" data-coreui-toggle="collapse" data-coreui-target="#collapseEditorSQL" aria-expanded="true" aria-controls="collapseEditorSQL">
                                            SQL
                                        </button>
                                    </h2>
                                    <div id="collapseEditorSQL" class="accordion-collapse collapse" data-coreui-parent="#accordionExample">
                                        <div class="accordion-body" style="padding: 0">
                                            <script type="module" src="libs/monaco/editor.main.js"></script>
                                            <script type="module" src="libs/monaco-editor.min.js"></script>
                                            <wc-monaco-editor 
                                                id="editorSQL" 
                                                language="sql" 
                                                no-minimap 
                                                style="width: 100%; height:400px;">
                                            </wc-monaco-editor>

                                        </div>
                                    </div>
                                </div>
                                <div class="accordion-item">
                                    <h2 class="accordion-header">
                                        <button class="accordion-button collapsed" type="button" data-coreui-toggle="collapse" data-coreui-target="#collapseEditorJSB" aria-expanded="false" aria-controls="collapseEditorJSB">
                                            Javascript Backend
                                        </button>
                                    </h2>
                                    <div id="collapseEditorJSB" class="accordion-collapse collapse" data-coreui-parent="#accordionExample">
                                        <div class="accordion-body" style="padding: 0">
                                            <wc-monaco-editor 
                                                id="editorJSB" 
                                                language="javascript" 
                                                no-minimap 
                                                style="width: 100%; height:400px;">
                                            </wc-monaco-editor>                                           
                                        </div>
                                    </div>
                                </div>
                            </div>

                        </div>

                        <div class="mb-3 col-md-4">
                            <label for="sqlViewDS" class="form-label">Datasource</label>
                            <select id="sqlViewDS" name="sqlViewDS" class="form-select" size="1">	
                                <% for (String key : DataSource.database.keySet()) {
                                        out.println("<option value=\"" + key + "\" " + (((Page) request.getAttribute("p")).getSqlUpdateViewDS().equals(key) ? "selected" : "") + ">" + key + "</option>");
                                    }%>
                            </select>
                        </div>
                    </div>
                </div>
                <br>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-light" data-coreui-dismiss="modal">Fechar</button>
                        <button type="button" id="save" class="btn btn-primary">Gravar</button>
                    </div>
            </div>
        </div>
    </div>
    <script>
        function fallbackCopyTextToClipboard(text) {
            var textArea = document.createElement("textarea");
            textArea.value = text;

            // Avoid scrolling to bottom
            textArea.style.top = "0";
            textArea.style.left = "0";
            textArea.style.position = "fixed";

            document.body.appendChild(textArea);
            textArea.focus();
            textArea.select();

            try {
                var successful = document.execCommand('copy');
                var msg = successful ? 'successful' : 'unsuccessful';
                console.log('Fallback: Copying text command was ' + msg);
                showLabelCopy();
            } catch (err) {
                console.error('Fallback: Oops, unable to copy', err);
            }

            document.body.removeChild(textArea);
        }

        function copyValue(copyTextarea) {
            var text = copyTextarea.selectedOptions[0].value;
            if ($('.accordion-collapse.collapse.show').length === 0) {
                return;
            }
            var id_opened = $('.accordion-collapse.collapse.show')[0].id;
            if (id_opened.includes('JS')) {
                text = text.replace(/[\#{}]/g, "");
            }
            if (!navigator.clipboard) {
                fallbackCopyTextToClipboard(text);
                return;
            }
            fallbackCopyTextToClipboard(text);
            navigator.clipboard.writeText(text).then(function () {
                console.log('Async: Copying to clipboard was successful!');
                showLabelCopy();
            }, function (err) {
                console.error('Async: Could not copy text: ', err);
            });
        }

        function showLabelCopy() {
            var lbl_copy = $('#lbl_copy');
            lbl_copy[0].style.display = 'block';
            setTimeout(() => {
                lbl_copy[0].style.display = 'none';
            }, 2000);
        }
    </script>
    <script type="module">
        import './libs/monaco/editor.main.js';
        editorJSB.value = "function teste() {\n    console.log('Hello World');\n}";
        editorSQL.value = "select * from produtos;\nselect * from clientes";
        editorJSF.value = "const fields = {\n";
        var options = $('#fieldsID')[0].options;
        for (var el in options) {
            var value = options[el].value;
            if (value) {
                value = value.replace(/[\#{}]/g, "");
                editorJSF.value += "    " + value + ": $('#" + value + "').val(),\n";
            }
        }
        editorJSF.value += "}\n\n";
        editorJSF.value += "const form = document.getElementById('form');\n";
        editorJSF.value += "form.submit();\n";
        
        $('#save').on('click', function (event) {
            var value = editorJSB.value;
            alert(value);
            var value = editorSQL.value;
            alert(value);
            var value = editorJSF.value;
            alert(value);
        });
    </script>
</form> 
<input type="hidden" id="copy_temp" name="copy_temp">

    <%}%>