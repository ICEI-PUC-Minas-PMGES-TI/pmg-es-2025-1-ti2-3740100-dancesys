import {
	ImageCropperComponent,
	ImageCroppedEvent,
	LoadedImage,
} from "ngx-image-cropper";
import { DomSanitizer, SafeUrl } from "@angular/platform-browser";
import { Component, inject, ViewChild } from "@angular/core";
import { SimpleTableComponent } from "../../../../../components/simple-table/simple-table.component";
import { BotaoComponent } from "../../../../../components/botao/botao.component";
import { CommonModule } from "@angular/common";
import { FormsModule, NgForm } from "@angular/forms";
import { Evento } from "../../../../../models/evento.model";
import { ModalComponent } from "../../../../../components/modal/modal.component";
import { AdminService } from "../../../../../services/admin.service";

@Component({
	selector: "app-eventos-admin-page",
	imports: [
		SimpleTableComponent,
		BotaoComponent,
		CommonModule,
		ImageCropperComponent,
		FormsModule,
		ModalComponent,
	],
	templateUrl: "./eventos-admin-page.component.html",
	styleUrl: "./eventos-admin-page.component.css",
})
export class EventosAdminPageComponent {
	@ViewChild("filterForm") filterForm!: NgForm;
	private adminService = inject(AdminService);

	paginaAtual: number = 0;
	itensPage: number = 10;

	isModalCriarOpen: boolean = false;
	isModalEditarOpen: boolean = false;
	isModalExcluirOpen: boolean = false;

	colunas = [
		{ chave: "nome", titulo: "Professor" },
		{ chave: "data", titulo: "Data" },
		{ chave: "horario", titulo: "Horário" },
		{ chave: "local", titulo: "Local" },
	];
	acoes = [
		{
			icon: "edit",
			title: "Editar",
			cor: "dark",
			callback: (item: any) => console.log(item),
		},
		{
			icon: "delete",
			title: "Excluir",
			cor: "dark",
			callback: (item: any) => console.log(item),
		},
	];

	eventos: Evento[] = [];

	imageChangedEvent: any = null;
	croppedImage: any = "";

	constructor() {}

	fileChangeEvent(event: Event): void {
		this.imageChangedEvent = event;
	}
	imageCropped(event: ImageCroppedEvent) {
		this.croppedImage = event!.blob;
	}
	imageLoaded(image: LoadedImage) {}
	cropperReady() {}
	loadImageFailed() {}

	onPaginacaoChange(event: { paginaSelecionada: number; itensPage: number }) {
		this.paginaAtual = --event.paginaSelecionada;
		this.itensPage = event.itensPage;
		this.onFiltrar();
	}

	onToggleCriarModal() {
		this.isModalCriarOpen = !this.isModalCriarOpen;
	}
	onToggleEditarModal() {
		this.isModalEditarOpen = !this.isModalEditarOpen;
	}
	onToggleExcluirModal() {
		this.isModalExcluirOpen = !this.isModalExcluirOpen;
	}

	submitCriarEventoForm(form: NgForm) {
		console.log(form);
		console.log(this.croppedImage);
		const imgFile = new File(
			[this.croppedImage],
			this.imageChangedEvent?.target.files[0].name,
		);
		if (form.valid) {
			console.log(form.value);
			this.adminService.uploadFileAzure(imgFile).subscribe({
				next: (imgUrl: string) => {
					console.log(imgUrl);
				},
				error: (err) => {
					console.log(err);
				},
			});
			this.onToggleCriarModal();
		}
	}

	onFiltrar() {}
}
