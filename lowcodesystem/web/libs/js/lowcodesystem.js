
$(".btn-menu-remove").click(function () {
    event.preventDefault();
    $.get("builder", {session: "menu-remove", page: $('#page').val(), idMenu: $(this).attr('href')}, function (data, status) {
        if (status === "success") {
            window.location.href = "render";
        }
    });
});
$(".btn-menu-up").click(function () {
    event.preventDefault();
    $.get("builder", {session: "menu-up", page: $('#page').val(), idMenu: $(this).attr('href')}, function (data, status) {
        if (status === "success") {
            window.location.href = "render";
        }
    });
});
$(".btn-menu-open").click(function () {
    event.preventDefault();
    $.get("builder", {session: "menu-open", page: $('#page').val(), idMenu: $(this).attr('href')}, function (data, status) {
        if (status === "success") {
            var obj = $.parseJSON(data);
            $('#formMenu input[name=session]').val("menu");
            $('#formMenu input[name=idMenuOld]').val(obj.menu.id);
            $('#formMenu input[name=idMenu]').val(obj.menu.id);
            $('#formMenu input[name=label]').val(obj.menu.label);
            $('#formMenu input[name=pageReferencia]').val(obj.menu.page);
            $('#formMenu input[name=pageParam]').val(obj.menu.param);
            $('#formMenu input[name=icon]').val(obj.menu.icon);
            if (obj.menu.childrenId.length === 0) {
                $('#formMenu select[name=idparent]').val(obj.menu.parentId);
                $('#formMenu select[name=idparent]').prop('disabled', false);
            } else {
                $('#formMenu select[name=idparent]').prop('disabled', true);
            }
            $("#formMenu select[name=idparent] option[value='" + obj.menu.id + "']").remove();
            $('#modalMenu').modal('show');
        }
    });
});


$(".btn-form-remove").click(function () {
    event.preventDefault();
    $.get("builder", {session: "form-remove", page: $('#page').val()}, function (data, status) {
        if (status === "success") {
            window.location.href = "render?page=" + $('#page').val();
        }
    });
});

$(".btn-field-up").click(function (event) {
    event.preventDefault();
    const url = `builder?session=field-up&page=${$('#page').val()}&idField=${$(this).attr('href').replace("#", "")}`;
    console.log("build-field-left:" + url);
    $.get(url, function (data, status) {
        if (status === "success") {
            window.location.href = "render?page=" + $('#page').val() + "&action=" + $('#action').val();
        }
    });
});

