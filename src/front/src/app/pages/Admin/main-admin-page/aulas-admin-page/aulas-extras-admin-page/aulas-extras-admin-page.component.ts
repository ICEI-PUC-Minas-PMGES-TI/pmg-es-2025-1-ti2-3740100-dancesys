import { Component } from '@angular/core';
import { MiniCalendarComponent } from '../../../../../components/mini-calendar/mini-calendar.component';

@Component({
  selector: 'app-aulas-extras-admin-page',
  imports: [MiniCalendarComponent],
  templateUrl: './aulas-extras-admin-page.component.html',
  styleUrl: './aulas-extras-admin-page.component.css',
  standalone: true
})
export class AulasExtrasAdminPageComponent {
  handleDiaSelecionado(data: Date) {
    console.log('Dia selecionado:', data);
  }
}
