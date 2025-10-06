import React, { useState, useEffect } from "react";
import axios from "axios";
import { useNavigate, useParams } from "react-router-dom";
import styles from "./Movimento.module.css";

const API_URL = process.env.REACT_APP_API;

interface Page<T> {
  content: T[];
  totalPages: number;
  totalElements: number;
  size: number;
  number: number;
}

interface Movimento {
  id: number;
  numeroDoProcesso: string;
  advogado: string;
  movimento: string;
  registro: string;
}

export default function Movimento() {
  const [movimentos, setMovimentos] = useState<Movimento[]>([]);
  const navigate = useNavigate();

  const params = useParams();
  const numeroDoProcesso = params.numeroDoProcesso || '';

  useEffect(() => {

    const fetchMovimentos = async () => {
      try {
        const response = await axios.get(`${API_URL}/movimentos/buscar/${numeroDoProcesso}`); 
        const pageData: Page<Movimento> = response.data;
        setMovimentos(pageData.content);

      } catch(error) {
        console.log("Error ao buscar movimentos: " +  error);
      }
    }

    fetchMovimentos();
  }, [numeroDoProcesso]);

  return (
    <div className={styles.container}>
      <button className={styles.backButton} onClick={() => navigate("/processos")}>
        ← Voltar
      </button>

      <h1 className={styles.title}>Movimento</h1>

      <div className={styles.divBtn}>
        <button className={styles.button} onClick={() => navigate(`/cadastrarMovimento/${numeroDoProcesso}`)}>
          Cadastrar Movimento
        </button>
      </div>

      <div className={styles.list}>
        {movimentos.length > 0 ? ( movimentos.map((item) => (
          <div key={item.id} className={styles.card}>
            <p className={styles.numero}><span>Nº Processo:</span> {item.numeroDoProcesso}</p>
            <p className={styles.assunto}><span>Nº Assunto:</span> {item.movimento}</p>
            <p className={styles.assunto}><span>Advogado:</span> {item.advogado}</p>
            <p className={styles.prazo}><span>{item.registro}</span></p>
          </div>
        ))) : (
          <div className={styles.emptyContainer}>
            <p className={styles.emptyText}>Nenhum Movimento foi cadastrado.</p>
          </div>
        )}
      </div>
    </div>
  );
}
