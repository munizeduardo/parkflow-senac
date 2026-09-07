'use client';

import styles from './vagas.module.css';
import Header from '../components/Header';
import { API_URL, toIsoDateTime } from '../lib/api';
import { useEffect, useState } from 'react';
import { useRouter } from 'next/navigation';

/**
 * Página de consulta de disponibilidade e seleção de vaga.
 */
export default function Vagas() {
  const router = useRouter();
  const [inicio, setInicio] = useState('');
  const [fim, setFim] = useState('');
  const [vagas, setVagas] = useState([]);
  const [consultou, setConsultou] = useState(false);
  const [carregando, setCarregando] = useState(false);

  useEffect(() => {
    if (!localStorage.getItem('parkflowUserId')) {
      router.push('/login');
    }
  }, [router]);

  const handleConsultar = async (e) => {
    e.preventDefault();
    setCarregando(true);
    setConsultou(true);

    try {
      const inicioIso = toIsoDateTime(inicio);
      const fimIso = toIsoDateTime(fim);
      const response = await fetch(
        `${API_URL}/vagas/disponibilidade?inicio=${inicioIso}&fim=${fimIso}`
      );
      const data = await response.json();

      if (response.ok) {
        setVagas(data);
      } else {
        setVagas([]);
        window.alert(data.mensagem || 'Não foi possível consultar as vagas.');
      }
    } catch (error) {
      setVagas([]);
      console.error('Erro na requisição:', error);
    } finally {
      setCarregando(false);
    }
  };

  const handleSelecionar = (vaga) => {
    router.push(
      `/reservas/nova?vagaId=${vaga.id}&codigo=${vaga.codigo}&tipo=${vaga.tipo}&setor=${vaga.setor}&inicio=${toIsoDateTime(inicio)}&fim=${toIsoDateTime(fim)}`
    );
  };

  const links = [
    { href: '/vagas', label: 'Vagas' },
    { href: '/reservas', label: 'Minhas Reservas' },
  ];

  return (
    <main className={styles.main}>
      <Header links={links} />

      <section className={styles.container}>
        <h2 className={styles.pageTitle}>Consultar disponibilidade</h2>

        <form className={styles.filtro} onSubmit={handleConsultar}>
          <div className={styles.formGroup}>
            <label htmlFor="id_inicio">Chegada:</label>
            <input
              type="datetime-local"
              id="id_inicio"
              className={styles.input}
              value={inicio}
              onChange={(e) => setInicio(e.target.value)}
              required
            />
          </div>

          <div className={styles.formGroup}>
            <label htmlFor="id_fim">Saída:</label>
            <input
              type="datetime-local"
              id="id_fim"
              className={styles.input}
              value={fim}
              onChange={(e) => setFim(e.target.value)}
              required
            />
          </div>

          <button type="submit" className={styles.botao}>
            <span className="material-symbols-outlined">search</span> Consultar
          </button>
        </form>

        {carregando && <p className={styles.mensagem}>Carregando vagas...</p>}

        {!carregando && consultou && vagas.length === 0 && (
          <p className={styles.mensagem}>Nenhuma vaga disponível para este período.</p>
        )}

        {vagas.length > 0 && (
          <div className={styles.grid}>
            {vagas.map((vaga) => (
              <article key={vaga.id} className={styles.card}>
                <span className={`${styles.icon} material-symbols-outlined`}>local_parking</span>
                <h3>Vaga {vaga.codigo}</h3>
                <p>Setor: {vaga.setor || '—'}</p>
                <p>Tipo: {vaga.tipo}</p>
                <button className={styles.botaoReservar} onClick={() => handleSelecionar(vaga)}>
                  Reservar
                </button>
              </article>
            ))}
          </div>
        )}
      </section>
    </main>
  );
}
