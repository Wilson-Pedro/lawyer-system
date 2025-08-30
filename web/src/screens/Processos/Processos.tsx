import React, { useState, useEffect } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import styles from "./Processos.module.css";

const API_URL = process.env.REACT_APP_API_URL;

type Processo = {
  id: string;
  numeroDoProcesso: string;
  assunto: string;
  prazoFinal: string;
  responsavel: string;
};

export default function Processos() {
  const [processos, setProcessos] = useState<Processo[]>([]);
  const navigate = useNavigate();

  useEffect(() => {
    const fetchProcessos = async () => {
      try {
        const response = await axios.get(`${API_URL}/processos/statusDoProcesso`);
        setProcessos(response.data);
      } catch (error) {
        console.error(error);
      }
    };
    fetchProcessos();
  }, []);

  return (
    <div className={styles.container}>
      <button className={styles.backButton} onClick={() => navigate(-1)}>
        ← Voltar
      </button>

      <h1 className={styles.title}>Processos em Andamento</h1>

      <div className={styles.list}>
        {processos.map((item) => (
          <div key={item.id} className={styles.card}>
            <p className={styles.numero}>Nº Processo: {item.numeroDoProcesso}</p>
            <p className={styles.assunto}>Assunto: {item.assunto}</p>
            <p className={styles.prazo}>Prazo: {item.prazoFinal}</p>
            <p className={styles.responsavel}>Responsável: {item.responsavel}</p>
          </div>
        ))}
      </div>
    </div>
  );
}
