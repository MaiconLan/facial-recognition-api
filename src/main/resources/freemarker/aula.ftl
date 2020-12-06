<html>
    <head>
        <style>
            table, th, td {
                border: 1px solid black;
                border-collapse: collapse;
                padding: 5px;
                width: 80%;
            }

            .aula {
                border: 1px solid black;
                border-collapse: collapse;
                padding: 5px;
            }

        </style>
    </head>
    <body>
        <div id="content">
            <fieldset>

                <h1>Presenças</h1>
                <h3>Turma: ${turma.turma}, Professor: ${turma.professor}</h3>

                <#list turma.aulas as aula>
                    <div class="aula">
                        <h3>${aula.titulo} - ${aula.data}</h3>
                        <h4>Horário: ${aula.inicio} a ${aula.termino}</h4>

                        <table keep-together.within-page="always">
                            <tr>
                                <th>Nome</th>
                                <th>Presente</th>
                            </tr>
                            <#list aula.presencas as presenca>
                                <tr>
                                    <td>${presenca.aluno}</td>
                                    <#if presenca.presenca>
                                        <td>Sim</td>
                                    <#else>
                                        <td>Não</td>
                                    </#if>
                                </tr>
                            </#list>
                        </table>
                    </div>
                </#list>
            </fieldset>
        </div>
    </body>
</html>