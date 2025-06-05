import { Component, inject } from '@angular/core';
import { ModalComponent } from '../../../../../components/modal/modal.component';
import { SimpleTableComponent } from '../../../../../components/simple-table/simple-table.component';
import { SearchBoxSingleComponent } from '../../../../../components/search-box-single/search-box-single.component';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { BotaoComponent } from '../../../../../components/botao/botao.component';
import { CommonModule } from '@angular/common';
import { EventoService } from '../../../../../services/evento.service';
import { AdminService } from '../../../../../services/admin.service';
import { UsuarioFiltro } from '../../../../../models/usuario.model';
import { FigurinoFilter } from '../../../../../models/Figurino.model';
import { Evento, EventoFilter, EventoResponse } from '../../../../../models/evento.model';
import { ApresentacaoEvento, ApresentacaoEventoFilter, ApresentacaoEventoResponse } from '../../../../../models/apresentacao_evento.model';
import { FigurinoAluno, FigurinoAlunoFilter } from '../../../../../models/FigurinoAluno.model';
import { AlertService } from '../../../../../services/Alert.service';

enum ToggleModal {
	NEW = "Criar Figurino relacionado a aluno",
	EDIT = "Editar Figurino relacionado a aluno",
}

@Component({
  selector: 'app-figurinos-por-aluno-admin-page',
  standalone: true,
  imports: [
    ModalComponent,
		SimpleTableComponent,
		SearchBoxSingleComponent,
		ReactiveFormsModule,
		BotaoComponent,
		CommonModule,
  ],
  templateUrl: './figurinos-por-aluno-admin-page.component.html',
  styleUrl: './figurinos-por-aluno-admin-page.component.css'
})
export class FigurinosPorAlunoAdminPageComponent {
  eventoService = inject(EventoService);
  adminService = inject(AdminService);
  aleretService = inject(AlertService)

	filterForm: FormGroup;
	figurinoAlunoForm: FormGroup;


  ToggleModal = ToggleModal

	alunosFilterLs: any[] = [];
	figurinoFilterLs: any[] = [];
	eventoFilterLs: Evento[] = [];
  apresentacoesFilterLs: ApresentacaoEvento[] = []
  figurinoObj: any = []

  isModalOpen: boolean = false
  isEdit: boolean = false

  paginaAtual: number = 0;
  itensPage: number = 10;
  orderByValue!: string;
  orderValue!: string

  tamanhosLs: string[] = ['PP', 'P', 'M', 'G', 'GG', 'XGG']

  colunas = [
		{ chave: "idAluno.idUsuario.nome", titulo: "Aluno" },
		{ chave: "idFigurino.nome", titulo: "Figurino"},
		{ chave: "idApresentacaoEvento.nome", titulo: "Apresentação"},
		{ chave: "tamanho", titulo: "Tamanho" },
	];

  acoes = [
		{
			icon: "edit",
			title: "Editar",
			cor: "dark",
			callback: (item: any) => this.editar(item),
		},
		{
			icon: "delete",
			title: "Excluir",
			cor: "dark",
			callback: (item: any) => this.excluir(item),
		},
	];


  constructor(private fb: FormBuilder) {
		this.filterForm = this.fb.group({
			idAluno: [],
			idEvento: [],
			idApresentacao: [],
			idFigurino: [],
      pagina: [this.paginaAtual],
      tamanho: [this.itensPage],
      orderBy: [this.orderByValue],
      order: [this.orderValue]
		});

    this.figurinoAlunoForm = this.fb.group({
      id: [],
      idAluno: [],
      idApresentacaoEvento: [],
      idFigurino: [],
      codigo: [],
      tamanho: [],
      status: []
    })
	}

  ngOnInit(){
    this.buscar()
  }

  limparFiltros(){
    this.filterForm = this.fb.group({
			idAluno: [],
			idEvento: [],
			idApresentacao: [],
			idFigurino: [],
      pagina: [this.paginaAtual],
      tamanho: [this.itensPage],
      orderBy: [this.orderByValue],
      order: [this.orderValue]
		});
  }

  buscarAluno(termo: any) {
    const filtro: UsuarioFiltro = {
      nome: termo,
      email: "",
      cpf: "",
      tipo: 2,
      status: 1,
    };

    this.adminService.filterUsuarios(filtro).subscribe({
      next: (response) => {
        this.alunosFilterLs = response;
      },
    });
  }

  buscarEvento(termo: any) {
    const filtro: EventoFilter = {
      nome: termo,
      local: null,
      data: null,
      alunos: [],
      pagina: 0,
      tamanho: 0,
    };

    this.adminService.fetchEventos(filtro).subscribe({
      next: (response: EventoResponse) => {
        this.eventoFilterLs = response.conteudo;
      },
    });
  }

  buscarFigurino(termo: any) {
    const filtro: FigurinoFilter = {
      nome: termo,
      valor: null,
      tipo: null,
      tamanho: 0,
      pagina: 0,
      orderBy: null,
      order: null
    };

    this.eventoService.filterfigurno(filtro).subscribe({
      next: (response: any) => {
        this.figurinoFilterLs = response.conteudo;
      },
    });
  }

  buscarApresentacao(termo: any) {
    const filtro: ApresentacaoEventoFilter = {
      nome: termo,
      idEvento: null,
      alunos: [],
      tamanho:  0,
      pagina: 0
    };

    this.adminService.fetchApresentacoes(filtro).subscribe({
      next: (response: ApresentacaoEventoResponse) => {
        this.apresentacoesFilterLs = response.conteudo;
      },
    });
  }

  novo(){
    this.isModalOpen = true
  }

  closeModal(){
    this.isModalOpen = false
    this.isEdit = false
  }

  salvar(){
    this.eventoService.salvarFigurinoAluno(this.getFigurnoAlunoForm()).subscribe({
      next: (response) =>{
        this.aleretService.sucesso("Figurino relacionado a aluno com sucesso")
        this.buscar()
        this.closeModal()
      }
    })
  }

  editar(item: any){
    this.isEdit = true
    this.isModalOpen = true
  }

  excluir(item: any){

  }

  getFigurnoAlunoForm(){
    const item = this.figurinoAlunoForm.value;
		const Figurino : FigurinoAluno = item;

		return Figurino;
  }

  getfilter(){
    this.filterForm.get("tamanho")?.setValue(this.itensPage);
		this.filterForm.get("pagina")?.setValue(this.paginaAtual);
		this.filterForm.get("orderBy")?.setValue(this.orderByValue);
		this.filterForm.get("order")?.setValue(this.orderValue);


		const item = this.filterForm.value;
		const FigurinoFilter: FigurinoAlunoFilter = item;

		return FigurinoFilter;
  }

  onFilter(){
    this.buscar()
  }

  buscar(){
    this.eventoService.filterFigurinoAluno(this.getfilter()).subscribe({
      next: (reponse: any) =>{
        this.figurinoObj = reponse
        console.log(this.figurinoObj);
      }
    })
  }

  onPaginacaoChange(event: { paginaSelecionada: number; itensPage: number }) {
    //this.tabela.isLoad(true)
    this.paginaAtual = --event.paginaSelecionada;
    this.itensPage = event.itensPage;
    this.buscar();
  }

  orderBy(event: { chave: string, direcao: 'asc' | 'desc' }){
    //this.tabela.isLoad(true)
    this.orderByValue = event.chave;
    this.orderValue = event.direcao;
    this.buscar()
  }

}