$(".btn-field-remove").click(function () {
    event.preventDefault();
    $.get("builder", {session: "field-remove", page: $('#page').val(), idField: $(this).attr('href')}, function (data, status) {
        if (status === "success") {
            window.location.href = "render?page=" + $('#page').val() + "&action=" + $('#action').val();
        }
    });
});
$(".btn-field-open").click(function () {
    event.preventDefault();
    $.get("builder", {session: "field-open", page: $('#page').val(), idField: $(this).attr('href')}, function (data, status) {
        if (status === "success") {
            var obj = $.parseJSON(data);
            if (!obj.page.hasNew) {
                $('#formField input[name=hasNewField]').hide();
                $('#formField label[for=hasNewField]').hide();
            }
            if (!obj.page.hasUpdate) {
                $('#formField input[name=hasUpdateField]').hide();
                $('#formField label[for=hasUpdateField]').hide();
            }
            if (!obj.page.hasView) {
                $('#formField input[name=hasViewField]').hide();
                $('#formField label[for=hasViewField]').hide();
            }
            if (!obj.page.hasSearch) {
                $('#formField input[name=hasSearchField]').hide();
                $('#formField label[for=hasSearchField]').hide();
            }
            renderModalField(obj.field.component);

            $('#formField input[name=session]').val("field");
            $('#formField input[name=page]').val(obj.page.id);
            $('#formField input[name=idFieldOld]').val(obj.field.id);
            $('#formField input[name=idField]').val(obj.field.id);
            $('#formField input[name=labelField]').val(obj.field.label);
            $('#formField input[name=defaultValueField]').val(obj.field.defaultValue);
            $('#formField select[name=componentField]').val(obj.field.component);
            $('#formField select[name=scopeField]').val(obj.field.scope);
            $('#formField select[name=typeField]').val(obj.field.type);
            $('#formField input[name=formatField]').val(obj.field.format);
            $('#formField input[name=maskField]').val(obj.field.mask);
            $('#formField input[name=titleField]').val(obj.field.title);
            $('#formField input[name=placeholderField]').val(obj.field.placeholder);
            $('#formField input[name=sizeField]').val(obj.field.size);
            $('#formField input[name=styleField]').val(obj.field.style);
            $('#formField input[name=classStyleField]').val(obj.field.classStyle);
            $('#formField input[name=widthField]').val(obj.field.width);
            $('#formField input[name=requiredField]').prop('checked', obj.field.required);
            $('#formField input[name=submitOnchange]').prop('checked', obj.field.submitOnchange);
            $('#formField textarea[name=optionsField]').val(obj.field.options);
            $('#formField textarea[name=sqlOptionsField]').val(obj.field.sqlOptions);
            $('#formField select[name=datasourceField]').val(obj.field.datasource);
            $('#formField input[name=hasNewField]').prop('checked', obj.field.hasNew);
            $('#formField input[name=hasUpdateField]').prop('checked', obj.field.hasUpdate);
            $('#formField input[name=hasViewField]').prop('checked', obj.field.hasView);
            $('#formField input[name=hasSearchField]').prop('checked', obj.field.hasSearch);

            $('#formField input[name=hasCheck]').prop('checked', obj.field.tbCheck);
            $('#formField input[name=hasPaging]').prop('checked', obj.field.tbPaging);
            $('#formField input[name=hasOrdering]').prop('checked', obj.field.tbOrdering);
            $('#formField input[name=hasInfo]').prop('checked', obj.field.tbInfo);
            $('#formField input[name=hasSearch]').prop('checked', obj.field.tbSearching);

            $('#modalField').modal('show');
        }
    });
});

$('#componentField').on('change', function (event) {
    event.preventDefault();
    var valueSelected = this.value;
    renderModalField(valueSelected);
});

