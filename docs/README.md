# DanceSys


**Aline Cristina Braz de Oliveira, acboliveira@sga.pucminas.br**

**Ana Carolina Costa Coimbra, ana.carolina@sga.pucminas.br**

**Artur Costa Cavalcante Coelho, acccoelho@sga.pucminas.br**

**Henrique Moreira Gomes de Carvalho, henrique.carvalho.1521318@sga.pucminas.br**

**Julia Rocha Fiorini, jfiorini@sga.pucminas.br**

**Renato Douglas Nascimento Silva De Oliveira, rdnsoliveira@sga.pucminas.br**

**Vicenzo Fonseca de Mello Souza, vicenzo.fonseca@sga.pucminas.br**

---

Professores:

** Michelle Hanne Soares de Andrade **

** Joana gabriela Ribeiro de Souza **

** Danilo de Quadros Maia Filho **

---

_Curso de Engenharia de Software_

_Instituto de Informática e Ciências Exatas – Pontifícia Universidade Católica de Minas Gerais (PUC MINAS), Belo Horizonte – MG – Brasil_

---

_**Resumo**. Este projeto visa desenvolver um sistema especializado para a gestão de escolas de dança, abordando necessidades específicas não plenamente atendidas pelas soluções atuais. O sistema integrará alunos, professores e administradores, facilitando processos como organização e agendamento de aulas, controle de frequência, monitoramento do desempenho dos alunos e comunicação interna. Além disso, proporcionará autonomia aos alunos para gerenciar suas aulas, otimizará a gestão financeira e aprimorará a coordenação de eventos escolares. 

---


## 1. Introdução

_A dança é uma expressão cultural rica e diversificada no Brasil, refletindo a mistura de influências indígenas, africanas e europeias. Cada região tem suas danças típicas, como o frevo em Pernambuco e o samba no Rio de Janeiro, que contam histórias e expressam emoções [1.6]. O Brasil abriga um número expressivo de escolas de dança, que formam bailarinos e bailarinas de destaque no cenário nacional e internacional [1.7]. De acordo com o Catálogo de Escolas do Instituto Nacional de Estudos e Pesquisas Educacionais Anísio Teixeira (Inep), há diversas instituições de ensino básico que oferecem disciplinas relacionadas à dança.

A gestão dessas instituições envolve desafios complexos, como o controle de matrículas, a organização de turmas e horários, o acompanhamento financeiro e a comunicação eficaz entre alunos, professores e administradores. Embora existam sistemas de gestão disponíveis no mercado, muitos não atendem às particularidades das escolas de dança, como a necessidade de flexibilidade nos agendamentos e a gestão detalhada do desempenho artístico dos alunos. Por exemplo, o Moodle[1.8] é uma plataforma amplamente utilizada em instituições de ensino, mas pode não contemplar todas as especificidades necessárias para a gestão de escolas de dança.

Essa lacuna cria uma demanda clara por soluções integradas e personalizadas. Atualmente, existem sistemas especializados, como o SisDança[1.9], que oferecem funcionalidades adaptadas às necessidades dessas instituições, incluindo cobranças automatizadas e aplicativos para alunos.
A adoção de sistemas de gestão especializados pode otimizar a administração e proporcionar uma experiência mais eficiente e satisfatória para todos os envolvidos, contribuindo para o desenvolvimento contínuo da dança no Brasil._

### 1.1 Contextualização

_A administração de escolas de dança vai além do ensino artístico, exigindo um gerenciamento eficaz de turmas, horários, pagamentos e comunicação. Diferente de instituições de ensino tradicionais, essas escolas necessitam de maior flexibilidade nos agendamentos e de um acompanhamento detalhado do progresso técnico e artístico dos alunos.

Muitos gestores enfrentam dificuldades com ferramentas genéricas, que não atendem às especificidades desse setor. Isso cria a necessidade de uma solução tecnológica que unifique e otimize os processos administrativos, garantindo mais organização, eficiência e uma melhor experiência para alunos, professores e administradores._

### 1.2 Problema

