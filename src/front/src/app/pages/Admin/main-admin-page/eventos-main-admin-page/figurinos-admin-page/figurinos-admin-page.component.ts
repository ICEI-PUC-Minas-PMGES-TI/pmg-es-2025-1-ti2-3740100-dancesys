import { Component, inject, ViewChild } from "@angular/core";
import { ModalComponent } from "../../../../../components/modal/modal.component";
import { SimpleTableComponent } from "../../../../../components/simple-table/simple-table.component";
import { SearchBoxSingleComponent } from "../../../../../components/search-box-single/search-box-single.component";
import { FormBuilder, FormGroup, ReactiveFormsModule } from "@angular/forms";
import { BotaoComponent } from "../../../../../components/botao/botao.component";
import { CommonModule } from "@angular/common";
import { FigurinoFilter } from "../../../../../models/Figurino.model";
import { EventoService } from "../../../../../services/evento.service";
import { AlertService } from "../../../../../services/Alert.service";
import { Figurino } from "../../../../../models/Figurino.model";
import {
	ImageCroppedEvent,
	ImageCropperComponent,
	LoadedImage,
} from "ngx-image-cropper";

enum ToggleModal {
	NEW = "Criar Figurino",
	EDIT = "Editar Figurino",
}

@Component({
	selector: "app-figurinos-admin-page",
	imports: [
		ModalComponent,
		SimpleTableComponent,
		SearchBoxSingleComponent,
		ReactiveFormsModule,
		BotaoComponent,
		CommonModule,
		ImageCropperComponent,
	],
	templateUrl: "./figurinos-admin-page.component.html",
	styleUrl: "./figurinos-admin-page.component.css",
})
export class FigurinosAdminPageComponent {
	@ViewChild(SimpleTableComponent) tabela!: SimpleTableComponent;

	eventoService = inject(EventoService);
	alertService = inject(AlertService);

	filterForm: FormGroup;
	figurinoForm: FormGroup;

	ToggleModal = ToggleModal;

	paginaAtual: number = 0;
	itensPage: number = 10;
	orderByValue!: string;
	orderValue!: string;

	isModalOpen: boolean = false;
	isModalFoto: boolean = false;
	ImageCropped: boolean = false;
	isModalConfirm: boolean = false;
	isEdit: boolean = false;

	imageChangedEvent: any = null;
	croppedImage: any = "";
	nomeArquivoFoto: string = "";

	urlFotoEdit: string = "";

	idDelete: number = 0;

	figurinoObj: any = [];

	tipoMap: Record<number, string> = {
		1: "Aluguel",
		2: "Compra",
	};

	colunas = [
		{ chave: "nome", titulo: "Nome" },
		{
			chave: "valor",
			titulo: "Valor",
			formatar: (valor: Number) =>
				valor != null ? this.valorFormater(valor) : "",
		},
		{
			chave: "tipo",
			titulo: "tipo",
			formatar: (valor: number) => this.tipoMap[valor] ?? String(valor),
		},
	];

	acoes = [
		{
			icon: "edit",
			title: "Editar",
			cor: "dark",
			callback: (item: any) => this.editar(item),
		},
		{
			icon: "delete",
			title: "Excluir",
			cor: "dark",
			callback: (item: any) => this.excluir(item.id),
		},
	];

	tipos: { value: number; name: string }[] = [
		{ value: 1, name: "Aluguel" },
		{ value: 2, name: "Compra" },
	];

	constructor(private fb: FormBuilder) {
		this.filterForm = this.fb.group({
			nome: [],
			tipo: [],
			valor: [],
			pagina: [this.paginaAtual],
			tamanho: [this.itensPage],
			orderBy: [this.orderByValue],
			order: [this.orderValue],
		});

		this.figurinoForm = this.fb.group({
			id: [],
			nome: [],
			tipo: [],
			valor: [],
			urlFoto: [],
		});
	}

