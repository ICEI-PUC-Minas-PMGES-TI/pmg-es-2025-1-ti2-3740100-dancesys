### 3.3.2 Processo 2 – CADASTRO PROFESSORES

Aqui é apresentado o processo de cadastro de professores.  
O cadastro é realizado pela administração da escola, onde são solicitadas as informações pessoais e profissionais do professor.  
Após a verificação e validação dos dados, a compatibilidade de horários é analisada. Caso os horários sejam compatíveis com a disponibilidade da escola, uma grade de horários é montada e um login temporário é gerado.  
No final do processo, o professor recebe um e-mail com as credenciais de acesso e uma senha temporária, sendo solicitado que ele altere a senha ao acessar o sistema pela primeira vez.  

#### Modelo do Processo

![Modelo BPMN do PROCESSO CADASTRO PROFESSORES](images/CadastroProfessores_Diagrama.png "Modelo BPMN do Processo CADASTRO PROFESSORES.")

#### Detalhamento das atividades

### **Atividade 1 – Cadastro de Professor**  

_Cadastro completo para professores que desejam atuar na escola._

| **Campo**                       | **Tipo**             | **Restrições**                          | **Valor default**               |
|---------------------------------|---------------------|----------------------------------------|--------------------------------|
| Nome                            | Caixa de Texto     | Obrigatório                            |                                |
| Telefone                        | Caixa de Texto     | Obrigatório                            |                                |
| E-mail                          | Caixa de Texto     | Formato de e-mail                      |                                |
| Endereço                        | Área de Texto      | Obrigatório                            |                                |
| Modalidade pretendida           | Seleção única      | Obrigatório                            |                                |
| Informações profissionais       | Área de Texto      | Obrigatório                            |                                |
| Disponibilidade de horários     | Tabela             | Obrigatório                            |                                |
| Experiência                     | Seleção única      | Sim/Não                                |                                |
| Login                           | Caixa de Texto     | Auto-gerado                            | Gerado automaticamente        |
| Senha                           | Caixa de Texto     | Senha padrão enviada por e-mail        | Auto-gerado                    |

| **Comandos**          | **Destino**                                   | **Tipo**   |
|----------------------|---------------------------------------------|-----------|
| Finalizar Cadastro  | Enviar credenciais por e-mail e ativar conta | default   |
| Cancelar            | Fim do Processo                              | cancel    |

---

### **Fluxo de Cadastro**
1. A administração solicita os dados do professor.  
2. O professor insere suas informações pessoais e profissionais.  
3. O professor define sua disponibilidade de horários e a modalidade pretendida.  
4. A administração valida as informações e verifica a compatibilidade dos horários.  
   - Caso as informações estejam incorretas, o professor deve corrigi-las.  
   - Se os horários não forem compatíveis com a disponibilidade da escola, o professor deve ajustá-los.  
5. Se tudo estiver correto, uma grade de horários é montada.  
6. O sistema gera um login e uma senha temporária para o professor.  
7. Um e-mail automático é enviado contendo:  
   - Login do professor.  
   - Senha temporária.  
   - Instruções para acessar o perfil e alterar a senha.  
8. O professor acessa o sistema e altera a senha temporária no primeiro login.  

---
