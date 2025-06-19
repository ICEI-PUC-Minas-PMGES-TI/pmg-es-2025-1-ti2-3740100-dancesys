import { Component, effect, inject } from "@angular/core";
import { NavbarComponent } from "../../../components/navbar/navbar.component";
import { NavbarButtonComponent } from "../../../components/navbar-button/navbar-button.component";
import { ActivatedRoute, Router, RouterOutlet } from "@angular/router";
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
	EVENTOS = "eventos",
	PROFILE = "profile",
}
@Component({
	selector: "app-dashboard-professor-page",
	standalone: true,
	imports: [
		RouterOutlet,
		NavbarComponent,
		NavbarButtonComponent,
		IconComponent,
		RouterOutlet,
	],
	templateUrl: "./dashboard-professor-page.component.html",
	styleUrl: "./dashboard-professor-page.component.css",
})
export class DashboardProfessorPageComponent {
	usuarioService = inject(UsuarioService);
	router = inject(Router);
	currRoute = inject(ActivatedRoute);
	public possibleRoutes = PossibleRoutes;
	currentSelectedRoute: string = PossibleRoutes.MAIN;

	urlFoto: string | null = null;

	constructor() {
		effect(() => {
			console.log("rodou");
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
