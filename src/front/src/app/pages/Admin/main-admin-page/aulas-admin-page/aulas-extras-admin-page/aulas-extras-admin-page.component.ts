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
    diasComEvento: Date[] = [
      new Date(2025, 5, 1),
      new Date(2025, 5, 10),
      new Date(2025, 5, 15),
      new Date(2025, 5, 20),
    ];

  handleDiaSelecionado(data: Date) {
    console.log('Dia selecionado:', data);
  }

  onMesCarregado(event: { firstDay: Date; lastDay: Date }) {
    console.log('Primeiro dia:', event.firstDay);
    console.log('Último dia:', event.lastDay);
  }
}
