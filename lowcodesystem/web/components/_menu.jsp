<%@page import="br.com.lowcodesystem.model.ProfileAction"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="br.com.lowcodesystem.model.Profile"%>
<%@page import="br.com.lowcodesystem.ctrl.Render"%>
<%@page import="br.com.lowcodesystem.model.Menu"%>

<ul class="sidebar-nav" data-coreui="navigation" data-simplebar="">
    <%
        Profile profileMenu = null;
        String perfilMenu = session.getAttribute("perfil").toString();
        int indexMenu = Render.profiles.indexOf(new Profile(perfilMenu));
        if (indexMenu >= 0) {
            profileMenu = Render.profiles.get(indexMenu);
        }
        List<Menu> listMenu = new ArrayList<>();
        if (profileMenu != null) {
            if (!perfilMenu.equals(Render.ADMINISTRADOR)) {
                for (Menu menu : Render.menus) {
                    ProfileAction action = profileMenu.getActions().get(menu.getPage());
                    if (action != null) {
                        if (action.isCustom()) {
                            if (menu.getParentId() != null && !menu.getParentId().isEmpty()) {
                                int id = Render.menus.indexOf(new Menu(menu.getParentId(), ""));
                                if (id < 0) {
                                    System.out.println(menu.getId() + ":" + menu.getParentId());
                                }
                                if (id >= 0 && !listMenu.contains(Render.menus.get(id))) {
                                    listMenu.add(Render.menus.get(id));
                                }
                            }
                            listMenu.add(menu);
                        }
                    }
                }
            } else {
                listMenu.addAll(Render.menus);
            }
            for (Menu menu : listMenu) {
                // Menus com filhos
                if (menu.getChildrenId() != null && !menu.getChildrenId().isEmpty()) {
                    out.println("<li class=\"nav-group\" >");
                    out.println("    <a class=\"nav-link nav-group-toggle\" id=\"" + menu.getId() + "\" href=\"#\"><span class=\"nav-icon\"><i class=\"" + menu.getIcon() + "\"></i></span><span> " + menu.getLabel() + "</span></a>");
                    if (session.getAttribute("dev") != null && (Boolean) session.getAttribute("dev")) {
                        out.println(" <div class='menu-config lcs-settings'>");
                        out.println("   <a href=\"#" + menu.getId() + "\" class=\"btn-menu-remove \" title=\"Excluir menu\">");
                        out.println("        <i class=\"fa fa-trash\"></i>");
                        out.println("   </a>");
                        out.println("   <a href=\"#" + menu.getId() + "\" class=\"btn-menu-open\" title=\"Configurar menu\" data-coreui-toggle=\"modal\" data-coreui-target=\"#modalMenu\">");
                        out.println("        <i class=\"fa fa-cog\"></i>");
                        out.println("   </a>");
                        out.println("   <a href=\"#" + menu.getId() + "\" class=\"btn-menu-up \" title=\"Mover menu para cima\">");
                        out.println("        <i class=\"fa fa-level-up\"></i>");
                        out.println("   </a>");
                        out.println(" </div>");
                    }
                    out.println("        <ul class=\"nav-group-items\" >");
                    for (Menu m : menu.getChildrenId()) {
                        // Filho de menu
                        if (listMenu.contains(m)) {
                            out.println("   <li class=\"nav-item\">");
                            out.println("       <a class=\"nav-link\" id=\"" + m.getId() + "\" href=\"render?page=" + m.getPage() + m.getParam() + "\"><span class=\"nav-icon\"><i class=\"" + m.getIcon() + "\"></i></span><span> " + m.getLabel() + "</span></a>");
                            out.println("   </li>");
                            if (session.getAttribute("dev") != null && (Boolean) session.getAttribute("dev")) {
                                out.println(" <div class='menu-config lcs-settings'>");
                                out.println("   <a href=\"#" + m.getId() + "\" class=\"btn-menu-remove \" title=\"Excluir menu\">");
                                out.println("        <i class=\"fa fa-trash\"></i>");
                                out.println("   </a>");
                                out.println("   <a href=\"#" + m.getId() + "\" class=\"btn-menu-open\" title=\"Configurar menu\" data-coreui-toggle=\"modal\" data-coreui-target=\"#modalMenu\">");
                                out.println("        <i class=\"fa fa-cog\"></i>");
                                out.println("   </a>");
                                out.println("   <a href=\"#" + m.getId() + "\" class=\"btn-menu-up \" title=\"Mover menu para cima\">");
                                out.println("        <i class=\"fa fa-level-up\"></i>");
                                out.println("   </a>");
                                out.println(" </div>");
                            }
                        }
                    }
                    out.println("    </ul>");
                    out.println("</li>");
                    out.println("<li class=\"nav-divider\"></li>");
                }

                // menu sem link - separador de menu
                if (menu.getPage() == null || menu.getPage().isEmpty() && (menu.getChildrenId() == null || menu.getChildrenId().isEmpty()) && (menu.getParentId() == null || menu.getParentId().isEmpty())) {
                    out.println("<li id=\"" + menu.getId() + "\" class=\"nav-title\" >" + menu.getLabel() + "</li>");
                    if (session.getAttribute("dev") != null && (Boolean) session.getAttribute("dev")) {
                        out.println(" <div class='menu-config lcs-settings'>");
                        out.println("   <a href=\"#" + menu.getId() + "\" class=\"btn-menu-remove \" title=\"Excluir menu\">");
                        out.println("        <i class=\"fa fa-trash\"></i>");
                        out.println("   </a>");
                        out.println("   <a href=\"#" + menu.getId() + "\" class=\"btn-menu-open\" title=\"Configurar menu\" data-coreui-toggle=\"modal\" data-coreui-target=\"#modalMenu\">");
                        out.println("        <i class=\"fa fa-cog\"></i>");
                        out.println("   </a>");
                        out.println("   <a href=\"#" + menu.getId() + "\" class=\"btn-menu-up \" title=\"Mover menu para cima\">");
                        out.println("        <i class=\"fa fa-level-up\"></i>");
                        out.println("   </a>");
                        out.println(" </div>");
                    }
                    out.println("<li class=\"nav-divider\"></li>");
                }

                // Menus com link
                if (menu.getPage() != null && !menu.getPage().isEmpty() && (menu.getParentId() == null || menu.getParentId().isEmpty()) && (menu.getChildrenId() == null || menu.getChildrenId().isEmpty())) {
                    out.println("<li class='nav-item'>");
                    out.println("  <a id=\"" + menu.getId() + "\" class=\"nav-link\" href=\"render?page=" + menu.getPage() + menu.getParam() + "\"><span class=\"nav-icon\"><i class=\"" + menu.getIcon() + "\"></i></span>" + menu.getLabel() + "</a>");
                    out.println("</li>");
                    if (session.getAttribute("dev") != null && (Boolean) session.getAttribute("dev")) {
                        out.println(" <div class='menu-config lcs-settings'>");
                        out.println("   <a href=\"#" + menu.getId() + "\" class=\"btn-menu-remove \" title=\"Excluir menu\">");
                        out.println("        <i class=\"fa fa-trash\"></i>");
                        out.println("   </a>");
                        out.println("   <a href=\"#" + menu.getId() + "\" class=\"btn-menu-open\" title=\"Configurar menu\" data-coreui-toggle=\"modal\" data-coreui-target=\"#modalMenu\">");
                        out.println("        <i class=\"fa fa-cog\"></i>");
                        out.println("   </a>");
                        out.println("   <a href=\"#" + menu.getId() + "\" class=\"btn-menu-up \" title=\"Mover menu para cima\">");
                        out.println("        <i class=\"fa fa-level-up\"></i>");
                        out.println("   </a>");
                        out.println(" </div>");
                    }
                    out.println("<li class=\"nav-divider\"></li>");
                }
            }
        }
    %>
</ul>

