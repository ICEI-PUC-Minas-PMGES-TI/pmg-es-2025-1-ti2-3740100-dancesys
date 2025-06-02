import { Component } from '@angular/core';
import { ModalComponent } from '../../../../../components/modal/modal.component';
import { SimpleTableComponent } from '../../../../../components/simple-table/simple-table.component';
import { ReactiveFormsModule } from '@angular/forms';
import { BotaoComponent } from '../../../../../components/botao/botao.component';
import { CommonModule } from '@angular/common';

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

  novo(){
    
  }
}
