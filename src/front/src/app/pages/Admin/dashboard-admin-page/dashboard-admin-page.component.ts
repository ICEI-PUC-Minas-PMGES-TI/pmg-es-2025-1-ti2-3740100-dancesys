import { Component, inject } from '@angular/core';
import { Router, RouterOutlet } from '@angular/router';
import { NavbarComponent } from '../../../components/navbar/navbar.component';
import { NavbarButtonComponent } from '../../../components/navbar-button/navbar-button.component';
import { IconComponent } from '../../../components/icon/icon.component';

@Component({
	selector: 'app-dashboard-admin-page',
	imports: [RouterOutlet, NavbarComponent, NavbarButtonComponent, IconComponent],
	templateUrl: './dashboard-admin-page.component.html',
	styleUrl: './dashboard-admin-page.component.css'
})
export class DashboardAdminPageComponent {

	router = inject(Router);

}
