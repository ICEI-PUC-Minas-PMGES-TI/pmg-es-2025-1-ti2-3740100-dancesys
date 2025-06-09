import { Component, inject } from "@angular/core";
import { FormsModule } from "@angular/forms";
import { Usuario } from "../../models/usuario.model";
import { UsuarioService } from "../../services/usuario.service";
import { BotaoComponent } from "../../components/botao/botao.component";
import { CurrencyPipe } from "@angular/common";

@Component({
	selector: "app-login-page",
	imports: [FormsModule, BotaoComponent],
	standalone: true,
	templateUrl: "./login-page.component.html",
	styleUrl: "./login-page.component.css",
})
export class LoginPageComponent {
	usuarioService = inject(UsuarioService);

	currentEmail: string = "";
	currentPassword: string = "";

	onSubmitLoginForm() {
		this.usuarioService.login(this.currentEmail, this.currentPassword);
	}
}