_Verifica-se que uma escola de dança seria um ambiente adequado para o desenvolvimento desse trabalho, pois se identificam claramente os tópicos que necessitam de aprimoramento. Entre eles, destacam-se a falta de comunicação entre a administração e os alunos, a necessidade de melhoria significativa no gerenciamento financeiro e a implementação de uma gestão fixa de horários que facilite o agendamento de aulas. Além disso, é um problema a falta de autonomia dos alunos ao interagir com o aplicativo e com a escola, sem a necessidade de recorrer a alguém da gerência._

### 1.3 Objetivo geral

_Desenvolver um software para organizar, gerenciar e dar suporte às escolas de dança, solucionando problemas de desordem, falta de gestão financeira e de eventos e comunicação entre integrantes._

#### 1.3.1 Objetivos específicos

_Solucionar dificuldades dos alunos ao marcar e desmarcar aulas;
Realizar toda a gestão financeira das aulas, facilitando os pagamentos dos alunos aos professores e escola de dança;
Ajudar no gerenciamento de eventos da escola, mostrando visualmente por meio de um calendário._

### 1.4 Justificativas

_A administração de uma escola de dança envolve uma série de tarefas essenciais, como o controle de matrículas, a organização de turmas e horários, o acompanhamento financeiro e a comunicação entre alunos e professores. Quando esses processos são realizados manualmente ou com ferramentas inadequadas, surgem riscos como erros, perda de informações e dificuldades na organização, o que afeta diretamente a eficiência da instituição. Esse tipo de falha pode resultar em prejuízos financeiros e danos à imagem da escola, afastando potenciais clientes.

Um Sistema de Gerenciamento de Escola de Dança é fundamental para otimizar a administração, garantindo maior controle sobre as operações diárias e proporcionando uma experiência mais fluida e eficiente para alunos e professores. Essa solução tecnológica contribui para a profissionalização da gestão, agregando valor à escola e fortalecendo sua imagem no mercado._

## 2. Participantes do processo

_Alunos - incluso pais e responsáveis: São os clientes e alunos da escola, que seriam uma vesão extrema do usuário final. São essas pessoas quem interagem com o sistema na sua versão final.

Professores: Tem pouco acesso direto ao sistema, mas são uma parte integrante do processo.

Administradores - financeiros, gestores e coordenadores criativos: são responsáveis por parte expressiva de interação com o sistema. São eles quem tem acesso à todas as partes e gerenciamentos do sistema dentro da escola._

## 3. Modelagem do processo de negócio
### 3.1. Análise da situação atual

_Atualmente, a administração de escolas de dança é realizada através de sistemas genéricos, planilhas ou até mesmo manualmente.  Numerosos programas educacionais não satisfazem as demandas específicas dessas instituições de ensino, como a adaptabilidade no horário das aulas e a monitorização do rendimento artístico dos estudantes [1.8][1.9].
 
 Gereciameto de pessoas
O gerenciamento pessoal de alunos e pofessores e até mesmo de funcionários é feito, em alguns casos, de forma manual, mantendo pastas de arquivos do tipo docx, ou planilhas do excel e em algumas escolas até mesmo de forma analógica.

 Gerenciamento/ cadastramento de turmas
 De modo geral, as turmas são montadas no inicio do ano, de acordo com a disponibilidade dos professores, de salas (dependendo da capacidade física da escola) e da demanda dos alunos. Os horários são comunicados por grupos de WhatsApp ou por e-mail. Na maioria das escolas os horários e turmas são definitivas e não possuel muita liberdade de mudanças a curto prazo, e nas escolas onde exite essa flexibilidade não há um controle específico ou o controle é tambem feito pelo WhatsApp.

 Gerenciamento de calendário e eventos
Em sua maioria, não existe um sistema que gerencie eventos ou que informe especificamente aos alunos e responsáveis sobre os acontecimentos da escola. Os informes são feitos pelo WhatsApp e por e-mail. 

 Gestão financeira
 Feita utilizando o cadastro de alunos. Usualmente cobranças são feitas por aplicativos de terceiros._

### 3.2. Descrição geral da proposta de solução

_O propósito deste trabalho será o desenvolvimento de um sistema integrado que otimize o gerenciamento interno de escolas de dança. Em entrevistas com interessados da área, foi constatada a necessidade de serviços especializados no mercado, uma vez que os sistemas utilizados são genéricos e pouco intuitivos.

