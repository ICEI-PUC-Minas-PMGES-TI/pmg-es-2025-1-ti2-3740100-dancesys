import { Component, inject, OnInit, ViewChild } from "@angular/core";
import { SimpleTableComponent } from "../../../../../components/simple-table/simple-table.component";
import { BotaoComponent } from "../../../../../components/botao/botao.component";
import { CommonModule, DatePipe, Time } from "@angular/common";
import { FormsModule, NgForm } from "@angular/forms";
import { ModalComponent } from "../../../../../components/modal/modal.component";
import { AdminService } from "../../../../../services/admin.service";
import {
	ApresentacaoEvento,
	ApresentacaoEventoResponse,
} from "../../../../../models/apresentacao_evento.model";
import {
	EventoFilter,
	EventoResponse,
} from "../../../../../models/evento.model";
import { SearchBoxMultiComponent } from "../../../../../components/search-box-multi/search-box-multi.component";
import { UsuarioFiltro } from "../../../../../models/usuario.model";
import { AlertService } from "../../../../../services/Alert.service";
import { AlunoFilter } from "../../../../../models/aluno.model";
import { SearchBoxSingleComponent } from "../../../../../components/search-box-single/search-box-single.component";

@Component({
	selector: "app-eventos-admin-page",
	imports: [
		SimpleTableComponent,
		BotaoComponent,
		CommonModule,
		SearchBoxMultiComponent,
		DatePipe,
		FormsModule,
		ModalComponent,
		SearchBoxSingleComponent
	],
	templateUrl: "./apresentacoes-admin-page.component.html",
	styleUrl: "./apresentacoes-admin-page.component.css",
})
export class ApresentacoesAdminPageComponent implements OnInit {
	@ViewChild("filterForm") filterForm!: NgForm;
	@ViewChild(SimpleTableComponent) tabela!: SimpleTableComponent;

	private adminService = inject(AdminService);
	private alertService = inject(AlertService);

	paginaAtual: number = 0;
	itensPage: number = 10;
	orderByValue!: string;
	orderValue!: string;

	currentApresentacaoEditar: ApresentacaoEvento | undefined = undefined;

	excluirApresentacaoId: number | undefined = undefined;

	isModalCriarOpen: boolean = false;
	isModalEditarOpen: boolean = false;
	isModalExcluirOpen: boolean = false;

	colunas = [
		{ chave: "nome", titulo: "Apresentação" },
		{
			chave: "horaInicio",
			titulo: "Horário de Início",
		},
		{
			chave: "horaFim",
			titulo: "Horário Final",
		},
		{ chave: "eventoNome", titulo: "Evento", order: false },
	];
	acoes = [
		{
			icon: "edit",
			title: "Editar",
			cor: "black_blue",
			callback: (item: ApresentacaoEvento) =>
				this.onToggleEditarModal(item),
		},
		{
			icon: "trash",
			title: "Excluir",
			cor: "dark",
			callback: (item: ApresentacaoEvento) =>
				this.onOpenExcluirModal(item.id),
		},
	];

	apresentacoes: ApresentacaoEventoResponse | undefined = undefined;
	eventos: any = [];

	alunosLs: any = [];
	alunosEditar: any = [];

	ngOnInit(): void {
		this.onFiltrar(); // esse método é o equivalente ao fetchApresentacoes
	}

	limparFiltros(form: NgForm) {
		form.reset();
		this.onFiltrar();
	}

	hasFormValues(form: NgForm): boolean {
		if (form) {
			return Object.keys(form.value).some((k) => !!form.value[k]);
		}
		return false;
	}

	buscarEventos(termo: any){
		this.adminService
			.fetchEventos({
				nome: termo,
				local: "",
				data: null,
				alunos: null,
				tamanho: 0,
				pagina: 0,
			} as EventoFilter)
			.subscribe({
				next: (ev: EventoResponse) => {
					this.eventos = ev.conteudo;
				},
			});
	}

	buscarAlunos(termo: any) {
		const filtro: AlunoFilter = {
			nome: termo,
			email: "",
			cpf: "",
			status: 1,
		};

		this.adminService.filterAlunos(filtro).subscribe({
			next: (response) => {
				this.alunosLs = response.conteudo;
			},
		});
	}

	// necessário para a tabela
	onPaginacaoChange(event: { paginaSelecionada: number; itensPage: number }) {
		this.tabela.isLoad(true);
		this.paginaAtual = --event.paginaSelecionada;
		this.itensPage = event.itensPage;
		this.onFiltrar();
	}

