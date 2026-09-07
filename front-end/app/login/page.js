'use client';

import styles from './login.module.css';
import Link from 'next/link';
import { useState } from 'react';
import { useRouter } from 'next/navigation';

/**
 * Página de login. Autentica usuários e administradores e redireciona pelo perfil.
 */
export default function Login() {
  const router = useRouter();
  const [email, setEmail] = useState('');
  const [senha, setSenha] = useState('');

  const handleLogin = async (e) => {
    e.preventDefault();

    try {
      const response = await fetch('http://localhost:8080/usuarios/login', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ email, senha }),
      });

      const resposta = await response.json();

      if (response.ok) {
        localStorage.setItem('parkflowUserId', resposta.id);
        localStorage.setItem('parkflowUserNome', resposta.nome);
        localStorage.setItem('parkflowUserPerfil', resposta.perfil);

        if (resposta.perfil === 'ADMIN') {
          router.push('/admin');
        } else {
          router.push('/vagas');
        }
      } else {
        window.alert(resposta.mensagem || 'E-mail ou senha inválidos.');
      }
    } catch (error) {
      window.alert('Erro na requisição');
      console.error('Erro na requisição:', error);
    }
  };

  return (
    <main className={styles.main}>
      <section className={styles.login}>
        <div className={styles.logoContainer}>
          <span className={`${styles.logoIcon} material-symbols-outlined`}>local_parking</span>
          <h1 className={styles.title}>ParkFlow</h1>
        </div>

        <p className={styles.subtitle}>Reserve sua vaga com antecedência</p>

        <form onSubmit={handleLogin} autoComplete="on">
          <div className={styles.formGroup}>
            <label htmlFor="id_email">Email:</label><br />
            <input
              type="email"
              id="id_email"
              className={styles.input}
              autoComplete="email"
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
              autoComplete="current-password"
              value={senha}
              onChange={(e) => setSenha(e.target.value)}
              required
              placeholder="********"
            />
          </div>

          <div className={styles.btn_login}>
            <button type="submit">Entrar</button>
          </div>

          <div className={styles.btn_cadastro}>
            <Link href="/cadastro">
              <button type="button">Criar conta</button>
            </Link>
          </div>
        </form>

        <p className={styles.hint}>
          Acesso de teste: admin@parkflow.com / 123456
        </p>
      </section>

      <div className={styles.btn_voltar}>
        <button onClick={() => router.push('/')}>
          <span className="material-symbols-outlined">arrow_back</span> Voltar
        </button>
      </div>
    </main>
  );
}