function renderModalField(valueSelected) {
    $('#typeField').find('option').remove().end()
            .append('<option value="boolean">Boolean</option>')
            .append('<option value="numeric">Numérico</option>')
            .append('<option value="string" selected="true">String</option>')
            .append('<option value="date">Date</option>');
    if (valueSelected === "select" || valueSelected === "radio") {
        $('#formField div[id=div_id_field]').show();
        $('#formField div[id=div_label]').show();
        $('#formField div[id=div_defaultvalue]').show();
        $('#formField div[id=div_component]').show();
        $('#formField div[id=div_type]').show();
        $('#formField div[id=div_title]').show();
        $('#formField div[id=div_placeholder]').hide();
        $('#formField div[id=div_size]').hide();
        $('#formField div[id=div_width]').show();
        $('#formField div[id=div_required]').show();
        $('#formField div[id=div_submit]').show();
        $('#formField div[id=div_optionsField]').show();
        $('#formField div[id=div_sqlOptionsField]').show();
        $('#formField div[id=div_datasource]').show();
        $('#formField div[id=div_type_action]').show();
        $('#formField div[id=div_table_options]').hide();
        $('#formField div[id=div_format]').hide();
        $('#formField div[id=div_mask]').hide();
    } else if (valueSelected === "text" || valueSelected === "area"
            || valueSelected === "number" || valueSelected === "password"
            || valueSelected === "email" || valueSelected === "url") {
        $('#formField div[id=div_id_field]').show();
        $('#formField div[id=div_label]').show();
        $('#formField div[id=div_defaultvalue]').show();
        $('#formField div[id=div_component]').show();
        $('#formField div[id=div_type]').show();
        $('#formField div[id=div_title]').show();
        $('#formField div[id=div_placeholder]').show();
        $('#formField div[id=div_size]').show();
        $('#formField div[id=div_width]').show();
        $('#formField div[id=div_required]').show();
        $('#formField div[id=div_submit]').hide();
        $('#formField div[id=div_optionsField]').hide();
        $('#formField div[id=div_sqlOptionsField]').hide();
        $('#formField div[id=div_datasource]').hide();
        $('#formField div[id=div_type_action]').show();
        $('#formField div[id=div_table_options]').hide();
        $('#formField div[id=div_format]').show();
        $('#formField div[id=div_mask]').show();
    } else if (valueSelected === "message" || valueSelected === "color") {
        $('#formField div[id=div_id_field]').show();
        $('#formField div[id=div_label]').show();
        $('#formField div[id=div_defaultvalue]').show();
        $('#formField div[id=div_component]').show();
        $('#formField div[id=div_type]').hide();
        $('#formField div[id=div_title]').hide();
        $('#formField div[id=div_placeholder]').hide();
        $('#formField div[id=div_size]').hide();
        $('#formField div[id=div_width]').show();
        $('#formField div[id=div_required]').hide();
        $('#formField div[id=div_submit]').hide();
        $('#formField div[id=div_optionsField]').hide();
        $('#formField div[id=div_sqlOptionsField]').hide();
        $('#formField div[id=div_datasource]').hide();
        $('#formField div[id=div_type_action]').show();
        $('#formField div[id=div_table_options]').hide();
        $('#formField div[id=div_format]').hide();
        $('#formField div[id=div_mask]').hide();
    } else if (valueSelected === "label") {
        $('#formField div[id=div_id_field]').show();
        $('#formField div[id=div_label]').show();
        $('#formField div[id=div_defaultvalue]').hide();
        $('#formField div[id=div_component]').show();
        $('#formField div[id=div_type]').hide();
        $('#formField div[id=div_title]').hide();
        $('#formField div[id=div_placeholder]').hide();
        $('#formField div[id=div_size]').hide();
        $('#formField div[id=div_width]').show();
        $('#formField div[id=div_required]').hide();
        $('#formField div[id=div_submit]').hide();
        $('#formField div[id=div_optionsField]').hide();
        $('#formField div[id=div_sqlOptionsField]').hide();
        $('#formField div[id=div_datasource]').hide();
        $('#formField div[id=div_type_action]').show();
        $('#formField div[id=div_table_options]').hide();
        $('#formField div[id=div_format]').hide();
        $('#formField div[id=div_mask]').hide();
    } else if (valueSelected === "checkbox") {
        $('#formField div[id=div_id_field]').show();
        $('#formField div[id=div_label]').show();
        $('#formField div[id=div_defaultvalue]').show();
        $('#formField div[id=div_component]').show();
        $('#formField div[id=div_type]').show();
        $('#formField div[id=div_title]').show();
        $('#formField div[id=div_placeholder]').hide();
        $('#formField div[id=div_size]').hide();
        $('#formField div[id=div_width]').show();
        $('#formField div[id=div_required]').hide();
        $('#formField div[id=div_submit]').hide();
        $('#formField div[id=div_optionsField]').hide();
        $('#formField div[id=div_sqlOptionsField]').hide();
        $('#formField div[id=div_datasource]').hide();
        $('#formField div[id=div_type_action]').show();
        $('#formField div[id=div_table_options]').hide();
        $('#formField div[id=div_format]').hide();
        $('#formField div[id=div_mask]').hide();
    } else if (valueSelected === "range") {
        $('#formField div[id=div_id_field]').show();
        $('#formField div[id=div_label]').show();
        $('#formField div[id=div_defaultvalue]').show();
        $('#formField div[id=div_component]').show();
        $('#formField div[id=div_type]').show();
        $('#formField div[id=div_title]').show();
        $('#formField div[id=div_placeholder]').hide();
        $('#formField div[id=div_size]').show();
        $('#formField div[id=div_width]').show();
        $('#formField div[id=div_required]').hide();
        $('#formField div[id=div_submit]').hide();
        $('#formField div[id=div_optionsField]').hide();
        $('#formField div[id=div_sqlOptionsField]').hide();
        $('#formField div[id=div_datasource]').hide();
        $('#formField div[id=div_type_action]').show();
        $('#formField div[id=div_table_options]').hide();
        $('#formField div[id=div_format]').hide();
        $('#formField div[id=div_mask]').hide();
    } else if (valueSelected === "file") {
        $('#formField div[id=div_id_field]').show();
        $('#formField div[id=div_label]').show();
        $('#formField div[id=div_defaultvalue]').hide();
        $('#formField div[id=div_component]').show();
        $('#formField div[id=div_type]').hide();
        $('#formField div[id=div_title]').show();
        $('#formField div[id=div_placeholder]').hide();
        $('#formField div[id=div_size]').show();
        $('#formField div[id=div_width]').show();
        $('#formField div[id=div_required]').show();
        $('#formField div[id=div_submit]').hide();
        $('#formField div[id=div_optionsField]').hide();
        $('#formField div[id=div_sqlOptionsField]').hide();
        $('#formField div[id=div_datasource]').hide();
        $('#formField div[id=div_type_action]').show();
        $('#formField div[id=div_table_options]').hide();
        $('#formField div[id=div_format]').hide();
        $('#formField div[id=div_mask]').hide();
    } else if (valueSelected === "hidden") {
        $('#formField div[id=div_id_field]').show();
        $('#formField div[id=div_label]').hide();
        $('#formField div[id=div_defaultvalue]').show();
        $('#formField div[id=div_component]').show();
        $('#formField div[id=div_type]').show();
        $('#formField div[id=div_title]').hide();
        $('#formField div[id=div_placeholder]').hide();
        $('#formField div[id=div_size]').hide();
        $('#formField div[id=div_width]').hide();
        $('#formField div[id=div_required]').hide();
        $('#formField div[id=div_submit]').hide();
        $('#formField div[id=div_optionsField]').hide();
        $('#formField div[id=div_sqlOptionsField]').hide();
        $('#formField div[id=div_datasource]').hide();
        $('#formField div[id=div_type_action]').show();
        $('#formField div[id=div_table_options]').hide();
        $('#formField div[id=div_format]').hide();
        $('#formField div[id=div_mask]').hide();
    } else if (valueSelected === "button" || valueSelected === "reset" || valueSelected === "submit") {
        $('#formField div[id=div_id_field]').show();
        $('#formField div[id=div_label]').show();
        $('#formField div[id=div_defaultvalue]').hide();
        $('#formField div[id=div_component]').show();
        $('#formField div[id=div_type]').hide();
        $('#formField div[id=div_title]').show();
        $('#formField div[id=div_placeholder]').hide();
        $('#formField div[id=div_size]').hide();
        $('#formField div[id=div_width]').show();
        $('#formField div[id=div_required]').hide();
        $('#formField div[id=div_submit]').hide();
        $('#formField div[id=div_optionsField]').hide();
        $('#formField div[id=div_sqlOptionsField]').hide();
        $('#formField div[id=div_datasource]').hide();
        $('#formField div[id=div_type_action]').show();
        $('#formField div[id=div_table_options]').hide();
        $('#formField div[id=div_format]').hide();
        $('#formField div[id=div_mask]').hide();
    } else if (valueSelected === "script") {
        $('#formField div[id=div_id_field]').show();
        $('#formField div[id=div_label]').show();
        $('#formField div[id=div_defaultvalue]').hide();
        $('#formField div[id=div_component]').show();
        $('#formField div[id=div_type]').hide();
        $('#formField div[id=div_title]').show();
        $('#formField div[id=div_placeholder]').hide();
        $('#formField div[id=div_size]').hide();
        $('#formField div[id=div_width]').show();
        $('#formField div[id=div_required]').hide();
        $('#formField div[id=div_submit]').hide();
        $('#formField div[id=div_optionsField]').hide();
        $('#formField div[id=div_sqlOptionsField]').show();
        $('#formField div[id=div_datasource]').show();
        $('#formField div[id=div_type_action]').show();
        $('#formField div[id=div_table_options]').hide();
        $('#formField div[id=div_format]').hide();
        $('#formField div[id=div_mask]').hide();
    } else if (valueSelected === "empty") {
        $('#formField div[id=div_id_field]').show();
        $('#formField div[id=div_label]').hide();
        $('#formField div[id=div_defaultvalue]').hide();
        $('#formField div[id=div_component]').show();
        $('#formField div[id=div_type]').hide();
        $('#formField div[id=div_title]').hide();
        $('#formField div[id=div_placeholder]').hide();
        $('#formField div[id=div_size]').hide();
        $('#formField div[id=div_width]').show();
        $('#formField div[id=div_required]').hide();
        $('#formField div[id=div_submit]').hide();
        $('#formField div[id=div_optionsField]').hide();
        $('#formField div[id=div_sqlOptionsField]').hide();
        $('#formField div[id=div_datasource]').hide();
        $('#formField div[id=div_type_action]').show();
        $('#formField div[id=div_table_options]').hide();
        $('#formField div[id=div_format]').hide();
        $('#formField div[id=div_mask]').hide();
    } else if (valueSelected === "free") {
        $('#formField div[id=div_id_field]').show();
        $('#formField div[id=div_label]').hide();
        $('#formField div[id=div_defaultvalue]').show();
        $('#formField div[id=div_component]').show();
        $('#formField div[id=div_type]').hide();
        $('#formField div[id=div_title]').hide();
        $('#formField div[id=div_placeholder]').hide();
        $('#formField div[id=div_size]').hide();
        $('#formField div[id=div_width]').show();
        $('#formField div[id=div_required]').hide();
        $('#formField div[id=div_submit]').hide();
        $('#formField div[id=div_optionsField]').hide();
        $('#formField div[id=div_sqlOptionsField]').hide();
        $('#formField div[id=div_datasource]').hide();
        $('#formField div[id=div_type_action]').show();
        $('#formField div[id=div_table_options]').hide();
        $('#formField div[id=div_format]').hide();
        $('#formField div[id=div_mask]').hide();
    } else if (valueSelected === "table") {
        $('#formField div[id=div_id_field]').show();
        $('#formField div[id=div_label]').show();
        $('#formField div[id=div_defaultvalue]').hide();
        $('#formField div[id=div_component]').show();
        $('#formField div[id=div_type]').hide();
        $('#formField div[id=div_title]').hide();
        $('#formField div[id=div_placeholder]').hide();
        $('#formField div[id=div_size]').hide();
        $('#formField div[id=div_width]').show();
        $('#formField div[id=div_required]').hide();
        $('#formField div[id=div_submit]').hide();
        $('#formField div[id=div_optionsField]').hide();
        $('#formField div[id=div_sqlOptionsField]').show();
        $('#formField div[id=div_datasource]').show();
        $('#formField div[id=div_type_action]').show();
        $('#formField div[id=div_table_options]').show();
        $('#formField div[id=div_format]').hide();
        $('#formField div[id=div_mask]').hide();
    } else if (valueSelected === "chart") {
        $('#formField div[id=div_id_field]').show();
        $('#formField div[id=div_label]').show();
        $('#formField div[id=div_defaultvalue]').hide();
        $('#formField div[id=div_component]').show();
        $('#formField div[id=div_type]').show();
        $('#formField div[id=div_title]').hide();
        $('#formField div[id=div_placeholder]').hide();
        $('#formField div[id=div_size]').hide();
        $('#formField div[id=div_width]').show();
        $('#formField div[id=div_required]').hide();
        $('#formField div[id=div_submit]').hide();
        $('#formField div[id=div_optionsField]').hide();
        $('#formField div[id=div_sqlOptionsField]').show();
        $('#formField div[id=div_datasource]').show();
        $('#formField div[id=div_type_action]').show();
        $('#formField div[id=div_table_options]').hide();
        $('#formField div[id=div_format]').hide();
        $('#formField div[id=div_mask]').hide();

        $('#typeField').find('option').remove().end()
                .append('<option value="bar">Barra</option>')
                .append('<option value="horizontalBar">Barra Horizontal</option>')
                .append('<option value="bubble">Bolha</option>')
                .append('<option value="doughnut">Rosquinha</option>')
                .append('<option value="line">Linha</option>')
                .append('<option value="pie">Pizza</option>')
                .append('<option value="radar">Radar</option>')
                .append('<option value="multi">Multi</option>');
    }
}

