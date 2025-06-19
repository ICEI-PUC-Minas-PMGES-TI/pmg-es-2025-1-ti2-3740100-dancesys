import { Component, inject, ViewChild } from "@angular/core";
import { ModalComponent } from "../../../../../components/modal/modal.component";
import { SimpleTableComponent } from "../../../../../components/simple-table/simple-table.component";
import { SearchBoxSingleComponent } from "../../../../../components/search-box-single/search-box-single.component";
import { FormBuilder, FormGroup, ReactiveFormsModule } from "@angular/forms";
import { BotaoComponent } from "../../../../../components/botao/botao.component";
import { CommonModule } from "@angular/common";
import { EventoService } from "../../../../../services/evento.service";
import { AdminService } from "../../../../../services/admin.service";
import { UsuarioFiltro } from "../../../../../models/usuario.model";
import { FigurinoFilter } from "../../../../../models/Figurino.model";
import {
	Evento,
	EventoFilter,
	EventoResponse,
} from "../../../../../models/evento.model";
import {
	ApresentacaoEvento,
	ApresentacaoEventoFilter,
	ApresentacaoEventoResponse,
} from "../../../../../models/apresentacao_evento.model";
import {
	FigurinoAluno,
	FigurinoAlunoFilter,
} from "../../../../../models/FigurinoAluno.model";
import { AlertService } from "../../../../../services/Alert.service";
import { AlunoFilter } from "../../../../../models/aluno.model";

enum ToggleModal {
	NEW = "Criar Figurino relacionado a aluno",
	EDIT = "Editar Figurino relacionado a aluno",
}

@Component({
	selector: "app-figurinos-por-aluno-admin-page",
	standalone: true,
	imports: [
		ModalComponent,
		SimpleTableComponent,
		SearchBoxSingleComponent,
		ReactiveFormsModule,
		BotaoComponent,
		CommonModule,
	],
	templateUrl: "./figurinos-por-aluno-admin-page.component.html",
	styleUrl: "./figurinos-por-aluno-admin-page.component.css",
})
export class FigurinosPorAlunoAdminPageComponent {
	eventoService = inject(EventoService);
	adminService = inject(AdminService);
	alertService = inject(AlertService);

	@ViewChild(SimpleTableComponent) tabela!: SimpleTableComponent;

	filterForm: FormGroup;
	figurinoAlunoForm: FormGroup;

	ToggleModal = ToggleModal;

	alunosFilterLs: any[] = [];
	figurinoFilterLs: any[] = [];
	eventoFilterLs: Evento[] = [];
	apresentacoesFilterLs: ApresentacaoEvento[] = [];
	figurinoObj: any = [];

	isModalOpen: boolean = false;
	isStatusModalOpen: boolean = false;
	isDeleteModalOpen: boolean = false;
	isEdit: boolean = false;
	selectId: number = 0;

	paginaAtual: number = 0;
	itensPage: number = 10;
	orderByValue!: string;
	orderValue!: string;

	tamanhosLs: string[] = ["PP", "P", "M", "G", "GG"];

	statusMap: Record<number, string> = {
		1: "No estoque",
		2: "Entregue",
		3: "Devolvido",
	};

