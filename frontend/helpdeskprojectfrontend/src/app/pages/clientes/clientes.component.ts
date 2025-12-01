import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ClienteService, Cliente } from '../../services/cliente.service';

@Component({
  selector: 'app-clientes',
  templateUrl: './clientes.component.html',
  styleUrls: ['./clientes.component.css']
})
export class ClientesComponent implements OnInit {

  clientes: Cliente[] = [];

  displayedColumns: string[] = ['id', 'nome', 'email', 'cpfOuCnpj', 'acoes'];

  constructor(
    private clienteService: ClienteService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loadClientes();
  }

  loadClientes(): void {
    this.clienteService.findAll().subscribe(
      dados => this.clientes = dados,
      err => console.error(err)
    );
  }

  novo(): void {
    this.router.navigate(['/clientes/novo']);
  }

  editar(id: number | undefined): void {
    if (!id) return;
    this.router.navigate([`/clientes/${id}/editar`]);
  }

  deletar(id: number | undefined): void {
    if (!id) return;
    if (!confirm('Deseja realmente excluir este cliente?')) return;

    this.clienteService.delete(id).subscribe(
      () => this.loadClientes(),
      err => console.error(err)
    );
  }
}
