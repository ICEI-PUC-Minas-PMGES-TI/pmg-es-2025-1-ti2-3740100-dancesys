### 3.3.4 Processo 4 – gestão Financeira

O objetivo da gestão financeira é dar a liberade de vizualição de manipulação do controle financeiro pela parte da administração da escola, tanto em pagamentos, reembolsos, dividendos e mensalidades. Os alunos teram um acesso facilitados aos seus boletos, pagamentos de eventos e figurinos e dividendos extras que possa ocorrer

#### Detalhamento das atividades

![Modelo BPMN do pagamento de mensalidade](images/process.png "Modelo BPMN de pagamento de mensalidade")

*Pagamento de Mensalidade*
```
Pagamento de mensalidade se resume em o aluno gerar seus boletos, o processo calculara seus dividendos (mensalidades atrasadas e valores extras) e somar ao valor total da mensalidade, tudo sera salvo e podera ser exibido em uma tela de vizualização da administração
```

_Os tipos de dados a serem utilizados são:_

_* **Status Mensalidade** - Booleano sobre o status da mensalides, se esta paga ou não

_* **Dividendos** - Campo que armazerá pagamentos extras caso alunos precise

_* **Valor Boleto** - Campo com o valor da mensalidade somado aos dividendos

_* **Boleto** - Campo que sera gerado o pdf do boleto

| **Campo**          | **Tipo**         | **Restrições**         | **Valor default** |
| ---                | ---              | ---                    | ---               |
| Status Mensalidade | Boolean          |                        | 0                 |
| Dividendos         | float            | (9,2)                  |                   |
| Valor Boleto       | float            | (9,2)                  |                   |
| Boleto             | image            | pdf                    |                   |


| **Comandos**         |  **Destino**                   | **Tipo**          |
| ---                  | ---                            | ---               |
| Gerar Boleto         | Gera boleto com valor total    | default           |



