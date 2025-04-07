import { Component, inject, OnInit } from "@angular/core";
import { BotaoComponent } from "../../../../components/botao/botao.component";
import { AdminService } from "../../../../services/admin.service";
import { Usuario } from "../../../../models/usuario.model";

@Component({
	selector: "app-usuarios-admin-page",
	imports: [BotaoComponent],
	templateUrl: "./usuarios-admin-page.component.html",
	styleUrl: "./usuarios-admin-page.component.css",
})
export class UsuariosAdminPageComponent implements OnInit {
	adminService = inject(AdminService);
	users: Usuario[] = [];

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

	get usersTabela() {
		return this.users.map((usuario) => {
			const dataNascimento = new Date(usuario.dataNascimento);
			dataNascimento.setDate(dataNascimento.getDate() + 1);
			return {
				nome: usuario.nome,
				email: usuario.email,
				telefone: usuario.numero,
				tipo: Usuario.getTipoString(usuario.tipo),
				status: Usuario.getStatusString(usuario.status),
				dataNasc: dataNascimento.toLocaleDateString("pt-BR"),
			};
		});
	}
}
