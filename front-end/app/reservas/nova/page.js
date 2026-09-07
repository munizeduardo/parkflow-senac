'use client';

import styles from './nova.module.css';
import Header from '../../components/Header';
import { API_URL, formatarDataHora, getUserId } from '../../lib/api';
import { useEffect, useState } from 'react';
import { useRouter } from 'next/navigation';

/**
 * Página de realização de reserva (dados da reserva e confirmação).
 */
export default function NovaReserva() {
  const router = useRouter();
  const [vaga, setVaga] = useState({});
  const [periodo, setPeriodo] = useState({});
  const [veiculos, setVeiculos] = useState([]);
  const [idVeiculo, setIdVeiculo] = useState('');
  const [reservaCriada, setReservaCriada] = useState(null);

  useEffect(() => {
    const params = new URLSearchParams(window.location.search);
    setVaga({
      id: params.get('vagaId'),
      codigo: params.get('codigo'),
      tipo: params.get('tipo'),
      setor: params.get('setor'),
    });
    setPeriodo({
      inicio: params.get('inicio'),
      fim: params.get('fim'),
    });

    const usuarioId = getUserId();
    if (!usuarioId) {
      router.push('/login');
      return;
    }

    fetch(`${API_URL}/veiculos/usuario/${usuarioId}`)
      .then((res) => res.json())
      .then((data) => setVeiculos(data))
      .catch((error) => console.error('Erro na requisição:', error));
  }, [router]);

  const handleConfirmar = async (e) => {
    e.preventDefault();

    if (!idVeiculo) {
      window.alert('Selecione um veículo.');
      return;
    }

    const body = {
      idUsuario: Number(getUserId()),
      idVeiculo: Number(idVeiculo),
      idVaga: Number(vaga.id),
      dataInicio: periodo.inicio,
      dataFim: periodo.fim,
    };

    try {
      const response = await fetch(`${API_URL}/reservas`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(body),
      });

      const data = await response.json();

      if (response.ok) {
        setReservaCriada(data);
      } else {
        window.alert(data.mensagem || 'Não foi possível realizar a reserva.');
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
        {reservaCriada ? (
          <div className={styles.confirmacao}>
            <span className={`${styles.confirmaIcon} material-symbols-outlined`}>check_circle</span>
            <h2>Reserva confirmada!</h2>
            <p>
              Vaga <strong>{reservaCriada.codigoVaga}</strong> reservada para o veículo{' '}
              <strong>{reservaCriada.placaVeiculo}</strong>.
            </p>
            <p>
              {formatarDataHora(reservaCriada.dataInicio)} até {formatarDataHora(reservaCriada.dataFim)}
            </p>
            <div className={styles.acoes}>
              <button className={styles.botaoPrimario} onClick={() => router.push('/reservas')}>
                Ver minhas reservas
              </button>
              <button className={styles.botaoSecundario} onClick={() => router.push('/vagas')}>
                Nova reserva
              </button>
            </div>
          </div>
        ) : (
          <>
            <h2 className={styles.pageTitle}>Dados da reserva</h2>

            <div className={styles.resumo}>
              <h3>Vaga selecionada</h3>
              <p>
                Código: <strong>{vaga.codigo}</strong>
              </p>
              <p>Setor: {vaga.setor || '—'} — Tipo: {vaga.tipo}</p>
              <p>
                Período: <strong>{formatarDataHora(periodo.inicio)}</strong> até{' '}
                <strong>{formatarDataHora(periodo.fim)}</strong>
              </p>
            </div>

            <form className={styles.form} onSubmit={handleConfirmar}>
              <div className={styles.formGroup}>
                <label htmlFor="id_veiculo">Veículo:</label>
                <select
                  id="id_veiculo"
                  className={styles.input}
                  value={idVeiculo}
                  onChange={(e) => setIdVeiculo(e.target.value)}
                  required
                >
                  <option value="">Selecione um veículo</option>
                  {veiculos.map((veiculo) => (
                    <option key={veiculo.id} value={veiculo.id}>
                      {veiculo.placa} — {veiculo.marca} {veiculo.modelo}
                    </option>
                  ))}
                </select>
              </div>

              {veiculos.length === 0 && (
                <p className={styles.aviso}>
                  Você ainda não possui veículos cadastrados.
                </p>
              )}

              <button type="submit" className={styles.botaoPrimario}>
                Confirmar reserva
              </button>
            </form>
          </>
        )}
      </section>
    </main>
  );
}
