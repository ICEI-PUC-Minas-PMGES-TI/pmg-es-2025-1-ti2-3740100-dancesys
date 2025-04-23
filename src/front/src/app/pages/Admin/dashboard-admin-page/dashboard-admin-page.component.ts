import { Component, inject } from "@angular/core";
import { ActivatedRoute, Router, RouterOutlet } from "@angular/router";
import { NavbarComponent } from "../../../components/navbar/navbar.component";
import { NavbarButtonComponent } from "../../../components/navbar-button/navbar-button.component";
import { IconComponent } from "../../../components/icon/icon.component";

enum PossibleRoutes {
	MAIN = "main",
	CALENDAR = "calendar",
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
	templateUrl: "./dashboard-admin-page.component.html",
	styleUrl: "./dashboard-admin-page.component.css",
})
export class DashboardAdminPageComponent {
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
