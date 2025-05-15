import { Component, inject } from '@angular/core';
import { ModalComponent } from '../../../../../components/modal/modal.component';
import { SimpleTableComponent } from '../../../../../components/simple-table/simple-table.component';
import { SearchBoxMultiComponent } from '../../../../../components/search-box-multi/search-box-multi.component'
import { MultiSelectInputComponent } from '../../../../../components/multi-select-input/multi-select-input.component';
import { Form, FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { AdminService, ProfessorResponse } from '../../../../../services/admin.service';
import { BotaoComponent } from '../../../../../components/botao/botao.component';
import { CommonModule } from '@angular/common';
import { UsuarioFiltro } from '../../../../../models/usuario.model';
import { ProfessorFiltro } from '../../../../../models/professor.model';
import { AulaOcorrenciaFilter } from '../../../../../models/AulaOcorrencia.model';
import { Mensagem } from '../../../../../models/Mensagem.model';
import { AlertService } from '../../../../../services/Alert.service';

@Component({
  selector: "app-aulas-recorrentes-admin-page",
  imports: [
    ModalComponent,
    SimpleTableComponent,
    SearchBoxMultiComponent,
    MultiSelectInputComponent,
    ReactiveFormsModule,
    BotaoComponent,
    CommonModule
  ],
  templateUrl: "./aulas-recorrentes-admin-page.component.html",
  styleUrl: "./aulas-recorrentes-admin-page.component.css",
})
export class AulasRecorrentesAdminPageComponent {
  adminService = inject(AdminService);
  alertService = inject(AlertService);

  filterForm: FormGroup
  mensagemForm: FormGroup

  statusMap: Record<number, string> = {
		0: "Cancelada",
		1: "Ativa",
	};

  colunas = [
    { chave: 'codigo', titulo: 'Codigo' },
    { chave: 'idAula.idProfessor.idUsuario.nome', titulo: 'Professor' },
    { chave: 'data', titulo: 'Data', formatar: (valor: Date) => valor != null ? this.formatardata(valor) : '' },
    { chave: 'idAula.horarioInicio', titulo: 'Inico', formatar: (valor: any) => valor != null ? this.formatarHorario(valor) : '' },
    { chave: 'idAula.horarioFim', titulo: 'Fim', formatar: (valor: any) => valor != null ? this.formatarHorario(valor) : '' },
    { chave: 'idAula.idModalidade.nome', titulo: 'Modalidade' },
    { chave: 'status', titulo: 'Status', width: '10%', formatar: (valor: number) => this.statusMap[valor] ?? String(valor) }
  ];

  acoes = [
    {
      icon: 'warning',
      title: 'Status',
      cor: 'dark',
      callback: (item: any) => this.status(item)
    },
    {
      icon: 'view',
      title: 'Visualizar',
      cor: 'dark',
      callback: (item: any) => this.visualizar(item)
    }
  ];
  constructor(private fb: FormBuilder) {
    this.filterForm = this.fb.group({
      professores: [[]],
      alunos: [[]],
      dataInicio: [],
      dataFim: [],
      codigo: [],
      tamanho: [this.itensPage],
      pagina: [this.paginaAtual]
    });

    this.mensagemForm = this.fb.group({
      mensagem: ['']
    })
  }

  paginaAtual: number = 0;
  itensPage: number = 10;

  alunosFilterLs: any = []
  professoresFilterLs: any = []
  aulas: any = []
  selectedaula: any = {}

  isModalOpen: boolean = false
  isCancelModalOpen: boolean = false

  idAula!: number;

  ngOnInit() {
    this.buscar()
  }

  buscarAlunos(termo: any) {
    const filtro: UsuarioFiltro = {
      nome: termo,
      email: "",
      cpf: "",
      tipo: 2,
      status: 1,
    };

    this.adminService.filterUsuarios(filtro).subscribe({
      next: (response) => {
        this.alunosFilterLs = response
      },
    });
  }

  buscarProfessores(termo: any) {
    const filtro: ProfessorFiltro = {
      nome: termo,
      email: "",
      cpf: "",
      status: 1,
    };

    this.adminService.filterProfessores(filtro).subscribe({
      next: (response) => {
        this.professoresFilterLs = response
      },
    });
  }

  getFilterForm() {
    this.filterForm.get('tamanho')?.setValue(this.itensPage);
    this.filterForm.get('pagina')?.setValue(this.paginaAtual);

    const item = this.filterForm.value;
    const AulaFilter: AulaOcorrenciaFilter = item;

    return AulaFilter;
  }

  getMensagemForm(){
    const item = this.mensagemForm.value
    const mensagem: Mensagem = item

    return mensagem;
  }

  buscar() {
    this.adminService.fetchAulasOcorrentes(this.getFilterForm()).subscribe({
      next: (response) => {
        this.aulas = response
      }
    })
  }

  visualizar(item: any) {
    this.isModalOpen = true;
    this.selectedaula = {
      codigo: item.codigo,
      professor: item.idAula.idProfessor.idUsuario.nome,
      status: item.status == 1 ? 'Ativa' : 'Cancelada',
      data: `${this.formatardata(item.data)}`,
      horario: `${this.formatarHorario(item.idAula.horarioInicio)} - ${this.formatarHorario(item.idAula.horarioFim)}`,
      modalidade: item.idAula.idModalidade.nome,
      nivel: item.idAula.nivel == 1 ? 'Basico' : item.idAula.nivel == 2 ? 'Intermediario' : 'Avaçado',
      maxAlunos: item.idAula.maxAlunos,
      chamada: item.chamada,
      mensagem: item.motivoCancelamento
    }
  }

  formatarHorario(valor: any) {
    const split = valor.split(':');

    return `${split[0]}:${split[1]}`
  }

  formatardata(valor: Date) {
    const str = valor.toString()
    const split = str.split("-")

    return `${split[2]}/${split[1]}/${split[0]}`
  }

  status(item: any) {
    this.isCancelModalOpen = true
    this.idAula = item.id
  }

  cancelar(){
    this.adminService.cancelarAulaOcorrente(this.getMensagemForm(), this.idAula).subscribe({
      next: (reponse) =>{
        this.closeMensagemModal()
        this.buscar()
        this.alertService.sucesso("Aula cancelada com sucesso!")
      },
      error: (err) =>{
        const msg = err?.error?.mensagem || 'Erro inesperado';
        this.closeMensagemModal()
        this.alertService.erro(msg);
      }
    })
  }


  closeModal() {
    this.isModalOpen = false
  }

  closeMensagemModal(){
    this.isCancelModalOpen = false;
    this.resetMensagemForm()
  }

  resetMensagemForm(){
    this.mensagemForm = this.fb.group({
      mensagem: ['']
    })
  }

  isFormValido(): boolean {
		return this.mensagemForm.valid;
	}

  onPaginacaoChange(event: { paginaSelecionada: number; itensPage: number }) {
    this.paginaAtual = --event.paginaSelecionada;
    this.itensPage = event.itensPage;
    this.buscar();
  }

  orderBy(event: { chave: string, direcao: 'asc' | 'desc' }){
    console.log(event.chave,"-",event.direcao)
  }
}
