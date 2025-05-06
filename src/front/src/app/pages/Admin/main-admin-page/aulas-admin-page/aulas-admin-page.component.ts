import { Component } from "@angular/core";
import {
	InternalSidebarComponent,
	InternalSidebarRoute,
} from "../../../../components/internal-sidebar/internal-sidebar.component";

@Component({
	selector: "app-aulas-admin-page",
	imports: [InternalSidebarComponent],
	templateUrl: "./aulas-admin-page.component.html",
	styleUrl: "./aulas-admin-page.component.css",
})
export class AulasAdminPageComponent {
	sidebarRoutes: InternalSidebarRoute[] = [
		{ label: "Aulas Fixas", route: "fixas" },
		{ label: "Aulas Recorrentes", route: "recorrentes" },
		{ label: "Aulas Extras", route: "extras" },
	];
}
