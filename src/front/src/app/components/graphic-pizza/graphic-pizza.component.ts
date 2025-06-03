import { Component, ElementRef, Input, ViewChild } from '@angular/core';
import { NgChartsModule } from 'ng2-charts';
import { Chart, ChartDataset } from 'chart.js';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-graphic-pizza',
  imports: [CommonModule,NgChartsModule],
  templateUrl: './graphic-pizza.component.html',
  styleUrl: './graphic-pizza.component.css'
})
export class GraphicPizzaComponent {
  @Input() labels: string[] = []
  @Input() data: ChartDataset [] = []

  @ViewChild('graficoPizza') canvasRef!: ElementRef<HTMLCanvasElement>;
  graficoPizza: Chart | undefined;
  gerarGraficoPizza(){
    if (this.graficoPizza) {
      this.graficoPizza.destroy();
    }
    const ctx = this.canvasRef.nativeElement.getContext('2d');
    if (ctx) {
      this.graficoPizza = new Chart(ctx, {
        type: 'doughnut',
        data: {
          labels: this.labels,
          datasets: this.data
        },
        options: {
          responsive: true,
          animation: {
            duration: 3000,
            easing: 'easeOutBounce'
          },
          plugins: {
            legend: {
              position: 'top',
            }
          }
        }
      });
    }
  }

}
