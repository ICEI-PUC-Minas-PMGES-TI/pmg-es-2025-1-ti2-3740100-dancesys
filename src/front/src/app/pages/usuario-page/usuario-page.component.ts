import { AfterViewChecked, Component, inject, OnInit } from "@angular/core";
import { BotaoComponent } from "../../components/botao/botao.component";
import { UsuarioService } from "../../services/usuario.service";
import {
	ImageCroppedEvent,
	ImageCropperComponent,
	LoadedImage,
} from "ngx-image-cropper";
import { ModalComponent } from "../../components/modal/modal.component";
import { Usuario, UsuarioTipos } from "../../models/usuario.model";
import { AlunoResponse, ProfessorResponse } from "../../services/admin.service";
import { FormsModule, NgForm } from "@angular/forms";
import { AlertService } from "../../services/Alert.service";

@Component({
	selector: "app-usuario-page",
	standalone: true,
	imports: [
		BotaoComponent,
		ModalComponent,
		ImageCropperComponent,
		FormsModule,
	],
	templateUrl: "./usuario-page.component.html",
	styleUrl: "./usuario-page.component.css",
})
export class UsuarioPageComponent implements OnInit {
	usuarioService = inject(UsuarioService);
	alertService = inject(AlertService);
	exibirSenha: boolean = false;
	ImageCropped: boolean = false;
	redefinirSenhaModal: boolean = false;
	imageChangedEvent: any = null;
	croppedImage: any = "";
	nomeArquivoFoto: string = "";

	ngOnInit(): void {}

	get userdata(): Usuario {
		if (this.usuarioService.getLoggedInUserType() === UsuarioTipos.ADMIN) {
			return this.usuarioService.usuario() as Usuario;
		}
		return (
			this.usuarioService.usuario() as AlunoResponse | ProfessorResponse
		).idUsuario;
	}

	flipExibirSenha() {
		this.exibirSenha = !this.exibirSenha;
	}

	get urlFoto() {
		return this.userdata.urlFoto;
	}

	deslogar() {
		this.usuarioService.deslogar();
	}

	redefinirSenha(form?: NgForm) {
		if (form?.value.novaSenha !== form?.value.novaSenhaConfirm) {
			this.alertService.erro("As senhas não são iguais, tente novamente");
			return;
		}
		this.redefinirSenhaModal = false;
		if (!form) {
			return;
		}
		this.usuarioService.redefinirSenha(form.value.novaSenha);
	}

	abrirModalRedefinirSenha() {
		this.redefinirSenhaModal = true;
	}

	fileChangeEvent(event: any): void {
		if (event.target!.files[0].name) {
			this.ImageCropped = true;
			this.imageChangedEvent = event;
			this.nomeArquivoFoto = event.target!.files[0].name;
		}
	}
	imageCropped(event: ImageCroppedEvent) {
		this.croppedImage = event!.base64;
	}
	imageLoaded(image: LoadedImage) {}
	cropperReady() {}
	loadImageFailed() {}

	confirmar(confirm: boolean) {
		if (confirm) {
			this.usuarioService.mudarImagem(
				this.croppedImage,
				this.nomeArquivoFoto,
			);
		}
		this.ImageCropped = false;
	}
}
