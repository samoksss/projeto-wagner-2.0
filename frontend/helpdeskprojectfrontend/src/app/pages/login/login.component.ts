import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
// É necessário importar o ReactiveFormsModule no app.module.ts para que isso funcione!

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  // Declara o objeto que irá gerenciar o estado do formulário
  loginForm: FormGroup; 
  
  // Declara a variável de controle de erros (opcional)
  // Ex: true se a tentativa de login falhou
  // loginInvalido: boolean = false; 

  // Injeta o FormBuilder (construtor de formulários reativos)
  constructor(private fb: FormBuilder) { }

  ngOnInit(): void {
    // 1. Inicializa o formulário com os campos 'email' e 'senha'
    this.loginForm = this.fb.group({
      // O 'email' será o campo de username para a API (com validação básica)
      email: ['', [Validators.required, Validators.email]], 
      // A 'senha' também é obrigatória
      senha: ['', Validators.required]
    });
  }

  // 2. Método chamado quando o usuário clica no botão "Entrar"
  login(): void {
    // Verifica se o formulário é válido (se todos os campos obrigatórios estão preenchidos)
    if (this.loginForm.valid) {
      console.log('Tentativa de login com:', this.loginForm.value);
      
      // *** ESTE É O PONTO CHAVE: Aqui você chamará o AuthService ***
      // Ex: this.authService.authenticate(this.loginForm.value).subscribe(...);
      
      // Por enquanto, apenas exibe os dados no console
    } else {
      console.error('Formulário inválido. Preencha todos os campos.');
    }
  }

}
