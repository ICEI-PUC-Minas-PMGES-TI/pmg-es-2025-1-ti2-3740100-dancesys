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
import { ModalidadeAlunoNivel } from "../../../../models/modalidade.model";

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
	users: Usuario[] = [];

	isModalOpen: boolean = false;
	modalidadesAlunoArr: ModalidadeAlunoNivel[] = [];

	ngOnInit(): void {
		this.reloadUsers();
	}

	reloadUsers() {
		this.adminService.fetchUsuarios().subscribe({
			next: (response) => {
				this.users = response;
			},
			error: (err) => {
				console.log(err);
			},
		});
	}

	openAddAlunoModal() {
		this.isModalOpen = true;
	}

	closeAddAlunoModal() {
		this.isModalOpen = false;
		this.modalidadesAlunoArr = [];
	}

	get usersTabela() {
		return this.users.map((usuario) => {
			const dataNascimento = new Date(usuario.dataNascimento);
			dataNascimento.setDate(dataNascimento.getDate() + 1);
			return {
				nome: usuario.nome,
				email: usuario.email,
				telefone: usuario.numero,
				tipo: usuario.tipo,
				status: usuario.status,
				dataNasc: dataNascimento.toLocaleDateString("pt-BR"),
			};
		});
	}

	addModalidadeAluno(
		modalidadeInput: HTMLSelectElement,
		nivelInput: HTMLSelectElement,
	) {
		this.modalidadesAlunoArr.push({
			idModalidade: modalidadeInput.value as unknown as number,
			nivel: nivelInput.value as unknown as 1 | 2 | 3,
		});
	}

	deleteModalidadeAluno(index: number) {
		this.modalidadesAlunoArr.splice(index, 1);
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
}
