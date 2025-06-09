import { Component, inject, ViewChild } from "@angular/core";
import { ModalComponent } from "../../../../../components/modal/modal.component";
import { SimpleTableComponent } from "../../../../../components/simple-table/simple-table.component";
import { SearchBoxMultiComponent } from "../../../../../components/search-box-multi/search-box-multi.component";
import { MultiSelectInputComponent } from "../../../../../components/multi-select-input/multi-select-input.component";
import {
	Form,
	FormBuilder,
	FormGroup,
	ReactiveFormsModule,
} from "@angular/forms";
import {
	AdminService,
	ProfessorResponse,
} from "../../../../../services/admin.service";
import { BotaoComponent } from "../../../../../components/botao/botao.component";
import { CommonModule } from "@angular/common";
import { UsuarioFiltro } from "../../../../../models/usuario.model";
import { ProfessorFiltro } from "../../../../../models/professor.model";
import { AlertService } from "../../../../../services/Alert.service";
import {
	ApresentacaoEventoFilter,
	ApresentacaoEventoResponse,
} from "../../../../../models/apresentacao_evento.model";
import { EventoResponse } from "../../../../../models/evento.model";
import { Ensaio, EnsaioFilter } from "../../../../../models/Ensaio.model";
import { AlunoFilter } from "../../../../../models/aluno.model";

enum ToggleModal {
	NEW = "Criar Ensaio",
	EDIT = "Editar Ensaio",
}

@Component({
	selector: "app-ensaios-admin-page",
	standalone: true,
	imports: [
		ModalComponent,
		SimpleTableComponent,
		SearchBoxMultiComponent,
		MultiSelectInputComponent,
		ReactiveFormsModule,
		BotaoComponent,
		CommonModule,
	],
	templateUrl: "./ensaios-admin-page.component.html",
	styleUrl: "./ensaios-admin-page.component.css",
})
export class EnsaiosAdminPageComponent {
	@ViewChild(SimpleTableComponent) tabela!: SimpleTableComponent;

	adminService = inject(AdminService);
	alertService = inject(AlertService);

	filterForm: FormGroup;
	ensaioForm: FormGroup;

	ToggleModal = ToggleModal;

	paginaAtual: number = 0;
	itensPage: number = 10;

	isModalOpen: boolean = false;
	isEdit: boolean = false;
	isModalConfirm: boolean = false;

	idDelete: number = 0;

	colunas = [
		{ chave: "idProfessor.idUsuario.nome", titulo: "Professor" },
		{ chave: "idApresentacaoEvento.nome", titulo: "Apresentação" },
		{
			chave: "dataHoraInicio",
			titulo: "Inico",
			formatar: (valor: Date) =>
				valor != null ? this.formartarData(valor) : "",
		},
		{
			chave: "dataHoraFim",
			titulo: "Fim",
			formatar: (valor: Date) =>
				valor != null ? this.formartarData(valor) : "",
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
			callback: (item: any) => this.excluir(item),
		},
	];

	constructor(private fb: FormBuilder) {
		this.filterForm = this.fb.group({
			idProfessor: [],
			alunos: [[]],
			apresentacoes: [[]],
			dataInicio: [],
			dataFim: [],
			tamanho: [this.itensPage],
			pagina: [this.paginaAtual],
		});

		this.ensaioForm = this.fb.group({
			id: [],
			dataHoraInicio: [],
			dataHoraFim: [],
			idProfessor: [],
			idApresentacaoEvento: [],
			alunos: [[]],
		});
	}

	alunosFilterLs: any = [];
	apresentacoesFilterLs: any = [];
	professoresObj: ProfessorResponse[] = [];
	ensaios: any = [];

	alunosLs: any = [];
	apresentacoesLs: any = [];

	ngOnInit() {
		this.carregarProfessores();
		this.carregarApresentacoes();
		this.buscar();
	}

	getEnsaioForm() {
		const item = this.ensaioForm.value;
		const Ensaio: Ensaio = item;

		return Ensaio;
	}

	getFilterForm() {
		this.filterForm.get("tamanho")?.setValue(this.itensPage);
		this.filterForm.get("pagina")?.setValue(this.paginaAtual);

		const item = this.filterForm.value;
		const EnsaioFilter: EnsaioFilter = item;

		return EnsaioFilter;
	}

