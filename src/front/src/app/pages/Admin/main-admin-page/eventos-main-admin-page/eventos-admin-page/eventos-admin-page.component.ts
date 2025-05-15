import {
	ImageCropperComponent,
	ImageCroppedEvent,
	LoadedImage,
} from "ngx-image-cropper";
import { Component, inject, OnDestroy, OnInit, ViewChild } from "@angular/core";
import { SimpleTableComponent } from "../../../../../components/simple-table/simple-table.component";
import { BotaoComponent } from "../../../../../components/botao/botao.component";
import { CommonModule, DatePipe } from "@angular/common";
import { FormsModule, NgForm } from "@angular/forms";
import { Evento } from "../../../../../models/evento.model";
import { ModalComponent } from "../../../../../components/modal/modal.component";
import { AdminService } from "../../../../../services/admin.service";
import { Subscription } from "rxjs";

@Component({
	selector: "app-eventos-admin-page",
	imports: [
		SimpleTableComponent,
		BotaoComponent,
		CommonModule,
		DatePipe,
		ImageCropperComponent,
		FormsModule,
		ModalComponent,
	],
	templateUrl: "./eventos-admin-page.component.html",
	styleUrl: "./eventos-admin-page.component.css",
})
export class EventosAdminPageComponent implements OnInit, OnDestroy {
	@ViewChild("filterForm") filterForm!: NgForm;
	private adminService = inject(AdminService);

	paginaAtual: number = 0;
	itensPage: number = 10;

	currentEventoEditar: Evento | undefined = undefined;

	excluirEventoId: number | undefined = undefined;

	isModalCriarOpen: boolean = false;
	isModalEditarOpen: boolean = false;
	isModalExcluirOpen: boolean = false;

	colunas = [
		{ chave: "nome", titulo: "Evento" },
		{
			chave: "dataHoraInicio",
			titulo: "Data Inicio",
			formatar: (valor: Date) =>
				valor != null ? this.formartarData(valor) : "",
		},
		{
			chave: "dataHoraFim",
			titulo: "Data Fim",
			formatar: (valor: Date) =>
				valor != null ? this.formartarData(valor) : "",
		},
		{ chave: "local", titulo: "Local" },
	];
	acoes = [
		{
			icon: "edit",
			title: "Editar",
			cor: "dark",
			callback: (item: Evento) => this.onToggleEditarModal(item),
		},
		{
			icon: "delete",
			title: "Excluir",
			cor: "dark",
			callback: (item: Evento) => this.onOpenExcluirModal(item.id),
		},
	];

	eventos: Evento[] = [];
	eventosSub?: Subscription;

	imageChangedEvent: any = null;
	croppedImage: any = "";

	constructor() { }

	ngOnInit(): void {
		this.eventosSub = this.adminService.fetchEventos().subscribe({
			next: (ev: Evento[]) => {
				this.eventos = [...ev];
			},
		});
	}

	ngOnDestroy(): void {
		this.eventosSub?.unsubscribe();
	}

	fileChangeEvent(event: Event): void {
		this.imageChangedEvent = event;
	}
	imageCropped(event: ImageCroppedEvent) {
		this.croppedImage = event!.blob;
	}
	imageLoaded(image: LoadedImage) { }
	cropperReady() { }
	loadImageFailed() { }

	onPaginacaoChange(event: { paginaSelecionada: number; itensPage: number }) {
		this.paginaAtual = --event.paginaSelecionada;
		this.itensPage = event.itensPage;
		this.onFiltrar();
	}

	onToggleCriarModal() {
		this.isModalCriarOpen = !this.isModalCriarOpen;
	}
	onToggleEditarModal(item?: Evento) {
		this.isModalEditarOpen = !this.isModalEditarOpen;
		if (this.isModalEditarOpen) {
			this.currentEventoEditar = item;
			return;
		}
		this.imageChangedEvent = null;
	}
	onToggleExcluirModal(confirmed?: boolean | void) {
		this.isModalExcluirOpen = !this.isModalExcluirOpen;
		if (confirmed) {
			this.adminService.excluirEvento(this.excluirEventoId!).subscribe();
		}
		if (!this.isModalEditarOpen) {
			this.excluirEventoId = undefined;
		}
	}

	onOpenExcluirModal(id: number) {
		this.onToggleExcluirModal();
		this.excluirEventoId = id;
	}

	submitCriarEventoForm(form: NgForm) {
		const imgFile = new File(
			[this.croppedImage],
			this.imageChangedEvent?.target.files[0].name,
		);
		if (form.valid && this.croppedImage) {
			this.adminService
				.uploadFileAzure(imgFile)
				.then((res) => {
					// nunca chega aqui por algum motivo
					// retorna um Erro com código 200
					console.log(res);
				})
				.catch((err) => {
					// infelizmente foi necessario, desculpa
					// tava dando erro com a infomraçao que eu precisava
					// ent só deu pra colocar a logica no catch e pedir perdão
					console.log(err.error.text);
					const evento: Evento = {
						...(form.value as Evento),
						urlFoto: err.error.text,
					};
					this.adminService.criarEvento(evento).subscribe({
						next: () => {
							this.onToggleCriarModal();
						},
					});
				});
		}
	}

	formartarData(valor: Date) {
		const str = valor.toLocaleString();
		const strarr = str.split("T");
		const strD = strarr[0].split("-");
		return `${strD[2]}/${strD[1]}/${strD[0]} - ${strarr[1]}`;
	}

	onFiltrar() { }
}
