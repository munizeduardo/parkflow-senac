'use client';

import styles from './Header.module.css';
import { useRouter, usePathname } from 'next/navigation';
import { useEffect, useState } from 'react';

/**
 * Cabeçalho da área autenticada: logo, navegação, nome do usuário e botão de sair.
 */
export default function Header({ links = [] }) {
  const router = useRouter();
  const pathname = usePathname();
  const [nome, setNome] = useState('');

  useEffect(() => {
    setNome(localStorage.getItem('parkflowUserNome') || '');
  }, []);

  const handleLogout = () => {
    localStorage.removeItem('parkflowUserId');
    localStorage.removeItem('parkflowUserNome');
    localStorage.removeItem('parkflowUserPerfil');
    router.push('/login');
  };

  return (
    <header className={styles.header}>
      <div className={styles.brand} onClick={() => router.push('/')}>
        <span className="material-symbols-outlined">local_parking</span>
        <h1>ParkFlow</h1>
      </div>

      <nav className={styles.nav}>
        {links.map((link) => (
          <button
            key={link.href}
            className={pathname === link.href ? styles.active : ''}
            onClick={() => router.push(link.href)}
          >
            {link.label}
          </button>
        ))}
      </nav>

      <div className={styles.userArea}>
        <span className={styles.userName}>{nome}</span>
        <button className={styles.logout} onClick={handleLogout}>
          <span className="material-symbols-outlined">logout</span> Sair
        </button>
      </div>
    </header>
  );
}