	colunas = [
		{ chave: "idAluno.idUsuario.nome", titulo: "Aluno" },
		{ chave: "idFigurino.nome", titulo: "Figurino" },
		{ chave: "idApresentacaoEvento.nome", titulo: "Apresentação" },
		{ chave: "tamanho", titulo: "Tamanho" },
		{
			chave: "status",
			titulo: "Status",
			formatar: (valor: number) => this.statusMap[valor] ?? String(valor),
		},
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
			callback: (item: any) => this.excluir(item.id),
		},
		{
			icon: "warning",
			title: "Status",
			cor: "dark",
			callback: (item: any) => this.status(item.id),
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
			order: [this.orderValue],
		});

		this.figurinoAlunoForm = this.fb.group({
			id: [],
			idAluno: [],
			idApresentacaoEvento: [],
			idFigurino: [],
			codigo: [],
			tamanho: [],
			status: [],
		});
	}

	ngOnInit() {
		this.buscar();
	}

	limparFiltros() {
		this.filterForm = this.fb.group({
			idAluno: [],
			idEvento: [],
			idApresentacao: [],
			idFigurino: [],
			pagina: [this.paginaAtual],
			tamanho: [this.itensPage],
			orderBy: [this.orderByValue],
			order: [this.orderValue],
		});
	}

	buscarAluno(termo: any) {
		const filtro: AlunoFilter = {
			nome: termo,
			email: "",
			cpf: "",
			status: 1,
		};

		this.adminService.filterAlunos(filtro).subscribe({
			next: (response) => {
				this.alunosFilterLs = response.conteudo;
			},
		});
	}

	buscarEvento(termo: any) {
		const filtro: EventoFilter = {
			nome: termo,
			local: null,
			data: null,
			dataInicio: new Date(),
			alunos: [],
			pagina: 0,
			tamanho: 0,
			orderBy: '',
			order: ''
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
			order: null,
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
			tamanho: 0,
			pagina: 0,
			orderBy: '',
			order: ''
		};

		this.adminService.fetchApresentacoes(filtro).subscribe({
			next: (response: ApresentacaoEventoResponse) => {
				this.apresentacoesFilterLs = response.conteudo;
			},
		});
	}

	novo() {
		this.isModalOpen = true;
	}

	limparFigrunoAlunoForm() {
		this.figurinoAlunoForm = this.fb.group({
			id: [],
			idAluno: [],
			idApresentacaoEvento: [],
			idFigurino: [],
			codigo: [],
			tamanho: [],
			status: [],
		});
	}

	preencherFigrunoAlunoForm(item: any) {
		this.figurinoAlunoForm = this.fb.group({
			id: [item.id],
			idAluno: [item.idAluno.id],
			idApresentacaoEvento: [item.idApresentacaoEvento.id],
			idFigurino: [item.idFigurino.id],
			codigo: [item.codigo],
			tamanho: [item.tamanho],
			status: [item.status],
		});
	}

	closeModal() {
		this.isModalOpen = false;
		this.isEdit = false;
		this.limparFigrunoAlunoForm();
	}

	salvar() {
		this.eventoService
			.salvarFigurinoAluno(this.getFigurinoAlunoForm())
			.subscribe({
				next: (response) => {
					this.alertService.sucesso(
						"Figurino relacionado a aluno com sucesso",
					);
					this.buscar();
					this.closeModal();
				},
			});
	}

	editar(item: any) {
		this.isEdit = true;
		this.isModalOpen = true;
		this.preencherFigrunoAlunoForm(item);
	}

	status(id: number) {
		this.selectId;
		this.isStatusModalOpen = true;
	}

	onConfirmStatus(choice: boolean | void) {
		if (choice) {
			this.eventoService
				.toogleStatusFigurinoAluno(this.selectId)
				.subscribe({
					next: () => {
						this.buscar();
						this.alertService.info("Status do figurino alterado!");
						this.selectId = 0;
						this.isStatusModalOpen = false;
					},
					error: (err) => {
						this.alertService.erro(
							err?.error?.mensagem || "Erro inesperado!",
						);
						this.selectId = 0;
						this.isStatusModalOpen = false;
					},
				});
		}
	}

	onConfirmDelete(choice: boolean | void) {
		if (choice) {
			this.eventoService.excluirFigurinoAluno(this.selectId).subscribe({
				next: () => {
					this.buscar();
					this.alertService.exclusao("Figurino excluido!");
					this.selectId = 0;
					this.isDeleteModalOpen = false;
				},
				error: (err) => {
					this.alertService.erro(
						err?.error?.mensagem || "Erro inesperado!",
					);
					this.selectId = 0;
					this.isDeleteModalOpen = false;
				},
			});
		}
	}

	closeStatusModal() {
		this.isStatusModalOpen = false;
	}

	excluir(id: number) {
		this.selectId = id;
		this.isDeleteModalOpen = true;
	}

	isFormValido(): boolean {
		return this.figurinoAlunoForm.valid;
	}

	getFigurinoAlunoForm() {
		const item = this.figurinoAlunoForm.value;
		const Figurino: FigurinoAluno = item;

		return Figurino;
	}

	getfilter() {
		this.filterForm.get("tamanho")?.setValue(this.itensPage);
		this.filterForm.get("pagina")?.setValue(this.paginaAtual);
		this.filterForm.get("orderBy")?.setValue(this.orderByValue);
		this.filterForm.get("order")?.setValue(this.orderValue);

		const item = this.filterForm.value;
		const FigurinoFilter: FigurinoAlunoFilter = item;

		return FigurinoFilter;
	}

	onFilter() {
		this.tabela.isLoad(true);
		this.paginaAtual = 0;
		this.tabela.resetPage();
		this.buscar();
	}

	buscar() {
		this.eventoService.filterFigurinoAluno(this.getfilter()).subscribe({
			next: (reponse: any) => {
				if (reponse.total <= 0) {
					this.alertService.info("Nenhum registro encontrado");
				} else {
					this.figurinoObj = reponse;
				}
				this.tabela.isLoad(false);
			},
		});
	}

	onPaginacaoChange(event: { paginaSelecionada: number; itensPage: number }) {
		this.tabela.isLoad(true);
		this.paginaAtual = --event.paginaSelecionada;
		this.itensPage = event.itensPage;
		this.buscar();
	}

	orderBy(event: { chave: string; direcao: "asc" | "desc" }) {
		this.tabela.isLoad(true);
		this.orderByValue = event.chave;
		this.orderValue = event.direcao;
		this.buscar();
	}
}
