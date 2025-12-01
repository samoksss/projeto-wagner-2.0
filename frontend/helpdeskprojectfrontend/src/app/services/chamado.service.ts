import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { API_CONFIG } from '../config/api.config';

// Ajuste os campos e tipos de acordo com sua API
export interface Chamado {
  id?: number;
  titulo: string;
  observacoes: string;
  status: string;      // ABERTO, ANDAMENTO, ENCERRADO...
  prioridade: string;  // BAIXA, MEDIA, ALTA...
  tecnicoId: number;
  clienteId: number;
  dataAbertura?: string;
  dataFechamento?: string;
}

@Injectable({
  providedIn: 'root'
})
export class ChamadoService {

  private baseUrl = `${API_CONFIG.baseUrl}/chamados`;

  constructor(private http: HttpClient) { }

  // LISTAR TODOS
  findAll(): Observable<Chamado[]> {
    return this.http.get<Chamado[]>(this.baseUrl);
  }

  // BUSCAR POR ID
  findById(id: number): Observable<Chamado> {
    return this.http.get<Chamado>(`${this.baseUrl}/${id}`);
  }

  // CRIAR
  create(chamado: Chamado): Observable<Chamado> {
    return this.http.post<Chamado>(this.baseUrl, chamado);
  }

  // ATUALIZAR
  update(id: number, chamado: Chamado): Observable<Chamado> {
    return this.http.put<Chamado>(`${this.baseUrl}/${id}`, chamado);
  }

  // DELETAR
  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }
}
