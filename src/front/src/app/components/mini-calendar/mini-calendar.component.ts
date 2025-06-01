import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Output, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-mini-calendar',
  imports: [CommonModule],
  templateUrl: './mini-calendar.component.html',
  styleUrl: './mini-calendar.component.css',
  standalone: true
})
export class MiniCalendarComponent implements OnInit {
  currentDate: Date = new Date();
  weeks: (Date | null)[][] = [];
  selectedDate: Date = new Date();

  @Input() eventsDays: Date[] = []

  @Output() diaSelecionado = new EventEmitter<Date>();
  @Output() mesCarregado = new EventEmitter<{ firstDay: Date; lastDay: Date }>();

  ngOnInit() {
    this.generateCalendar();
  }

  previousMonth() {
    this.currentDate = new Date(this.currentDate.getFullYear(), this.currentDate.getMonth() - 1, 1);
    this.generateCalendar();
  }

  nextMonth() {
    this.currentDate = new Date(this.currentDate.getFullYear(), this.currentDate.getMonth() + 1, 1);
    this.generateCalendar();
  }

  generateCalendar() {
    const firstDayOfMonth = new Date(this.currentDate.getFullYear(), this.currentDate.getMonth(), 1);
    const lastDayOfMonth = new Date(this.currentDate.getFullYear(), this.currentDate.getMonth() + 1, 0);

    const weeks: (Date | null)[][] = [];
    let week: (Date | null)[] = [];

    for (let i = 0; i < firstDayOfMonth.getDay(); i++) {
      week.push(null);
    }

    for (let day = 1; day <= lastDayOfMonth.getDate(); day++) {
      const date = new Date(this.currentDate.getFullYear(), this.currentDate.getMonth(), day);
      week.push(date);

      if (week.length === 7) {
        weeks.push(week);
        week = [];
      }
    }

    if (week.length > 0) {
      while (week.length < 7) {
        week.push(null);
      }
      weeks.push(week);
    }

    this.weeks = weeks;
    
    this.mesCarregado.emit({ firstDay: firstDayOfMonth, lastDay: lastDayOfMonth });
  }

  onSelectDay(date: Date | null) {
    if (!date) return;
    this.selectedDate = date;
    this.diaSelecionado.emit(date);
  }

  isSelected(date: Date): boolean {
    return this.selectedDate?.toDateString() === date.toDateString();
  }

  getMonthYear(): string {
    return this.currentDate.toLocaleDateString('default', { month: 'long', year: 'numeric' });
  }

  hasEvento(date: Date): boolean {
    return this.eventsDays.some(eventDate =>
      date &&
      eventDate.getDate() === date.getDate() &&
      eventDate.getMonth() === date.getMonth() &&
      eventDate.getFullYear() === date.getFullYear()
    );
  }
}
