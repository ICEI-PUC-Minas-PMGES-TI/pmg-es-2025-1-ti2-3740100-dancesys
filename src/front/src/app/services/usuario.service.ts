import { Injectable, inject, signal } from "@angular/core";
import { Usuario, UsuarioCookie, UsuarioTipos } from "../models/usuario.model";
import { HttpClient } from "@angular/common/http";
import { environment } from "../../environment/environment";
import { Router } from "@angular/router";
import { AlunoResponse, ProfessorResponse } from "./admin.service";
import { Observable, switchMap } from "rxjs";
import { AlertService } from "./Alert.service";
import { Aluno } from "../models/aluno.model";

const USER_INFO_EXPIRE_DAYS: number = 10; // em dias

type possibleUserTypes = Usuario | AlunoResponse | ProfessorResponse | null;

@Injectable({
	providedIn: "root",
})
export class UsuarioService {
	// atributos
	private _myCookie = signal<UsuarioCookie | null>(null);
	private currentUsuario = signal<possibleUserTypes>(null);
	public usuario = this.currentUsuario.asReadonly();

	// injeções
	private http = inject(HttpClient);
	private router = inject(Router);
	private alertService = inject(AlertService);

	// controller
	private usuarioController: String = "usuario";

	constructor() {
		// verificar se existe algum usuário no cookie
		const cookie = localStorage.getItem("user_cookie");
		if (cookie) {
			// coloca o usuário no signal
			const userCookie: UsuarioCookie = JSON.parse(cookie);
			this._myCookie.set(userCookie);
			this.http
				.post<possibleUserTypes>(
					`${environment.API_URL}${this.usuarioController}/validar`,
					{ ...userCookie },
				)
				.subscribe({
					next: (response) => {
						this.currentUsuario.set(response);
						this.redirecionarUsuario();
					},
				});
		}
	}

	private redirecionarUsuario() {
		// redireciona baseado no tipo do usuário
		if (this.getLoggedInUserType() === null) {
			this.router.navigate(["login"]);
			return;
		}
		if (this.getLoggedInUserType() === UsuarioTipos.ADMIN) {
			// logado como admin
			this.router.navigate(["admin"]);
		} else if (this.getLoggedInUserType() === UsuarioTipos.FUNCIONARIO) {
			this.router.navigate(["professor"]);
		} else if (this.getLoggedInUserType() === UsuarioTipos.ALUNO) {
			this.router.navigate(["aluno"]);
		}
	}

	public login(email: string, password: string) {
		const url = `${environment.API_URL}${this.usuarioController}/auth`;
		this.http
			.post<UsuarioCookie>(url, { email, senha: password })
			.pipe(
				switchMap((cookie) => {
					localStorage.clear();
					localStorage.setItem("user_cookie", JSON.stringify(cookie));
					this._myCookie.set({ ...cookie });
					return this.http.post<possibleUserTypes>(
						`${environment.API_URL}${this.usuarioController}/validar`,
						{ ...cookie },
					);
				}),
			)
			.subscribe({
				next: (response: possibleUserTypes) => {
					this.currentUsuario.set(response);
					this.redirecionarUsuario();
				},
				error: (err: any) => {
					this.alertService.erro(err.error.mensagem);
					console.log(err);
				},
			});
	}

	public getLoggedInUserType() {
		return this._myCookie()?.tipo ? this._myCookie()?.tipo : null;
	}

	public mudarImagem(foto: string, nomeArquivo: string) {
		const usuario =
			this.getLoggedInUserType() === UsuarioTipos.ADMIN
				? this.usuario()
				: (this.usuario() as AlunoResponse | ProfessorResponse)
						.idUsuario;
		this.http
			.post<Usuario>(
				`${environment.API_URL}${this.usuarioController}/foto`,
				{
					...usuario,
					base64: foto,
					nomeArquivo,
				},
			)
			.subscribe({
				next: (response: Usuario) => {
					this.currentUsuario.update((valor: possibleUserTypes) => {
						if (this.getLoggedInUserType() === UsuarioTipos.ADMIN) {
							return response;
						}
						return {
							...(valor as AlunoResponse | ProfessorResponse),
							idUsuario: response,
						} as possibleUserTypes;
					});
				},
				error: (err: any) => {
					this.alertService.erro(err.error.mensagem);
					console.log(err);
				},
			});
	}

	public redefinirSenha(senha: string) {
		const usuario =
			this.getLoggedInUserType() === UsuarioTipos.ADMIN
				? this.usuario()
				: (this.usuario() as AlunoResponse | ProfessorResponse)
						.idUsuario;
		this.http
			.post(`${environment.API_URL}${this.usuarioController}/senha`, {
				...usuario,
				senha,
			})
			.subscribe();
	}

	public deslogar() {
		localStorage.clear();
		this._myCookie.set(null);
		this.currentUsuario.set(null);
		this.redirecionarUsuario();
	}
}
