import { Component, inject } from "@angular/core";
import { BotaoComponent } from "../../../components/botao/botao.component";
import { SimpleCardComponent } from "../../../components/simple-card/simple-card.component";
import { UsuarioService } from "../../../services/usuario.service";

@Component({
	selector: "app-main-aluno-page",
	imports: [BotaoComponent, SimpleCardComponent],
	templateUrl: "./main-aluno-page.component.html",
	styleUrl: "./main-aluno-page.component.css",
})
export class MainAlunoPageComponent {}
