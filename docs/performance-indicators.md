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


_Processo 4: **Gestão financeira**_

| **Indicador**                     | **Objetivos**                                         | **Descrição**                                                             | **Fonte de dados**                  | **Fórmula de cálculo**                                    |
| --------------------------------- | ----------------------------------------------------- | ------------------------------------------------------------------------- | ----------------------------------- | --------------------------------------------------------- |
| Tempo médio de geração de boleto  | Medir a eficiência do processo de geração de boletos  | Tempo médio entre o recebimento da solicitação e a geração do boleto      | Sistema de gestão de cobranças      | Soma dos tempos de geração ÷ Número de boletos gerados    |
| % de boletos pagos                | Avaliar a taxa de adimplência dos alunos/responsáveis | Proporção de boletos gerados que foram efetivamente pagos                 | Sistema de pagamentos               | (Nº de boletos pagos ÷ Nº total de boletos gerados) × 100 |
| Valor pendente acumulado          | Monitorar o montante financeiro não pago              | Soma dos valores de boletos que não foram quitados                        | Sistema financeiro                  | Soma de todos os valores de boletos com status "pendente" |
| Frequência de tipo de boleto      | Identificar a demanda por tipo de cobrança            | Quantidade de boletos por categoria: mensalidade, matrícula, eventos etc. | Base de dados de cobranças          | Contagem por tipo (mensalidade, matrícula, eventos, etc.) |
| Erros no processamento de boletos | Avaliar a qualidade do processo                       | Número de falhas ou inconsistências no processo de geração de boletos     | Relatórios de incidentes do sistema | Nº de boletos com erro ÷ Nº total de boletos gerados      |


_Processo 5: **Marcação de aulas**_

| **Indicador**                                             | **Objetivos**                                     | **Descrição**                                                              | **Fonte de dados**                       | **Fórmula de cálculo**                                                       |
| --------------------------------------------------------- | ------------------------------------------------- | -------------------------------------------------------------------------- | ---------------------------------------- | ---------------------------------------------------------------------------- |
| Tempo médio de marcação de aula                           | Avaliar a agilidade no agendamento de aulas       | Tempo médio entre a solicitação do aluno e a confirmação da aula           | Sistema de agendamento de aulas          | Soma dos tempos de marcação ÷ Nº de marcações realizadas                     |
| % de alunos que realizam matrícula após aula experimental | Medir taxa de conversão                           | Proporção de alunos que fizeram aula experimental e depois se matricularam | Sistema acadêmico                        | (Nº de alunos matriculados após experimental ÷ Nº de experimentais) × 100    |
| Taxa de ocupação dos horários fixos                       | Avaliar o aproveitamento dos horários disponíveis | Percentual de horários fixos ocupados pelos alunos                         | Sistema de gestão de horários            | (Nº de horários ocupados ÷ Nº de horários disponíveis) × 100                 |
| Taxa de remarcação/desmarcação de aulas                   | Monitorar o volume de alterações de agendamentos  | Número de alterações feitas pelos alunos em relação ao agendamento inicial | Registro de agendamentos e cancelamentos | (Nº de remarcações + desmarcações) ÷ Nº total de aulas agendadas × 100       |
| Grau de aderência ao cronograma fixo                      | Avaliar o comprometimento com o cronograma fixo   | Número de aulas frequentadas conforme cronograma fixo vs. agendadas        | Sistema de frequência                    | (Nº de aulas assistidas no horário fixo ÷ Nº de aulas agendadas fixas) × 100 |