	// habilita ou desabilita os modais
	onToggleCriarModal() {
		this.isModalCriarOpen = !this.isModalCriarOpen;
		if (!this.isModalCriarOpen) {
			this.eventos = []
			this.alunosLs = [];
		}
	}

	onToggleEditarModal(item?: ApresentacaoEvento) {
		this.isModalEditarOpen = !this.isModalEditarOpen;
		if (this.isModalEditarOpen) {
			this.currentApresentacaoEditar = item;
			this.eventos = [{id: item?.idEvento, nome: item?.eventoNome}]
			this.alunosLs = this.getAlunos(item!.alunos);
			this.alunosEditar = this.getAlunosIds(item!.alunos);

			return;
		}

		this.eventos = []
		this.alunosLs = [];
	}
	onToggleExcluirModal(confirmed?: boolean | void) {
		this.isModalExcluirOpen = !this.isModalExcluirOpen;
		if (confirmed) {
			// codigo de deletar uma apresentacao pelo adminService
			this.adminService
				.deleteApresentacaoEvento(this.excluirApresentacaoId!)
				.subscribe({
					next: () => {
						this.alertService.exclusao(
							"Apresentação excluida com sucesso!",
						);
						this.onFiltrar();
					},
					error: (err: any) => {
						this.alertService.erro(
							err?.error?.mensagem || "Erro inesperado!",
						);
					},
				});
		}
		if (!this.isModalEditarOpen) {
			this.excluirApresentacaoId = undefined;
		}
	}

	// abre o modal de excluir
	onOpenExcluirModal(id: number) {
		this.onToggleExcluirModal();
		this.excluirApresentacaoId = id;
	}

	// envia os form de criação e edição
	submitCriarApresentacaoForm(form: NgForm) {
		if (form.valid) {
			// codigo para criar a apresentacao pelo adminService
			this.adminService
				.updateApresentacaoEvento({
					...form.value,
				} as ApresentacaoEvento)
				.subscribe({
					next: () => {
						this.onToggleCriarModal();
						this.alertService.sucesso(
							"Apresentação criada com sucesso!",
						);
						this.onFiltrar();
					},
				});
		}
	}

	submitEditarApresentacaoForm(form: NgForm) {
		if (form.valid) {
			// codigo para editar a apresentacao pelo adminService
			this.adminService
				.updateApresentacaoEvento({
					...this.currentApresentacaoEditar,
					...form.value,
				} as ApresentacaoEvento)
				.subscribe({
					next: () => {
						this.onToggleEditarModal();
						this.alertService.sucesso(
							"Apresentação editada com sucesso!",
						);
						this.onFiltrar();
					},
				});
		}
	}

	onFilter() {
		this.tabela.isLoad(true);
		this.paginaAtual = 0;
		this.tabela.resetPage();
		this.onFiltrar();
	}

	// como se fosse o fetchApresentacoes
	onFiltrar() {
		// verifica se tem filtro
		if (this.filterForm) {
			this.adminService
				.fetchApresentacoes({ ...this.filterForm.value, orderBy: this.orderByValue, order: this.orderValue })
				.subscribe({
					next: (apRes: ApresentacaoEventoResponse) => {
						if (apRes.total == 0) {
							this.alertService.info(
								"Nenhum registro encontrado!",
							);
						} else {
							this.apresentacoes = apRes;
						}
						this.tabela.isLoad(false);
					},
				});
			return;
		}
		// aqui nao tem filtro
		this.adminService
			.fetchApresentacoes({
				nome: null,
				alunos: null,
				idEvento: null,
				tamanho: 0,
				pagina: 0,
				orderBy: '',
				order: ''
			})
			.subscribe({
				next: (apRes: ApresentacaoEventoResponse) => {
					this.apresentacoes = apRes;
				},
			});
	}

	getAlunosIds(item: any) {
		return item.map((i: any) => {
			return i.idAluno.id;
		});
	}

	getAlunos(item: any) {
		return item.map((i: any) => {
			return i.idAluno;
		});
	}

	orderBy(event: { chave: string; direcao: "asc" | "desc" }) {
		this.tabela.isLoad(true);
		this.orderByValue = event.chave;
		this.orderValue = event.direcao;
		this.onFiltrar();
	}
}
