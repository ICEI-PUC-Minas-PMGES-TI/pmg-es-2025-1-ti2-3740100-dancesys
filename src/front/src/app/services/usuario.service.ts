import { Injectable, inject, signal } from "@angular/core";
import { CookieService } from "ngx-cookie-service";
import { Usuario, UsuarioCookie, UsuarioTipos } from "../models/usuario.model";
import { HttpClient } from "@angular/common/http";
import { environment } from "../../environment/environment";
import { Router } from "@angular/router";
import { AlunoResponse } from "./admin.service";
import { Observable } from "rxjs";

const USER_INFO_EXPIRE_DAYS: number = 10; // em dias

@Injectable({
	providedIn: "root",
})
export class UsuarioService {
	// atributos
	private currentUsuario = signal<Usuario | null>(null);
	public usuario = this.currentUsuario.asReadonly();

	// injeções
	private http = inject(HttpClient);
	private cookieService = inject(CookieService);
	private router = inject(Router);

	// controller
	private usuarioController: String = "usuario";

	constructor() {
		// verificar se existe algum usuário no cookie
		const cookie = this.cookieService.get("user_cookie");
		if (cookie) {
			// coloca o usuário no signal
			console.log(cookie);
			const userCookie: UsuarioCookie = JSON.parse(cookie);
			const newUser: Usuario = new Usuario();
			newUser.id = userCookie.id;
			newUser.tipo = userCookie.tipo;
			newUser.status = userCookie.status;
			newUser.nome = userCookie.nome;
			newUser.urlFoto = userCookie.urlFoto;
			this.currentUsuario.set(structuredClone(newUser));
			// redirecionamento do usuario
			this.redirecionarUsuario();
		}
	}

	private redirecionarUsuario() {
		// redireciona baseado no tipo do usuário
		if (this.currentUsuario() === null) {
			this.router.navigate(["login"]);
			return;
		}
		if (this.currentUsuario()!.tipo == UsuarioTipos.ADMIN) {
			this.router.navigate(["admin"]);
		} else if (this.currentUsuario()!.tipo == UsuarioTipos.FUNCIONARIO) {
			this.router.navigate(["funcionario"]);
		} else if (this.currentUsuario()!.tipo == UsuarioTipos.ALUNO) {
			this.router.navigate(["aluno"]);
		}
	}

	public login(item: Usuario) {
		const url = `${environment.API_URL}${this.usuarioController}/auth`;
		this.http.post(url, item).subscribe({
			next: (resp) => {
				// coloca o usuario no signal
				const userResp = <Usuario>resp;
				this.currentUsuario.set(userResp);
				// gerenciamento de cookie
				const userCookie: UsuarioCookie = {
					id: userResp.id,
					tipo: userResp.tipo,
					status: userResp.status,
					nome: userResp.nome,
					urlFoto: userResp.urlFoto,
				};
				this.cookieService.set(
					"user_cookie",
					JSON.stringify(userCookie),
					USER_INFO_EXPIRE_DAYS,
				);
				// redireciona o usuário
				this.redirecionarUsuario();
			},
			error: (err) => {
				console.log(err);
			},
		});
	}

	public deslogar() {
		this.cookieService.delete("user_cookie", "/");
		this.currentUsuario.set(null);
		this.redirecionarUsuario();
	}

	public getAlunoIdByUserId(uid: number): Observable<number> {
		return this.http.get<number>(
			`${environment.API_URL}usuario/aluno/achar/${uid}`,
		);
	}
}
