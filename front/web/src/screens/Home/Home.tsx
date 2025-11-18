import React from "react";
import { useNavigate } from "react-router-dom";
import styles from "./Home.module.css"; // estilos separados em CSS

export default function Home() {
  const navigate = useNavigate();

  return (
    <div className={styles.container}>
      <img
        src={require("./../../assets/Balanca-da-justica.png")}
        alt="Logo"
        className={styles.logo}
      />

      <h1 className={styles.title}>Núcleo de Práticas Jurídicas</h1>
      <p className={styles.subtitle}>
        Atendimento jurídico gratuito e orientado à comunidade.
      </p>

      <button
        className={`${styles.button}`}
        onClick={() => navigate("/login")}
      >
        Fazer Login
      </button>

    </div>
  );
}
