import { Component, inject } from "@angular/core";
import { ActivatedRoute, Router, RouterOutlet } from "@angular/router";
import { NavbarComponent } from "../../../components/navbar/navbar.component";
import { NavbarButtonComponent } from "../../../components/navbar-button/navbar-button.component";
import { IconComponent } from "../../../components/icon/icon.component";

enum PossibleRoutes {
	MAIN = "main",
	CALENDAR = "calendar",
	FINANCEIRO = "financeiro",
	EVENTOS = "eventos",
	PROFILE = "profile",
}

@Component({
	selector: "app-dashboard-admin-page",
	imports: [
		RouterOutlet,
		NavbarComponent,
		NavbarButtonComponent,
		IconComponent,
	],
	templateUrl: "./dashboard-aluno-page.component.html",
	styleUrl: "./dashboard-aluno-page.component.css",
})
export class DashboardAlunoPageComponent {
	router = inject(Router);
	currRoute = inject(ActivatedRoute);
	public possibleRoutes = PossibleRoutes;
	currentSelectedRoute: string = PossibleRoutes.MAIN;

	handleSelectRoute(gotoRoute: string) {
		this.router
			.navigate([gotoRoute], { relativeTo: this.currRoute })
			.then(() => {
				this.currentSelectedRoute = gotoRoute;
			});
	}
}
