### 3.3.2 Processo 2 – Marcação de aulas

_Este é o processo de marcação de aulas, aqui encontramos uma forma tanto da administração quanto do aluno de interagirem com o sistema. O aluno que possui um determinado tipo de matricula pode escolher quais aulas ele pretende fazer durante a semana, sendo guiado pelas determinantes do sistema. A administração pode controlar quais turmas o aluno de matricula fixa tem acesso._

![Porcesso_MarcaçãoAula](https://github.com/user-attachments/assets/84684ac8-9567-4fd9-a1f2-baa33d05014c)



#### Detalhamento das atividades

_Atividade 1 – Marcação de aula experimental ou nivelamento_
 -Responsável: Aluno
 -Objetivo: Permitir que o aluno solicite a marcação de uma aula de nivelamento ou experimental para avaliar seu nível ou conhecer o funcionamento das aulas.
Detalhamento das etapas:
1- O aluno preenche um formulário com os seguintes dados:
  *Nome
  *E-mail
  *Tipo de aula (Experimental ou Nivelamento)
  *Nível da aula (Iniciante, Básico, Intermediário ou Avançado)
2- O sistema valida as informações inseridas.
3- O aluno pode:
  Confirmar ➔ O sistema verifica o tipo de aula e prossegue com o processo.
  Cancelar ➔ O processo é encerrado sem registrar a solicitação.
  
_Atividade 2 – Marcar aula fixa_
 -Responsável: Administrador
 -Objetivo: Permitir que o administrador marque uma aula fixa para o aluno.
Detalhamento das etapas:
1- O administrador acessa o sistema e preenche os seguintes campos:
  *Nome do aluno (obrigatório)
  *Nível da aula (preenchido automaticamente com base nos dados do aluno)
  *Data da aula (dentro dos dias disponíveis)
  *Horário da aula (dentro do horário de funcionamento)
  *Modalidade (tipo de dança)
  *Professor (escolha da lista de professores disponíveis)
2- O administrador pode:
  Confirmar ➔ O sistema agenda a aula e confirma com o aluno.
  Cancelar ➔ O processo é encerrado sem marcação.
  
_Atividade 3 – Marcar aula livre_
 -Responsável: Aluno
 -Objetivo: Permitir que o aluno marque uma aula livre diretamente pelo sistema.
Detalhamento das etapas:
1- O aluno acessa o sistema e preenche os seguintes campos:
  *Nível da aula (preenchido automaticamente com base nos dados do aluno)
  *Data da aula (dentro dos dias disponíveis)
  *Horário da aula (dentro do horário de funcionamento)
  *Modalidade (presencial ou online)
  *Professor (escolha da lista de professores disponíveis)
2- O aluno pode:
  Confirmar ➔ O sistema agenda a aula e envia a confirmação.
  Cancelar ➔ O processo é encerrado sem marcação.
  
📌 Atividade 4 – Verificar disponibilidade de vagas para aulas livres
 -Responsável: Sistema
 -Objetivo: Permitir que o sistema valide automaticamente a disponibilidade de vagas para uma aula livre.
Detalhamento das etapas:
1- O sistema verifica automaticamente os seguintes dados:
  *Data da aula (obrigatório)
  *Horário da aula (dentro do horário de funcionamento)
  *Modalidade (presencial ou online)
  *Professor disponível (baseado na agenda do professor)
  *Data e horário (com base nos horários disponíveis)
2- O sistema permite:
  Confirmar ➔ O sistema registra a aula.
  Cancelar aula ➔ O sistema cancela a aula se estiver dentro do prazo permitido.

_Fluxo geral do processo_
- O aluno solicita uma aula experimental ou de nivelamento.
- O sistema valida o tipo de aula e o aluno decide se vai se matricular.
- Caso o aluno se matricule, ele pode escolher entre aula fixa ou livre.
- Para aulas fixas, o administrador faz o agendamento.
- Para aulas livres, o aluno faz o agendamento diretamente pelo sistema.
- O sistema verifica automaticamente a disponibilidade de horários, modalidades e professores antes de confirmar a aula.
- O aluno pode cancelar a aula, desde que respeite o prazo definido.

**Nome da atividade 1 - Marcação de aula experimental/ nivelamento**

| **Campo**       | **Tipo**         | **Restrições**                               | **Valor default** |
| ---             | ---              | ---                                          | ---               |
| Nome do aluno   | Caixa de Texto   | obrigatório                                  |   -               |
| email           | Caixa de Texto   | formato de email                             |   -               |
| Tipo de aula    | Seleção única    | Experimental ou Nivelamento                  |   -               |
| nível de aula   | Seleção únicac   | Iniciante, Basico, Intermediário ou Avançado |   -               |

| **Comandos**         |  **Destino**            | **Tipo**          |
| ---                  | ---                     | ---               |
| confirma             | Qual o tipo de aula?    | default           |
| cancelar             | Fim do processo 1       | cancel            |


**Nome da atividade 2 - Marcar aula Fixa (Operação realizada pelo administrador)**

| **Campo**       | **Tipo**         | **Restrições**                                   | **Valor default** |
| ---             | ---              | ---                                              | ---               |
|Nome do aluno    | Caixa de texto   | Obrigatório                                      |  -                |
|Nível da aula    | Caixa de texto   | Somente leitura (nível determinado do aluno)     |  -                |
|Datas da aula    | Data             | Obrigatório (dias da semana determinados)        |  -                |
|Horário da aula  | Hora             | Obrigatório (dentro do horário de funcionamento) |  -                |
|Modalidade       | Seleção única    | Lista de modalidades disponíveis                 |  -                |
|Professor        | Seleção única    | Lista de professores disponíveis                 |  -                |



| **Comandos**         |  **Destino**                     | **Tipo**    |
| ---                  | ---                              | ---         |
| confirmar            | Confirmar aula em horário fixo?  | default     |
| cancelar             | Fim do processo                  |             |


**Nome da atividade 3 - Marcar aula Livre (Operação realizada pelo aluno)**

| **Campo**       | **Tipo**         | **Restrições**                                   | **Valor default** |
| ---             | ---              | ---                                              | ---               |
|Nível da aula    | Caixa de texto   | Somente leitura (nível determinado do aluno)     |  -                |
|Datas da aula    | Data             | Obrigatório (dias da semana determinados)        |  -                |
|Horário da aula  | Hora             | Obrigatório (dentro do horário de funcionamento) |  -                |
|Modalidade       | Seleção única    | Lista de modalidades disponíveis                 |  -                |
|Professor        | Seleção única    | Lista de professores disponíveis                 |  -                |



| **Comandos**         |  **Destino**                     | **Tipo**    |
| ---                  | ---                              | ---         |
| confirmar            | Confirmar aula em horário fixo?  | default     |
| cancelar             | Fim do processo                  |             |


**Nome da atividade 4 - Verificar disponibilidade de vagas para aulas Livre (Operação realizada pelo sistema)**

| **Campo**             | **Tipo**         | **Restrições**                                   | **Valor default** |
| ---                   | ---              | ---                                              | ---               |
|Data da aula           | Data             | Obrigatório                                      |  -                |
|Horário da aula        | Hora             | Obrigatório (dentro do horário de funcionamento) |  -                |
|Modalidade             | Seleção única    | Lista de modalidades disponíveis                 |  -                |
|Professor              | Seleção única    | Lista de professores disponíveis                 |  -                |
|Data e horário da aula | Data e hora      | Somente horários disponíveis                     |  -                |


| **Comandos**         |  **Destino**                      | **Tipo**    |
| ---                  | ---                               | ---         |
| confirmar            | Confirmar aula em horário livre?  | default     |
| cancelar aula        | Cancelar aula dentro do prazo?    | default     |




