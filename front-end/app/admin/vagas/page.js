'use client';

import styles from './vagas.module.css';
import Header from '../../components/Header';
import { API_URL, getUserPerfil } from '../../lib/api';
import { useEffect, useState } from 'react';
import { useRouter } from 'next/navigation';

const TIPOS = ['COMUM', 'COBERTA', 'PCD'];
const STATUS = ['DISPONIVEL', 'OCUPADA', 'INDISPONIVEL'];

/**
 * Página de gestão de vagas (cadastrar, alterar status e excluir).
 */
export default function GestaoVagas() {
  const router = useRouter();
  const [vagas, setVagas] = useState([]);
  const [form, setForm] = useState({ codigo: '', setor: '', tipo: 'COMUM', status: 'DISPONIVEL' });

  const carregarVagas = () => {
    fetch(`${API_URL}/vagas`)
      .then((res) => res.json())
      .then((data) => setVagas(data))
      .catch((error) => console.error('Erro na requisição:', error));
  };

  useEffect(() => {
    if (getUserPerfil() !== 'ADMIN') {
      router.push('/login');
      return;
    }
    carregarVagas();
  }, [router]);

  const handleCadastrar = async (e) => {
    e.preventDefault();

    try {
      const response = await fetch(`${API_URL}/vagas`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(form),
      });

      const data = await response.json();

      if (response.ok) {
        setForm({ codigo: '', setor: '', tipo: 'COMUM', status: 'DISPONIVEL' });
        carregarVagas();
      } else {
        window.alert(data.mensagem || 'Não foi possível cadastrar a vaga.');
      }
    } catch (error) {
      window.alert('Erro na requisição');
      console.error('Erro na requisição:', error);
    }
  };

  const handleAtualizarStatus = async (vaga, novoStatus) => {
    const body = {
      codigo: vaga.codigo,
      setor: vaga.setor,
      tipo: vaga.tipo,
      status: novoStatus,
    };

    try {
      const response = await fetch(`${API_URL}/vagas/${vaga.id}`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(body),
      });

      if (response.ok) {
        carregarVagas();
      } else {
        window.alert('Não foi possível atualizar a vaga.');
      }
    } catch (error) {
      window.alert('Erro na requisição');
      console.error('Erro na requisição:', error);
    }
  };

  const handleExcluir = async (id) => {
    try {
      const response = await fetch(`${API_URL}/vagas/${id}`, { method: 'DELETE' });
      if (response.ok) {
        carregarVagas();
      } else {
        window.alert('Não foi possível excluir a vaga.');
      }
    } catch (error) {
      window.alert('Erro na requisição');
      console.error('Erro na requisição:', error);
    }
  };

  const links = [
    { href: '/admin', label: 'Dashboard' },
    { href: '/admin/vagas', label: 'Gestão de Vagas' },
    { href: '/admin/reservas', label: 'Gestão de Reservas' },
  ];

  return (
    <main className={styles.main}>
      <Header links={links} />

      <section className={styles.container}>
        <h2 className={styles.pageTitle}>Gestão de Vagas</h2>

        <form className={styles.formCadastro} onSubmit={handleCadastrar}>
          <h3>Cadastrar nova vaga</h3>
          <div className={styles.campos}>
            <input
              type="text"
              className={styles.input}
              placeholder="Código (ex.: A05)"
              value={form.codigo}
              onChange={(e) => setForm({ ...form, codigo: e.target.value })}
              required
            />
            <input
              type="text"
              className={styles.input}
              placeholder="Setor (ex.: A)"
              value={form.setor}
              onChange={(e) => setForm({ ...form, setor: e.target.value })}
            />
            <select
              className={styles.input}
              value={form.tipo}
              onChange={(e) => setForm({ ...form, tipo: e.target.value })}
            >
              {TIPOS.map((t) => (
                <option key={t} value={t}>{t}</option>
              ))}
            </select>
            <select
              className={styles.input}
              value={form.status}
              onChange={(e) => setForm({ ...form, status: e.target.value })}
            >
              {STATUS.map((s) => (
                <option key={s} value={s}>{s}</option>
              ))}
            </select>
            <button type="submit" className={styles.botaoCadastrar}>Cadastrar</button>
          </div>
        </form>

        <div className={styles.lista}>
          {vagas.map((vaga) => (
            <div key={vaga.id} className={styles.linha}>
              <div className={styles.info}>
                <span className={`${styles.icon} material-symbols-outlined`}>local_parking</span>
                <strong>{vaga.codigo}</strong>
                <span>Setor {vaga.setor || '—'}</span>
                <span>{vaga.tipo}</span>
              </div>
              <div className={styles.acoes}>
                <select
                  className={styles.select}
                  value={vaga.status}
                  onChange={(e) => handleAtualizarStatus(vaga, e.target.value)}
                >
                  {STATUS.map((s) => (
                    <option key={s} value={s}>{s}</option>
                  ))}
                </select>
                <button className={styles.botaoExcluir} onClick={() => handleExcluir(vaga.id)}>
                  <span className="material-symbols-outlined">delete</span>
                </button>
              </div>
            </div>
          ))}
        </div>
      </section>
    </main>
  );
}
