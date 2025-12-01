import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { API_CONFIG } from '../config/api.config';

export interface Tecnico {
  id?: number;
  nome: string;
  cpf: string;
  email: string;
  senha?: string;
  perfis?: string[];
}

@Injectable({
  providedIn: 'root'
})
export class TecnicoService {

  private baseUrl = `${API_CONFIG.baseUrl}/tecnicos`;

  constructor(private http: HttpClient) {}

  findAll(): Observable<Tecnico[]> {
    return this.http.get<Tecnico[]>(this.baseUrl);
  }

  findById(id: number): Observable<Tecnico> {
    return this.http.get<Tecnico>(`${this.baseUrl}/${id}`);
  }

  create(tecnico: Tecnico): Observable<Tecnico> {
    return this.http.post<Tecnico>(this.baseUrl, tecnico);
  }

  update(id: number, tecnico: Tecnico): Observable<Tecnico> {
    return this.http.put<Tecnico>(`${this.baseUrl}/${id}`, tecnico);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }
}
