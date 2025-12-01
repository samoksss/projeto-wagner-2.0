import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { TecnicoService, Tecnico } from '../../services/tecnico.service';

@Component({
  selector: 'app-tecnicos',
  templateUrl: './tecnicos.component.html',
  styleUrls: ['./tecnicos.component.css']
})
export class TecnicosComponent implements OnInit {

  tecnicos: Tecnico[] = [];

  constructor(
    private tecnicoService: TecnicoService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loadTecnicos();
  }

  loadTecnicos(): void {
    this.tecnicoService.findAll().subscribe(
      dados => this.tecnicos = dados,
      err => console.error(err)
    );
  }

  novo(): void {
    this.router.navigate(['/tecnicos/novo']);
  }

  editar(id: number | undefined): void {
    if (!id) { return; }
    this.router.navigate([`/tecnicos/${id}/editar`]);
  }

  deletar(id: number | undefined): void {
    if (!id) { return; }
    if (!confirm('Deseja realmente excluir este técnico?')) { return; }

    this.tecnicoService.delete(id).subscribe(
      () => this.loadTecnicos(),
      err => console.error(err)
    );
  }
}