$('#database').on('change', function (event) {
    event.preventDefault();
    var valueSelected = this.value;
    updateDriver(valueSelected);
});

// ############### DATASOURCE ##########################
function updateDriver(db) {
    if (db === "Postgres") {
        document.getElementById("driverClass").value = "org.postgresql.Driver";
        document.getElementById("jdbcUrl").value = "jdbc:postgresql://localhost:5432/database_name";
        document.getElementById("testSQL").value = "SELECT 1";
    } else if (db === "Oracle") {
        document.getElementById("driverClass").value = "oracle.jdbc.OracleDriver";
        document.getElementById("jdbcUrl").value = "jdbc:oracle:thin:@localhost:1521:database_name";
        document.getElementById("testSQL").value = "SELECT 1 from dual";
    } else if (db === "MySQL") {
        document.getElementById("driverClass").value = "com.mysql.jdbc.Driver";
        document.getElementById("jdbcUrl").value = "jdbc:mysql://localhost:3306/database_name?zeroDateTimeBehavior=convertToNull";
        document.getElementById("testSQL").value = "";
    } else if (db === "MSSQL") {
        document.getElementById("driverClass").value = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        document.getElementById("jdbcUrl").value = "jdbc:sqlserver://localhost:1433;databaseName=database_name";
        document.getElementById("testSQL").value = "";
    } else if (db === "Sqlite") {
        document.getElementById("driverClass").value = "org.sqlite.JDBC";
        document.getElementById("jdbcUrl").value = "jdbc:sqlite:C:\\Users\\[user]\\database_name.db";
        document.getElementById("testSQL").value = "SELECT 1";
    }
}

