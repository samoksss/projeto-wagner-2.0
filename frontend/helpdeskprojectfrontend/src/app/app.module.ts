// Conteúdo de app.module.ts (Adicione as importações do Material aqui)

// ... outros imports

// NOVO: MÓDULOS PARA O LAYOUT (TOOLBAR, SIDENAV, LISTA E ÍCONES)
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatListModule } from '@angular/material/list';
import { MatIconModule } from '@angular/material/icon'; 
import { AppRoutingModule } from './app-routing.module'; // NOVO: Para o roteamento

// Crie o componente Home antes de importar aqui
// import { HomeComponent } from './home/home.component'; // (Faremos no Passo 3)

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    // HomeComponent // Incluir aqui após criar o componente
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    AppRoutingModule, // NOVO
    
    // Lógica e Comunicação
    ReactiveFormsModule,
    HttpClientModule, 
    
    // Componentes Visuais do Material (Login)
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    
    // NOVO: Componentes Visuais do Material (Layout/Sidenav)
    MatToolbarModule,
    MatSidenavModule,
    MatListModule,
    MatIconModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }