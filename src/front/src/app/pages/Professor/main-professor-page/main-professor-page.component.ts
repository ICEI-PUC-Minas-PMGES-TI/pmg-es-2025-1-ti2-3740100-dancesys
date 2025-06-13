import { Component, inject } from "@angular/core";
import { BotaoComponent } from "../../../components/botao/botao.component";
import { SimpleCardComponent } from "../../../components/simple-card/simple-card.component";
import { UsuarioService } from "../../../services/usuario.service";
import { IconComponent } from "../../../components/icon/icon.component";

@Component({
	selector: "app-main-professor-page",
    standalone: true,
	imports: [BotaoComponent, SimpleCardComponent,
			IconComponent],
	templateUrl: "./main-professor-page.component.html",
	styleUrl: "./main-professor-page.component.css",
})
export class MainProfessorPageComponent {}