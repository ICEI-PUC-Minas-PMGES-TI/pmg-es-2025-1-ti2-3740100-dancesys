import { Component, inject, ViewChild } from "@angular/core";
import { NgxMaskPipe, NgxMaskDirective } from "ngx-mask";
import { ModalComponent } from "../../../../../components/modal/modal.component";
import { BotaoComponent } from "../../../../../components/botao/botao.component";
import { SimpleTableComponent } from "../../../../../components/simple-table/simple-table.component";
import {
	AdminService,
	AlunoResponse,
} from "../../../../../services/admin.service";
import { ModalidadesService } from "../../../../../services/modalidades.service";
import { FormsModule, NgForm } from "@angular/forms";
import {
	Aluno,
	AlunoFilter,
	TipoAluno,
} from "../../../../../models/aluno.model";
import {
	Modalidade,
	ModalidadeAlunoNivel,
} from "../../../../../models/modalidade.model";
import {
	UsuarioFiltro,
	UsuarioTipos,
} from "../../../../../models/usuario.model";
import {
	formatarData,
	formatarTelefone,
} from "../../../../../utils/formatters";

export type FormAlunoValue = {
	nome: string;
	endereco: string;
	email: string;
	dataNascimento: Date;
	modalidades: ModalidadeAlunoNivel[];
	cpf: string;
	senha: string;
	telefone: string;
	tipoAluno: 1 | 2;
	id: number;
	tipo: UsuarioTipos.ALUNO;
	boolBaile: 0 | 1;
};

enum ToggleUserStatusMessages {
	ACTIVATE = "Tem certeza que deseja habilitar a conta?",
	DEACTIVATE = "Tem certeza que deseja desabilitar a conta?",
}

@Component({
	selector: "app-aluno-tabela-admin-page",
	imports: [
		ModalComponent,
		BotaoComponent,
		SimpleTableComponent,
		NgxMaskDirective,
		NgxMaskPipe,
		FormsModule,
	],
	templateUrl: "./aluno-tabela-admin-page.component.html",
	styleUrl: "./aluno-tabela-admin-page.component.css",
})
export class AlunoTabelaAdminPageComponent {
	adminService = inject(AdminService);
	modalidadesService = inject(ModalidadesService);

	@ViewChild("filterForm") filterForm!: NgForm;

	alunos: Aluno[] = [];
	modalidades: Modalidade[] = [];

	isModalOpen:
		| "addAluno"
		| "addProfessor"
		| "confirmToggleAluno"
		| "editAluno"
		| "editProf"
		| false = false;
	isLoading: boolean = false;
	idToggleStatusUser: number = -1;
	tempEditAluno: Aluno | undefined = undefined;
	isActivatingUser: boolean = false;
	modalidadesAlunoArr: ModalidadeAlunoNivel[] = [];
	modalidadesProfArr: number[] = [];

	toggleUserStatusMessages = ToggleUserStatusMessages;

	@ViewChild(SimpleTableComponent) tabela!: SimpleTableComponent;
	paginaAtual: number = 0;
	itensPage: number = 10;
	totalItens: number = 0;
	orderByValue!: string;
	orderValue!: string;
	colunas = [
		{ chave: "idUsuario.nome", titulo: "Aluno" },
		{ chave: "idUsuario.email", titulo: "Email" },
		{
			chave: "idUsuario.numero",
			titulo: "Telefone",
			formatar: (valor: number) => formatarTelefone(valor),
		},
		{
			chave: "idUsuario.dataNascimento",
			titulo: "Data de Nascimento",
			formatar: (valor: string) => formatarData(valor),
		},
		{
			chave: "tipo",
			titulo: "Tipo",
			formatar: (valor: TipoAluno) =>
				valor == TipoAluno.FIXO ? "Fixo" : "Flexível",
		},
		{
			chave: "idUsuario.status",
			titulo: "Status",
			formatar: (valor: number) => (valor == 0 ? "Desativado" : "Ativo"),
		},
	];

	acoes = [
		{
			icon: "edit",
			title: "Editar",
			cor: "dark",
			callback: (item: any) => this.openEditModal(item.id),
		},
		{
			title: "Status",
			text: "Mudar Status",
			cor: "dark",
			callback: (item: any) =>
				this.toggleUserStatus(item.idUsuario.id, item.idUsuario.status),
		},
	];

	ngOnInit(): void {
		this.reloadUsers();
		this.reloadModalidades();
	}

	reloadUsers() {
		this.isLoading = true;
		this.adminService
			.fetchAlunos({
				tamanho: this.itensPage,
				pagina: this.paginaAtual,
				orderBy: this.orderByValue,
				order: this.orderValue,
			})
			.subscribe({
				next: (response) => {
					this.handleAlunoResponse(response);
				},
				error: (err) => {},
			});
	}

	private handleAlunoResponse(response: {
		conteudo: AlunoResponse[];
		total: number;
	}) {
		this.alunos = [];
		response.conteudo.forEach((alResponse) => {
			const aluno: Aluno = {
				id: alResponse.id,
				creditos: alResponse.creditos,
				boolBaile: alResponse.boolBaile,
				tipo: alResponse.tipo,
				idUsuario: {
					...alResponse.idUsuario,
				},
				modalidades: alResponse.modalidades.map((obj) => {
					const mod: ModalidadeAlunoNivel = {
						idModalidade: obj.idModalidade.id,
						nivel: obj.nivel,
					};
					return mod;
				}),
			};
			this.alunos = [...this.alunos, aluno];
		});
		this.totalItens = response.total;
		this.tabela.isLoad(false);
		this.isLoading = false;
	}

	reloadModalidades() {
		this.isLoading = true;
		this.modalidadesService.fetchModalidades().subscribe({
			next: (response) => {
				this.modalidades = response;
				this.isLoading = false;
			},
			error: (err) => {},
		});
	}

