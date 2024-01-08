<%@page import="br.com.lowcodesystem.model.Profile"%>
<%@page import="java.io.File"%>
<%@page import="br.com.lowcodesystem.model.Field"%>
<%@page import="br.com.lowcodesystem.model.Page"%>
<%@page import="br.com.lowcodesystem.ctrl.Render"%>
<%@page import="br.com.lowcodesystem.model.Menu"%>

<%
    Profile profile = null;
    String perfil = session.getAttribute("perfil").toString();
    int index = Render.profiles.indexOf(new Profile(perfil));
    if (index >= 0) {
        profile = Render.profiles.get(index);
    }

    if (profile != null) {
%>

<div id="div_page_container">
    <% Object error = request.getAttribute("error");
        if (error != null && !error.toString().isEmpty()) {
            out.println("<div class=\"alert alert-danger alert-dismissible fade show\" role=\"alert\">");
            out.println("<button type=\"button\" class=\"btn-close\" data-coreui-dismiss=\"alert\" aria-label=\"Close\"></button>");
            out.println(error);
            out.println("</div>");
        }
        Object success = request.getAttribute("success");
        if (success != null && !success.toString().isEmpty()) {
            out.println("<div class=\"alert alert-success alert-dismissible fade show\" role=\"alert\">");
            out.println("<button type=\"button\" class=\"btn-close\" data-coreui-dismiss=\"alert\" aria-label=\"Close\"></button>");
            out.println(success);
            out.println("</div>");
        }
    %>

    <% Page p = (Page) request.getAttribute("p");
        if (p != null && (profile.getActions().get(p.getId()) != null && profile.getActions().get(p.getId()).isCustom() || perfil.equals(Render.ADMINISTRADOR))) {
            if ((request.getAttribute("action").toString().equals(Render.ACTION_ADD) && profile.getActions().get(p.getId()) != null && profile.getActions().get(p.getId()).isAdd())
                    || (request.getAttribute("action").toString().equals(Render.ACTION_ACTIVATE) && profile.getActions().get(p.getId()) != null && profile.getActions().get(p.getId()).isActive())
                    || (request.getAttribute("action").toString().equals(Render.ACTION_ALTER) && profile.getActions().get(p.getId()) != null && profile.getActions().get(p.getId()).isUpdate())
                    || (request.getAttribute("action").toString().equals(Render.ACTION_BASE) && profile.getActions().get(p.getId()) != null && profile.getActions().get(p.getId()).isCustom())
                    || (request.getAttribute("action").toString().equals(Render.ACTION_DELETE) && profile.getActions().get(p.getId()) != null && profile.getActions().get(p.getId()).isDelete())
                    || (request.getAttribute("action").toString().equals(Render.ACTION_LIST) && profile.getActions().get(p.getId()) != null && profile.getActions().get(p.getId()).isList())
                    || (request.getAttribute("action").toString().equals(Render.ACTION_SEARCH) && profile.getActions().get(p.getId()) != null && (profile.getActions().get(p.getId()).isSearch() || profile.getActions().get(p.getId()).isList()))
                    || (request.getAttribute("action").toString().equals(Render.ACTION_VIEW) && profile.getActions().get(p.getId()) != null && profile.getActions().get(p.getId()).isView())
                    || (perfil.equals(Render.ADMINISTRADOR))) {

    %>

    <div class="card">
        <h5 class="card-header">
            <%=p.getName()%>
        </h5>  
        <div class="card-body" style="padding-bottom: 0px">
            <form role="form" method="POST" action="render" id="form">
                <input type="hidden" name="page" id="page" value="<%=request.getParameter("page")%>"/>
                <input type="hidden" name="action" id="action" value="<%=request.getAttribute("action")%>"/>

                <%
                    if (request.getAttribute("action").toString().equals(Render.ACTION_ALTER)) {
                        out.println("<input "
                                + "type=\"hidden\" "
                                + "id=\"id\" "
                                + "name=\"id\" "
                                + "value=\"" + request.getParameter("id") + "\" "
                                + "/>");
                    }
                    if (!request.getAttribute("action").toString().equals(Render.ACTION_LIST)) {
                %>
                <header class="header p-0">
                    <div class="row flex-grow-1">
                        <%
                            for (Field f : p.getFields()) {
                                if ((f.isHasNew() && request.getAttribute("action").toString().equals(Render.ACTION_ADD) && (profile.getActions().get(p.getId()) != null && profile.getActions().get(p.getId()).isAdd() || perfil.equals(Render.ADMINISTRADOR)))
                                        || (f.isHasUpdate() && request.getAttribute("action").toString().equals(Render.ACTION_ALTER) && (profile.getActions().get(p.getId()) != null && profile.getActions().get(p.getId()).isUpdate() || perfil.equals(Render.ADMINISTRADOR)))
                                        || (f.isHasView() && request.getAttribute("action").toString().equals(Render.ACTION_VIEW) && (profile.getActions().get(p.getId()) != null && profile.getActions().get(p.getId()).isView() || perfil.equals(Render.ADMINISTRADOR)))
                                        || (f.isHasSearch() && request.getAttribute("action").toString().equals(Render.ACTION_SEARCH) && (profile.getActions().get(p.getId()) != null && profile.getActions().get(p.getId()).isSearch() || perfil.equals(Render.ADMINISTRADOR)))
                                        || (request.getAttribute("action").toString().equals(Render.ACTION_ALL))
                                        || (request.getAttribute("action").equals(Render.ACTION_BASE) && (profile.getActions().get(p.getId()) != null && profile.getActions().get(p.getId()).isCustom() || perfil.equals(Render.ADMINISTRADOR)))) {
                                    String id, name;
                                    if (f.getId().contains(":")) {
                                        id = f.getId().split(":")[0];
                                        name = f.getId().split(":")[1];
                                    } else {
                                        id = f.getId();
                                        name = f.getId();
                                    }
                                    if (f.getComponent().equals("hidden") || f.getComponent().equals("free") || f.getComponent().startsWith("chart")) {
                                        if (f.getComponent().equals("hidden")) {
                                            out.println("   <input type=\"" + f.getComponent() + "\" id=\"" + id + "\" name=\"" + name + "\" value=\"" + ((request.getAttribute(name) == null) ? f.getDefaultValue() : request.getAttribute(name)) + "\"/>");
                                        } else {
                                            out.println("    <div class=\"col-md-" + f.getWidth() + " " + ((f.getWidth() <= 6) ? "col-sm-6" : "") + " col-xs-12 mb-3\">");
                                            if (request.getAttribute(f.getId()) != null) {
                                                out.println(request.getAttribute(f.getId()));
                                            } else {
                                                out.println(f.getDefaultValue());
                                            }
                                        }
                                        if (session.getAttribute("dev") != null && (Boolean) session.getAttribute("dev")) {
                                            out.println("<div class=\"lcs-settings\">");
                                            out.println("   <a href=\"#" + f.getId() + "\" class=\"btn-field-up\" title=\"Mover campo para esquerda\" >");
                                            out.println("        <i class=\"fa fa-arrow-left\"></i>");
                                            out.println("   </a>");
                                            out.println("   <a href=\"#" + f.getId() + "\" class=\"btn-field-open\" title=\"Configurar campo\" >");
                                            out.println("        <i class=\"fa fa-cog\"></i>");
                                            out.println("   </a>");
                                            out.println("   <a href=\"#" + f.getId() + "\" class=\"btn-field-remove\" title=\"Excluir campo\" >");
                                            out.println("        <i class=\"fa fa-trash\"></i>");
                                            out.println("   </a>");
                                            out.println("</div>");
                                        }
                                        if (f.getComponent().equals("free") || f.getComponent().startsWith("chart")) {
                                            out.println("</div>");
                                        }
                                        continue;
                                    }

                                    out.println("    <div class=\"col-md-" + f.getWidth() + " " + ((f.getWidth() <= 6) ? "col-sm-6" : "") + " col-xs-12 mb-3\">");
                                    String styleClass = "form-control";
                                    String style = "";
                                    String disabled = "";
                                    String extensions = "";
                                    if (request.getAttribute("action").toString().equals(Render.ACTION_VIEW)) {
                                        disabled = "disabled";
                                    }
                                    if (!f.getComponent().equals("checkbox") && !f.getComponent().equals("empty")
                                            && !f.getComponent().equals("button") && !f.getComponent().equals("submit") && !f.getComponent().equals("reset") && !f.getComponent().equals("script")) {
                                        out.println("        <label for=\"" + id + "\" class=\"form-label\">" + f.getLabel() + "</label>");
                                        if (f.isRequired() && !request.getAttribute("action").toString().equals(Render.ACTION_SEARCH)) {
                                            out.println("<span style=\"color:Red;font-style:normal;font-weight:bold;\" title=\"Campo Obrigatório.\">*</span>");
                                        }
                                    } else {
                                        if (f.getComponent().equals("button") || f.getComponent().equals("submit") || f.getComponent().equals("reset") || f.getComponent().equals("script")) {
                                            out.println("<div class=\"" + f.getComponent() + "\" style=\"margin-top: 25px;\">");
                                        } else {
                                            out.println("<div class=\"" + f.getComponent() + "\" style=\"margin-top: 30px;\">");
                                        }
                                        styleClass = "";
                                        style = "margin-left: 0px;";
                                    }
                                    if (f.getComponent().equals("file")) {
                                        styleClass = "file";
                                        if (!f.getOptions().isEmpty()) {
                                            extensions = "data-allowed-file-extensions=' " + f.getOptions() + "'";
                                        }
                                    }
                                    if (f.getComponent().equals("range")) {
                                        styleClass = "form-range";
                                        out.println(" <div id=\"slider\">");
                                    } else {
                                        out.println(" <div>");
                                    }
                                    if (f.getComponent().equals("label") || f.getComponent().equals("empty")) {
                                        out.println(" <div style=\"" + f.getStyle() + " \" class=\"" + f.getClassStyle() + "\" \"></div>");
                                        if (f.getComponent().equals("empty")) {
                                            out.println("</div>");
                                        }
                                    } else if (f.getComponent().equals("table")) {
                                        out.println(request.getAttribute(f.getId()));
                                    } else if (f.getComponent().equals("message")) {
                                        out.println("<div id=\"msg_" + f.getId() + "\" class=\"alert alert-info " + f.getClassStyle() + "\" style=\"" + f.getStyle() + "\" \">");
                                        out.println("<button type=\"button\" class=\"close\" data-dismiss=\"alert\">×</button>");
                                        out.println(f.getDefaultValue());
                                        out.println("</div>");
                                    } else if (f.getComponent().equals("button") || f.getComponent().equals("submit") || f.getComponent().equals("reset")) {
                                        out.println("        <input "
                                                + "type=\"" + f.getComponent() + "\" "
                                                + "id=\"" + id + "\" "
                                                + "name=\"" + name + "\" "
                                                + "value=\"" + f.getLabel() + "\" "
                                                + ""
                                                + "title=\"" + f.getTitle() + "\""
                                                + "style=\"" + f.getStyle() + "\" "
                                                + "class=\"form-control " + f.getClassStyle() + "\" "
                                                + "/>");
                                        out.println("</div>");
                                    } else if (f.getComponent().equals("script")) {
                                        out.println("  <input "
                                                + "type=\"submit\" "
                                                + "id=\"" + id + "\" "
                                                + "name=\"" + name + "\" "
                                                + "value=\"" + f.getLabel() + "\" "
                                                + ""
                                                + "title=\"" + f.getTitle() + "\""
                                                + "style=\"" + f.getStyle() + "\" "
                                                + "class=\"form-control " + f.getClassStyle() + "\" "
                                                + "/>");
                                        out.println("</div>");
                                    } else if (f.getComponent().equals("select")) {
                                        out.println("<select id=\"" + id + "\" name=\"" + name + "\" class=\"form-select " + f.getClassStyle() + "\" style=\"" + f.getStyle() + "\" size=\"1\"" + ((f.isRequired() && !request.getAttribute("action").toString().equals(Render.ACTION_SEARCH)) ? " required " : "") + disabled + " " + (f.isSubmitOnchange() ? "onchange=\"document.getElementById('form').submit();\"" : "") + ">");
                                        for (String opcao : f.getOptions()) {
                                            if (opcao.contains("=")) {
                                                String[] opt = opcao.split("=");
                                                String valueDefault = "";
                                                if (request.getAttribute(name) == null) {
                                                    if (opt[0].equals(f.getDefaultValue())) {
                                                        valueDefault = " selected=\"selected\" ";
                                                    }
                                                } else {
                                                    if (opt[0].equals(request.getAttribute(name).toString())) {
                                                        valueDefault = " selected=\"selected\" ";
                                                    }
                                                }
                                                if (opt.length > 1) {
                                                    out.println("<option value=\"" + ((opt[0] == null || opt[0].toString().equals("null")) ? "" : opt[0]) + "\"" + valueDefault + ">" + opt[1] + "</option>");
                                                } else {
                                                    out.println("<option value=\"" + ((opt[0] == null || opt[0].toString().equals("null")) ? "" : opt[0]) + "\"" + valueDefault + ">" + opt[0] + "</option>");
                                                }
                                            } else {
                                                out.println("<option value=\"" + ((opcao == null || opcao.equals("null")) ? "" : opcao) + "\"" + (opcao.equals(f.getDefaultValue()) ? " selected=\"selected\" " : " ") + ">" + opcao + "</option>");
                                            }
                                        }
                                        out.println("</select>");
                                    } else if (f.getComponent().equals("radio")) {
                                        out.println("<div style='display: flex; flex-wrap: wrap;'>");
                                        int id_rb = 0;
                                        for (String opcao : f.getOptions()) {
                                            id_rb++;
                                            if (opcao.contains("=")) {
                                                String[] opt = opcao.split("=");
                                                String valueDefault = "";
                                                String value = opt.length > 0 ? opt[0] : "";
                                                String label = opt.length > 1 ? opt[1] : "";

                                                if (request.getAttribute(name) == null) {
                                                    valueDefault = f.getDefaultValue();
                                                } else {
                                                    valueDefault = request.getAttribute(name).toString();
                                                }
                                                out.println("<div class=\"form-check\" style='" + f.getStyle() + "'>");
                                                out.println("<input "
                                                        + "type=\"radio\" "
                                                        + "id=\"" + id + id_rb + "rb\" "
                                                        + "name=\"" + name + "rb\" "
                                                        + "title=\"" + f.getTitle() + "\" "
                                                        + "style=\"margin: 5px;" + style + ";" + "\" "
                                                        + disabled
                                                        + (value.equals(valueDefault) ? " checked " : "")
                                                        + " value=\"" + value + "\" "
                                                        + " class=\"form-check-input " + f.getClassStyle() + "\" "
                                                        + "/>");
                                                out.println(" <label class=\"form-check-label\" for=\"" + id + id_rb + "rb\">" + label + "</label>");
                                                out.println("</div>");
                                            }
                                        }
                                        out.println("</div>");
                                    } else if (f.getComponent().equals("area")) {
                                        out.println("        <textarea id=\"" + id + "\" "
                                                + "name=\"" + name + "\" "
                                                + ""
                                                + "title=\"" + f.getTitle() + "\" "
                                                + "placeholder=\"" + f.getPlaceholder() + "\" "
                                                + "row=\"5\" "
                                                + " style=\" " + f.getStyle() + "\" "
                                                + " class=\"form-control " + f.getClassStyle() + "\" "
                                                + ((f.isRequired() && !request.getAttribute("action").toString().equals(Render.ACTION_SEARCH)) ? " required " : "")
                                                + disabled + ">"
                                                + ((request.getAttribute(name) == null) ? f.getDefaultValue() : request.getAttribute(name))
                                                + "</textarea>");
                                    } else if (f.getComponent().equals("checkbox")) {
                                        out.println("<div style='height: 40px; display: flex; align-items: end'>");
                                        out.println("<input "
                                                + "type=\"hidden\" "
                                                + "id=\"" + id + "\" "
                                                + "name=\"" + name + "\" "
                                                + "value=\"" + ((request.getAttribute(name) == null) ? f.getDefaultValue() : request.getAttribute(name)) + "\" "
                                                + " style=\"" + f.getStyle() + "\" "
                                                + " class=\"" + f.getClassStyle() + "\" "
                                                + "/>");
                                        out.println("<input "
                                                + "type=\"checkbox\" "
                                                + "id=\"" + id + "ckb\" "
                                                + "name=\"" + name + "ckb\" "
                                                + ""
                                                + "title=\"" + f.getTitle() + "\" "
                                                + "onclick=\"$('#" + id + "').val($(this).is(':checked') ? 'true' : 'false');\""
                                                + "placeholder=\"" + f.getPlaceholder() + "\" "
                                                + "style=\"margin: 5px;" + style + ";" + f.getStyle() + "\" "
                                                + disabled
                                                + ((request.getAttribute(name) == null) ? (f.getDefaultValue().equals("true") ? " checked=\"checked\"" : "") : (request.getAttribute(name).toString().equals("true") ? " checked=\"checked\"" : ""))
                                                + " class=\"form-check-input " + styleClass + " " + f.getClassStyle() + "\" "
                                                + "/>");
                                        out.println("        <label for=\"" + id + "ckb\" class=\"form-check-label\" \">" + f.getLabel() + "</label>");
                                        if (f.isRequired() && !request.getAttribute("action").toString().equals(Render.ACTION_SEARCH)) {
                                            out.println("<span style=\"color:Red;font-style:normal;font-weight:bold;\" title=\"Campo Obrigatório.\">*</span>");
                                        }
                                        out.println("</div>");
                                        out.println("</div>");
                                    } else {
                                        if (f.getComponent().equals("color")) {
                                            out.println("<div style='display: flex;'>");
                                            style = "width: 100px;height: 38px;border-right: none;border-radius: 0.375rem 0rem 0rem 0.375rem;padding: 0px;overflow: hidden;";
                                        }
                                        out.println("        <input "
                                                + "type=\"" + f.getComponent() + "\" "
                                                + "id=\"" + ((f.getComponent().equals("file")) ? "file_" : "") + id + "\" "
                                                + "name=\"" + ((f.getComponent().equals("file")) ? "file_" : "") + name + "\" "
                                                + ""
                                                + "title=\"" + f.getTitle() + "\" "
                                                + ((f.getMask() != null && !f.getMask().isEmpty()) ? "data-mask=\"" + f.getMask() + "\"" : "")
                                                + ((f.getSize() > 0) ? "maxlength=\"" + f.getSize() + "\" size= \"" + f.getSize() + "\" " : "")
                                                + "value=\"" + ((f.getComponent().equals("radio")) ? f.getDefaultValue() : ((request.getAttribute(name) == null) ? f.getDefaultValue() : request.getAttribute(name))) + "\" "
                                                + "placeholder=\"" + f.getPlaceholder() + "\" "
                                                + ((f.getComponent().equals("range")) ? " onchange=\"rangevalue" + id + ".value=value\"" : "")
                                                + "style=\" " + style + ";" + f.getStyle() + "\" "
                                                + extensions + " "
                                                + ((f.isRequired() && !f.getComponent().equals("file") && !request.getAttribute("action").toString().equals(Render.ACTION_SEARCH)) ? " required " : "")
                                                + disabled
                                                + ((request.getAttribute(name) != null && request.getAttribute(name).toString().equals(f.getDefaultValue()) ? " checked=\"checked\"" : ""))
                                                + " class=\"" + styleClass + " " + f.getClassStyle() + "\" "
                                                + "/>"
                                        );
                                        if (f.getComponent().equals("color")) {
                                            String value = "#000000";
                                            if (request.getAttribute(name) != null) {
                                                value = request.getAttribute(name).toString();
                                            }
                                            out.println("<input type='text' value='" + value + "' name='" + id + "_iptColor' style='border-left: none;border-radius: 0rem 0.375rem 0.375rem 0rem;' class='form-control' id='" + id + "_iptColor'>");
                                            out.println("<script>$('#" + id + "_iptColor" + "').change(function(){ $('#" + id + "').val($(this).val());});</script>");
                                            out.println("<script>$('#" + id + "').change(function(){ $('#" + id + "_iptColor" + "').val($(this).val());});</script>");
                                            out.println("</div>");
                                        }
                                        if (f.getComponent().equals("file")) {
                                            out.println("<input type=\"hidden\" id=\"" + id + "\" name=\"" + name + "\" value=\"" + ((request.getAttribute(name) == null) ? "" : request.getAttribute(name)) + "\"/>");
                                            out.println("<input type=\"hidden\" id=\"tmp_name_" + id + "\" name=\"tmp_name_" + name + "\" value=\"" + ((request.getAttribute(name) == null) ? "" : request.getAttribute(name)) + "\"/>");
                                            out.println("<script>$(\"#file_" + id + "\").fileinput({language: 'pt-BR', uploadUrl: 'upload', uploadAsync: true" + ((f.getSize() > 0) ? ", maxFileSize:" + f.getSize() : "") + ((request.getAttribute(name) == null || request.getAttribute(name).toString().isEmpty()) ? "" : ",  initialPreview: [\"<img style='max-width:160px;max-height:160px;' src='upload/" + (request.getAttribute(name)) + "'>\"]") + "}).on('fileuploaded', function(event, data) {$('#" + id + "').val(data.response.name);});</script>");
                                        }
                                        if (f.getComponent().equals("range")) {
                                            out.println("<span class=\"highlight\"></span><output class=\"rangevalue\" id=\"rangevalue" + id + "\">50</output>");
                                        }
                                    }
                                    if (session.getAttribute("dev") != null && (Boolean) session.getAttribute("dev")) {
                                        out.println("<div class=\"lcs-settings\">");
                                        out.println("   <a href=\"#" + f.getId() + "\" class=\"btn-field-up\" title=\"Mover campo para esquerda\">");
                                        out.println("        <i class=\"fa fa-arrow-left\"></i>");
                                        out.println("   </a>");
                                        out.println("   <a href=\"#" + f.getId() + "\" class=\"btn-field-open \" title=\"Configurar campo\" data-coreui-toggle=\"modal\" data-coreui-target=\"#modalField\">");
                                        out.println("        <i class=\"fa fa-cog\"></i>");
                                        out.println("   </a>");
                                        out.println("   <a href=\"#" + f.getId() + "\" class=\"btn-field-remove \" title=\"Excluir campo\" >");
                                        out.println("        <i class=\"fa fa-trash\"></i>");
                                        out.println("   </a>");
                                        out.println("</div>");
                                    }
                                    out.println(" </div>");
                                    out.println("</div>");
                                }
                            }
                        %>
                    </div>
                </header>
                <div class="card_actions_form" style="padding: 10px">
                    <%  if ((request.getAttribute("action").toString().equals(Render.ACTION_LIST)
                                || request.getAttribute("action").toString().equals(Render.ACTION_SEARCH)
                                || request.getAttribute("action").toString().equals(Render.ACTION_VIEW)
                                || request.getAttribute("action").toString().equals(Render.ACTION_ALTER)
                                || request.getAttribute("action").toString().equals(Render.ACTION_ALL)) && p.isHasNew()
                                && (profile.getActions().get(p.getId()) != null && profile.getActions().get(p.getId()).isAdd() || perfil.equals(Render.ADMINISTRADOR))) {%>
                    <div class="col-md-2 col-sm-6 col-xs-12" style="float: left">
                        <a id="pg_novo_<%= p.getId()%>" href="render?page=<%= p.getId()%>&action=<%=Render.ACTION_ADD%>" class="btn btn-primary form-control" data-dismiss="modal" style="float: left; ">Novo</a>
                    </div>
                    <%}%>
                    <% if ((request.getAttribute("action").toString().equals(Render.ACTION_ADD) || request.getAttribute("action").toString().equals(Render.ACTION_ALTER) || request.getAttribute("action").toString().equals(Render.ACTION_ALL)) && (p.isHasNew() || p.isHasUpdate())) {%>
                    <div class="col-md-2 col-sm-6 col-xs-12" style="float: right; margin-left: 25px;">
                        <input  id="pg_save_<%= p.getId()%>" type="submit" class="btn btn-primary form-control" id="btnGravar" name="btnGravar" style="" value="Gravar">
                    </div>
                    <div class="col-md-2 col-sm-6 col-xs-12" style="float: right">
                        <a id="pg_back_<%= p.getId()%>" href="render?page=<%= p.getId()%>" id="btn_voltar" class="btn btn-outline-primary form-control mx-3" style="" data-dismiss="modal">Cancelar</a>
                    </div>
                    <%}%>

                    <% if ((request.getAttribute("action").toString().equals(Render.ACTION_SEARCH) || request.getAttribute("action").toString().equals(Render.ACTION_ALL)) && p.isHasSearch()
                                && (profile.getActions().get(p.getId()) != null && profile.getActions().get(p.getId()).isSearch() || perfil.equals(Render.ADMINISTRADOR))) {%>
                    <div class="col-md-2 col-sm-6 col-xs-12" style="float: right">
                        <!--hx-target="#div_page_container" hx-post="render"  -->
                        <input 
                            id="pg_search_<%= p.getId()%>" 
                            type="submit" 
                            class="btn btn-primary form-control" 
                            id="btnPesquisar" 
                            onclick="document.getElementById('btnExportXLSX').value = 'false';" 
                            style="" 
                            name="btnPesquisar" 
                            value="Pesquisar">
                    </div>
                    <div class="" style="float: right">
                        <script type="text/javascript">
                            function submitlink()
                            {
                                var oForm = document.getElementById("form");
                                oForm.method = "GET";
                                document.getElementById("btnExportXLSX").value = "true";
                                oForm.submit();
                            }
                        </script>
                        <input type="hidden" name="btnExportXLSX" id="btnExportXLSX" value="false">
                        <button id="pg_export_<%= p.getId()%>" type="button" class="btn btn-outline-primary" onclick="submitlink()" name="btnExportXLSX" style="padding: 10px; margin: 0 10px 0 10px">
                            <i class="fa fa-file-excel-o primary" style="color: green" ></i>
                        </button>
                    </div>
                    <div class="col-md-2 col-sm-6 col-xs-12" style="float: right">
                        <a id="pg_clear_<%= p.getId()%>" href="render?page=<%= p.getId()%>&clear=true" style="" class="btn btn-outline-primary form-control">Limpar</a>
                    </div>
                    <%}%>

                    <% if ((request.getAttribute("action").toString().equals(Render.ACTION_VIEW) || request.getAttribute("action").toString().equals(Render.ACTION_ALL)) && p.isHasView()) {%>
                    <div class="col-md-2 col-sm-6 col-xs-12" style="float: right">
                        <a id="pg_back_<%= p.getId()%>" href="render?page=<%= p.getId()%>" id="btn_voltar" class="btn btn-outline-primary form-control" style="" data-dismiss="modal">Voltar</a>
                    </div>
                    <%}%>

                </div>
                <div class="clearfix"></div>
                <%
                    if (session.getAttribute("dev") != null && (Boolean) session.getAttribute("dev")) {
                        out.println("<div class=\"lcs-settings\">");
                        if ((p.isHasUpdate() || p.isHasView()) && (request.getAttribute("action").toString().equals(Render.ACTION_ALTER) || request.getAttribute("action").toString().equals(Render.ACTION_VIEW) || request.getAttribute("action").toString().equals(Render.ACTION_ALL))) {
                            out.println("   <a href=\"#" + p.getId() + "\" class=\"btn-sql-updateview-form\" title=\"Configurar visualização do registro (Alteração/Visualização)\" data-coreui-toggle=\"modal\" data-coreui-target=\"#modalSQLView\" >");
                            out.println("        <i class=\"fa fa-cog\"></i>");
                            out.println("   </a>");
                        }
                        if ((p.isHasUpdate()) && (request.getAttribute("action").toString().equals(Render.ACTION_ALTER) || request.getAttribute("action").toString().equals(Render.ACTION_ALL))) {
                            out.println("   <a href=\"#" + p.getId() + "\" class=\"btn-edit\" title=\"Configurar função alterar registro\" data-coreui-toggle=\"modal\" data-coreui-target=\"#modalSQLEdit\" >");
                            out.println("        <i class=\"fa fa-cog\"></i>");
                            out.println("   </a>");
                        }
                        if ((p.isHasNew()) && (request.getAttribute("action").toString().equals(Render.ACTION_ADD) || request.getAttribute("action").toString().equals(Render.ACTION_ALL))) {
                            out.println("   <a href=\"#" + p.getId() + "\" class=\"btn-save\" title=\"Configurar função gravar novo registro\" data-coreui-toggle=\"modal\" data-coreui-target=\"#modalSQLSave\" >");
                            out.println("        <i class=\"fa fa-cog\"></i>");
                            out.println("   </a>");
                        }
                        out.println("</div>");
                    }
                %>
                <div class="clearfix"></div>
                <%}%>
                <div class="clearfix"></div>
                <%if (request.getAttribute("action").toString().equals(Render.ACTION_LIST)
                            || request.getAttribute("action").toString().equals(Render.ACTION_SEARCH)
                            || request.getAttribute("action").toString().equals(Render.ACTION_DELETE)
                            || request.getAttribute("action").toString().equals(Render.ACTION_ACTIVATE)
                            || request.getAttribute("action").toString().equals(Render.ACTION_ALL)) {%>
                <div class="clearfix"></div>
                <%  if (request.getAttribute("action").toString().equals(Render.ACTION_LIST) && p.isHasNew()) {%>
                <div class="col-md-2 col-sm-6 col-xs-12" style="float: left">
                    <a id="pg_novo_<%= p.getId()%>" href="render?page=<%= p.getId()%>&action=<%=Render.ACTION_ADD%>" class="btn btn-primary form-control" data-dismiss="modal" style="float: left; margin: 15px 13px 13px 13px;">Novo</a>
                </div>
                <%}
                            if (session.getAttribute("dev") != null && (Boolean) session.getAttribute("dev")) {
                                out.println("<div class=\"lcs-settings mx-2\">");
                                if ((p.isHasSearch() || p.isHasList() || p.isHasActive() || p.isHasDelete()) && (request.getAttribute("action").toString().equals(Render.ACTION_ALL) || request.getAttribute("action").toString().equals(Render.ACTION_SEARCH) || request.getAttribute("action").toString().equals(Render.ACTION_LIST))) {
                                    out.println("   <a href=\"#" + p.getId() + "\" class=\"btn-sql-pesquisa-form\" title=\"Configurar pesquisa (preenche a grid)\" data-coreui-toggle=\"modal\" data-coreui-target=\"#modalSQLSearch\">");
                                    out.println("        <i class=\"fa fa-cog\"></i>");
                                    out.println("   </a>");
                                    out.println("   <a href=\"#" + p.getId() + "\" class=\"btn-sql-export-form\" title=\"Configurar pesquisa (exportar planilha)\" data-coreui-toggle=\"modal\" data-coreui-target=\"#modalSQLExport\">");
                                    out.println("        <i class=\"fa fa-cog\"></i>");
                                    out.println("   </a>");
                                }
                                if (p.isHasActive() && (request.getAttribute("action").toString().equals(Render.ACTION_ALL) || request.getAttribute("action").toString().equals(Render.ACTION_SEARCH) || request.getAttribute("action").toString().equals(Render.ACTION_LIST))) {
                                    out.println("   <a href=\"#" + p.getId() + "\" class=\"btn-activate\" title=\"Configurar função ativar/inativar registro\" data-coreui-toggle=\"modal\" data-coreui-target=\"#modalSQLActivate\">");
                                    out.println("        <i class=\"fa fa-cog\"></i>");
                                    out.println("   </a>");
                                }
                                if (p.isHasDelete() && (request.getAttribute("action").toString().equals(Render.ACTION_ALL) || request.getAttribute("action").toString().equals(Render.ACTION_SEARCH) || request.getAttribute("action").toString().equals(Render.ACTION_LIST))) {
                                    out.println("   <a href=\"#" + p.getId() + "\" class=\"btn-delete\" title=\"Configurar função excluir registro\" data-coreui-toggle=\"modal\" data-coreui-target=\"#modalSQLDelete\">");
                                    out.println("        <i class=\"fa fa-cog\"></i>");
                                    out.println("   </a>");
                                }
                                out.println("   <a href=\"#" + p.getId() + "\" class=\"btn-backend-action\" title=\"Configurar função excluir registro\" data-coreui-toggle=\"modal\" data-coreui-target=\"#modalBackendAction\">");
                                out.println("        <i class=\"fa fa-server\"></i>");
                                out.println("   </a>");
                                out.println("</div>");
                            }
                            //                <!--INICIO TABLE-->
                            out.println("<div class=\"clearfix\"></div>");
                            out.println("<!-- https://datatables.net/examples/index -->");
                            if (profile.getActions().get(p.getId()) != null && profile.getActions().get(p.getId()).isList() || perfil.equals(Render.ADMINISTRADOR)) {
                                out.println(request.getAttribute("result"));
                            }
                            //                <!--FIM TABLE-->
                        }
                    }
                %>
            </form>
        </div>
    </div>
</div>
<% } else {
            if (session.getAttribute("dev") != null && (Boolean) session.getAttribute("dev")) {
                if (request.getParameter("page") != null && request.getParameter("page").equals("all")) {
                    out.println("<div class='col-md-12'>");
                    for (Page pg : Render.pages) {
                        out.println("<br/>Página: <a title='Abrir página' href=\"render?page=" + pg.getId() + "\" >" + pg.getName() + "</a> (Id: " + pg.getId() + ") <a class='text-decoration-none mx-2' title='Baixar arquivo xml' href='process?action=downloadPage&id=" + pg.getId() + "'><i class='fa fa-file-excel-o'></i></a>");
                    }
                    out.println("<br><br><form role='form' method='POST' action='process' id='formPage'>");
                    out.println("<input type='hidden' name='action' value='uploadPage'>");
                    out.println("Colar xml da página:<br>");
                    out.println("<textarea name='xml_page' class='form-control' rows='15'></textarea>");
                    out.println("<br><input type='submit' value='Adicionar' class='btn btn-primary'/>");
                    out.println("</form></div>");
                }
            }
        }
    }%>

