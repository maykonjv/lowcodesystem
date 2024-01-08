<%@page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <script src="libs/js/jquery.min.js"></script>
    </head>
    <body>
        <script type="module" src="libs/monaco/editor.main.js"></script>
        <script type="module" src="libs/monaco-editor.min.js"></script>
    <wc-monaco-editor 
        id="editor" 
        language="sql" 
        no-minimap 
        style="width: 100%; height:400px;"
        >
    </wc-monaco-editor>
    <button id="save">Save</button>
    <script type="module">
        import './libs/monaco/editor.main.js';
        editor.value = "select * from produtos;\nselect * from clientes";
        $('#save').on('click', function (event) {
            var value = editor.value;
            alert(value);
        });

    </script>
</body>

</html>