Gerenciamento de pessoas
Nessa parte, seria feito um cadastro de cada participante dos processos da instituição:
-Professores
-Alunos/ Responsáveis
-Administradores
-Funcionários
Esse cadastro facilita o manejo das informações pessoais e de contato de cada membro, bem como informações profissionais de cada um dos professores, funcionários e administradores da escola, tornando viável sua utilização no sistema. 
O cadastro de alunos pode ser feito pelo próprio aluno, sem a necessidade de intervenção da escola. Os cadastros de professores e funcionários e ate mesmo administrativos deverão ser feito pelo administrador.

Gerenciamento/ cadastramento de turmas
Dadas as disponibilidades de horários e de salas, um processo realizado internamente na escola, será feito  através de uma aba onde será possivel gerenciar quantas e quais aulas cada professor dará, sendo visível para os alunos. Dessa forma os alunos terão conhecimento de quais horários cada modalidade e professor estará disponível.
Nessa aba, os alunos serão divididos em duas categorias, a depender da idade e tipo de matricula. Uma seria uma modalidade livre, onde o aluno tem a liberdade de escolher qual modalidade e qual horário ele gostaria de fazer a aula, a depender da lotação de cada sala. Nessa modalidade estariam alunos mais velhos e/ou com maior experiencia na dança. E uma categoria fixa (previamente estabelecida pela idade e/ou nível técnico do aluno) onde mostraria apenas a grade fixa das aulas que o aluno poderia participar.
O gerenciamento de turmas e horários deve ser feito pelo administrador, podendo ou não dar alguma liberdade para o professor fazer alguma alteração em sua própria grade de horários.

Gerenciamento de calendário e eventos
Nessa parte do produto, será feito uma gestão interna da escola que ficaria visível para os alunos e responsáveis de eventos e do calendário útil da escola. Nesse local, será possivel comunicar os dias em que a escola estaria aberta, visto que, muitas vezes as escolas de dança continuam seu funcionamento em feriados nacionais e as atividades extracurriculares aos alunos, como concursos, recitais, audições e espetáculos. Dando uma visibilidade maior aos acontecimentos da instituição, sem a necessidade de um contato direto via e-mail ou WhatsApp.
Nessa aba, pensamos em criar uma espécie de calendário interativo, para que o aluno possa filtrar quais eventos lhes são importantes.
Essa parte será unicamente gerenciada pelo administrador.

Gestão Financeira  
Parte gerencial, onde a administração irá estabelecer e enviar cobranças e pagamentos referentes à salário de professores, ademais, gestão das mensalidades, taxas de atividades extras, valores de figurinos e afins.
Essa gestão será feita unicamente pelo administrador._

### 3.3. Modelagem dos processos

[PROCESSO 1 - Cadastro de Alunos](processo-1-cadastro-alunos.md "Processo de cadastro de alunos.")

[PROCESSO 2 - Cadastro de professores](processo-2-cadastro-professores.md "Processo de cadastro de professores.")

[PROCESSO 3 - Gerenciamento de Calendário](processo-3-gerenciamento-de-calendario.md "Detalhamento da gerencia de calendário.")

[PROCESSO 4 - Gestão Financeira](processo-4-gestao-financeira.md "Detalhamento da gestão financeira.")

[PROCESSO 5 - Marcação de Aulas](processo-5-marcação-de-aulas.md "Detalhamento da marcação de aulas.")

## 4. Projeto da solução

_O documento a seguir apresenta o detalhamento do projeto da solução. São apresentadas duas seções que descrevem, respectivamente: modelo relacional e tecnologias._

[Projeto da solução](solution-design.md "Detalhamento do projeto da solução: modelo relacional e tecnologias.")


## 5. Indicadores de desempenho

_O documento a seguir apresenta os indicadores de desempenho dos processos._

[Indicadores de desempenho dos processos](performance-indicators.md)


## 6. Interface do sistema

_A sessão a seguir apresenta a descrição do produto de software desenvolvido._ 

[Documentação da interface do sistema](interface.md)

## 7. Conclusão

