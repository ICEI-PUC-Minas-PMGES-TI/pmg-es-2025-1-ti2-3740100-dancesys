import { Component, inject, } from '@angular/core';
import { FormsModule } from "@angular/forms";
import { Usuario } from '../../models/usuario.model';
import { UsuarioService } from '../../services/usuario.service';
import { BotaoComponent } from '../../components/botao/botao.component';

@Component({
	selector: 'app-login-page',
	imports: [FormsModule, BotaoComponent],
	standalone: true,
	templateUrl: './login-page.component.html',
	styleUrl: './login-page.component.css'
})
export class LoginPageComponent {

	usuarioService = inject(UsuarioService);

	currentEmail: string = "";
	currentPassword: string = "";

	onSubmitLoginForm() {
		const usuario = new Usuario();
		usuario.email = this.currentEmail;
		usuario.senha = this.currentPassword;
		this.usuarioService.login(usuario)
	}

}
