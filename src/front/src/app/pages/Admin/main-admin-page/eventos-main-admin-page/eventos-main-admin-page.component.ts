import { Component } from "@angular/core";
import {
	InternalSidebarComponent,
	InternalSidebarRoute,
} from "../../../../components/internal-sidebar/internal-sidebar.component";
import { RouterOutlet } from "@angular/router";

@Component({
	selector: "app-eventos-main-admin-page",
	imports: [InternalSidebarComponent, RouterOutlet],
	templateUrl: "./eventos-main-admin-page.component.html",
	styleUrl: "./eventos-main-admin-page.component.css",
})
export class EventosMainAdminPageComponent {
	sidebarRoutes: InternalSidebarRoute[] = [
		{ label: "Eventos", route: "eventos" },
		{ label: "Apresentações", route: "apresentacoes" },
		{ label: "Ensaios", route: "ensaios" },
		{ label: "Figurinos", route: "figurinos" },
		{ label: "Figurinos por Aluno", route: "figurinosPorAluno" },
	];
}