	resetEnsaioForm() {
		this.ensaioForm = this.fb.group({
			id: [],
			dataHoraInicio: [],
			dataHoraFim: [],
			idProfessor: [],
			idApresentacaoEvento: [],
			alunos: [[]],
		});
	}

	preencherEnsaioForm(item: any) {
		this.alunosLs = this.getAlunos(item.alunos);

		this.ensaioForm = this.fb.group({
			id: [item.id],
			dataHoraInicio: [item.dataHoraInicio],
			dataHoraFim: [item.dataHoraFim],
			idProfessor: [item.idProfessor.id],
			idApresentacaoEvento: [item.idApresentacaoEvento.id],
			alunos: [this.getAlunosIds(item.alunos)],
		});
	}

	salvar() {
		this.adminService.addEnsaio(this.getEnsaioForm()).subscribe({
			next: (response: any) => {
				this.buscar();
				this.closeModal();
				this.alertService.sucesso(
					this.isEdit
						? `Ensaio editado com sucesso!`
						: `Ensaio criado com sucesso!`,
				);
			},
		});
	}

	editar(item: any) {
		this.openModal();
		this.isEdit = true;
		this.preencherEnsaioForm(item);
	}

	excluir(item: any) {
		this.idDelete = item.id;
		this.isModalConfirm = true;
	}

	onConfirmDelete(choice: boolean | void) {
		if (choice) {
			this.adminService.deleteEnsaio(this.idDelete).subscribe({
				next: () => {
					this.buscar();
					this.alertService.exclusao("Ensaio excluido com sucesso");
				},
				error: (err) => {},
			});
		}
		this.idDelete = 0;
		this.isModalConfirm = false;
	}

	openModal() {
		this.isModalOpen = true;
	}

	closeModal() {
		this.isModalOpen = false;
	}

	novo() {
		this.openModal();
		this.resetEnsaioForm();
		this.isEdit = false;
	}

	onFilter() {
		this.tabela.isLoad(true);
		this.paginaAtual = 0;
		this.tabela.resetPage();
		this.buscar();
	}

	buscar() {
		this.adminService.filterEnsaio2(this.getFilterForm()).subscribe({
			next: (response: any) => {
				if (response.total == 0) {
					this.alertService.info("Nenhum registro encontrado!");
				} else {
					this.ensaios = response;
				}
				this.tabela.isLoad(false);
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
				this.alunosFilterLs = response.conteudo;
			},
		});
	}

	buscarAlunosForm(termo: any) {
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

	buscarApresentacoes(termo: any) {
		const filtro: ApresentacaoEventoFilter = {
			nome: termo,
			idEvento: null,
			alunos: null,
			tamanho: 0,
			pagina: 0,
		};

		this.adminService.fetchApresentacoes(filtro).subscribe({
			next: (reponse) => {
				this.apresentacoesFilterLs = reponse.conteudo;
			},
		});
	}

	carregarProfessores() {
		this.adminService.fetchProfessores().subscribe({
			next: (response) => {
				this.professoresObj = response.conteudo;
			},
			error: (err: any) => {},
		});
	}

	carregarApresentacoes() {
		const filtro: ApresentacaoEventoFilter = {
			nome: null,
			idEvento: null,
			alunos: null,
			tamanho: 0,
			pagina: 0,
		};

		this.adminService.fetchApresentacoes(filtro).subscribe({
			next: (reponse) => {
				this.apresentacoesLs = reponse.conteudo;
			},
		});
	}

	getAlunosIds(item: any) {
		const ids: any[] = [];
		item.forEach((i: any) => {
			ids.push(i.idAluno.id);
		});

		return ids;
	}

	getAlunos(item: any) {
		const alunos: any = [];
		item.forEach((i: any) => {
			alunos.push(i.idAluno);
		});

		return alunos;
	}

	formartarData(valor: Date) {
		const str = valor.toLocaleString();
		const strarr = str.split("T");
		const strD = strarr[0].split("-");
		return `${strD[2]}/${strD[1]}/${strD[0]} - ${strarr[1]}`;
	}

	onPaginacaoChange(event: { paginaSelecionada: number; itensPage: number }) {
		this.tabela.isLoad(true);
		this.paginaAtual = --event.paginaSelecionada;
		this.itensPage = event.itensPage;
		this.buscar();
	}
}
