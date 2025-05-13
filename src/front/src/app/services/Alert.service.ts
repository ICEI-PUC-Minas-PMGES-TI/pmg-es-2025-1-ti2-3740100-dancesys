import { Injectable, Renderer2, RendererFactory2 } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class AlertService {
  private renderer: Renderer2;

  constructor(rendererFactory: RendererFactory2) {
    this.renderer = rendererFactory.createRenderer(null, null);
  }

  private criarAlerta(mensagem: string, classe: string, icone: string) {
    let alertaBox = document.getElementById('alertaBox');
    if (!alertaBox) return;

    const alerta = this.renderer.createElement('div');
    this.renderer.addClass(alerta, classe);
    alerta.innerHTML = `<i class="${icone}"></i> ${mensagem}`;

    this.renderer.appendChild(alertaBox, alerta);

    setTimeout(() => {
      this.renderer.removeChild(alertaBox, alerta);
    }, 4000);
  }

  erro(msg: string) {
    this.criarAlerta(msg, 'alerta', 'fa-solid fa-circle-xmark');
  }

  sucesso(msg: string) {
    this.criarAlerta(msg, 'alertaC', 'fa-solid fa-circle-check');
  }

  exclusao(msg: string) {
    this.criarAlerta(msg, 'alertaE', 'fa-solid fa-trash');
  }

  info(msg: string){
    this.criarAlerta(msg, 'alertaI', 'fa-solid fa-triangle-exclamation')
  }
}
