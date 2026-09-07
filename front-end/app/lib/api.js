/**
 * Utilitários de integração com a API e formatação de dados.
 */

export const API_URL = 'http://localhost:8080';

export function formatarDataHora(iso) {
  if (!iso) return '';
  const d = new Date(iso);
  return d.toLocaleString('pt-BR', { dateStyle: 'short', timeStyle: 'short' });
}

export function toIsoDateTime(value) {
  if (!value) return null;
  return value.length === 16 ? `${value}:00` : value;
}

export function getUserId() {
  return typeof window !== 'undefined' ? localStorage.getItem('parkflowUserId') : null;
}

export function getUserPerfil() {
  return typeof window !== 'undefined' ? localStorage.getItem('parkflowUserPerfil') : null;
}