$("#modalDatasourceForm").on("submit", function (event) {
    // Stop form from submitting normally
    event.preventDefault();
    $('.spinner-border').show();

    const dataForm = $('form#modalDatasourceForm').serialize();
    $.get('builder', dataForm,
            function (data, status) {
                console.log('builder result:', status, data);
                if (status === 'success') {
                    $('#datasource')
                            .append(`<option value="${$('#input_datasource').val()}">${$('#input_datasource').val()}</option>`);
                    $('#input_datasource').val('');
                }
                $('.spinner-border').hide();
            });
});

$("#formAdminDS #btn_excluir").click(function () {
    event.preventDefault();
    $('.spinner-border').show();
    $.get(`builder?session=adminB&excluir=true&datasource=${$('#datasource').val()}`, function (data, status) {
        if (status === "success") {
            $("#datasource option[value='" + $('#datasource').val() + "']").remove();
            $('.spinner-border').hide();
        }
    });
});
$("#formAdminDS #btn_testar").click(function () {
    event.preventDefault();
    $('.spinner-border').show();
    $.get(`builder?session=adminB&btn_testar=true&datasource=${$('#datasource').val()}&testSQL=${$('#testSQL').val()}`, function (data, status) {
        if (status === "success") {
            $("#sqlResult").html(data);
        }
        $('.spinner-border').hide();
    });
});

