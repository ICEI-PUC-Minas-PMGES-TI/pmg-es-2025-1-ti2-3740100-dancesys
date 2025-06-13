import { Component, inject } from '@angular/core';
import { NavbarComponent } from '../../../components/navbar/navbar.component';
import { NavbarButtonComponent } from '../../../components/navbar-button/navbar-button.component';
import { ActivatedRoute, Router, RouterOutlet } from '@angular/router';
import { IconComponent } from '../../../components/icon/icon.component';
enum PossibleRoutes {
	MAIN = "main",
	CALENDAR = "calendar",
	FINANCEIRO = "financeiro",
	EVENTOS = "eventos",
	PROFILE = "profile",
}
@Component({
	selector: 'app-dashboard-professor-page',
	standalone: true,
	imports: [
		RouterOutlet,
		NavbarComponent,
		NavbarButtonComponent,
		IconComponent,RouterOutlet
	],
	templateUrl: './dashboard-professor-page.component.html',
	styleUrl: './dashboard-professor-page.component.css'
})
export class DashboardProfessorPageComponent {

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
