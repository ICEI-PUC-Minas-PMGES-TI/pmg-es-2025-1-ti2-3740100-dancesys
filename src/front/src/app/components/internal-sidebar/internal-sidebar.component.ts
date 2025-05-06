import { Component, Input } from "@angular/core";
import { RouterLink, RouterLinkActive } from "@angular/router";

export type InternalSidebarRoute = {
	label: string;
	route: string;
};

@Component({
	selector: "app-internal-sidebar",
	imports: [RouterLink, RouterLinkActive],
	templateUrl: "./internal-sidebar.component.html",
	styleUrl: "./internal-sidebar.component.css",
})
export class InternalSidebarComponent {
	@Input({ required: true }) routes!: InternalSidebarRoute[];
}
