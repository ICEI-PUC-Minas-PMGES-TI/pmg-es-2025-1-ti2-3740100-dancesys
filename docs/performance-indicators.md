## 5. Indicadores de desempenho

_Processo 1: **Cadastro de aluno**_

| **Indicador**                                         | **Objetivos**                                 | **Descrição**                                                                                        | **Fonte de dados**                                | **Fórmula de cálculo**                                                                   |
| ----------------------------------------------------- | --------------------------------------------- | ---------------------------------------------------------------------------------------------------- | ------------------------------------------------- | ---------------------------------------------------------------------------------------- |
| Tempo médio de processamento do pré-cadastro          | Avaliar a eficiência do processo              | Mede o tempo decorrido desde o preenchimento do pré-cadastro até o envio do e-mail com login e senha | Sistema de pré-cadastro e logs de envio de e-mail | Soma dos tempos de processamento ÷ Número total de pré-cadastros                         |
| Taxa de conversão em matrícula após aula experimental | Avaliar a eficácia da aula experimental       | Mede quantos alunos se matriculam após participarem da aula experimental                             | Sistema de agendamento e de matrículas            | (Nº de alunos matriculados após aula ÷ Nº de alunos que fizeram aula experimental) × 100 |
| Taxa de sucesso no envio de e-mails                   | Verificar falhas de comunicação               | Mede o percentual de e-mails entregues com sucesso                                                   | Logs de e-mail do sistema                         | (Nº de e-mails entregues ÷ Nº de e-mails enviados) × 100                                 |
| Tempo médio para primeiro login e troca de senha      | Avaliar tempo de engajamento inicial do aluno | Mede o tempo entre o envio do e-mail e a primeira troca de senha                                     | Sistema de autenticação                           | Soma dos tempos até a troca ÷ Nº de alunos que trocaram a senha                          |

_Processo 2: **Cadastro de professor**_

| **Indicador**                               | **Objetivos**                                                                  | **Descrição**                                              | **Fonte de dados**                                       | **Fórmula de cálculo**                                                       |
| ------------------------------------------- | ------------------------------------------------------------------------------ | ---------------------------------------------------------- | -------------------------------------------------------- | ---------------------------------------------------------------------------- |
| Tempo médio de cadastro do professor        | Medir eficiência do processo de cadastro                                       | Tempo entre início do cadastro e envio de login/senha      | Formulário de cadastro e sistema de envio de credenciais | Soma dos tempos de cadastro ÷ Nº de professores cadastrados                  |
| Taxa de compatibilidade de horários         | Avaliar alinhamento entre disponibilidade do professor e necessidade da escola | Mede quantos cadastros são aceitos sem ajustes             | Sistema de disponibilidade                               | (Nº de cadastros sem ajuste ÷ Nº total de cadastros) × 100                   |
| Número médio de ajustes de disponibilidade  | Medir retrabalho no processo de alocação                                       | Média de vezes que o horário é ajustado até o aceite       | Sistema de agendamento de disponibilidade                | Total de ajustes ÷ Nº de professores cadastrados                             |
| Taxa de professores cadastrados com sucesso | Verificar eficácia do processo de integração                                   | Mede a porcentagem de professores que completam o processo | Sistema de login e cadastro                              | (Nº de professores com primeiro login efetuado ÷ Nº total cadastrados) × 100 |


_Processo 3: **Gerenciamento de Calendário**_

| **Indicador**                                      | **Objetivos**                                        | **Descrição**                                                      | **Fonte de dados**                        | **Fórmula de cálculo**                                         |
| -------------------------------------------------- | ---------------------------------------------------- | ------------------------------------------------------------------ | ----------------------------------------- | -------------------------------------------------------------- |
| Tempo médio para atualização do calendário         | Avaliar agilidade administrativa                     | Tempo entre o início da edição e o envio de notificação            | Sistema de calendário e logs de mensagens | Soma dos tempos de atualização ÷ Nº de atualizações realizadas |
| Número de atualizações realizadas por mês          | Acompanhar a frequência de manutenções no calendário | Total de alterações feitas no calendário no mês                    | Sistema de calendário                     | Contagem de registros de atualização no mês                    |
| Percentual de atualizações que geram pagamento     | Relacionar eventos com impacto financeiro            | Mede quantas atualizações envolvem geração de boletos              | Sistema financeiro e de calendário        | (Nº de ações com geração de boleto ÷ Nº total de ações) × 100  |
| Taxa de sucesso no envio de mensagens aos usuários | Avaliar eficácia da comunicação das alterações       | Mede se os usuários foram corretamente notificados após alterações | Sistema de mensagens / notificações       | (Nº de mensagens entregues ÷ Nº de mensagens enviadas) × 100   |


