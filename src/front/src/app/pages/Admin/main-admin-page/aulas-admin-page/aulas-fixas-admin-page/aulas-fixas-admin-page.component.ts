import { Component, inject } from "@angular/core";
import { Aula, AulaFilter } from "../../../../../models/Aula.model";
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
import { Modalidade } from "../../../../../models/modalidade.model";
import { ModalidadesService } from "../../../../../services/modalidades.service";
import { BotaoComponent } from "../../../../../components/botao/botao.component";
import { CommonModule } from "@angular/common";
import { UsuarioFiltro } from "../../../../../models/usuario.model";
import { SalaService } from "../../../../../services/sala.service";
import { Sala } from "../../../../../models/Sala.model";
import { AlertService } from "../../../../../services/Alert.service";

enum ToggleModal {
	NEW = "Criar Aula",
	EDIT = "Editar Aula",
}

@Component({
	selector: "app-aulas-fixas-admin-page",
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
	templateUrl: "./aulas-fixas-admin-page.component.html",
	styleUrl: "./aulas-fixas-admin-page.component.css",
})
export class AulasFixasAdminPageComponent {
	filterForm: FormGroup;
	aulaForm: FormGroup;

	adminService = inject(AdminService);
	modalidadeService = inject(ModalidadesService);
	salaService = inject(SalaService);
	alertService = inject(AlertService);
	

	ToggleModal = ToggleModal;
	paginaAtual: number = 0;
	itensPage: number = 10;
	isModalOpen: boolean = false;
	isEdit: boolean = false;

	diaSemanaMap: Record<number, string> = {
		1: "Segunda",
		2: "Terça",
		3: "Quarta",
		4: "Quinta",
		5: "Sexta",
		6: "Sábado",
		7: "Domingo",
	};

	statusMap: Record<number, string> = {
		0: "Desativa",
		1: "Ativa",
	};

	colunas = [
		{ chave: "idProfessor.idUsuario.nome", titulo: "Professor" },
		{ chave: "horarioInicio", titulo: "Inicio" },
		{ chave: "horarioFim", titulo: "Fim" },
		{
			chave: "diaSemana",
			titulo: "Dia",
			width: "10%",
			formatar: (valor: number) =>
				this.diaSemanaMap[valor] ?? String(valor),
		},
		{ chave: "idModalidade.nome", titulo: "Modalidade" },
		{
			chave: "status",
			titulo: "Status",
			width: "10%",
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
			icon: "warning",
			title: "Status",
			cor: "dark",
			callback: (item: any) => this.excluir(item),
		},
	];

	constructor(private fb: FormBuilder) {
		this.filterForm = this.fb.group({
			dias: [this.diasFilter],
			professores: [this.professorFilter],
			modalidades: [this.modalidesFilter],
			tamanho: [this.itensPage],
			pagina: [this.paginaAtual],
		});

		this.aulaForm = this.fb.group({
			id: [],
			diaSemana: [],
			horarioInicio: [],
			horarioFim: [],
			maxAlunos: [],
			nivel: [],
			status: [],
			idSala: [],
			idModalidade: [],
			idProfessor: [],
			alunos: [[]],
		});
	}

	diasObj = [
		{ dia: 1, nome: "Segunda" },
		{ dia: 2, nome: "Terça" },
		{ dia: 3, nome: "Quarta" },
		{ dia: 4, nome: "Quinta" },
		{ dia: 5, nome: "Sexta" },
		{ dia: 6, nome: "Sabado" },
		{ dia: 7, nome: "Domingo" },
	];

	diasFilter: number[] = [];
	modalidesFilter: number[] = [];
	professorFilter: number[] = [];
	modalidadesObj: Modalidade[] = [];
	professoresObj: ProfessorResponse[] = [];
	salasObj: Sala[] = [];
	alunosFilterLs: any = [];
	aulaObj: any = [];
	selectAlunos: number[] = [];

