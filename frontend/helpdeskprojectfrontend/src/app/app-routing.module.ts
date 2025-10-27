// No seu app-routing.module.ts

import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './pages/login/login.component';
import { HomeComponent } from './home/home.component';
import { LayoutComponent } from './layout/layout.component'; // <-- NOVO

const routes: Routes = [
  // 1. Rota de Login (Isolada)
  { path: 'login', component: LoginComponent },

  // 2. Rotas que usam o Layout Principal
  { 
    path: '', // Caminho raiz
    component: LayoutComponent, // Este componente conterá a Sidenav/Toolbar
    children: [
      { path: 'home', component: HomeComponent },
      // { path: 'tecnicos', component: TecnicosComponent }, // Adicionar depois
      // { path: 'clientes', component: ClientesComponent }, // Adicionar depois
      { path: '', redirectTo: 'home', pathMatch: 'full' } // Padrão para a raiz do layout
    ]
  },

  // 3. Rota de Falha (Opcional, mas recomendado)
  { path: '**', redirectTo: 'login' } // Se a rota não existir, manda pro login.
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }