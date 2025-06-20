import { Component, inject } from '@angular/core';
import { ModalComponent } from '../../../../../components/modal/modal.component';
import { SimpleTableComponent } from '../../../../../components/simple-table/simple-table.component';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { BotaoComponent } from '../../../../../components/botao/botao.component';
import { CommonModule } from '@angular/common';
import { Modalidade } from '../../../../../models/modalidade.model';
import { ModalidadesService } from '../../../../../services/modalidades.service';
import { AlertService } from '../../../../../services/Alert.service';

enum ToggleModal {
	NEW = "Criar modalidade",
	EDIT = "Editar modalidade",
}

@Component({
  selector: 'app-modalidade-admin-page',
  imports: 
    [
      ModalComponent,
      SimpleTableComponent,
      ReactiveFormsModule,
      BotaoComponent,
      CommonModule
  ],
  templateUrl: './modalidade-admin-page.component.html',
  styleUrl: './modalidade-admin-page.component.css'
})
export class ModalidadeAdminPageComponent {
  modalidadeService = inject(ModalidadesService)
  alertService = inject(AlertService)

  modalidadeForm: FormGroup;
  isModalOpen: boolean = false
  isEdit: boolean = false
  isModalConfirm: boolean = false
  selectId: number = 0
  ToggleModal = ToggleModal
  modalidadeObj: Modalidade[] = []

  colunas = [
		{ chave: 'nome', titulo: 'Nome' },
	];
	  	  
	acoes = [
		{
		  icon: 'edit',
		  title: 'Editar',
		  cor: 'black_blue',
		  callback: (item: any) => this.editar(item)
		},
		{
		  icon: 'trash',
		  title: 'Excluir',
		  cor: 'dark',
		  callback: (item: any) => this.excluir(item.id)
		}
	];

  constructor(private fb: FormBuilder) {
    this.modalidadeForm = this.fb.group({
      id: [],
      nome: []
    });
	}

  ngOnInit(){
    this.buscar()
  }

  buscar(){
    this.modalidadeService.fetchModalidades().subscribe({
      next: (response) =>{
        this.modalidadeObj = response
      }
    })
  }

  novo(){
    this.isModalOpen = true
    this.isEdit = false
  }

  getModalidadeForm(){
    const item = this.modalidadeForm.value;
    const modalidadeItem: Modalidade = item;
    
    return modalidadeItem;
  }

  salvar(){
    this.modalidadeService.salvarModalidade(this.getModalidadeForm()).subscribe({
      next: (response) =>{
        this.alertService.sucesso("Modalidade salva com sucesso")
        this.closeModal()
        this.buscar()
      }
    })
  }

  excluir(id: number){
    this.isModalConfirm = true
    this.selectId = id
  }

  onConfirmDelete(choice: boolean | void){
    if(choice){
			this.modalidadeService.excluir(this.selectId).subscribe({
				next: () =>{
					this.buscar();
					this.alertService.exclusao("Horario excluido com sucesso!")
				},
				error: (err) => {
          this.alertService.erro("Modalidade em uso")
				},
			})
		}
		this.selectId = 0
		this.isModalConfirm = false;
  }

  preencherModalidadeForm(item: Modalidade){
    this.modalidadeForm = this.fb.group({
      id: [item.id],
      nome: [item.nome]
    });
  }

  resetModalidadeForm(){
    this.modalidadeForm = this.fb.group({
      id: [],
      nome: []
    });
  }

  editar(item: Modalidade){
    this.isEdit = true
    this.isModalOpen = true
    this.preencherModalidadeForm(item)
  }

  isFormValido(){
    return this.modalidadeForm.valid
  }

  closeModal(){
    this.isModalOpen = false
    this.isEdit = false
    this.resetModalidadeForm()
  }
}
