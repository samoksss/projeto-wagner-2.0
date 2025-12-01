import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ChamadoService, Chamado } from '../../../services/chamado.service';

@Component({
  selector: 'app-chamado-form',
  templateUrl: './chamado-form.component.html',
  styleUrls: ['./chamado-form.component.css']
})
export class ChamadoFormComponent implements OnInit {

  form!: FormGroup;
  isEdit = false;
  chamadoId?: number;

  statusOptions = ['ABERTO', 'ANDAMENTO', 'ENCERRADO'];
  prioridadeOptions = ['BAIXA', 'MEDIA', 'ALTA'];

  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private chamadoService: ChamadoService
  ) {}

  ngOnInit(): void {
    this.form = this.fb.group({
      titulo: ['', Validators.required],
      observacoes: [''],
      status: ['ABERTO', Validators.required],
      prioridade: ['BAIXA', Validators.required],
      tecnicoId: [null, Validators.required],
      clienteId: [null, Validators.required]
    });

    this.route.paramMap.subscribe(params => {
      const idParam = params.get('id');
      if (idParam) {
        this.isEdit = true;
        this.chamadoId = +idParam;
        this.carregarChamado(this.chamadoId);
      }
    });
  }

  carregarChamado(id: number): void {
    this.chamadoService.findById(id).subscribe(
      (ch: Chamado) => {
        this.form.patchValue(ch);
      },
      err => console.error(err)
    );
  }

  salvar(): void {
    if (this.form.invalid) return;

    const dados: Chamado = this.form.value;

    if (this.isEdit && this.chamadoId) {
      this.chamadoService.update(this.chamadoId, dados).subscribe(
        () => this.router.navigate(['/chamados']),
        err => console.error(err)
      );
    } else {
      this.chamadoService.create(dados).subscribe(
        () => this.router.navigate(['/chamados']),
        err => console.error(err)
      );
    }
  }

  cancelar(): void {
    this.router.navigate(['/chamados']);
  }
}
