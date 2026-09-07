import './globals.css';

/**
 * Metadados da aplicação.
 */
export const metadata = {
  title: "ParkFlow",
  description: "Sistema integrado de gestão e reserva de vagas de estacionamento",
};

/**
 * Layout raiz de toda a aplicação.
 */
export default function RootLayout({ children }) {
  return (
    <html lang="pt-br">
      <head>
        <link
          href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined"
          rel="stylesheet"
        />
      </head>
      <body>{children}</body>
    </html>
  );
}