$("#formAdminDS #datasource").change(function () {
    event.preventDefault();
    $('.spinner-border').show();
    $.get(`builder?session=adminB&datasource=${$('#datasource').val()}`, function (data, status) {
        if (status === "success" && data) {
            const dataJson = JSON.parse(data);
            $('#minPoolSize').val(dataJson.minPoolSize);
            $('#maxPoolSize').val(dataJson.maxPoolSize);
            $('#maxStatements').val(dataJson.maxStatements);
            $('#timeout').val(dataJson.timeout);
            $('#idleTestPeriod').val(dataJson.idleTestPeriod);
            $('#testSQL').val(dataJson.testSQL);
            $('#passDB').val(dataJson.pass);
            $('#userDB').val(dataJson.user);
            $('#jdbcUrl').val(dataJson.jdbcUrl);
            $('#driverClass').val(dataJson.driverClass);
            $('#database').val(dataJson.database);
        }
        $('.spinner-border').hide();
    });
});

$("#formAdminDS").on("submit", function (event) {
    // Stop form from submitting normally
    event.preventDefault();
    $('.spinner-border').show();

    const dataForm = $('form#formAdminDS').serialize();
    $.get('builder', dataForm + "&btn_gravar=true",
            function (data, status) {
                console.log('builder result:', status, data);
                if (status === 'success') {

                }
                $('.spinner-border').hide();
            });
});

function checkTable(id) {
    if ($(id).is(':checked'))
        $(id).prop('checked', false);
    else
        $(id).prop('checked', true);
}
function checkTableAll(e) {
    if (!$(e).is(':checked'))
        $('.row_chk').prop('checked', false);
    else
        $('.row_chk').prop('checked', true);
}