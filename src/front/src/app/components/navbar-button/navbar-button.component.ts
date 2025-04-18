import { Component, EventEmitter, Input, Output } from "@angular/core";

@Component({
	selector: "app-navbar-button",
	imports: [],
	templateUrl: "./navbar-button.component.html",
	styleUrl: "./navbar-button.component.css",
})
export class NavbarButtonComponent {
	@Input({ required: true }) active!: boolean;
	@Output() selected = new EventEmitter<string>();
	@Input({
		alias: "route",
		required: true,
	})
	route!: string;

	onClick() {
		this.selected.emit(this.route);
	}
}
