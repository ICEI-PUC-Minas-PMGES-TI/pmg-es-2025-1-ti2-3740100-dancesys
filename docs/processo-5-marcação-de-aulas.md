### 3.3.2 Processo 5 – Marcação de aulas

_Este é o processo de marcação de aulas, onde tanto a administração quanto o aluno podem interagir com o sistema. O aluno, que possui um determinado tipo de matrícula, pode escolher quais aulas pretende fazer durante a semana, sendo guiado pelas regras e validações definidas pelo sistema. A administração pode controlar quais turmas o aluno de matrícula fixa tem acesso._

![Porcesso_MarcaçãoAula](https://github.com/user-attachments/assets/366d4677-7af2-4fae-8991-beaecc1443af)



---

## **Detalhamento das atividades**

### **Atividade 1 – Marcação de aula experimental ou nivelamento**  
- **Responsável:** Aluno  
- **Objetivo:** Permitir que o aluno solicite a marcação de uma aula de nivelamento ou experimental para avaliar seu nível ou conhecer o funcionamento das aulas.  

#### **Detalhamento das etapas:**
1. O aluno preenche um formulário com os seguintes dados:  
   - **Nome**  
   - **E-mail**  
   - **Tipo de aula** – Experimental ou Nivelamento  
   - **Nível da aula** – Iniciante, Básico, Intermediário ou Avançado  
2. O sistema valida as informações inseridas.  
3. O aluno pode:  
   - **Confirmar** ➔ O sistema verifica o tipo de aula e prossegue com o processo.  
   - **Cancelar** ➔ O processo é encerrado sem registrar a solicitação.  


### **Atividade 2 – Marcar aula fixa**  
- **Responsável:** Administrador  
- **Objetivo:** Permitir que o administrador marque uma aula fixa para o aluno.  

#### **Detalhamento das etapas:**
1. O administrador acessa o sistema e preenche os seguintes campos:  
   - **Nome do aluno** – Obrigatório  
   - **Nível da aula** – Preenchido automaticamente com base nos dados do aluno  
   - **Data da aula** – Dentro dos dias disponíveis  
   - **Horário da aula** – Dentro do horário de funcionamento  
   - **Modalidade** – Tipo de dança  
   - **Professor** – Escolha da lista de professores disponíveis  
2. O administrador pode:  
   - **Confirmar** ➔ O sistema agenda a aula e confirma com o aluno.  
   - **Cancelar** ➔ O processo é encerrado sem marcação.  


### **Atividade 3 – Marcar aula livre**  
- **Responsável:** Aluno  
- **Objetivo:** Permitir que o aluno marque uma aula livre diretamente pelo sistema.  

#### **Detalhamento das etapas:**
1. O aluno acessa o sistema e preenche os seguintes campos:  
   - **Nível da aula** – Preenchido automaticamente com base nos dados do aluno  
   - **Data da aula** – Dentro dos dias disponíveis  
   - **Horário da aula** – Dentro do horário de funcionamento  
   - **Modalidade** – Presencial ou online  
   - **Professor** – Escolha da lista de professores disponíveis  
2. O aluno pode:  
   - **Confirmar** ➔ O sistema agenda a aula e envia a confirmação.  
   - **Cancelar** ➔ O processo é encerrado sem marcação.  


### **Atividade 4 – Verificar disponibilidade de vagas para aulas livres**  
- **Responsável:** Sistema  
- **Objetivo:** Permitir que o sistema valide automaticamente a disponibilidade de vagas para uma aula livre.  

#### **Detalhamento das etapas:**
1. O sistema verifica automaticamente os seguintes dados:  
   - **Data da aula** – Obrigatório  
   - **Horário da aula** – Dentro do horário de funcionamento  
   - **Modalidade** – Presencial ou online  
   - **Professor disponível** – Baseado na agenda do professor  
   - **Data e horário** – Com base nos horários disponíveis  
2. O sistema permite:  
   - **Confirmar** ➔ O sistema registra a aula.  
   - **Cancelar aula** ➔ O sistema cancela a aula se estiver dentro do prazo permitido.  


##  **Fluxo geral do processo**  
- O aluno solicita uma aula experimental ou de nivelamento.  
- O sistema valida o tipo de aula e o aluno decide se vai se matricular.  
- Caso o aluno se matricule, ele pode escolher entre aula fixa ou livre.  
- Para aulas fixas, o administrador faz o agendamento.  
- Para aulas livres, o aluno faz o agendamento diretamente pelo sistema.  
- O sistema verifica automaticamente a disponibilidade de horários, modalidades e professores antes de confirmar a aula.  
- O aluno pode cancelar a aula, desde que respeite o prazo definido.  

---

## **Resumo das atividades**  

### **Nome da atividade 1 - Marcação de aula experimental/nivelamento**  

| **Campo**       | **Tipo**         | **Restrições**                               | **Valor default**|
|-----------------|------------------|----------------------------------------------|------------------|
| Nome do aluno   | Caixa de Texto   | Obrigatório                                  | -                |
| E-mail          | Caixa de Texto   | Formato de e-mail                            | -                |
| Tipo de aula    | Seleção única    | Experimental ou Nivelamento                  | -                |
| Nível de aula   | Seleção única    | Iniciante, Básico, Intermediário ou Avançado | -                |

| **Comandos**         |  **Destino**            | **Tipo**          |
| ---                  | ---                     | ---               |
| confirma             | Qual o tipo de aula?    | default           |
| cancelar             | Fim do processo 1       | cancel            |

---

### **Nome da atividade 2 - Marcar aula fixa (Operação realizada pelo administrador)**  

| **Campo**        | **Tipo**         | **Restrições**                                   | **Valor default** |
|------------------|------------------|--------------------------------------------------|-------------------|
| Nome do aluno    | Caixa de texto   | Obrigatório                                      | -                 |
| Nível da aula    | Caixa de texto   | Somente leitura (nível determinado do aluno)     | -                 |
| Data da aula     | Data             | Obrigatório (dias da semana determinados)        | -                 |
| Horário da aula  | Hora             | Obrigatório (dentro do horário de funcionamento) | -                 |
| Modalidade       | Seleção única    | Lista de modalidades disponíveis                 | -                 |
| Professor        | Seleção única    | Lista de professores disponíveis                 | -                 |

| **Comandos**         |  **Destino**                     | **Tipo**    |
| ---                  | ---                              | ---         |
| confirmar            | Confirmar aula em horário fixo?  | default     |
| cancelar             | Fim do processo                  |             |

---

### **Nome da atividade 3 - Marcar aula livre (Operação realizada pelo aluno)**  

| **Campo**        | **Tipo**         | **Restrições**                                   | **Valor default**|
|------------------|------------------|--------------------------------------------------|------------------|
| Nível da aula    | Caixa de texto   | Somente leitura (nível determinado do aluno)     | -                |
| Data da aula     | Data             | Obrigatório (dias da semana determinados)        | -                |
| Horário da aula  | Hora             | Obrigatório (dentro do horário de funcionamento) | -                |
| Modalidade       | Seleção única    | Lista de modalidades disponíveis                 | -                |
| Professor        | Seleção única    | Lista de professores disponíveis                 | -                |

| **Comandos**         |  **Destino**                     | **Tipo**    |
| ---                  | ---                              | ---         |
| confirmar            | Confirmar aula em horário fixo?  | default     |
| cancelar             | Fim do processo                  |             |

---

### **Nome da atividade 4 - Verificar disponibilidade de vagas para aulas livres (Operação realizada pelo sistema)**  

| **Campo**             | **Tipo**         | **Restrições**                                   | **Valor default** |
|-----------------------|------------------|--------------------------------------------------|-------------------|
| Data da aula          | Data             | Obrigatório                                      | -                 |
| Horário da aula       | Hora             | Obrigatório (dentro do horário de funcionamento) | -                 |
| Modalidade            | Seleção única    | Lista de modalidades disponíveis                 | -                 |
| Professor             | Seleção única    | Lista de professores disponíveis                 | -                 |
| Data e horário da aula| Data e hora      | Somente horários disponíveis                     | -                 |

| **Comandos**         |  **Destino**                      | **Tipo**    |
| ---                  | ---                               | ---         |
| confirmar            | Confirmar aula em horário livre?  | default     |
| cancelar aula        | Cancelar aula dentro do prazo?    | default     |

---


