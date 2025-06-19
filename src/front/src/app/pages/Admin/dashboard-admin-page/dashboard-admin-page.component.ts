import { Component, effect, inject, OnInit } from "@angular/core";
import { ActivatedRoute, Router, RouterOutlet } from "@angular/router";
import { NavbarComponent } from "../../../components/navbar/navbar.component";
import { NavbarButtonComponent } from "../../../components/navbar-button/navbar-button.component";
import { IconComponent } from "../../../components/icon/icon.component";
import { UsuarioService } from "../../../services/usuario.service";
import { Usuario, UsuarioTipos } from "../../../models/usuario.model";
import {
	AlunoResponse,
	ProfessorResponse,
} from "../../../services/admin.service";

enum PossibleRoutes {
	MAIN = "main",
	CALENDAR = "calendar",
	PROFILE = "profile",
	INDICADORES = "indicadores",
}

@Component({
	selector: "app-dashboard-admin-page",
	imports: [
		RouterOutlet,
		NavbarComponent,
		NavbarButtonComponent,
		IconComponent,
	],
	templateUrl: "./dashboard-admin-page.component.html",
	styleUrl: "./dashboard-admin-page.component.css",
})
export class DashboardAdminPageComponent {
	usuarioService = inject(UsuarioService);
	router = inject(Router);
	currRoute = inject(ActivatedRoute);
	public possibleRoutes = PossibleRoutes;
	currentSelectedRoute: string = PossibleRoutes.MAIN;

	urlFoto: string | null = null;

	constructor() {
		effect(() => {
			this.urlFoto = (
				this.usuarioService.getLoggedInUserType() === UsuarioTipos.ADMIN
					? (this.usuarioService.usuario() as Usuario)
					: ((
							this.usuarioService.usuario() as
								| AlunoResponse
								| ProfessorResponse
						).idUsuario as Usuario)
			)?.urlFoto;
		});
	}

	handleSelectRoute(gotoRoute: string) {
		this.router
			.navigate([gotoRoute], { relativeTo: this.currRoute })
			.then(() => {
				this.currentSelectedRoute = gotoRoute;
			});
	}
}
