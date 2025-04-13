import { Component, inject, OnInit } from "@angular/core";
import { BotaoComponent } from "../../../../components/botao/botao.component";
import { AdminService } from "../../../../services/admin.service";
import { Usuario } from "../../../../models/usuario.model";
import { ModalComponent } from "../../../../components/modal/modal.component";
import { FormsModule } from "@angular/forms";
import { TipoUsuarioPipe } from "../../../../pipes/tipo-usuario.pipe";
import { StatusUsuarioPipe } from "../../../../pipes/status-usuario.pipe";

@Component({
	selector: "app-usuarios-admin-page",
	imports: [BotaoComponent, ModalComponent, FormsModule, TipoUsuarioPipe, StatusUsuarioPipe],
	templateUrl: "./usuarios-admin-page.component.html",
	styleUrl: "./usuarios-admin-page.component.css",
})
export class UsuariosAdminPageComponent implements OnInit {
	adminService = inject(AdminService);
	users: Usuario[] = [];

	isModalOpen: boolean = false;

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
}
