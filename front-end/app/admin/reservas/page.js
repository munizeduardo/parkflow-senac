'use client';

import styles from './reservas.module.css';
import Header from '../../components/Header';
import { API_URL, formatarDataHora, getUserPerfil } from '../../lib/api';
import { useEffect, useState } from 'react';
import { useRouter } from 'next/navigation';

/**
 * Página de gestão de reservas (administrador).
 */
export default function GestaoReservas() {
  const router = useRouter();
  const [reservas, setReservas] = useState([]);

  const carregarReservas = () => {
    fetch(`${API_URL}/reservas`)
      .then((res) => res.json())
      .then((data) => setReservas(data))
      .catch((error) => console.error('Erro na requisição:', error));
  };

  useEffect(() => {
    if (getUserPerfil() !== 'ADMIN') {
      router.push('/login');
      return;
    }
    carregarReservas();
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
    { href: '/admin', label: 'Dashboard' },
    { href: '/admin/vagas', label: 'Gestão de Vagas' },
    { href: '/admin/reservas', label: 'Gestão de Reservas' },
  ];

  return (
    <main className={styles.main}>
      <Header links={links} />

      <section className={styles.container}>
        <h2 className={styles.pageTitle}>Gestão de Reservas</h2>

        {reservas.length === 0 && (
          <p className={styles.mensagem}>Nenhuma reserva cadastrada.</p>
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
                Usuário: <strong>{reserva.nomeUsuario}</strong>
              </p>
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
