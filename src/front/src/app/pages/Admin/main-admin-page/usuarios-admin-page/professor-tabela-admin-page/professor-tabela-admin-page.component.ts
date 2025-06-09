import { Component, inject, OnInit, ViewChild } from "@angular/core";
import { NgxMaskPipe, NgxMaskDirective } from "ngx-mask";
import { ModalComponent } from "../../../../../components/modal/modal.component";
import { BotaoComponent } from "../../../../../components/botao/botao.component";
import { FormsModule, NgForm } from "@angular/forms";
import {
	AdminService,
	ProfessorResponse,
} from "../../../../../services/admin.service";
import { ModalidadesService } from "../../../../../services/modalidades.service";
import {
	Professor,
	ProfessorFiltro,
} from "../../../../../models/professor.model";
import { Modalidade } from "../../../../../models/modalidade.model";
import { UsuarioTipos } from "../../../../../models/usuario.model";

export type FormProfessorValue = {
	nome: string;
	endereco: string;
	valorHoraExtra: number;
	dataNascimento: Date;
	modalidades: number[];
	cpf: string;
	senha: string;
	numero: string;
	email: string;
	id: number;
	informacoesProfissionais: string;
	idUsuario: number;
	tipo: UsuarioTipos.FUNCIONARIO;
};

enum ToggleUserStatusMessages {
	ACTIVATE = "Tem certeza que deseja habilitar a conta?",
	DEACTIVATE = "Tem certeza que deseja desabilitar a conta?",
}

@Component({
	selector: "app-professor-tabela-admin-page",
	imports: [
		ModalComponent,
		BotaoComponent,
		FormsModule,
		NgxMaskDirective,
		NgxMaskPipe,
	],
	templateUrl: "./professor-tabela-admin-page.component.html",
	styleUrl: "./professor-tabela-admin-page.component.css",
})
export class ProfessorTabelaAdminPageComponent implements OnInit {
	adminService = inject(AdminService);
	modalidadesService = inject(ModalidadesService);

	@ViewChild("filterForm") filterForm!: NgForm;

	professores: Professor[] = [];
	modalidades: Modalidade[] = [];

	isModalOpen: "addProfessor" | "confirmToggleAluno" | "editProf" | false =
		false;
	isLoading: boolean = false;
	idToggleStatusUser: number = -1;
	tempEditProf: Professor | undefined = undefined;
	isActivatingUser: boolean = false;
	modalidadesProfArr: number[] = [];

	toggleUserStatusMessages = ToggleUserStatusMessages;

	ngOnInit(): void {
		this.reloadUsers();
		this.reloadModalidades();
	}

	openAddProfessorModal() {
		this.isModalOpen = "addProfessor";
	}

	reloadUsers() {
		this.isLoading = true;
		this.adminService.fetchProfessores().subscribe({
			next: (response) => {
				this.handleProfessorResponse(response);
				this.isLoading = false;
			},
			error: (err) => {},
		});
	}

	private handleProfessorResponse(response: {
		conteudo: ProfessorResponse[];
		total: number;
	}) {
		this.professores = [];
		response.conteudo.forEach((profResponse) => {
			const professor: Professor = {
				id: profResponse.id,
				valorHoraExtra: profResponse.valorHoraExtra,
				informacoesProfissionais: profResponse.informacoesProfissionais,
				usuario: {
					...profResponse.idUsuario,
				},
				modalidades: profResponse.modalidades.map((obj) => {
					return obj.id.idModalidade;
				}),
			};
			this.professores = [...this.professores, professor];
		});
		console.log(this.professores);
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

	closeAddModal() {
		this.isModalOpen = false;
		this.modalidadesProfArr = [];
	}

	openEditModal(id: number) {
		const index = this.professores.findIndex((prof) => {
			return prof.id == id;
		});
		this.tempEditProf = structuredClone(this.professores[index]);
		this.isModalOpen = "editProf";
	}

	closeEditModal() {
		this.isModalOpen = false;
		this.tempEditProf = undefined;
	}

	submitAddProfessorForm(form: NgForm) {
		const value: FormProfessorValue = <FormProfessorValue>(
			structuredClone(form.value)
		);
		value.tipo = UsuarioTipos.FUNCIONARIO; // forçado
		value.modalidades = [...this.modalidadesProfArr];
		this.closeAddModal();
		this.adminService.addUsuarioProfessor(value).subscribe({
			next: () => {
				this.reloadUsers();
			},
		});
	}

	submitEditProfessorForm(form: NgForm) {
		const value: Professor = {
			...this.tempEditProf!,
		};
		this.closeEditModal();
		if (form.valid) {
			this.adminService.editarProfessor(value).subscribe({
				next: () => {
					this.reloadUsers();
				},
			});
		}
	}

	resetAllFilters() {
		let filtro: ProfessorFiltro = {
			nome: "",
			email: "",
			cpf: "",
		};
		this.adminService
			.filterProfessores(filtro as ProfessorFiltro)
			.subscribe({
				next: (response) => {
					this.handleProfessorResponse(response);
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

	addModalidadeProfessor(modalidadeInput: HTMLSelectElement) {
		const modalidade = modalidadeInput.value as unknown as number;
		if (!this.tempEditProf) {
			const found = this.modalidadesProfArr.findIndex((mod) => {
				return mod == modalidade;
			});
			if (found > -1) {
				return;
			}
			this.modalidadesProfArr.push(modalidade);
			return;
		}
		const found = this.tempEditProf.modalidades.findIndex((mod) => {
			return mod == modalidade;
		});
		if (found > -1) {
			this.tempEditProf.modalidades[found] = modalidade;
			return;
		}
		this.tempEditProf.modalidades.push(modalidade);
	}

	deleteModalidadeProfessor(index: number) {
		if (!this.tempEditProf) {
			this.modalidadesProfArr.splice(index, 1);
			return;
		}
		this.tempEditProf?.modalidades.splice(index, 1);
	}

	filterFormSubmit() {
		let filtro: ProfessorFiltro = {
			nome: this.filterForm.value.nomeFilter || "",
			email: this.filterForm.value.emailFilter || "",
			cpf: this.filterForm.value.cpfFilter || "",
			status: this.filterForm.value.statusFilter || "",
		};
		this.adminService
			.filterProfessores(filtro as ProfessorFiltro)
			.subscribe({
				next: (response) => {
					this.handleProfessorResponse(response);
				},
			});
	}

	openToggleConfirmAlunoModal() {
		this.isModalOpen = "confirmToggleAluno";
	}

	toggleUserStatus(id: number, status: boolean) {
		this.idToggleStatusUser = id;
		this.isActivatingUser = !status;
		this.openToggleConfirmAlunoModal();
	}

	getModalidadeNome(id: number) {
		const index = this.modalidades.findIndex((m) => {
			return m.id == id;
		});
		return this.modalidades[index].nome;
	}

	onConfirmToggleAluno(choice: boolean | void) {
		if (choice) {
			this.adminService
				.toggleUserStatus(this.idToggleStatusUser)
				.subscribe({
					next: () => {
						// this.reloadUsers();
						this.idToggleStatusUser = -1;
						this.isActivatingUser = false;
					},
				});
		}
		this.isModalOpen = false;
	}
}
