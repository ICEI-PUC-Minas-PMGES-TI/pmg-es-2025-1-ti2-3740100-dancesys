import { Component, inject, OnInit } from "@angular/core";
import { BotaoComponent } from "../../../../components/botao/botao.component";
import { AdminService } from "../../../../services/admin.service";
import {
	Usuario,
	UsuarioFiltro,
	UsuarioTipos,
} from "../../../../models/usuario.model";
import { ModalComponent } from "../../../../components/modal/modal.component";
import { FormsModule, NgForm } from "@angular/forms";
import { TipoUsuarioPipe } from "../../../../pipes/tipo-usuario.pipe";
import { StatusUsuarioPipe } from "../../../../pipes/status-usuario.pipe";
import {
	Modalidade,
	ModalidadeAlunoNivel,
} from "../../../../models/modalidade.model";
import { ModalidadesService } from "../../../../services/modalidades.service";
import { Aluno } from "../../../../models/aluno.model";

export type FormAlunoValue = {
	nome: string;
	endereco: string;
	dataNascimento: Date;
	modalidades: ModalidadeAlunoNivel[];
	cpf: string;
	senha: string;
	telefone: string;
	tipoAluno: 1 | 2;
	tipo: UsuarioTipos.ALUNO;
	boolBaile: 0 | 1;
};

enum ToggleUserStatusMessages {
	ACTIVATE = "Tem certeza que deseja habilitar a conta?",
	DEACTIVATE = "Tem certeza que deseja desabilitar a conta?",
}

@Component({
	selector: "app-usuarios-admin-page",
	imports: [
		BotaoComponent,
		ModalComponent,
		FormsModule,
		TipoUsuarioPipe,
		StatusUsuarioPipe,
	],
	templateUrl: "./usuarios-admin-page.component.html",
	styleUrl: "./usuarios-admin-page.component.css",
})
export class UsuariosAdminPageComponent implements OnInit {
	adminService = inject(AdminService);
	modalidadesService = inject(ModalidadesService);
	users: Usuario[] = [];
	alunos: Aluno[] = [];
	modalidades: Modalidade[] = [];

	isModalOpen: "addAluno" | "confirmToggleAluno" | "editAluno" | false =
		false;
	isLoading: boolean = false;
	idToggleStatusUser: number = -1;
	tempEditAluno: Aluno | undefined = undefined;
	isActivatingUser: boolean = false;
	modalidadesAlunoArr: ModalidadeAlunoNivel[] = [];

	toggleUserStatusMessages = ToggleUserStatusMessages;

	ngOnInit(): void {
		this.reloadUsers();
		this.reloadModalidades();
	}

	reloadUsers() {
		this.isLoading = true;
		this.adminService.fetchAlunos().subscribe({
			next: (response) => {
				this.alunos = [];
				response.forEach((alResponse) => {
					const aluno: Aluno = {
						id: alResponse.id,
						creditos: alResponse.creditos,
						boolBaile: alResponse.boolBaile,
						tipo: alResponse.tipo,
						usuario: {
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
					this.isLoading = false;
				});
			},
			error: (err) => {
				console.log(err);
			},
		});
	}

	reloadModalidades() {
		this.isLoading = true;
		this.modalidadesService.fetchModalidades().subscribe({
			next: (response) => {
				this.modalidades = response;
				this.isLoading = false;
			},
			error: (err) => {
				console.log(err);
			},
		});
	}

	openAddAlunoModal() {
		this.isModalOpen = "addAluno";
	}

	closeAddAlunoModal() {
		this.isModalOpen = false;
		this.modalidadesAlunoArr = [];
	}

	openEditAlunoModal(id: number) {
		const index = this.alunos.findIndex((al) => {
			return al.usuario.id == id;
		});
		this.tempEditAluno = structuredClone(this.alunos[index]);
		this.isModalOpen = "editAluno";
	}

	closeEditAlunoModal() {
		this.isModalOpen = false;
		this.tempEditAluno = undefined;
	}

	openToggleConfirmAlunoModal() {
		this.isModalOpen = "confirmToggleAluno";
	}

	onConfirmToggleAluno(choice: boolean | void) {
		if (choice) {
			this.adminService
				.toggleUserStatus(this.idToggleStatusUser)
				.subscribe({
					next: () => {
						this.reloadUsers();
						this.idToggleStatusUser = -1;
						this.isActivatingUser = false;
					},
				});
		}
		this.isModalOpen = false;
	}

	get usersTabela() {
		// return this.users.map((usuario) => {
		// 	const dataNascimento = new Date(usuario.dataNascimento);
		// 	dataNascimento.setDate(dataNascimento.getDate() + 1);
		// 	return {
		// 		nome: usuario.nome,
		// 		email: usuario.email,
		// 		telefone: usuario.numero,
		// 		tipo: usuario.tipo,
		// 		status: usuario.status,
		// 		dataNasc: dataNascimento.toLocaleDateString("pt-BR"),
		// 	};
		// });
		return this.alunos.map((aluno) => {
			const dataNascimento = new Date(aluno.usuario.dataNascimento);
			dataNascimento.setDate(dataNascimento.getDate() + 1);
			return {
				nome: aluno.usuario.nome,
				email: aluno.usuario.nome,
				telefone: aluno.usuario.numero,
				tipo: aluno.usuario.tipo,
				id: aluno.usuario.id,
				status: aluno.usuario.status,
				dataNasc: dataNascimento.toLocaleDateString("pt-BR"),
			};
		});
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
		this.closeAddAlunoModal();
		console.log(value);
		this.adminService.addUsuarioAluno(value).subscribe({
			next: () => {
				this.reloadUsers();
			},
		});
	}

	submitEditAlunoForm(form: NgForm) {
		const value: FormAlunoValue = <FormAlunoValue>(
			structuredClone(form.value)
		);
		value.tipo = UsuarioTipos.ALUNO; // forçado
		value.modalidades = [...this.tempEditAluno!.modalidades];
		this.closeEditAlunoModal();
		console.log(value);
		this.adminService.editarAluno(value).subscribe({
			next: ()=>{
				this.reloadUsers()
			}
		});
	}

	filterFormSubmit(form: NgForm) {
		console.log(form.value);
		const filtro: UsuarioFiltro = {
			nome: form.value.nomeFilter,
			email: form.value.emailFilter,
			cpf: form.value.cpfFilter,
			tipo: form.value.tipoFilter,
			status: form.value.statusFilter,
		};
		this.adminService.filterUsuarios(filtro).subscribe({
			next: () => {
				console.log("Filtrouu");
			},
		});
	}

	toggleUserStatus(id: number, status: boolean) {
		this.idToggleStatusUser = id;
		this.isActivatingUser = !status;
		this.openToggleConfirmAlunoModal();
	}
}