	ngOnInit() {
		this.buscar();
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

	salvar() {
		const form: Figurino = this.getFigurinoForm();

		const item: Figurino = {
			id: form.id == undefined ? null : form.id,
			nome: form.nome,
			tipo: form.tipo,
			valor: form.valor,
			base64: this.croppedImage,
			nomeArquivo: this.nomeArquivoFoto,
			urlFoto: form.urlFoto == undefined ? null : form.urlFoto,
		};

		this.eventoService.salvarFigurino(item).subscribe({
			next: (response) => {
				this.closeModal();
				this.alertService.sucesso(
					this.isEdit
						? "Figurino editado com sucesso"
						: "Figurino criado com sucesso",
				);
				this.buscar();
				this.imageChangedEvent = null;
				this.croppedImage = "";
				this.nomeArquivoFoto = "";
			},
		});
	}

	editar(item: any) {
		this.isEdit = true;
		this.isModalOpen = true;
		if (item.urlFoto != null) {
			this.urlFotoEdit = item.urlFoto;
		}
		this.preencherFigurinoForm(item);
	}

	excluir(id: number) {
		this.idDelete = id;
		this.isModalConfirm = true;
	}

	onConfirmDelete(choice: boolean | void) {
		if (choice) {
			this.eventoService.excluirFigurino(this.idDelete).subscribe({
				next: () => {
					this.alertService.exclusao("Figurino excluido com sucesso");
					this.buscar();
				},
				error: (err) => {
					this.alertService.erro(
						"Esse figurino ja esta relacionado a um aluno",
					);
				},
			});
		}
		this.idDelete = 0;
		this.isModalConfirm = false;
	}

	preencherFigurinoForm(item: any) {
		this.figurinoForm = this.fb.group({
			id: [item.id],
			nome: [item.nome],
			tipo: [item.tipo],
			valor: [item.valor],
			urlFoto: [item.urlFoto],
		});
	}

	resetFigurinoForm() {
		this.figurinoForm = this.fb.group({
			nome: [],
			tipo: [],
			valor: [],
			urlFoto: [],
		});

		this.urlFotoEdit = "";
	}

	getFigurinoForm() {
		const item = this.figurinoForm.value;
		const Figurino: Figurino = item;

		return Figurino;
	}

	isFormValido() {
		return this.figurinoForm.valid;
	}

	buscar() {
		this.eventoService.filterFigurino(this.getFilter()).subscribe({
			next: (reponse: any) => {
				if (reponse.total <= 0) {
					this.alertService.info("Nenhum registro encontrado");
				} else {
					this.figurinoObj = reponse;
				}
				this.tabela.isLoad(false);
			},
		});
	}

	getFilter() {
		this.filterForm.get("tamanho")?.setValue(this.itensPage);
		this.filterForm.get("pagina")?.setValue(this.paginaAtual);
		this.filterForm.get("orderBy")?.setValue(this.orderByValue);
		this.filterForm.get("order")?.setValue(this.orderValue);

		const item = this.filterForm.value;
		const FigurinoFilter: FigurinoFilter = item;

		return FigurinoFilter;
	}

	onFilter() {
		this.tabela.isLoad(true);
		this.paginaAtual = 0;
		this.tabela.resetPage();
		this.buscar();
	}

	confirmar() {
		this.ImageCropped = false;
	}

	ver() {
		this.isModalFoto = true;
	}

	novo() {
		this.isModalOpen = true;
	}

	closeModal() {
		this.isModalOpen = false;
		this.resetFigurinoForm();
	}

	closeModalFoto() {
		this.isModalFoto = false;
	}

	limparFiltros() {
		this.filterForm = this.fb.group({
			nome: [],
			tipo: [],
			valor: [],
			pagina: [this.paginaAtual],
			tamanho: [this.itensPage],
			orderBy: [this.orderByValue],
			order: [this.orderValue],
		});
	}

	valorFormater(valor: any) {
		return `R$${valor}`;
	}

	onPaginacaoChange(event: { paginaSelecionada: number; itensPage: number }) {
		this.tabela.isLoad(true);
		this.paginaAtual = --event.paginaSelecionada;
		this.itensPage = event.itensPage;
		this.buscar();
	}

	orderBy(event: { chave: string; direcao: "asc" | "desc" }) {
		this.tabela.isLoad(true);
		this.orderByValue = event.chave;
		this.orderValue = event.direcao;
		this.buscar();
	}
}