	ngOnInit() {
		this.carregarModalidade();
		this.carregarProfessores();
		this.carregarSalas();
		this.buscar();
	}

	carregarModalidade() {
		this.modalidadeService.fetchModalidades().subscribe({
			next: (response) => {
				this.modalidadesObj = response;
			},
			error: (err: any) => {},
		});
	}

	carregarProfessores() {
		this.adminService.fetchProfessores().subscribe({
			next: (response) => {
				this.professoresObj = response;
			},
			error: (err: any) => {},
		});
	}

	carregarSalas() {
		this.salaService.fetchSalas().subscribe({
			next: (response) => {
				this.salasObj = response;
			},
		});
	}

	getFormValue() {
		const item = this.aulaForm.value;
		const aulaItem: Aula = item;
		return aulaItem;
	}

	preencherFormValue(item: any) {
		this.alunosFilterLs = this.getAlunos(item.alunos);

		this.aulaForm = this.fb.group({
			id: [item.id],
			diaSemana: [item.diaSemana],
			horarioInicio: [item.horarioInicio],
			horarioFim: [item.horarioFim],
			maxAlunos: [item.maxAlunos],
			nivel: [item.nivel],
			status: [item.status],
			idSala: [item.idSala.id],
			idModalidade: [item.idModalidade.id],
			idProfessor: [item.idProfessor.id],
			alunos: [this.getAlunosIds(item.alunos)],
		});
	}

	resertFormValue() {
		this.aulaForm = this.fb.group({
			id: [],
			diaSemana: [],
			horarioInicio: [],
			horarioFim: [],
			maxAlunos: [],
			nivel: [],
			status: [],
			idSala: [],
			idModalidade: [],
			idProfessor: [],
			alunos: [[]],
		});
	}

	getfilter() {
		this.filterForm = this.fb.group({
			dias: [this.diasFilter],
			professores: [this.professorFilter],
			modalidades: [this.modalidesFilter],
			tamanho: [this.itensPage],
			pagina: [this.paginaAtual],
		});

		return this.filterForm.value;
	}

	buscar() {
		this.adminService.filterAulas(this.getfilter()).subscribe({
			next: (response: any) => {
				if(response.total == 0){
					this.alertService.info("Nenhum registro encontrado!")
				}else{
					this.aulaObj = response;
				}
			},
			error: (err: any) => {},
		});
	}

	salvar() {
		this.adminService.addAula(this.getFormValue()).subscribe({
			next: (response) => {
				this.buscar();
				this.resertFormValue();
				this.closeModal();
				this.alertService.sucesso(this.isEdit? "Aula editada com sucesso": "Aula criada com sucesso")
			},
		});
	}

	editar(item: any) {
		this.preencherFormValue(item);
		this.openModal();
	}

	excluir(item: any) {}

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

	closeModal() {
		this.isModalOpen = false;
	}

	openModal() {
		this.isModalOpen = true;
	}

	isFormValido(): boolean {
		return this.aulaForm.valid;
	}

	buscarAluno(termo: any) {
		const filtro: UsuarioFiltro = {
			nome: termo,
			email: "",
			cpf: "",
			tipo: 1,
			status: 1,
		};
		this.adminService.filterUsuarios(filtro).subscribe({
			next: (response) => {
				this.alunosFilterLs = response;
			},
		});
	}

	onPaginacaoChange(event: { paginaSelecionada: number; itensPage: number }) {
		this.paginaAtual = --event.paginaSelecionada;
		this.itensPage = event.itensPage;
		this.buscar();
	}

	onAlunoChange(alunos: any[]) {
		this.selectAlunos = alunos;
	}

	onDiasChange(selected: number[]) {
		this.diasFilter = selected;
	}

	onModalidadeChange(selected: number[]) {
		this.modalidesFilter = selected;
	}

	onProfessoresChange(selected: number[]) {
		this.professorFilter = selected;
	}
}
