import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { API_CONFIG } from '../config/api.config';

// Ajuste os campos de acordo com a sua API
export interface Cliente {
  id?: number;
  nome: string;
  cpfOuCnpj: string;
  email: string;
  senha?: string;
  perfis?: string[];
}

@Injectable({
  providedIn: 'root'
})
export class ClienteService {

  private baseUrl = `${API_CONFIG.baseUrl}/clientes`;

  constructor(private http: HttpClient) { }

  // LISTAR TODOS
  findAll(): Observable<Cliente[]> {
    return this.http.get<Cliente[]>(this.baseUrl);
  }

  // BUSCAR POR ID
  findById(id: number): Observable<Cliente> {
    return this.http.get<Cliente>(`${this.baseUrl}/${id}`);
  }

  // CRIAR
  create(cliente: Cliente): Observable<Cliente> {
    return this.http.post<Cliente>(this.baseUrl, cliente);
  }

  // ATUALIZAR
  update(id: number, cliente: Cliente): Observable<Cliente> {
    return this.http.put<Cliente>(`${this.baseUrl}/${id}`, cliente);
  }

  // DELETAR
  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }
}