	openAddAlunoModal() {
		this.isModalOpen = "addAluno";
	}

	closeAddModal() {
		this.isModalOpen = false;
		this.modalidadesAlunoArr = [];
	}

	openEditModal(id: number) {
		const index = this.alunos.findIndex((al) => {
			return al.id == id;
		}); //     
		this.tempEditAluno = structuredClone(this.alunos[index]);
		this.isModalOpen = "editAluno";
	}

	closeEditModal() {
		this.isModalOpen = false;
		this.tempEditAluno = undefined;
	}

	openToggleConfirmAlunoModal() {
		this.isModalOpen = "confirmToggleAluno";
	}

	onConfirmToggleAluno(choice: boolean | void) {
		console.log(this.isActivatingUser, this.idToggleStatusUser);
		if (choice) {
			this.adminService
				.toggleUserStatus(this.idToggleStatusUser)
				.subscribe({
					next: () => {
						this.filterFormSubmit();
						this.idToggleStatusUser = -1;
						this.isActivatingUser = false;
					},
				});
		}
		this.isModalOpen = false;
	}

	getModalidadeNome(id: number) {
		const index = this.modalidades.findIndex((m) => {
			return m.id == id;
		});
		return this.modalidades[index].nome;
	}

	getNivelTexto(nivel: 1 | 2 | 3) {
		if (nivel == 1) {
			return "Básico";
		} else if (nivel == 2) {
			return "Intermediário";
		}
		return "Avançado";
	}

	addModalidadeAluno(
		modalidadeInput: HTMLSelectElement,
		nivelInput: HTMLSelectElement,
	) {
		const modalidade: ModalidadeAlunoNivel = {
			idModalidade: modalidadeInput.value as unknown as number,
			nivel: nivelInput.value as unknown as 1 | 2 | 3,
		};
		if (!this.tempEditAluno) {
			const found = this.modalidadesAlunoArr.findIndex((mod) => {
				return mod.idModalidade == modalidade.idModalidade;
			});
			if (found > -1) {
				this.modalidadesAlunoArr[found].nivel = modalidade.nivel;
				return;
			}
			this.modalidadesAlunoArr.push(modalidade);
			return;
		}
		const found = this.tempEditAluno.modalidades.findIndex((mod) => {
			return mod.idModalidade == modalidade.idModalidade;
		});
		if (found > -1) {
			this.tempEditAluno.modalidades[found].nivel = modalidade.nivel;
			return;
		}
		this.tempEditAluno.modalidades.push(modalidade);
	}

	deleteModalidadeAluno(index: number) {
		if (!this.tempEditAluno) {
			this.modalidadesAlunoArr.splice(index, 1);
			return;
		}
		this.tempEditAluno?.modalidades.splice(index, 1);
	}

	submitAddAlunoForm(form: NgForm) {
		const value: FormAlunoValue = <FormAlunoValue>(
			structuredClone(form.value)
		);
		value.tipo = UsuarioTipos.ALUNO; // forçado
		value.modalidades = [...this.modalidadesAlunoArr];
		this.closeAddModal();
		this.adminService.addUsuarioAluno(value).subscribe({
			next: () => {
				this.filterFormSubmit();
			},
		});
	}

	submitEditAlunoForm(form: NgForm) {
		const value: Aluno = {
			...this.tempEditAluno!,
			...(<FormAlunoValue>form.value),
			tipo: (form.value as FormAlunoValue).tipoAluno,
			boolBaile: !!(form.value as FormAlunoValue).boolBaile,
		};
		this.closeEditModal();
		this.adminService.editarAluno(value).subscribe({
			next: () => {
				this.filterFormSubmit();
			},
		});
	}

	filterFormSubmit() {
		let filtro: AlunoFilter = {
			nome: this.filterForm.value.nomeFilter || "",
			email: this.filterForm.value.emailFilter || "",
			cpf: this.filterForm.value.cpfFilter || "",
			status: this.filterForm.value.statusFilter || "",
			tamanho: this.itensPage,
			pagina: this.paginaAtual,
			orderBy: this.orderByValue,
			order: this.orderValue,
			tipo: this.filterForm.value.tipoFilter || "",
		};
		this.adminService.filterAlunos(filtro).subscribe({
			next: (response) => {
				this.handleAlunoResponse(response);
			},
		});
	}

	resetAllFilters() {
		let filtro: AlunoFilter = {
			tamanho: this.itensPage,
			pagina: this.paginaAtual,
			orderBy: this.orderByValue,
			order: this.orderValue,
		};
		this.adminService.filterAlunos(filtro).subscribe({
			next: (response) => {
				this.handleAlunoResponse(response);
			},
		});
	}

	limparFiltros() {
		this.filterForm.reset();
		this.resetAllFilters();
	}

	get hasFormValues(): boolean {
		if (this.filterForm) {
			return Object.keys(this.filterForm.value).some(
				(k) => !!this.filterForm.value[k],
			);
		}
		return false;
	}

	toggleUserStatus(id: number, status: boolean) {
		console.log(id, status);
		this.idToggleStatusUser = id;
		this.isActivatingUser = !status;
		this.openToggleConfirmAlunoModal();
	}

	onPaginacaoChange(event: { paginaSelecionada: number; itensPage: number }) {
		this.tabela.isLoad(true);
		this.paginaAtual = --event.paginaSelecionada;
		this.itensPage = event.itensPage;
		this.filterFormSubmit();
	}

	orderBy(event: { chave: string; direcao: "asc" | "desc" }) {
		this.tabela.isLoad(true);
		this.orderByValue = event.chave;
		this.orderValue = event.direcao;
		this.filterFormSubmit();
	}
}
