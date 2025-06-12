import { CommonModule } from '@angular/common';
import { Component, ElementRef, Input, ViewChild } from '@angular/core';
import { Chart, ChartDataset } from 'chart.js';
import { NgChartsModule } from 'ng2-charts';

@Component({
  selector: 'app-graphic-bar-y',
  imports: [
    CommonModule,
    NgChartsModule 
  ],
  templateUrl: './graphic-bar-y.component.html',
  styleUrl: './graphic-bar-y.component.css'
})
export class GraphicBarYComponent {
  @Input() labels: string[] = []
  @Input() data: ChartDataset [] = []

  @ViewChild('graficoBarraY') canvasRef!: ElementRef<HTMLCanvasElement>;
  graficoLinha: Chart | undefined;
  gerarGraficoBar(){
    if (this.graficoLinha) {
      this.graficoLinha.destroy();
    }
    
    const ctx = this.canvasRef.nativeElement.getContext('2d');
    if (ctx) {
      this.graficoLinha = new Chart(ctx, {
        type: 'bar',
        data: {
          labels: this.labels,
          datasets: this.data
        },
        options: {
          indexAxis: 'y',
          responsive: true,
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
