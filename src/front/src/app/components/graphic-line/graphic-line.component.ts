import { Component, ElementRef, Input, ViewChild } from '@angular/core';
import { NgChartsModule } from 'ng2-charts';
import { Chart, ChartDataset } from 'chart.js';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-graphic-line',
  imports: [
    CommonModule,
    NgChartsModule 
  ],
  templateUrl: './graphic-line.component.html',
  styleUrl: './graphic-line.component.css'
})
export class GraphicLineComponent {
  @Input() labels: string[] = []
  @Input() data: ChartDataset [] = []

  @ViewChild('graficoLinha') canvasRef!: ElementRef<HTMLCanvasElement>;
  graficoLinha: Chart | undefined;
  gerarGraficoLinha(){
    const ctx = this.canvasRef.nativeElement.getContext('2d');
    if (ctx) {
      this.graficoLinha = new Chart(ctx, {
        type: 'line',
        data: {
          labels: this.labels,
          datasets: this.data
        },
        options: {
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
