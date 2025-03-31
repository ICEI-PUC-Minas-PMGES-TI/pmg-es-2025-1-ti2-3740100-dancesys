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

	@Input("icon") icon!: string;
	@Input("route") route!: string;

	public get iconSrc() {
		if (this.icon == "home") {
			return "home_icon.svg";
		}
		if (this.icon == "calendar") {
			return "calendar_icon.svg";
		}
		if (this.icon == "money") {
			return "money_icon.svg";
		}
		if (this.icon == "events") {
			return "events_icon.svg";
		}
		if (this.icon == "admin") {
			return "admin_icon.svg";
		}
		if (this.icon == "account") {
			return "account_icon.svg";
		}
		return undefined;
	}

	onClick() {
		this.router.navigate([this.route], { relativeTo: this.currentRoute })
	}

}
