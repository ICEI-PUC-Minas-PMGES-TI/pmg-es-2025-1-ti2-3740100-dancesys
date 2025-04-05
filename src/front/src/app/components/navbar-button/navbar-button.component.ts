import { Component, inject, Input } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
	selector: 'app-navbar-button',
	imports: [],
	templateUrl: './navbar-button.component.html',
	styleUrl: './navbar-button.component.css'
})
export class NavbarButtonComponent {

	router = inject(Router)
	currentRoute = inject(ActivatedRoute)

	@Input({
		alias: "route",
		required: true
	}) route!: string;


	onClick() {
		this.router.navigate([this.route], { relativeTo: this.currentRoute })
	}

}
