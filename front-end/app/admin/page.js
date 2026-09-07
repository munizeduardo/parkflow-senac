'use client';

import styles from './admin.module.css';
import Header from '../components/Header';
import { API_URL, getUserPerfil } from '../lib/api';
import { useEffect, useState } from 'react';
import { useRouter } from 'next/navigation';

/**
 * Dashboard administrativo com os indicadores de ocupação do estacionamento.
 */
export default function Dashboard() {
  const router = useRouter();
  const [ocupacao, setOcupacao] = useState(null);

  useEffect(() => {
    if (getUserPerfil() !== 'ADMIN') {
      router.push('/login');
      return;
    }

    fetch(`${API_URL}/vagas/ocupacao`)
      .then((res) => res.json())
      .then((data) => setOcupacao(data))
      .catch((error) => console.error('Erro na requisição:', error));
  }, [router]);

  const links = [
    { href: '/admin', label: 'Dashboard' },
    { href: '/admin/vagas', label: 'Gestão de Vagas' },
    { href: '/admin/reservas', label: 'Gestão de Reservas' },
  ];

  return (
    <main className={styles.main}>
      <Header links={links} />

      <section className={styles.container}>
        <h2 className={styles.pageTitle}>Dashboard</h2>

        {!ocupacao && <p className={styles.mensagem}>Carregando indicadores...</p>}

        {ocupacao && (
          <>
            <div className={styles.grid}>
              <div className={`${styles.card} ${styles.cardAzul}`}>
                <span className={`${styles.icon} material-symbols-outlined`}>local_parking</span>
                <h3>{ocupacao.totalVagas}</h3>
                <p>Total de vagas</p>
              </div>
              <div className={`${styles.card} ${styles.cardVerde}`}>
                <span className={`${styles.icon} material-symbols-outlined`}>check_circle</span>
                <h3>{ocupacao.vagasDisponiveis}</h3>
                <p>Vagas disponíveis</p>
              </div>
              <div className={`${styles.card} ${styles.cardVermelho}`}>
                <span className={`${styles.icon} material-symbols-outlined`}>cancel</span>
                <h3>{ocupacao.vagasOcupadas}</h3>
                <p>Vagas ocupadas</p>
              </div>
              <div className={`${styles.card} ${styles.cardCinza}`}>
                <span className={`${styles.icon} material-symbols-outlined`}>block</span>
                <h3>{ocupacao.vagasIndisponiveis}</h3>
                <p>Vagas indisponíveis</p>
              </div>
              <div className={`${styles.card} ${styles.cardAzul}`}>
                <span className={`${styles.icon} material-symbols-outlined`}>event_available</span>
                <h3>{ocupacao.totalReservas}</h3>
                <p>Total de reservas</p>
              </div>
              <div className={`${styles.card} ${styles.cardVerde}`}>
                <span className={`${styles.icon} material-symbols-outlined`}>bookmark</span>
                <h3>{ocupacao.reservasAtivas}</h3>
                <p>Reservas ativas</p>
              </div>
            </div>

            <div className={styles.acoes}>
              <button className={styles.botao} onClick={() => router.push('/admin/vagas')}>
                Gerenciar vagas
              </button>
              <button className={styles.botao} onClick={() => router.push('/admin/reservas')}>
                Gerenciar reservas
              </button>
            </div>
          </>
        )}
      </section>
    </main>
  );
}
