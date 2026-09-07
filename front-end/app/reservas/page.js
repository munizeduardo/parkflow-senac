'use client';

import styles from './reservas.module.css';
import Header from '../components/Header';
import { API_URL, formatarDataHora, getUserId } from '../lib/api';
import { useEffect, useState } from 'react';
import { useRouter } from 'next/navigation';

/**
 * Página "Minhas Reservas": lista as reservas do usuário e permite cancelar.
 */
export default function MinhasReservas() {
  const router = useRouter();
  const [reservas, setReservas] = useState([]);

  const carregarReservas = () => {
    const usuarioId = getUserId();
    if (!usuarioId) {
      router.push('/login');
      return;
    }

    fetch(`${API_URL}/reservas/usuario/${usuarioId}`)
      .then((res) => res.json())
      .then((data) => setReservas(data))
      .catch((error) => console.error('Erro na requisição:', error));
  };

  useEffect(() => {
    carregarReservas();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [router]);

  const handleCancelar = async (id) => {
    try {
      const response = await fetch(`${API_URL}/reservas/${id}/cancelar`, {
        method: 'PUT',
      });

      if (response.ok) {
        carregarReservas();
      } else {
        window.alert('Não foi possível cancelar a reserva.');
      }
    } catch (error) {
      window.alert('Erro na requisição');
      console.error('Erro na requisição:', error);
    }
  };

  const links = [
    { href: '/vagas', label: 'Vagas' },
    { href: '/reservas', label: 'Minhas Reservas' },
  ];

  return (
    <main className={styles.main}>
      <Header links={links} />

      <section className={styles.container}>
        <h2 className={styles.pageTitle}>Minhas Reservas</h2>

        {reservas.length === 0 && (
          <p className={styles.mensagem}>
            Você ainda não possui reservas.{' '}
            <a href="/vagas" className={styles.link}>Consultar vagas disponíveis</a>
          </p>
        )}

        <div className={styles.lista}>
          {reservas.map((reserva) => (
            <article key={reserva.id} className={styles.card}>
              <div className={styles.cardHeader}>
                <h3>Vaga {reserva.codigoVaga}</h3>
                <span className={`${styles.badge} ${styles[reserva.status.toLowerCase()]}`}>
                  {reserva.status}
                </span>
              </div>
              <p>
                Veículo: <strong>{reserva.placaVeiculo}</strong>
              </p>
              <p>
                {formatarDataHora(reserva.dataInicio)} até {formatarDataHora(reserva.dataFim)}
              </p>
              {reserva.status === 'ATIVA' && (
                <button className={styles.botaoCancelar} onClick={() => handleCancelar(reserva.id)}>
                  Cancelar reserva
                </button>
              )}
            </article>
          ))}
        </div>
      </section>
    </main>
  );
}
