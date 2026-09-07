'use client';

import styles from './page.module.css';
import Link from 'next/link';

/**
 * Página inicial com a apresentação do ParkFlow e as opções de entrada.
 */
export default function Home() {
  return (
    <main className={styles.container}>
      <header className={styles.topo}>
        <span className={`${styles.logoIcon} material-symbols-outlined`}>local_parking</span>
        <h1 className={styles.titulo}>ParkFlow</h1>
      </header>

      <p className={styles.apresentacao}>
        A solução integrada para gestão e reserva de vagas de estacionamento.
      </p>

      <section className={styles.cardAcesso}>
        <h2 className={styles.cardTitulo}>Como deseja entrar?</h2>

        <div className={styles.botoes}>
          <Link href="/login">
            <button type="button" className={styles.botaoUsuario}>
              <span className="material-symbols-outlined">directions_car</span>
              Sou Usuário
            </button>
          </Link>

          <Link href="/login">
            <button type="button" className={styles.botaoAdmin}>
              <span className="material-symbols-outlined">admin_panel_settings</span>
              Sou Administrador
            </button>
          </Link>
        </div>
      </section>
    </main>
  );
}
