import { CommonModule, DatePipe } from "@angular/common";
import { Component, inject, OnInit, ViewChild } from "@angular/core";
import { FormsModule, NgForm } from "@angular/forms";
import {
	ImageCroppedEvent,
	ImageCropperComponent,
	LoadedImage,
} from "ngx-image-cropper";
import { BotaoComponent } from "../../../../../components/botao/botao.component";
import { ModalComponent } from "../../../../../components/modal/modal.component";
import { SimpleTableComponent } from "../../../../../components/simple-table/simple-table.component";
import {
	Evento,
	EventoFilter,
	EventoResponse,
} from "../../../../../models/evento.model";
import { AdminService } from "../../../../../services/admin.service";
import { AlertService } from "../../../../../services/Alert.service";

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
export class EventosAdminPageComponent implements OnInit {
  	@ViewChild(SimpleTableComponent) tabela!: SimpleTableComponent

	private adminService = inject(AdminService);
	private alertService = inject(AlertService);

	paginaAtual: number = 0;
	itensPage: number = 10;
	orderByValue: string = '';
	orderValue: string = '';

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
			callback: (item: Evento) =>
				this.onOpenExcluirModal(item.id as number),
		},
	];

	eventos: EventoResponse | undefined = undefined;

	imageChangedEvent: any = null;
	croppedImage: any = "";
	nomeArquivoFoto: string = "";

	constructor() {}

	ngOnInit(): void {
		this.onFiltrar();
	}

	fileChangeEvent(event: any): void {
		this.imageChangedEvent = event;
		this.nomeArquivoFoto = event.target!.files[0].name;
	}
	imageCropped(event: ImageCroppedEvent) {
		this.croppedImage = event!.base64;
	}
	imageLoaded(image: LoadedImage) {}
	cropperReady() {}
	loadImageFailed() {}

	onPaginacaoChange(event: { paginaSelecionada: number; itensPage: number }) {
		this.tabela.isLoad(true)
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
		}
		this.imageChangedEvent = null;
	}
	onToggleExcluirModal(confirmed?: boolean | void) {
		this.isModalExcluirOpen = !this.isModalExcluirOpen;
		if (confirmed) {
			this.adminService.excluirEvento(this.excluirEventoId!).subscribe({
				next: () => {
					this.onFiltrar();
				},
				error: (err: any) => {
					this.alertService.erro(
						err?.error?.mensagem || "Erro inesperado!",
					);
				},
			});
		}
		if (!this.isModalEditarOpen) {
			this.excluirEventoId = undefined;
		}
	}

	limparFiltros(form: NgForm) {
		form.reset();
		this.onFiltrar();
	}

	onOpenExcluirModal(id: number) {
		this.onToggleExcluirModal();
		this.excluirEventoId = id;
	}

	submitCriarEventoForm(form: NgForm) {
		if (form.valid && this.croppedImage) {
			const ev: Evento = {
				...form.value,
				imgBase64: this.croppedImage,
				nomeArquivo: this.nomeArquivoFoto,
			};
			this.adminService.updateEvento(ev).subscribe({
				next: () => {
					this.onToggleCriarModal();
					this.alertService.sucesso("Evento criado com sucesso!");
					this.onFiltrar();
				},
			});
		}
	}

	submitEditarEventoForm(form: NgForm) {
		if (form.valid) {
			const ev: Evento = {
				...this.currentEventoEditar,
				...form.value,
				imgBase64: this.croppedImage as string,
				nomeArquivo: this.nomeArquivoFoto,
			};
			this.adminService.updateEvento(ev).subscribe({
				next: () => {
					this.onToggleEditarModal();
					this.alertService.sucesso("Evento editado com sucesso!");
					this.onFiltrar();
				},
				error: (err: any) => {
					this.onToggleEditarModal();
					this.alertService.erro(
						err?.error?.mensagem || "Erro inesperado",
					);
				},
			});
		}
	}

	formartarData(valor: Date) {
		const str = valor.toLocaleString();
		const strarr = str.split("T");
		const strD = strarr[0].split("-");
		return `${strD[2]}/${strD[1]}/${strD[0]} - ${strarr[1]}`;
	}

	onFilter(form?: NgForm){
		this.tabela.isLoad(true)
		this.paginaAtual = 0;
		this.tabela.resetPage()
		this.onFiltrar(form)
  	}

	onFiltrar(form?: NgForm) {
		this.adminService
			.fetchEventos({
				nome: form?.value.nome as string | "",
				local: form?.value.local as string | "",
				data: form?.value.data as Date | null,
				alunos: null,
				pagina: this.paginaAtual,
				tamanho: this.itensPage,
				orderBy: this.orderByValue,
				order: this.orderValue
			} as EventoFilter)
			.subscribe({
				next: (ev: EventoResponse) => {
					if(ev.total == 0){
						this.alertService.info("Nenhum registro encontrado!")
					}else{
						this.eventos = { ...ev };
					}
					this.tabela.isLoad(false)
				},
			});
	}

	hasFormValues(form: NgForm): boolean {
		if (form) {
			return Object.keys(form.value).some((k) => !!form.value[k]);
		}
		return false;
	}

	orderBy(event: { chave: string; direcao: "asc" | "desc" }) {
		this.tabela.isLoad(true);
		this.orderByValue = event.chave;
		this.orderValue = event.direcao;
		this.onFiltrar();
	}
}
