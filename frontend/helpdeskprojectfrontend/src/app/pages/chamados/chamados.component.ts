import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ChamadoService, Chamado } from '../../services/chamado.service';

@Component({
  selector: 'app-chamados',
  templateUrl: './chamados.component.html',
  styleUrls: ['./chamados.component.css']
})
export class ChamadosComponent implements OnInit {

  chamados: Chamado[] = [];

  constructor(
    private chamadoService: ChamadoService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loadChamados();
  }

  loadChamados(): void {
    this.chamadoService.findAll().subscribe(
      dados => this.chamados = dados,
      err => console.error(err)
    );
  }

  novo(): void {
    this.router.navigate(['/chamados/novo']);
  }

  editar(id: number | undefined): void {
    if (!id) return;
    this.router.navigate([`/chamados/${id}/editar`]);
  }

  deletar(id: number | undefined): void {
    if (!id) return;
    if (!confirm('Deseja realmente excluir este chamado?')) return;

    this.chamadoService.delete(id).subscribe(
      () => this.loadChamados(),
      err => console.error(err)
    );
  }
}
