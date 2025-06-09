import { CommonModule } from "@angular/common";
import { Component, inject, ViewChild } from "@angular/core";
import {
	FormBuilder,
	FormGroup,
	FormsModule,
	NgForm,
	ReactiveFormsModule,
} from "@angular/forms";
import { BotaoComponent } from "../../../../../components/botao/botao.component";
import { ModalComponent } from "../../../../../components/modal/modal.component";
import { MultiSelectInputComponent } from "../../../../../components/multi-select-input/multi-select-input.component";
import { SearchBoxSingleComponent } from "../../../../../components/search-box-single/search-box-single.component";
import { SimpleTableComponent } from "../../../../../components/simple-table/simple-table.component";
import { AulaExtraFilter } from "../../../../../models/AulaExtra.model";
import { ProfessorFiltro } from "../../../../../models/professor.model";
import { UsuarioFiltro } from "../../../../../models/usuario.model";
import { AdminService } from "../../../../../services/admin.service";
import { AlertService } from "../../../../../services/Alert.service";
import { AulaService } from "../../../../../services/aula.service";
import { SalaService } from "../../../../../services/sala.service";
import { Mensagem } from "../../../../../models/Mensagem.model";
import { AlunoFilter } from "../../../../../models/aluno.model";

@Component({
	selector: "app-aulas-extras-admin-page",
	imports: [
		ModalComponent,
		FormsModule,
		SimpleTableComponent,
		MultiSelectInputComponent,
		SearchBoxSingleComponent,
		ReactiveFormsModule,
		BotaoComponent,
		CommonModule,
	],
	templateUrl: "./aulas-extras-admin-page.component.html",
	styleUrl: "./aulas-extras-admin-page.component.css",
	standalone: true,
})
export class AulasExtrasAdminPageComponent {
	@ViewChild(SimpleTableComponent) tabela!: SimpleTableComponent;

	adminService = inject(AdminService);
	aulaService = inject(AulaService);
	alertService = inject(AlertService);
	salaService = inject(SalaService);

	filterForm: FormGroup;
	salaForm: FormGroup;

	professoresFilterLs: any[] = [];
	alunosFilterLs: any[] = [];
	aulaExtraObj: any = [];
	salasLs: any = [];

	paginaAtual: number = 0;
	itensPage: number = 10;
	orderByValue!: string;
	orderValue!: string;

	selectId: number = 0;

	statusMap: Record<number, string> = {
		1: "Pendente",
		2: "Aceita",
		3: "Indeferida",
		4: "Cancelada",
	};

	colunas = [
		{ chave: "idAluno.idUsuario.nome", titulo: "Aluno" },
		{ chave: "idProfessor.idUsuario.nome", titulo: "Professor" },
		{
			chave: "horarioInicio",
			titulo: "Inicio",
			formatar: (valor: Date) =>
				valor != null ? this.formartarData(valor) : "",
		},
		{
			chave: "horarioFim",
			titulo: "Fim",
			formatar: (valor: Date) =>
				valor != null ? this.formartarData(valor) : "",
		},
		{ chave: "motivo", titulo: "Motivo", width: "20%" },
		{
			chave: "situacao",
			titulo: "Situacao",
			formatar: (valor: number) => this.statusMap[valor] ?? String(valor),
		},
	];

	acoes = [
		{
			icon: "check",
			title: "Aceitar",
			cor: "green",
			callback: (item: any) => this.aceitar(item.id),
		},
		{
			icon: "delete",
			title: "Indeferir",
			cor: "red",
			callback: (item: any) => this.indeferir(item.id),
		},
		{
			icon: "warning",
			title: "Cancelar",
			cor: "yellow",
			callback: (item: any) => this.cancelar(item.id),
		},
	];

	openModal: "aceitar" | "recusar" | "cancelar" | null = null;

	statusObj: { value: number; name: string }[] = [
		{ value: 1, name: "Pendente" },
		{ value: 2, name: "Indeferido" },
		{ value: 3, name: "Aceito" },
		{ value: 4, name: "Cancelado" },
	];

	constructor(private fb: FormBuilder) {
		this.filterForm = this.fb.group({
			idAluno: [],
			idProfessor: [],
			status: [[]],
			pagina: [this.paginaAtual],
			tamanho: [this.itensPage],
			orderBy: [this.orderByValue],
			order: [this.orderValue],
		});

		this.salaForm = this.fb.group({
			sala: [],
		});
	}

	ngOnInit() {
		this.buscar();
		this.buscarSalas();
	}

