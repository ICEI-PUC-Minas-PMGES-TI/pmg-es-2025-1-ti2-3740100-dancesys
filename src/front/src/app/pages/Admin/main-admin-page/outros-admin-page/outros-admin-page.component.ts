import { Component } from '@angular/core';
import { InternalSidebarComponent, InternalSidebarRoute } from '../../../../components/internal-sidebar/internal-sidebar.component';
import { RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-outros-admin-page',
  imports: [InternalSidebarComponent, RouterOutlet],
  templateUrl: './outros-admin-page.component.html',
  styleUrl: './outros-admin-page.component.css'
})
export class OutrosAdminPageComponent {
  sidebarRoutes: InternalSidebarRoute[] = [
      { label: "Modalidades", route: "modalidade" },
      { label: "Salas", route: "sala" },
    ];
}
