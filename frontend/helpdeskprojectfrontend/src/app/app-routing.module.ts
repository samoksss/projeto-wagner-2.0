import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { LoginComponent } from './pages/login/login.component';
import { LayoutComponent } from './layout/layout.component';
import { HomeComponent } from './home/home.component';

import { ClientesComponent } from './pages/clientes/clientes.component';
import { ClienteFormComponent } from './pages/clientes/cliente-form/cliente-form.component';

import { ChamadosComponent } from './pages/chamados/chamados.component';
import { ChamadoFormComponent } from './pages/chamados/chamado-form/chamado-form.component';

import { TecnicosComponent } from './pages/tecnicos/tecnicos.component';
// Caso vá criar formulários de técnico no futuro
// import { TecnicoFormComponent } from './pages/tecnicos/tecnico-form/tecnico-form.component';

const routes: Routes = [
  { path: 'login', component: LoginComponent },

  {
    path: '',
    component: LayoutComponent,
    children: [
      { path: 'home', component: HomeComponent },

      // Técnicos
      { path: 'tecnicos', component: TecnicosComponent },
      // { path: 'tecnicos/novo', component: TecnicoFormComponent },
      // { path: 'tecnicos/:id/editar', component: TecnicoFormComponent },

      // Clientes
      { path: 'clientes', component: ClientesComponent },
      { path: 'clientes/novo', component: ClienteFormComponent },
      { path: 'clientes/:id/editar', component: ClienteFormComponent },

      // Chamados
      { path: 'chamados', component: ChamadosComponent },
      { path: 'chamados/novo', component: ChamadoFormComponent },
      { path: 'chamados/:id/editar', component: ChamadoFormComponent },

      // Default
      { path: '', redirectTo: '/home', pathMatch: 'full' }
    ]
  },

  { path: '**', redirectTo: '/home' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}
