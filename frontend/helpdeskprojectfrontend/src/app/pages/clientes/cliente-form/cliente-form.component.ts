import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ClienteService, Cliente } from '../../../services/cliente.service';

@Component({
  selector: 'app-cliente-form',
  templateUrl: './cliente-form.component.html',
  styleUrls: ['./cliente-form.component.css']
})
export class ClienteFormComponent implements OnInit {

  form!: FormGroup;
  isEdit = false;
  clienteId?: number;

  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private clienteService: ClienteService
  ) {}

  ngOnInit(): void {
    this.form = this.fb.group({
      nome: ['', Validators.required],
      cpfOuCnpj: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      senha: [''] // opcional no update
    });

    this.route.paramMap.subscribe(params => {
      const idParam = params.get('id');
      if (idParam) {
        this.isEdit = true;
        this.clienteId = +idParam;
        this.carregarCliente(this.clienteId);
      }
    });
  }

  carregarCliente(id: number): void {
    this.clienteService.findById(id).subscribe(
      (cliente: Cliente) => {
        this.form.patchValue({
          nome: cliente.nome,
          cpfOuCnpj: cliente.cpfOuCnpj,
          email: cliente.email
        });
      },
      err => console.error(err)
    );
  }

  salvar(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    const dados: Cliente = this.form.value;

    if (this.isEdit && this.clienteId) {
      this.clienteService.update(this.clienteId, dados).subscribe(
        () => this.router.navigate(['/clientes']),
        err => console.error(err)
      );
    } else {
      this.clienteService.create(dados).subscribe(
        () => this.router.navigate(['/clientes']),
        err => console.error(err)
      );
    }
  }

  cancelar(): void {
    this.router.navigate(['/clientes']);
  }
}
