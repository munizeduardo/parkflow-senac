'use client';

import styles from './cadastro.module.css';
import { useState } from 'react';
import { useRouter } from 'next/navigation';

/**
 * Página de cadastro de usuário.
 */
export default function Cadastro() {
  const router = useRouter();
  const [nome, setNome] = useState('');
  const [email, setEmail] = useState('');
  const [senha, setSenha] = useState('');

  const handleCadastro = async (e) => {
    e.preventDefault();

    try {
      const response = await fetch('http://localhost:8080/usuarios/cadastro', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ nome, email, senha }),
      });

      const resposta = await response.json();

      if (response.ok) {
        window.alert('Cadastro realizado com sucesso!');
        router.push('/login');
      } else {
        window.alert(resposta.mensagem || 'Não foi possível realizar o cadastro.');
      }
    } catch (error) {
      window.alert('Erro na requisição');
      console.error('Erro na requisição:', error);
    }
  };

  return (
    <main className={styles.main}>
      <section className={styles.card}>
        <div className={styles.logoContainer}>
          <span className={`${styles.logoIcon} material-symbols-outlined`}>local_parking</span>
          <h1 className={styles.title}>ParkFlow</h1>
        </div>

        <p className={styles.subtitle}>Crie sua conta de usuário</p>

        <form onSubmit={handleCadastro}>
          <div className={styles.formGroup}>
            <label htmlFor="id_nome">Nome:</label><br />
            <input
              type="text"
              id="id_nome"
              className={styles.input}
              value={nome}
              onChange={(e) => setNome(e.target.value)}
              required
              placeholder="Seu nome completo"
            />
          </div>

          <div className={styles.formGroup}>
            <label htmlFor="id_email">Email:</label><br />
            <input
              type="email"
              id="id_email"
              className={styles.input}
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              required
              placeholder="exemplo@email.com"
            />
          </div>

          <div className={styles.formGroup}>
            <label htmlFor="id_senha">Senha:</label><br />
            <input
              type="password"
              id="id_senha"
              className={styles.input}
              value={senha}
              onChange={(e) => setSenha(e.target.value)}
              required
              placeholder="********"
            />
          </div>

          <div className={styles.btn_cadastrar}>
            <button type="submit">Cadastrar</button>
          </div>
        </form>
      </section>

      <div className={styles.btn_voltar}>
        <button onClick={() => router.push('/login')}>
          <span className="material-symbols-outlined">arrow_back</span> Voltar
        </button>
      </div>
    </main>
  );
}