	getFilter() {
		this.filterForm.get("tamanho")?.setValue(this.itensPage);
		this.filterForm.get("pagina")?.setValue(this.paginaAtual);
		this.filterForm.get("orderBy")?.setValue(this.orderByValue);
		this.filterForm.get("order")?.setValue(this.orderValue);

		const item = this.filterForm.value;
		const aulaFilter: AulaExtraFilter = item;

		return aulaFilter;
	}

	buscarProfessor(termo: any) {
		const filtro: ProfessorFiltro = {
			nome: termo,
			email: "",
			cpf: "",
			status: 1,
		};
		this.adminService.filterProfessores(filtro).subscribe({
			next: (response) => {
				this.professoresFilterLs = response.conteudo;
			},
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

	getSalaForm() {
		const item = this.salaForm.value;
		const id = item.sala;

		return id;
	}

	buscarSalas() {
		this.salaService.fetchSalas().subscribe({
			next: (response) => {
				this.salasLs = response;
			},
		});
	}

	aceitar(id: number) {
		this.openModal = "aceitar";
		this.selectId = id;
	}

	closeAceitarModal() {
		this.openModal = null;
		this.selectId = 0;
	}

	aceitarConfirm() {
		this.aulaService
			.acitarAulaExtra(this.selectId, this.getSalaForm())
			.subscribe({
				next: (response) => {
					this.alertService.sucesso("Aula aceita com sucesso");
					this.closeAceitarModal();
					this.buscar();
				},
				error: (err) => {
					this.alertService.erro(
						err?.error?.mensagem || "Erro inesperado!",
					);
					this.closeAceitarModal();
				},
			});
	}

	onCancelarAula(form: NgForm | false) {
		if (form == false) {
			this.openModal = null;
			return;
		}
		if (form.invalid) {
			// não fecha o modal
			return;
		}
		this.openModal = null;
		const item = form.value.motivoCancelar;
		const msg: Mensagem = {
			mensagem: item,
		};

		this.aulaService.cancelarAulaextra(this.selectId, msg).subscribe({
			next: (response) => {
				this.alertService.sucesso("Aula cancelada com sucesso");
				this.closeCancelarModal();
				this.buscar();
			},
			error: (err) => {
				this.alertService.erro(
					err?.error?.mensagem || "Erro inesperado!",
				);
				this.closeCancelarModal();
			},
		});
	}

	onRecusarAula(form: NgForm | false) {
		if (form == false) {
			this.openModal = null;
			return;
		}
		if (form.invalid) {
			return;
		}
		const item = form.value.motivoRecusar;

		const msg: Mensagem = {
			mensagem: item,
		};

		this.aulaService.indeferirAulaExtra(this.selectId, msg).subscribe({
			next: (response) => {
				this.alertService.sucesso("Aula indeferida com sucesso");
				this.closeIndeferirModal();
				this.buscar();
			},
			error: (err) => {
				this.alertService.erro(
					err?.error?.mensagem || "Erro inesperado!",
				);
				this.closeIndeferirModal();
			},
		});
	}

	indeferir(id: number) {
		this.openModal = "recusar";
		this.selectId = id;
	}

	closeIndeferirModal() {
		this.openModal = null;
		this.selectId = 0;
	}

	cancelar(id: number) {
		this.openModal = "cancelar";
		this.selectId = id;
	}

	closeCancelarModal() {
		this.openModal = null;
		this.selectId = 0;
	}

	onFilter() {
		this.tabela.isLoad(true);
		this.paginaAtual = 0;
		this.tabela.resetPage();
		this.buscar();
	}

	limparFiltros() {
		this.filterForm = this.fb.group({
			idAluno: [],
			idProfessor: [],
			status: [[]],
			pagina: [this.paginaAtual],
			tamanho: [this.itensPage],
			orderBy: [this.orderByValue],
			order: [this.orderValue],
		});
	}

	buscar() {
		this.aulaService.filterAulaExtra(this.getFilter()).subscribe({
			next: (response: any) => {
				if (response.total == 0) {
					this.alertService.info("Nenhum registro encontrado");
				} else {
					this.aulaExtraObj = response;
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

	isFormSalaValido() {
		return this.salaForm.valid;
	}

	orderBy(event: { chave: string; direcao: "asc" | "desc" }) {
		this.tabela.isLoad(true);
		this.orderByValue = event.chave;
		this.orderValue = event.direcao;
		this.buscar();
	}

	formartarData(valor: Date) {
		console.log(valor);
		const str = valor.toLocaleString();
		const strarr = str.split("T");
		const strD = strarr[0].split("-");
		return `${strD[2]}/${strD[1]}/${strD[0]} - ${strarr[1]}`;
	}
}