O desenvolvimento do sistema DanceSys representa um avanço significativo na gestão de escolas de dança, oferecendo soluções adaptadas às necessidades específicas dessas instituições. A implementação de um sistema integrado e especializado facilita a administração de turmas, agendamentos, comunicação e controle financeiro, além de proporcionar maior autonomia aos alunos para gerenciar suas aulas.

A solução proposta visa otimizar a experiência de alunos, professores e administradores, tornando os processos mais eficientes e minimizando os erros comuns encontrados em sistemas genéricos. A flexibilidade no agendamento de aulas, o controle financeiro preciso e o gerenciamento de eventos são apenas alguns dos aspectos que tornam este sistema uma ferramenta poderosa para a administração de escolas de dança.

Além disso, a utilização de uma interface intuitiva e acessível garante que todos os usuários possam interagir com o sistema de forma simples e eficaz. A possibilidade de personalização de turmas e horários, juntamente com a gestão de eventos e comunicação, torna o DanceSys uma plataforma completa para escolas de dança de diferentes portes.

Para futuras melhorias, seria interessante explorar a integração com outros sistemas de gestão e expandir a plataforma para incluir recursos como avaliação de desempenho dos alunos, análise de relatórios mais detalhados e a criação de um módulo de feedback para professores e alunos. Essas sugestões podem aumentar ainda mais a eficiência da gestão e promover uma comunicação mais fluida e transparente dentro da escola de dança.

Esse trabalho contribui para a evolução das ferramentas utilizadas nas escolas de dança, alinhando-se às demandas modernas de gestão e proporcionando uma experiência de ensino e aprendizado mais organizada e profissionalizada.

# REFERÊNCIAS

_Como um projeto de software não requer revisão bibliográfica, a inclusão das referências não é obrigatória. No entanto, caso você deseje incluir referências relacionadas às tecnologias, padrões, ou metodologias que serão usadas no seu trabalho, relacione-as de acordo com a ABNT._

_Verifique no link abaixo como devem ser as referências no padrão ABNT:_

http://portal.pucminas.br/imagedb/documento/DOC_DSC_NOME_ARQUI20160217102425.pdf

**[1.1]** - _ELMASRI, Ramez; NAVATHE, Sham. **Sistemas de banco de dados**. 7. ed. São Paulo: Pearson, c2019. E-book. ISBN 9788543025001._

**[1.2]** - _COPPIN, Ben. **Inteligência artificial**. Rio de Janeiro, RJ: LTC, c2010. E-book. ISBN 978-85-216-2936-8._

**[1.3]** - _CORMEN, Thomas H. et al. **Algoritmos: teoria e prática**. Rio de Janeiro, RJ: Elsevier, Campus, c2012. xvi, 926 p. ISBN 9788535236996._

**[1.4]** - _SUTHERLAND, Jeffrey Victor. **Scrum: a arte de fazer o dobro do trabalho na metade do tempo**. 2. ed. rev. São Paulo, SP: Leya, 2016. 236, [4] p. ISBN 9788544104514._

**[1.5]** - _RUSSELL, Stuart J.; NORVIG, Peter. **Inteligência artificial**. Rio de Janeiro: Elsevier, c2013. xxi, 988 p. ISBN 9788535237016._

**[1.6]** - **Diversidade Cultural no Brasil.** Disponível em: <https://mundoeducacao.uol.com.br/geografia/diversidade-cultural-no-brasil.htm>.

**[1.7]** - **Diversidade Cultural no Brasil.** Disponível em: <https://www.suasaulasparticulares.com.br/blog/aulas-particulares-ballet-criancas-adultos-todo-brasil>.

**[1.8]** - **MOODLE** Disponível em: <https://moodle.org/>.

**[1.9]** - **SISDANÇA** Disponível em: <https://sisdanca.app.br/>.





# APÊNDICES


_Atualizar os links e adicionar novos links para que a estrutura do código esteja corretamente documentada._


## Apêndice A - Código fonte

[Código do front-end](../src/front) -- repositório do código do front-end

[Código do back-end](../src/back)  -- repositório do código do back-end


## Apêndice B - Apresentação final


[Slides da apresentação final](presentations/)


[Vídeo da apresentação final](video/)






