<html>
    <body>
        <div id="content">
            <fieldset>
                <legend>Presenças</legend>

                <#list model["aulas"] as aula>

                    <h1>${aula.nome}</h1>

                    <table class="datatable">
                        <tr>
                            <th>Make</th>
                            <th>Model</th>
                        </tr>

                        <tr>
                            <td>${aula.make}</td>
                            <td>${aula.model}</td>
                        </tr>
                    </table>
                </#list>

            </fieldset>
        </div>
    </body>
</html>