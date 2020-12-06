<html>
    <head>
        <style>
            table, th, td {
                border: 1px solid black;
                border-collapse: collapse;
                padding: 5px;
            }

            .aula {
                border: 1px solid black;
                border-collapse: collapse;
                padding: 5px;
            }

            table {
                width: 50%;
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

                        <#list aula.presencas as presenca>
                            <table keep-together.within-page="always">
                                <tr>
                                    <th>Nome</th>
                                    <th>Presente</th>
                                </tr>
                                <tr>
                                    <td>${presenca.aluno}</td>
                                    <#if presenca.presenca>
                                        <td>Sim</td>
                                    <#else>
                                        <td>Não</td>
                                    </#if>
                                </tr>
                            </table>
                        </#list>
                    </div>
                </#list>
            </fieldset>
        </div>
    </body>
</html>